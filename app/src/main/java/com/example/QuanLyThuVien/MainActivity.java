package com.example.QuanLyThuVien;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.QuanLyThuVien.controller.BookController;
import com.example.QuanLyThuVien.model.DauSach;
import com.example.QuanLyThuVien.ui.BookAdapter;
import com.example.QuanLyThuVien.ui.LoginActivity;
import com.example.QuanLyThuVien.ui.SachDangMuonActivity;
import com.example.QuanLyThuVien.ui.SearchActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView tvGreeting;
    private AppCompatButton btnLichSu;
    private BookController bookController;
    private RecyclerView rvRecommended;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Kiểm tra trạng thái đăng nhập (Sử dụng biến cũ: isLoggedIn)
        SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        boolean isLoggedIn = pref.getBoolean("isLoggedIn", false);

        if (!isLoggedIn) {
            redirectToLogin();
            return;
        }

        // 2. Thiết lập giao diện (CHỈ GỌI 1 LẦN)
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        View mainView = findViewById(android.R.id.content);
        ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 3. Ánh xạ View
        btnLichSu = findViewById(R.id.btnDetailExtend);
        tvGreeting = findViewById(R.id.tvGreeting);
        rvRecommended = findViewById(R.id.rvRecommendedBooks);
        EditText etSearch = findViewById(R.id.etSearch);

        // 4. Hiển thị thông tin (Sử dụng biến cũ: username)
        String username = pref.getString("username", "Người dùng");
        tvGreeting.setText("XIN CHÀO, " + username.toUpperCase() + " 👋");

        // 5. Cài đặt các sự kiện Click
        findViewById(R.id.cvAvatar).setOnClickListener(v -> performLogout());

        if (etSearch != null) {
            etSearch.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            });
        }

        findViewById(R.id.tvSeeAllBooks).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
        });

        // Xử lý nút mở màn hình Sách đang mượn
        if (btnLichSu != null) {
            btnLichSu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, SachDangMuonActivity.class);
                    startActivity(intent);
                }
            });
        }

        // 6. Cấu hình RecyclerView
        bookController = new BookController();
        rvRecommended.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        loadTrendingBooks();
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
                // Xử lý lỗi
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