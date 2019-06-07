package com.service;

import com.config.EcmServiceConfig;
import com.sap.ecm.api.EcmService;
import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class DocumentServiceTest {


//    @TestConfiguration
//    static class EmployeeServiceImplTestContextConfiguration {
//
//        @Bean
//        public EcmService ecmService() throws NamingException {
//            InitialContext ctx = new InitialContext();
//            return  (EcmService) ctx.lookup(EcmServiceConfig.ECM_LOOKUP_NAME);
//        }
//    }
//
//
//    @Autowired
//    private  ServletContext servletContext;


    @MockBean
    private DocumentService documentService;

//    @MockBean
//    private  EcmService ecmService;

    @Test
    public void getRootTest()
    {
     //   System.out.println(documentService.getRoot().isEmpty());
        System.out.println("!!");
    }
}