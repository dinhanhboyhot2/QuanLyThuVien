package com.example.QuanLyThuVien.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.QuanLyThuVien.R;
import com.example.QuanLyThuVien.api.ApiClient;
import com.example.QuanLyThuVien.api.PhieuMuonApiService;
import com.example.QuanLyThuVien.model.SachDangMuon;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SachDangMuonActivity extends AppCompatActivity {

    private RecyclerView rvSachDangMuon;
    private SachDangMuonAdapter adapter;
    private List<SachDangMuon> danhSachSach = new ArrayList<>();
    private static final String TAG = "DEBUG_MUON_TRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sach_dang_muon);

        rvSachDangMuon = findViewById(R.id.rvSachDangMuon);
        rvSachDangMuon.setLayoutManager(new LinearLayoutManager(this));

        adapter = new SachDangMuonAdapter(danhSachSach);
        rvSachDangMuon.setAdapter(adapter);
        ImageButton btnHome = findViewById(R.id.btnHome);

        btnHome.setOnClickListener(v -> {
            finish();
        });
        // Lấy thông tin đăng nhập để gọi API
        SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        // Theo Database: Tên đăng nhập (username) chính là Mã độc giả (sMaDocGia)
        String sMaDocGiaTuUsername = pref.getString("username", "");

        Log.d(TAG, "Đang gọi API cho mã độc giả: " + sMaDocGiaTuUsername);

        if (!sMaDocGiaTuUsername.isEmpty()) {
            taiDuLieu(sMaDocGiaTuUsername);
        } else {
            Toast.makeText(this, "Lỗi: Không tìm thấy mã độc giả!", Toast.LENGTH_SHORT).show();
        }
    }

    private void taiDuLieu(String sMaDocGia) {
        PhieuMuonApiService api = ApiClient.getClient().create(PhieuMuonApiService.class);
        api.layDanhSachSachDangMuon(sMaDocGia).enqueue(new Callback<List<SachDangMuon>>() {
            @Override
            public void onResponse(Call<List<SachDangMuon>> call, Response<List<SachDangMuon>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    danhSachSach.clear();
                    danhSachSach.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    Log.d(TAG, "Số lượng sách nhận được: " + danhSachSach.size());
                }
            }

            @Override
            public void onFailure(Call<List<SachDangMuon>> call, Throwable t) {
                Log.e(TAG, "Lỗi API: " + t.getMessage());
            }
        });
    }
}