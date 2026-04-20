package com.example.lib.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class LoginResponse {
    private String status; // "success" hoặc "error"
    private String sMaVaiTro;

    public LoginResponse(String status, String sMaVaiTro) {
        this.status = status;
        this.sMaVaiTro = sMaVaiTro;
    }

    public String getStatus() {
        return status;
    }

    public String getsMaVaiTro() {
        return sMaVaiTro;
    }
}
