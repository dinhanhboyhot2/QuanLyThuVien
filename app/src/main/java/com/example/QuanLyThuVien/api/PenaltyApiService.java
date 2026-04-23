package com.example.QuanLyThuVien.api;

import com.example.QuanLyThuVien.model.Violation;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PenaltyApiService {
    @GET("api/phat/danhsach")
    Call<List<Violation>> getViolationList();
}
