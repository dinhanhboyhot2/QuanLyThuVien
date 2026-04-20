package com.example.QuanLyThuVien.model;

public class LoginResponse {
    private String sTenDangNhap;
    private String sMaVaiTro; // "VT_ADMIN", "VT_THUTHU", "VT_DOCGIA"
    private String sMessage;

    // Getters và Setters
    public String getMaVaiTro() { return sMaVaiTro; }
}
