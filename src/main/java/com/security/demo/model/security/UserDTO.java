package com.security.demo.model.security;

public class UserDTO {
    String username;
    String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
