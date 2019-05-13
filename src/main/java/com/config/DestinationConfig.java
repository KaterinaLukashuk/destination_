package com.config;

import com.destination.DestinationFactory;
import com.sap.ea.eacp.okhttp.destinationclient.OkHttpDestination;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DestinationConfig {

    @Bean
    public OkHttpDestination okHttpDestination() {
        DestinationFactory destinationFactory = new DestinationFactory();
        return destinationFactory.provide("try");
    }

}
