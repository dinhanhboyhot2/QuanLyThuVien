package com.example.QuanLyThuVien.model;

import com.google.gson.annotations.SerializedName;

public class DauSach {

    @SerializedName("sMaDauSach")
    private String sMaDauSach;

    @SerializedName("sTenDauSach")
    private String sTenDauSach;

    @SerializedName("sMaNhaXuatBan")
    private String sMaNhaXuatBan;

    @SerializedName("iNamXuatBan")
    private int iNamXuatBan;
    @SerializedName("sAnhBia")
    private String sAnhBia;
    @SerializedName("dNgayTao")
    private String dNgayTao;

    public void setdNgayTao(String sdNgayTao) {
        this.dNgayTao = sdNgayTao;
    }

    public String getdNgayTao() {
        return dNgayTao;
    }

    public String getsAnhBia() {
        return sAnhBia;
    }

    public void setsAnhBia(String sAnhBia) {
        this.sAnhBia = sAnhBia;
    }

    public String getsMaDauSach() {
        return sMaDauSach;
    }

    public void setsMaDauSach(String sMaDauSach) {
        this.sMaDauSach = sMaDauSach;
    }

    public String getsTenDauSach() {
        return sTenDauSach;
    }

    public void setsTenDauSach(String sTenDauSach) {
        this.sTenDauSach = sTenDauSach;
    }

    public String getsMaNhaXuatBan() {
        return sMaNhaXuatBan;
    }

    public void setsMaNhaXuatBan(String sMaNhaXuatBan) {
        this.sMaNhaXuatBan = sMaNhaXuatBan;
    }

    public int getiNamXuatBan() {
        return iNamXuatBan;
    }

    public void setiNamXuatBan(int iNamXuatBan) {
        this.iNamXuatBan = iNamXuatBan;
    }
}