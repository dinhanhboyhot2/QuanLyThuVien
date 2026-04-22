package com.example.lib;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_doc_gia")
public class DocGia {
    @Id
    @Column(name = "sMaDocGia")
    public String sMaDocGia;

    @Column(name = "sHoTen")
    public String sHoTen;

    @Column(name = "iGioiTinh")
    public Integer iGioiTinh;

    @Column(name = "dNgaySinh")
    public LocalDate dNgaySinh;

    @Column(name = "sEmail")
    public String sEmail;

    @Column(name = "sDienThoai")
    public String sDienThoai;

    @Column(name = "sDiaChi1") // Thường trú
    public String sDiaChi1;

    @Column(name = "sDiaChi2") // Tạm trú
    public String sDiaChi2;

    @Column(name = "sMaLop")
    public String sMaLop;

    @Column(name = "dNgayTao", updatable = false)
    public LocalDateTime dNgayTao;

    @Column(name = "dNgayCapNhat")
    public LocalDateTime dNgayCapNhat;
}