package com.theironyard.charlotte;

public class User {

    String name;
    String password;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
