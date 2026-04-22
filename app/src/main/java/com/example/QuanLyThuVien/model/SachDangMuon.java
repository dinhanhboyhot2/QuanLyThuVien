package com.example.QuanLyThuVien.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SachDangMuon {
    private String sSoPhieuMuon;
    private String dNgayMuon;
    private String dNgayHenTra;
    private String sTenDauSach;
    private int iSoNgayConLai;
    private int iTrangThai; // 0: Bình thường (còn hạn), 1: Đã quá hạn

    public String getsSoPhieuMuon() { return sSoPhieuMuon; }
    public String getdNgayMuon() { return dNgayMuon; }
    public String getdNgayHenTra() { return dNgayHenTra; }
    public String getsTenDauSach() { return sTenDauSach; }
    public int getiSoNgayConLai() { return iSoNgayConLai; }
    public int getiTrangThai() { return iTrangThai; }

    // Logic tính toán số ngày còn lại và trạng thái
    public void xuLyThoiGian() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date hanTra = sdf.parse(this.dNgayHenTra);
            Date hienTai = new Date();

            long thoiGianChenhLech = hanTra.getTime() - hienTai.getTime();
            this.iSoNgayConLai = (int) (thoiGianChenhLech / (1000 * 60 * 60 * 24));

            if (this.iSoNgayConLai < 0) {
                this.iTrangThai = 1; // 1: Đã quá hạn
            } else {
                this.iTrangThai = 0; // 0: Bình thường (còn hạn)
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}