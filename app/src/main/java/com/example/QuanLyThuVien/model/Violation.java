package com.example.QuanLyThuVien.model;

import com.google.gson.annotations.SerializedName;

public class Violation {
    @SerializedName("sMaDocGia")
    private String sMaDocGia;
    @SerializedName("sHoTen")
    private String sHoTen;
    @SerializedName("sNoiDungViPham")
    private String sNoiDungViPham;
    @SerializedName("dNgayViPham")
    private String dNgayViPham;
    @SerializedName("fSoTienPhat")
    private double fSoTienPhat;
    @SerializedName("iLoaiViPham")
    private int iLoaiViPham; // 0: Trễ hạn, 1: Hỏng/Mất

    public Violation(String sHoTen, String sNoiDungViPham, String dNgayViPham, double fSoTienPhat, int iLoaiViPham) {
        this.sHoTen = sHoTen;
        this.sNoiDungViPham = sNoiDungViPham;
        this.dNgayViPham = dNgayViPham;
        this.fSoTienPhat = fSoTienPhat;
        this.iLoaiViPham = iLoaiViPham;
    }

    public String getsMaDocGia() { return sMaDocGia; }
    public String getsHoTen() { return sHoTen; }
    public String getsNoiDungViPham() { return sNoiDungViPham; }
    public String getdNgayViPham() { return dNgayViPham; }
    public double getfSoTienPhat() { return fSoTienPhat; }
    public int getiLoaiViPham() { return iLoaiViPham; }
}
