package com.example.lib;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

interface DocGiaRepository extends JpaRepository<DocGia, String> {
    @Query(value = "SELECT * FROM dbo.tbl_doc_gia WHERE sEmail = ?1 ", nativeQuery = true)
    Optional<DocGia> login(String email);
}

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired DocGiaRepository repo;
    @PostMapping("/login")
    public String login(@RequestParam String email/*, @RequestParam String password*/) {
        return repo.login(email).map(u -> "Chào " + u.sHoTen).orElse("Lỗi!");
    }
}