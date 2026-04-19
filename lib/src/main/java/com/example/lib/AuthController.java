package com.example.lib;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

interface DocGiaRepository extends JpaRepository<DocGia, Integer> {
    @Query(value = "SELECT * FROM tblDocGia WHERE sEmail = ?1 AND sMatKhau = ?2", nativeQuery = true)
    Optional<DocGia> login(String email, String pass);
}

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired DocGiaRepository repo;
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        return repo.login(email, password).map(u -> "Chào " + u.sHoTen).orElse("Lỗi!");
    }
}