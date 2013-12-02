package com.thoughtworks.bbs.util;

import com.thoughtworks.bbs.model.User;

public class UserBuilder {
    private User user;

    public UserBuilder() {
        user = new User();
    }

    public UserBuilder userName(String username) {
        user.setUserName(username);
        return this;
    }

    public UserBuilder password(String password) {
        user.setPasswordHash(password);
        return this;
    }

    public UserBuilder enable(boolean enabled) {
        user.setEnabled(enabled);
        return this;
    }

    public UserBuilder id(Long id) {
        user.setId(id);
        return this;
    }

    public User build() {
        return user;
    }
}
