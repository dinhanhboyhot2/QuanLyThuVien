package com.example.QuanLyThuVien.ui;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.QuanLyThuVien.R;
import com.example.QuanLyThuVien.api.ApiClient;
import com.example.QuanLyThuVien.api.PenaltyApiService;
import com.example.QuanLyThuVien.model.Violation;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PenaltyStatisticActivity extends AppCompatActivity {

    private TextView tvTotalFine, tvLateFineValue, tvDamagedFineValue;
    private RecyclerView rvViolationList;
    private ViolationAdapter adapter;
    private List<Violation> violationList = new ArrayList<>();
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penalty_statistic);

        initViews();
        setupRecyclerView();
        fetchData();

        btnBack.setOnClickListener(v -> finish());
    }

    private void initViews() {
        tvTotalFine = findViewById(R.id.tvTotalFine);
        tvLateFineValue = findViewById(R.id.tvLateFineValue);
        tvDamagedFineValue = findViewById(R.id.tvDamagedFineValue);
        rvViolationList = findViewById(R.id.rvViolationList);
        btnBack = findViewById(R.id.btnBack);
    }

    private void setupRecyclerView() {
        adapter = new ViolationAdapter(violationList);
        rvViolationList.setLayoutManager(new LinearLayoutManager(this));
        rvViolationList.setAdapter(adapter);
    }

    private void fetchData() {
        PenaltyApiService apiService = ApiClient.getClient().create(PenaltyApiService.class);
        apiService.getViolationList().enqueue(new Callback<List<Violation>>() {
            @Override
            public void onResponse(Call<List<Violation>> call, Response<List<Violation>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    violationList.clear();
                    violationList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    calculateStatistics(violationList);
                } else {
                    Toast.makeText(PenaltyStatisticActivity.this, "Không thể lấy dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Violation>> call, Throwable t) {
                Toast.makeText(PenaltyStatisticActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void calculateStatistics(List<Violation> list) {
        double total = 0;
        double lateFine = 0;
        double damagedFine = 0;

        for (Violation v : list) {
            total += v.getfSoTienPhat();
            if (v.getiLoaiViPham() == 0) {
                lateFine += v.getfSoTienPhat();
            } else {
                damagedFine += v.getfSoTienPhat();
            }
        }

        tvTotalFine.setText(String.format("%,.0f đ", total));
        tvLateFineValue.setText(String.format("%,.0f đ", lateFine));
        tvDamagedFineValue.setText(String.format("%,.0f đ", damagedFine));
    }
}
