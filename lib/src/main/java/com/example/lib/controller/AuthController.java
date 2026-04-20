package com.example.lib.controller;

import com.example.lib.repository.TaiKhoanRepository;
import com.example.lib.model.LoginRequest;
import com.example.lib.model.LoginResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private TaiKhoanRepository repository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return repository.findById(request.getUsername())
                .map(user -> {
                    // Kiểm tra mật khẩu và trạng thái hoạt động (iTrangThai = 1)
                    if (request.getPassword().equals(user.getsMatKhauMaHoa())){
                        if (user.getiTrangThai() == 1) {
                            return ResponseEntity.ok(new LoginResponse("success", user.getsMaVaiTro()));
                        }
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Tài khoản bị khóa");
                    }
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sai mật khẩu");
                })
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Không tìm thấy người dùng"));
    }
}
