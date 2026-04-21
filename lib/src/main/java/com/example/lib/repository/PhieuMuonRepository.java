package com.example.lib.repository;

import com.example.lib.dto.ISachDangMuon;
import com.example.lib.model.PhieuMuon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PhieuMuonRepository extends JpaRepository<PhieuMuon, String> {

    @Query(value = "SELECT " +
            "pm.sSoPhieuMuon AS sSoPhieuMuon, " +
            "pm.dNgayMuon AS dNgayMuon, " +
            "pm.dNgayHenTra AS dNgayHenTra, " +
            "ds.sTenDauSach AS sTenDauSach " +
            "FROM tbl_phieu_muon pm " +
            "JOIN tbl_chi_tiet_phieu_muon ctpm ON pm.sSoPhieuMuon = ctpm.sSoPhieuMuon " +
            "JOIN tbl_cuon_sach cs ON ctpm.sMaVach = cs.sMaVach " +
            "JOIN tbl_dau_sach ds ON cs.sMaDauSach = ds.sMaDauSach " +
            "WHERE pm.sMaDocGia = :sMaDocGia " +
            "AND NOT EXISTS (" +
            "    SELECT 1 FROM tbl_phieu_tra pt " +
            "    JOIN tbl_chi_tiet_phieu_tra ctpt ON pt.sSoPhieuTra = ctpt.sSoPhieuTra " +
            "    WHERE pt.sSoPhieuMuon = pm.sSoPhieuMuon " +
            "    AND ctpt.sMaVach = ctpm.sMaVach" +
            ")",
            nativeQuery = true)
    List<ISachDangMuon> layDanhSachSachDangMuon(@Param("sMaDocGia") String sMaDocGia);
}