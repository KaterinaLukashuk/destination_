package com.service;

import com.sap.ecm.api.EcmService;
import com.sap.ecm.api.RepositoryOptions;
import lombok.extern.slf4j.Slf4j;
import org.apache.chemistry.opencmis.client.api.*;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class DocumentService {
    public static final String REPOSITORY_NAME = "myrepo";
    public static final String REPOSITORY_KEY = "0123456789";

    private final ServletContext servletContext;

    private final
    EcmService ecmService;

    public DocumentService(EcmService ecmService, ServletContext servletContext) {
        this.ecmService = ecmService;
        this.servletContext = servletContext;
    }

    private Session connectRepo() {
        try {
            return ecmService.connect(REPOSITORY_NAME, REPOSITORY_KEY);
        } catch (CmisObjectNotFoundException e) {
            final RepositoryOptions options = new RepositoryOptions();
            options.setUniqueName(REPOSITORY_NAME);
            options.setRepositoryKey(REPOSITORY_KEY);
            options.setVisibility(RepositoryOptions.Visibility.PROTECTED);
            ecmService.createRepository(options);
            return ecmService.connect(REPOSITORY_NAME, REPOSITORY_KEY);
        }
    }

    public void createFolder(String folderName) {
        Session session = connectRepo();
        Folder root = session.getRootFolder();
        Map<String, String> newFolderProps = new HashMap<String, String>();
        newFolderProps.put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder");
        newFolderProps.put(PropertyIds.NAME, folderName);
        root.createFolder(newFolderProps);
    }

    public Document createDocument(String docName, byte[] content) throws IOException {
        Session session = connectRepo();
        Folder root = session.getRootFolder();
        Map<String, String> properties = new HashMap<String, String>();
        properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
        properties.put(PropertyIds.NAME, docName);

        InputStream stream = new ByteArrayInputStream(content);
        ObjectFactory factory = session.getObjectFactory();
        ContentStream contentStream = factory.createContentStream(docName, content.length
                , servletContext.getMimeType(docName), stream);
        return root.createDocument(properties, contentStream, VersioningState.MAJOR);
    }


    public ItemIterable<CmisObject> getChildren() {
        return connectRepo().getRootFolder().getChildren();
    }


    public ResponseEntity downloadDoc(String docId) {
        Document document = (Document) connectRepo().getObject(docId);
        InputStreamResource streamResource = new InputStreamResource(document.getContentStream().getStream());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + document.getName())
                .contentType(MediaType.parseMediaType(servletContext.getMimeType(document.getName())))
                .body(streamResource);
    }

    public void deleteDoc(String docId) {
        connectRepo().getObject(docId).delete();
    }
}
