package com.tmall.springboot.pojo;

public class Users {
    private int id;
    private String name;
    private String password;
    private String salt;
    private String headurl;
    private String role;

    public Users(){};

    public Users(String name){
        this.name = name;
        this.password = "";
        this.salt ="";
        this.headurl = "";
        this.role = "user";
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHeadurl() {
        return headurl;
    }

    public void setHeadurl(String headurl) {
        this.headurl = headurl;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
