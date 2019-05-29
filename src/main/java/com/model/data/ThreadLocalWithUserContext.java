package com.model.data;

import lombok.extern.slf4j.Slf4j;
import com.sap.security.um.user.User;

@Slf4j
public class ThreadLocalWithUserContext {

    private static ThreadLocal<User> userContext
            = new ThreadLocal<>();

    public static void setUser(User user)
    {
        userContext.set(user);
    }
    public static User getUser() {
        return userContext.get();
    }
}