package com.example.lib;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_doc_gia")
public class DocGia {
    @Id
    @Column(name = "sMaDocGia") // ÉP CHÍNH XÁC TÊN CỘT LÀ iMaDG
    public String sMaDocGia;

    @Column(name = "sEmail") // ÉP CHÍNH XÁC TÊN CỘT LÀ sEmail
    public String sEmail;

//    @Column(name = "sMatKhau") // ÉP CHÍNH XÁC TÊN CỘT LÀ sMatKhau
//    public String sMatKhau;

    @Column(name = "sHoTen") // ÉP CHÍNH XÁC TÊN CỘT LÀ sHoTen
    public String sHoTen;
}