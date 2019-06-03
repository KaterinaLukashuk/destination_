package com.filter;


import com.model.data.ThreadLocalWithUserContext;
import com.sap.security.um.service.UserManagementAccessor;
import com.sap.security.um.user.PersistenceException;
import com.sap.security.um.user.User;
import com.sap.security.um.user.UserProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Slf4j
public class SetUserFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        try {
            UserProvider users = UserManagementAccessor.getUserProvider();
            User user = users.getCurrentUser();
            log.info("this is user set filter: " + user.getName() + " " + req.getRemoteUser());
            ThreadLocalWithUserContext.setUser(user);

        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        chain.doFilter(request, response);
    }
}
