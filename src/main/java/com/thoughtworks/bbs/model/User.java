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

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public void setIsRegular(boolean isRegular){
        this.isRegular=isRegular;
    }
    public boolean getIsRegular(){
        return isRegular;
    }
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
