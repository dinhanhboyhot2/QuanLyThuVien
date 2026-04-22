package com.example.lib.repository;

import com.example.lib.DocGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocGiaRepository extends JpaRepository<DocGia, String> {
}