package com.example.QuanLyThuVien;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.QuanLyThuVien.controller.BookController;
import com.example.QuanLyThuVien.model.DauSach;
import com.example.QuanLyThuVien.ui.BookAdapter;
import com.example.QuanLyThuVien.ui.LoginActivity;
import com.example.QuanLyThuVien.ui.SearchActivity;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private TextView tvGreeting;
    private BookController bookController;
    private RecyclerView rvRecommended;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 1. Kiểm tra trạng thái đăng nhập
        super.onCreate(savedInstanceState);

        SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        boolean isLoggedIn = pref.getBoolean("isLoggedIn", false);
        String sVaiTro = pref.getString("role", "");

        if (!isLoggedIn) {
            redirectToLogin();
            return;
        }

        EdgeToEdge.enable(this);
            setContentView(R.layout.activity_main);

        View mainView = findViewById(android.R.id.content);
        ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvGreeting = findViewById(R.id.tvGreeting);

        String username = pref.getString("username", "Người dùng");
        tvGreeting.setText("XIN CHÀO, " + username.toUpperCase() + " 👋");

        findViewById(R.id.cvAvatar).setOnClickListener(v -> performLogout());

        // Mở SearchActivity khi bấm vào ô tìm kiếm ở trang chủ
        EditText etSearch = findViewById(R.id.etSearch);
        if (etSearch != null) {
            etSearch.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            });
        }

        bookController = new BookController();
        rvRecommended = findViewById(R.id.rvRecommendedBooks);

        // Cấu hình lướt ngang cho RecyclerView
        rvRecommended.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Gọi API lấy dữ liệu
        loadTrendingBooks();

        // Xử lý nút "Xem tất cả"
        findViewById(R.id.tvSeeAllBooks).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
        });
    }

    private void loadTrendingBooks() {
        bookController.getTrendingBooks(new BookController.BookListCallback() {
            @Override
            public void onReceived(List<DauSach> books) {
                // Tạo Adapter và gán vào RecyclerView
                // Lưu ý: Bạn cần tạo BookAdapter tương tự như mình đã hướng dẫn ở phản hồi trước
                BookAdapter adapter = new BookAdapter(books);
                rvRecommended.setAdapter(adapter);
            }

            @Override
            public void onError(String error) {
                // Xử lý lỗi nếu cần
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