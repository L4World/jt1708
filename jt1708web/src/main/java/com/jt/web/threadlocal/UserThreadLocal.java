package com.jt.web.threadlocal;

import com.jt.web.pojo.User;

public class UserThreadLocal {
    private static ThreadLocal<User> CURTHREAD = new ThreadLocal<>();

    public static User get() {
        return CURTHREAD.get();
    }

    public static void set(User user) {
        CURTHREAD.set(user);
    }

    public static Long getUserId() {
        User user = CURTHREAD.get();
        if (user != null) {
            return user.getId();
        } else {
            return null;
        }
    }
}
