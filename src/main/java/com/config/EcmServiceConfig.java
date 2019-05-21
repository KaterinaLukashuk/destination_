package com.config;

import com.sap.ecm.api.EcmService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.naming.InitialContext;
import javax.naming.NamingException;

@Configuration
public class EcmServiceConfig {
    public static final String ECM_LOOKUP_NAME = "java:comp/env/EcmService";

    @Bean
    public EcmService ecmService() throws NamingException {
        InitialContext ctx = new InitialContext();
        return  (EcmService) ctx.lookup(ECM_LOOKUP_NAME);
    }
}
