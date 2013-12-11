package com.thoughtworks.bbs.model;

public class User {
    private Long id;
    private String userName;
    private boolean enabled;
    private String passwordHash;
    private boolean isRegular;

    public Long getId() {
        return id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public User setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }
    public User setIsRegular(boolean isRegular){
        this.isRegular=isRegular;
        return this;
    }
    public boolean getIsRegular(){
        return isRegular;
    }
    public String getPasswordHash() {
        return passwordHash;
    }

    public User setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
        return this;
    }
}
