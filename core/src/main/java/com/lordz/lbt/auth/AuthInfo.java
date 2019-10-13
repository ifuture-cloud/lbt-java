package com.lordz.lbt.auth;

public class AuthInfo {
    private String id;//用户ID
    private String username;//登录名，唯一
    private String name;

    public AuthInfo(){}

    public AuthInfo(String username, String id, String name){
        this.username = username;
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
