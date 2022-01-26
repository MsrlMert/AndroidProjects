package com.mertmsrl.mef_idp_project.Models;

public class User {
    private String userId, userName, userEmail, userPassword, userCourseName;

    public User() {
    }

    public User(String userId, String userName, String userEmail, String userPassword, String userCourseName) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userCourseName = userCourseName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserCourseName() {
        return userCourseName;
    }

    public void setUserCourseName(String userCourseName) {
        this.userCourseName = userCourseName;
    }
}
