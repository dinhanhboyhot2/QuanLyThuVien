package com.example.QuanLyThuVien.api;

import com.example.QuanLyThuVien.model.SachDangMuon;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PhieuMuonApiService {
    @GET("api/phieumuon/dangmuon/{sMaDocGia}")
    Call<List<SachDangMuon>> layDanhSachSachDangMuon(@Path("sMaDocGia") String sMaDocGia);
}