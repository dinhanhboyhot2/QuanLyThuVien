package com.example.lib.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_tai_khoan")
public class TaiKhoan {
    @Id
    @Column(name = "sTenDangNhap")
    private String sTenDangNhap;

    @Column(name = "sMatKhauMaHoa")
    private String sMatKhauMaHoa;

    @Column(name = "iTrangThai")
    private int iTrangThai;

    @Column(name = "sMaVaiTro")
    private String sMaVaiTro;

    public void setsTenDangNhap(String sTenDangNhap) {
        this.sTenDangNhap = sTenDangNhap;
    }

    public void setsMatKhauMaHoa(String sMatKhauMaHoa) {
        this.sMatKhauMaHoa = sMatKhauMaHoa;
    }

    public void setiTrangThai(int iTrangThai) {
        this.iTrangThai = iTrangThai;
    }

    public void setsMaVaiTro(String sMaVaiTro) {
        this.sMaVaiTro = sMaVaiTro;
    }

    public String getsTenDangNhap() {
        return sTenDangNhap;
    }

    public String getsMatKhauMaHoa() {
        return sMatKhauMaHoa;
    }

    public int getiTrangThai() {
        return iTrangThai;
    }

    public String getsMaVaiTro() {
        return sMaVaiTro;
    }
}
