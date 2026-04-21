package com.example.lib.controller;

import com.example.lib.dto.ISachDangMuon;
import com.example.lib.repository.PhieuMuonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/phieumuon")
public class PhieuMuonApiController {

    @Autowired
    private PhieuMuonRepository phieuMuonRepository;

    @GetMapping("/dangmuon/{sMaDocGia}")
    public ResponseEntity<List<ISachDangMuon>> laySachDangMuon(@PathVariable("sMaDocGia") String sMaDocGia) {
        List<ISachDangMuon> danhSach = phieuMuonRepository.layDanhSachSachDangMuon(sMaDocGia);
        return ResponseEntity.ok(danhSach);
    }
}