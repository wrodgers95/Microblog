package com.theironyard.charlotte;

import java.util.ArrayList;

public class User {

    String name;
    ArrayList<String> messages = new ArrayList<>();

    public User(String name) {
        this.name = name;
    }
}
