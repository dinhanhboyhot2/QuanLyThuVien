package com.example.lib.dto;

import java.util.Date;

public interface ISachDangMuon {
    String getsSoPhieuMuon();
    Date getdNgayMuon();
    Date getdNgayHenTra();
    String getsTenDauSach();
    Integer getiTrangThai(); // 0: Bình thường (còn hạn), 1: Đã quá hạn
}