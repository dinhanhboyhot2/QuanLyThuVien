package com.example.QuanLyThuVien.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.QuanLyThuVien.R;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 1. Kiểm tra trạng thái đăng nhập
        super.onCreate(savedInstanceState);

        SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        boolean isLoggedIn = pref.getBoolean("isLoggedIn", false);

        if (!isLoggedIn) {
            redirectToLogin();
            return;
        }

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);
        // Ánh xạ nút Thêm sách từ XML (ID: btnThemSach)
        LinearLayout btnThemSach = findViewById(R.id.btnThemSach);

        // Thiết lập sự kiện click để chuyển màn hình
        btnThemSach.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, AddBookActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.ivAdminAvatar).setOnClickListener(v -> performLogout());
    }
    private void redirectToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void performLogout() {
        SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        pref.edit().clear().apply();
        redirectToLogin();
    }
}
