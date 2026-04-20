package com.example.QuanLyThuVien.api;

import com.example.QuanLyThuVien.model.CuonSach;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BookApiService {
    // Tìm kiếm theo từ khóa
    @GET("api/books/search")
    Call<List<CuonSach>> getBooksByKeyword(@Query("q") String sKeyword);

    // Lấy toàn bộ danh sách sách (khi không có điều kiện)
    @GET("api/books/all")
    Call<List<CuonSach>> getAllBooks();
}