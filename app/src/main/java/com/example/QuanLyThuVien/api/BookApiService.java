package com.example.QuanLyThuVien.api;

import com.example.QuanLyThuVien.model.DauSach;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BookApiService {
    // Tìm kiếm theo từ khóa
    @GET("api/books/search")
    Call<List<DauSach>> getBooksByKeyword(@Query("q") String sKeyword);

    // Lấy toàn bộ danh sách sách (khi không có điều kiện)
    @GET("api/books/all")
    Call<List<DauSach>> getAllBooks();

    // Them sach moi
    @POST("api/books/add")
    Call<ResponseBody> addBook(@Body DauSach book);

    @GET("api/books/trending")
    Call<List<DauSach>> getTrendingBooks();
}