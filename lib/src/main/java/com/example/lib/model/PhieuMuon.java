package com.example.lib.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "tbl_phieu_muon")
public class PhieuMuon {

    @Id
    private String sSoPhieuMuon;

    private String sMaDocGia;
    private Date dNgayMuon;
    private Date dNgayHenTra;

    // 0: Đang mượn, 1: Đã hoàn tất trả sách
    private Integer iTrangThaiPhieu;

    // Getters và Setters
    public String getsSoPhieuMuon() {
        return sSoPhieuMuon;
    }

    public void setsSoPhieuMuon(String sSoPhieuMuon) {
        this.sSoPhieuMuon = sSoPhieuMuon;
    }

    public String getsMaDocGia() {
        return sMaDocGia;
    }

    public void setsMaDocGia(String sMaDocGia) {
        this.sMaDocGia = sMaDocGia;
    }

    public Date getdNgayMuon() {
        return dNgayMuon;
    }

    public void setdNgayMuon(Date dNgayMuon) {
        this.dNgayMuon = dNgayMuon;
    }

    public Date getdNgayHenTra() {
        return dNgayHenTra;
    }

    public void setdNgayHenTra(Date dNgayHenTra) {
        this.dNgayHenTra = dNgayHenTra;
    }

    public Integer getiTrangThaiPhieu() {
        return iTrangThaiPhieu;
    }

    public void setiTrangThaiPhieu(Integer iTrangThaiPhieu) {
        this.iTrangThaiPhieu = iTrangThaiPhieu;
    }
}