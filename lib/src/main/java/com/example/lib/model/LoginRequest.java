package com.example.lib.model;

import lombok.Data;

@Data // Tự động tạo Getter, Setter nếu dùng thư viện Lombok
public class LoginRequest {
    private String username;
    private String password;

    public LoginRequest() {}
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
