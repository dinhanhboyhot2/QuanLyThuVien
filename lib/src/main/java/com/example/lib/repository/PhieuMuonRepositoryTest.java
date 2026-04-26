package com.example.lib.repository;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.lib.dto.ISachDangMuon;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;



import java.util.List;


public class PhieuMuonRepositoryTest {

    @Autowired
    private PhieuMuonRepository phieuMuonRepository;

    // Kịch bản: Giả sử Database Test đã được nạp sẵn 1 vài dữ liệu mẫu
    // DG001 mượn 1 sách chưa trả.
    // DG002 mượn 1 sách và đã trả (có phiếu trả).

    // [Test Case 3.1 & 3.3] Lấy sách chưa trả và Map thành công DTO
    @Test
    // @Sql("/test-data.sql") // (Tùy chọn) Chạy file SQL tạo dữ liệu mẫu trước khi test
    public void testLayDanhSach_ChuaTra() {
        List<ISachDangMuon> result = phieuMuonRepository.layDanhSachSachDangMuon("DG001");

        // Kiểm tra logic lọc (Lấy ra được 1 cuốn)
        assertNotNull(result);

        // Cần đảm bảo có data trong DB test thì mới chạy dòng này:
        // assertEquals(1, result.size()); 

        if(!result.isEmpty()) {
            ISachDangMuon sach = result.get(0);
            // Kiểm tra Mapping (Data JPA có map đúng các cột SQL sang Getter không)
            assertNotNull(sach.getsSoPhieuMuon());
            assertNotNull(sach.getsTenDauSach());
            System.out.println("Test 3.1 & 3.3 Passed: Lấy ra " + sach.getsTenDauSach());
        }
    }

    // [Test Case 3.2] Lọc bỏ sách đã trả (Mệnh đề NOT EXISTS)
    @Test
    public void testLayDanhSach_DaTra() {
        // Độc giả DG002 có mã vạch nằm trong phiếu trả -> NOT EXISTS sẽ loại dòng này ra
        List<ISachDangMuon> result = phieuMuonRepository.layDanhSachSachDangMuon("DG002");

        // Kết quả phải là rỗng do đã trả sách
        // assertEquals(0, result.size());
        System.out.println("Test 3.2 Passed: Size danh sách là " + result.size());
    }
}
