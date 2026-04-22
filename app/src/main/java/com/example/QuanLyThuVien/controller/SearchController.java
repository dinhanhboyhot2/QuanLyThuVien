package com.example.QuanLyThuVien.controller;

import com.example.QuanLyThuVien.api.ApiClient; // Import đúng package của ApiClient
import com.example.QuanLyThuVien.model.DauSach;
import com.example.QuanLyThuVien.api.BookApiService;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchController {
    private BookApiService apiService;

    public SearchController() {
        // Tận dụng Retrofit instance từ ApiClient
        this.apiService = ApiClient.getClient().create(BookApiService.class);
    }

    public void searchBook(String sKeyword, SearchCallback callback) {
        Call<List<DauSach>> call;

        // Nếu từ khóa trống hoặc chỉ có khoảng trắng -> Lấy toàn bộ
        if (sKeyword == null || sKeyword.trim().isEmpty()) {
            call = apiService.getAllBooks();
        } else {
            // Có từ khóa -> Tìm kiếm theo q
            call = apiService.getBooksByKeyword(sKeyword.trim());
        }

        call.enqueue(new Callback<List<DauSach>>() {
            @Override
            public void onResponse(Call<List<DauSach>> call, Response<List<DauSach>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Không tìm thấy dữ liệu phù hợp");
                }
            }

            @Override
            public void onFailure(Call<List<DauSach>> call, Throwable t) {
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    public interface SearchCallback {
        void onSuccess(List<DauSach> listSach);
        void onError(String sErrorMessage);
    }
}