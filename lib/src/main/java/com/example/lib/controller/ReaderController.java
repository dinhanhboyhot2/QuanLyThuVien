package com.example.lib.controller;

import com.example.lib.DocGia;
import com.example.lib.repository.DocGiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/reader")
@CrossOrigin(origins = "*")
public class ReaderController {
    @Autowired
    private DocGiaRepository repository;

    // Lấy thông tin để đổ vào Android khi vừa mở trang
    @GetMapping("/{id}")
    public ResponseEntity<DocGia> getProfile(@PathVariable String id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Cập nhật an toàn: Không làm mất Họ tên, Mã lớp, Ngày sinh
    @PostMapping("/update")
    public ResponseEntity<?> updateProfile(@RequestBody DocGia request) {
        return repository.findById(request.sMaDocGia)
            .map(target -> {
                // CHỈ CẬP NHẬT 4 trường này để tránh lỗi NOT NULL ở SQL Server
                target.sEmail = request.sEmail;
                target.sDienThoai = request.sDienThoai;
                target.sDiaChi1 = request.sDiaChi1;
                target.sDiaChi2 = request.sDiaChi2;
                target.dNgayCapNhat = LocalDateTime.now();

                repository.save(target);
                // Trả về JSON để Android không bị lỗi Parse
                return ResponseEntity.ok(Map.of("message", "success"));
            })
            .orElse(ResponseEntity.status(404).body(Map.of("message", "User not found")));
    }
}