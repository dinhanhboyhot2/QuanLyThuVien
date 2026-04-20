package com.example.lib.repository;

import com.example.lib.model.DauSach;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<DauSach, String> {

    // Tìm kiếm theo từ khóa (LIKE)
    String SEARCH_QUERY = "SELECT * FROM dbo.tbl_dau_sach WHERE sTenDauSach LIKE %?1% OR sMaNhaXuatBan LIKE %?1%";
    @Query(value = SEARCH_QUERY, nativeQuery = true)
    List<DauSach> getBooksByKeyword(String sKeyword);

    // Lấy toàn bộ danh sách
    List<DauSach> findAll();
}
