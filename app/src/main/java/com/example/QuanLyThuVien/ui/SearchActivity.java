package com.example.QuanLyThuVien.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.QuanLyThuVien.MainActivity;
import com.example.QuanLyThuVien.R;
import com.example.QuanLyThuVien.controller.BookController;
import com.example.QuanLyThuVien.controller.SearchController;
import com.example.QuanLyThuVien.model.DauSach;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private View layoutHome, layoutResults;
    private EditText etSearchHome, etSearchResults;
    private RecyclerView rvBooks;
    private SearchController searchController;
    private BookAdapter bookAdapter;
    private BookController bookController;
    private RecyclerView rvRecommended;
    private TextView tvGreeting;
    private AppCompatButton btnLichSu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        // 1. Ánh xạ View
        layoutHome = findViewById(R.id.layoutHome);
        layoutResults = findViewById(R.id.layoutResults);
        etSearchHome = findViewById(R.id.etSearch);
        etSearchResults = findViewById(R.id.etSearch_Result);
        rvBooks = findViewById(R.id.rvBooks);
        tvGreeting = findViewById(R.id.tvGreeting);
        btnLichSu = findViewById(R.id.btnDetailExtend);

        // 2. Thiết lập ban đầu (MẶC ĐỊNH LÀ TRANG CHỦ)
        layoutHome.setVisibility(View.VISIBLE);
        layoutResults.setVisibility(View.GONE);

        searchController = new SearchController();
        setupRecyclerView();

        String username = pref.getString("username", "Người dùng");
        tvGreeting.setText("XIN CHÀO, " + username.toUpperCase() + " 👋");

        // 3. Cấu hình Bàn phím
        etSearchHome.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        etSearchResults.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

        // 4. Xử lý Search từ bàn phím
        etSearchHome.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || isEnterPressed(event)) {
                executeSearchFromHome();
                hideKeyboard();
                return true;
            }
            return false;
        });

        etSearchResults.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || isEnterPressed(event)) {
                executeSearchFromResults();
                hideKeyboard();
                return true;
            }
            return false;
        });

        // 5. Xử lý Search từ Icon kính lúp
        setSearchIconClickListener(etSearchHome, this::executeSearchFromHome);
        setSearchIconClickListener(etSearchResults, this::executeSearchFromResults);

        // 6. Các nút Filter nhanh
        findViewById(R.id.btnAll).setOnClickListener(v -> performSearch(""));
        findViewById(R.id.btnCongNghe).setOnClickListener(v -> performSearch("Công nghệ"));

        // 7. Xử lý Intent cuối cùng để ghi đè layout nếu cần
        handleIntent(getIntent());

        // 8. Xử lý click trên Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation_Main);
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                // Nếu người dùng nhấn vào icon Trang chủ
                if (layoutResults.getVisibility() == View.VISIBLE) {
                    layoutResults.setVisibility(View.GONE);
                    layoutHome.setVisibility(View.VISIBLE);
                    // Xóa nội dung tìm kiếm cũ nếu muốn
                    etSearchHome.setText("");
                    etSearchResults.setText("");
                }
                return true;
            } else if (itemId == R.id.nav_bookshelf) {
                return true;
            }
            return false;
        });

        bookController = new BookController();
        rvRecommended = findViewById(R.id.rvRecommendedBooks);

        // Cấu hình lướt ngang cho RecyclerView
        rvRecommended.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Gọi API lấy dữ liệu
        loadTrendingBooks();

        // Xử lý nút "Xem tất cả"
        findViewById(R.id.tvSeeAllBooks).setOnClickListener(v -> {
            performSearch("");
        });

        findViewById(R.id.cvAvatar).setOnClickListener(v -> performLogout());

        if (btnLichSu != null) {
            btnLichSu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SearchActivity.this, SachDangMuonActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    private void performLogout() {
        SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        pref.edit().clear().apply();
        redirectToLogin();
    }

    private void redirectToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
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

    private void handleIntent(Intent intent) {
        if (intent != null && intent.hasExtra("search_keyword")) {
            String keyword = intent.getStringExtra("search_keyword");
            switchToResults(keyword);
        }
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private boolean isEnterPressed(KeyEvent event) {
        return event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER
                && event.getAction() == KeyEvent.ACTION_DOWN;
    }

    private void setSearchIconClickListener(EditText editText, Runnable searchTask) {
        editText.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (editText.getCompoundDrawablesRelative()[0] != null) {
                    int iconSize = editText.getCompoundDrawablesRelative()[0].getBounds().width();
                    if (event.getX() <= (iconSize + editText.getPaddingStart() + 60)) {
                        searchTask.run();
                        hideKeyboard();
                        return true;
                    }
                }
            }
            return false;
        });
    }

    private void executeSearchFromHome() {
        String sKeyword = etSearchHome.getText().toString().trim();
        switchToResults(sKeyword);
    }

    private void executeSearchFromResults() {
        String sKeyword = etSearchResults.getText().toString().trim();
        performSearch(sKeyword);
    }

    private void switchToResults(String sKeyword) {
        // Chuyển đổi hiển thị layout trước khi gọi API để tránh cảm giác bị lag
        layoutHome.setVisibility(View.GONE);
        layoutResults.setVisibility(View.VISIBLE);
        etSearchResults.setText(sKeyword);
        performSearch(sKeyword);
    }

    private void performSearch(String sKeyword) {
        searchController.searchBook(sKeyword, new SearchController.SearchCallback() {
            @Override
            public void onSuccess(List<DauSach> listSach) {
                displaySearchResults(listSach);
            }

            @Override
            public void onError(String sErrorMessage) {
                Toast.makeText(SearchActivity.this, "Lỗi: " + sErrorMessage, Toast.LENGTH_SHORT).show();
                displaySearchResults(new ArrayList<>());
            }
        });
    }

    public void displaySearchResults(List<DauSach> listSach) {
        if (listSach != null && bookAdapter != null) {
            bookAdapter.updateData(listSach);
        }
    }

    private void setupRecyclerView() {
        bookAdapter = new BookAdapter(new ArrayList<>());
        rvBooks.setLayoutManager(new LinearLayoutManager(this));
        rvBooks.setAdapter(bookAdapter);
    }

    @Override
    public void onBackPressed() {
        if (layoutResults.getVisibility() == View.VISIBLE) {
            layoutResults.setVisibility(View.GONE);
            layoutHome.setVisibility(View.VISIBLE);
            etSearchHome.setText("");
        } else {
            super.onBackPressed();
        }
    }
}