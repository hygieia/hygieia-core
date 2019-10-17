package com.capitalone.dashboard.client;

public class RestUserInfo {
    private String userId;
    private String passCode;
    private String token;

    public RestUserInfo(String userId, String passCode) {
        this.userId = userId;
        this.passCode = passCode;
    }

    public RestUserInfo(String userId, String passCode,String token) {
        this.userId = userId;
        this.passCode = passCode;
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassCode() {
        return passCode;
    }

    public void setPassCode(String passCode) {
        this.passCode = passCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFormattedString() {
        return userId.trim() + ":" + passCode.trim();
    }
}

