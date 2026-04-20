package com.example.lib.controller;

import com.example.lib.model.DauSach;
import com.example.lib.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookApiController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/search")
    public ResponseEntity<List<DauSach>> searchBooks(@RequestParam(value = "q", required = false) String sKeyword) {
        List<DauSach> listSach;

        // Kiểm tra nếu từ khóa null hoặc rỗng
        if (sKeyword == null || sKeyword.trim().isEmpty()) {
            // Trường hợp không có điều kiện -> Trả về toàn bộ danh sách
            listSach = bookRepository.findAll();
        } else {
            // Trường hợp có từ khóa -> Tìm kiếm theo tên hoặc mã NXB
            listSach = bookRepository.getBooksByKeyword(sKeyword.trim());
        }

        // Trả về danh sách dưới dạng JSON
        if (listSach.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listSach);
    }

    @GetMapping("/all")
    public ResponseEntity<List<DauSach>> getAllBooks() {
        return ResponseEntity.ok(bookRepository.findAll());
    }
}
