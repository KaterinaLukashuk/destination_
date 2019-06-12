package com.service;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
public class DocumentServiceTest {


    @MockBean
    private DocumentService documentService;

//
//    @Before
//    public void setUp() throws NamingException {
//        InitialContext ctx = new InitialContext();
//        EcmService ecmService =  (EcmService) ctx.lookup(EcmServiceConfig.ECM_LOOKUP_NAME);
//        Session session;
//        try {
//            session = ecmService.connect(DocumentService.REPOSITORY_NAME, DocumentService.REPOSITORY_KEY);
//        } catch (CmisObjectNotFoundException e) {
//            final RepositoryOptions options = new RepositoryOptions();
//            options.setUniqueName(DocumentService.REPOSITORY_NAME);
//            options.setRepositoryKey(DocumentService.REPOSITORY_KEY);
//            options.setVisibility(RepositoryOptions.Visibility.PROTECTED);
//            ecmService.createRepository(options);
//            session = ecmService.connect(DocumentService.REPOSITORY_NAME, DocumentService.REPOSITORY_KEY);
//        }
//        Folder root = session.getRootFolder();
//        Map<String, String> newFolderProps = new HashMap<String, String>();
//        newFolderProps.put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder");
//        newFolderProps.put(PropertyIds.NAME, "testname");
//        root.createFolder(newFolderProps);
//    }

}