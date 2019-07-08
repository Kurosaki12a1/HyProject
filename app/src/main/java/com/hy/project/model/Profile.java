package com.hy.project.model;

public class Profile {
    private String userName;
    private String userId;
    private String avatarURL;
    private String email;

    public Profile(String userId, String userName, String avatarURL, String email) {
        this.userId = userId;
        this.userName = userName;
        this.avatarURL = avatarURL;
        this.email = email;
    }

    public Profile(){

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
