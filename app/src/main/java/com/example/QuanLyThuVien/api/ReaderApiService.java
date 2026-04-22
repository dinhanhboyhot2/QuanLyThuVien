package com.example.QuanLyThuVien.api;

import com.example.QuanLyThuVien.model.DocGia;
import retrofit2.Call;
import retrofit2.http.*;

public interface ReaderApiService {
    @GET("api/reader/{id}")
    Call<DocGia> getReader(@Path("id") String id);

    @POST("api/reader/update")
    Call<Object> updateReader(@Body DocGia request);
}