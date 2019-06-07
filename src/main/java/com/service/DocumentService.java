package com.service;

import com.model.data.ThreadLocalWithUserContext;
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
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class DocumentService {
    public static final String REPOSITORY_NAME = "myrepo";
    public static final String REPOSITORY_KEY = "0123456789";

    private final ServletContext servletContext;

    private final EcmService ecmService;

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

    private void createFolder(String folderName) {
        Session session = connectRepo();
        Folder root = session.getRootFolder();
        Map<String, String> newFolderProps = new HashMap<String, String>();
        newFolderProps.put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder");
        newFolderProps.put(PropertyIds.NAME, folderName);
        root.createFolder(newFolderProps);
    }

    public Document createDocument(String docName, byte[] content, String username) {
        Session session = connectRepo();
        Folder root = getUsersFolder(username);
        Map<String, String> properties = new HashMap<String, String>();
        properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
        properties.put(PropertyIds.NAME, docName);

        InputStream stream = new ByteArrayInputStream(content);
        ObjectFactory factory = session.getObjectFactory();
        ContentStream contentStream = factory.createContentStream(docName, content.length
                , servletContext.getMimeType(docName), stream);
        return root.createDocument(properties, contentStream, VersioningState.MAJOR);
    }


    public ItemIterable<CmisObject> getUsersDocuments(Folder folder) {
        return folder.getChildren();
    }


    public List<CmisObject> getAdminDocs() {
        return StreamSupport
                .stream(connectRepo().getRootFolder().getChildren().spliterator(), false)
                .filter(ob -> ob instanceof Folder)
                .flatMap(ob -> StreamSupport.stream(((Folder) ob).getChildren().spliterator(), false))
                .collect(Collectors.toList());
    }

    public List<Folder> getRoot(){
        List<Folder> folders = new ArrayList<>();
        folders.add(connectRepo().getRootFolder());
        return folders;
    }

    public Folder getUsersFolder(String username) {
        try {
            return (Folder) connectRepo().getObjectByPath("/" + username);
        } catch (CmisObjectNotFoundException e) {
            createFolder(ThreadLocalWithUserContext.getUser().getName());
        }
        return (Folder) connectRepo().getObjectByPath("/" + username);
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
        try {
            connectRepo().getObject(docId).delete();
        } catch (CmisObjectNotFoundException e) {
            log.error(e.getMessage());
        }
    }
}
