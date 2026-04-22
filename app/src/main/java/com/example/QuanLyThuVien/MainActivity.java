package com.example.QuanLyThuVien;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.QuanLyThuVien.controller.BookController;
import com.example.QuanLyThuVien.model.CuonSach;
import com.example.QuanLyThuVien.ui.BookAdapter;
import com.example.QuanLyThuVien.ui.LoginActivity;
import com.example.QuanLyThuVien.ui.ReaderDetailActivity; // ĐÃ THÊM IMPORT
import com.example.QuanLyThuVien.ui.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView; // ĐÃ THÊM IMPORT

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView tvGreeting;
    private BookController bookController;
    private RecyclerView rvRecommended;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Kiểm tra trạng thái đăng nhập [cite: 92-93]
        SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        boolean isLoggedIn = pref.getBoolean("isLoggedIn", false);

        if (!isLoggedIn) {
            redirectToLogin();
            return;
        }

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 2. Cấu hình Insets cho giao diện tràn viền
        View mainView = findViewById(android.R.id.content);
        ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 3. Ánh xạ View và hiển thị lời chào [cite: 94-113]
        tvGreeting = findViewById(R.id.tvGreeting);
        String username = pref.getString("username", "Người dùng");
        tvGreeting.setText("XIN CHÀO, " + username.toUpperCase() + " 👋");

        // 4. XỬ LÝ BOTTOM NAVIGATION (PHẦN ÔNG ĐANG THIẾU)
        // Tìm đoạn BottomNavigationView trong onCreate của MainActivity.java
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation_Main); //
        if (bottomNav != null) {
            bottomNav.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_home) { // [cite: 336]
                    // Xử lý về trang chủ
                    return true;
                } else if (itemId == R.id.nav_profile) { // ID CHUẨN LÀ nav_profile
                    // MỞ MÀN HÌNH HỒ SƠ CÁ NHÂN
                    Intent intent = new Intent(MainActivity.this, ReaderDetailActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.nav_bookshelf || itemId == R.id.nav_notifications) { // [cite: 336]
                    // Các chức năng khác
                    return true;
                }
                return false;
            });
        }

        // 5. Các xử lý khác giữ nguyên [cite: 104-113]
        findViewById(R.id.cvAvatar).setOnClickListener(v -> performLogout());

        EditText etSearch = findViewById(R.id.etSearch);
        if (etSearch != null) {
            etSearch.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            });
        }

        bookController = new BookController();
        rvRecommended = findViewById(R.id.rvRecommendedBooks);
        rvRecommended.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        loadTrendingBooks();

        findViewById(R.id.tvSeeAllBooks).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
        });
    }

    private void loadTrendingBooks() {
        bookController.getTrendingBooks(new BookController.BookListCallback() {
            @Override
            public void onReceived(List<CuonSach> books) {
                BookAdapter adapter = new BookAdapter(books);
                rvRecommended.setAdapter(adapter);
            }

            @Override
            public void onError(String error) {
                Toast.makeText(MainActivity.this, "Lỗi tải sách: " + error, Toast.LENGTH_SHORT).show();
            }
        });
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