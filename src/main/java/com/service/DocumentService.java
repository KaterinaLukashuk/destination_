package com.service;

import com.sap.ecm.api.EcmService;
import com.sap.ecm.api.RepositoryOptions;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ObjectFactory;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Service
public class DocumentService {
    public static final String REPOSITORY_NAME = "myrepo";
    public static final String REPOSITORY_KEY = "0123456789";


    @Autowired
    EcmService ecmService;

    public Session connectRepo() {
        try {
            ecmService.connect(REPOSITORY_NAME, REPOSITORY_KEY);
        } catch (CmisObjectNotFoundException e) {
            final RepositoryOptions options = new RepositoryOptions();
            options.setUniqueName(REPOSITORY_NAME);
            options.setRepositoryKey(REPOSITORY_KEY);
            options.setVisibility(RepositoryOptions.Visibility.PROTECTED);
            ecmService.createRepository(options);
        }
        return ecmService.connect(REPOSITORY_NAME, REPOSITORY_KEY);
    }
    public void createDocument() throws UnsupportedEncodingException {
        Session session = connectRepo();
        Folder root = session.getRootFolder();
        Map<String, String> newFolderProps = new HashMap<String, String>();
        newFolderProps.put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder");
        newFolderProps.put(PropertyIds.NAME, "MyFirstFolder");
        root.createFolder(newFolderProps);
    }

}
