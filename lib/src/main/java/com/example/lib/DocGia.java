package com.example.lib;

import jakarta.persistence.*;

@Entity
@Table(name = "tblDocGia")
public class DocGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iMaDG") // ÉP CHÍNH XÁC TÊN CỘT LÀ iMaDG
    public Integer iMaDG;

    @Column(name = "sEmail") // ÉP CHÍNH XÁC TÊN CỘT LÀ sEmail
    public String sEmail;

    @Column(name = "sMatKhau") // ÉP CHÍNH XÁC TÊN CỘT LÀ sMatKhau
    public String sMatKhau;

    @Column(name = "sHoTen") // ÉP CHÍNH XÁC TÊN CỘT LÀ sHoTen
    public String sHoTen;
}