package com.config;

import com.filters.LogFilter;
import com.filters.SetUserFilter;
import com.filters.TestFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final MyBasicAuthenticationEntryPoint authenticationEntryPoint;

    public WebSecurityConfig(MyBasicAuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
    }


    @Override
    protected void configure(HttpSecurity http) {
        http.addFilterAfter(new TestFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new SetUserFilter(), TestFilter.class)
                .addFilterAfter(new LogFilter(), SetUserFilter.class);
    }

}
