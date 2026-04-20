package com.example.QuanLyThuVien.api;

import com.example.QuanLyThuVien.model.LoginRequest;
import com.example.QuanLyThuVien.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("api/auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);
}