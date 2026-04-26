package com.example.lib.repository;

// 1. THÊM IMPORT CONTROLLER VÀO ĐÂY
import com.example.lib.controller.PhieuMuonApiController;
import com.example.lib.dto.ISachDangMuon;
import com.example.lib.repository.PhieuMuonRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// 2. SỬA DÒNG NÀY: Truyền đúng PhieuMuonApiController.class
@WebMvcTest(PhieuMuonApiController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PhieuMuonRepositoryTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PhieuMuonRepository phieuMuonRepository;

    // --- TEST CASE 2.1: LẤY SÁCH THÀNH CÔNG ---
    @Test
    public void testLaySachDangMuon_Hople_CoDuLieu() throws Exception {
        ISachDangMuon mockSach = new ISachDangMuon() {
            @Override public String getsSoPhieuMuon() { return "PM-999"; }
            @Override public Date getdNgayMuon() { return new Date(); }
            @Override public Date getdNgayHenTra() { return new Date(); }
            @Override public String getsTenDauSach() { return "Lập trình Spring Boot"; }
            @Override public Integer getiTrangThai() { return 0; }
        };

        when(phieuMuonRepository.layDanhSachSachDangMuon("DG001"))
                .thenReturn(Arrays.asList(mockSach));

        mockMvc.perform(get("/api/phieumuon/dangmuon/DG001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].sSoPhieuMuon").value("PM-999"))
                .andExpect(jsonPath("$[0].sTenDauSach").value("Lập trình Spring Boot"));

        System.out.println("Test 2.1 Passed - Dữ liệu JSON trả về chính xác!");
    }

    // --- TEST CASE 2.2: ĐỘC GIẢ KHÔNG MƯỢN SÁCH HOẶC KHÔNG TỒN TẠI ---
    @Test
    public void testLaySachDangMuon_KhongCoSach_MangRong() throws Exception {
        when(phieuMuonRepository.layDanhSachSachDangMuon("DG-RONG"))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/phieumuon/dangmuon/DG-RONG"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));

        System.out.println("Test 2.2 Passed - Đã xử lý mảng rỗng an toàn!");
    }
}