package com.example.QuanLyThuVien.model;

import com.google.gson.annotations.SerializedName;

public class DocGia {
    @SerializedName("sMaDocGia") public String sMaDocGia;
    @SerializedName("sHoTen") public String sHoTen;
    @SerializedName("sEmail") public String sEmail;
    @SerializedName("sDienThoai") public String sDienThoai;
    @SerializedName("sDiaChi1") public String sDiaChi1;
    @SerializedName("sDiaChi2") public String sDiaChi2;
    
    // THÊM 3 TRƯỜNG NÀY ĐỂ HẾT LỖI ĐỎ
    @SerializedName("iGioiTinh") public Integer iGioiTinh;
    @SerializedName("dNgaySinh") public Object dNgaySinh; 
    @SerializedName("dNgayCapNhat") public Object dNgayCapNhat;
}