package com.example.lib.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_dau_sach")
public class DauSach {
    @Id
    private String sMaDauSach;
    private String sTenDauSach;
    private String sMaNhaXuatBan;
    private int iNamXuatBan;
    private LocalDateTime dNgayTao;
    private LocalDateTime dNgayCapNhat;

    public String getsMaDauSach() {
        return sMaDauSach;
    }

    public String getsTenDauSach() {
        return sTenDauSach;
    }

    public String getsMaNhaXuatBan() {
        return sMaNhaXuatBan;
    }

    public int getiNamXuatBan() {
        return iNamXuatBan;
    }

    public LocalDateTime getdNgayTao() {
        return dNgayTao;
    }

    public LocalDateTime getdNgayCapNhat() {
        return dNgayCapNhat;
    }

    public void setsMaDauSach(String sMaDauSach) {
        this.sMaDauSach = sMaDauSach;
    }

    public void setsTenDauSach(String sTenDauSach) {
        this.sTenDauSach = sTenDauSach;
    }

    public void setsMaNhaXuatBan(String sMaNhaXuatBan) {
        this.sMaNhaXuatBan = sMaNhaXuatBan;
    }

    public void setiNamXuatBan(int iNamXuatBan) {
        this.iNamXuatBan = iNamXuatBan;
    }

    public void setdNgayTao(LocalDateTime dNgayTao) {
        this.dNgayTao = dNgayTao;
    }

    public void setdNgayCapNhat(LocalDateTime dNgayCapNhat) {
        this.dNgayCapNhat = dNgayCapNhat;
    }
}
