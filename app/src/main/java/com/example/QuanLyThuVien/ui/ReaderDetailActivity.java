package com.example.QuanLyThuVien.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.QuanLyThuVien.R;
import com.example.QuanLyThuVien.api.ApiClient;
import com.example.QuanLyThuVien.api.ReaderApiService;
import com.example.QuanLyThuVien.model.DocGia;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReaderDetailActivity extends AppCompatActivity {
    // Các trường chỉ xem
    private EditText edtMaDocGia, edtHoTen, edtGioiTinh, edtNgaySinh, edtNgayCapNhat;
    // Các trường cho phép sửa
    private EditText edtEmail, edtDienThoai, edtDiaChi1, edtDiaChi2;
    private Button btnSave;
    private String maDG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader_detail);

        SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        maDG = pref.getString("username", "");

        initViews();
        loadCurrentProfile(); 

        btnSave.setOnClickListener(v -> handleUpdate());
    }

    private void initViews() {
        edtMaDocGia = findViewById(R.id.edtMaDocGia);
        edtHoTen = findViewById(R.id.edtHoTen);
        edtGioiTinh = findViewById(R.id.edtGioiTinh);
        edtNgaySinh = findViewById(R.id.edtNgaySinh);
        edtNgayCapNhat = findViewById(R.id.edtNgayCapNhat);
        edtEmail = findViewById(R.id.edtEmail);
        edtDienThoai = findViewById(R.id.edtDienThoai);
        edtDiaChi1 = findViewById(R.id.edtDiaChi1);
        edtDiaChi2 = findViewById(R.id.edtDiaChi2);
        btnSave = findViewById(R.id.btnSaveProfile);
    }

    private void loadCurrentProfile() {
        ReaderApiService api = ApiClient.getClient().create(ReaderApiService.class);
        api.getReader(maDG).enqueue(new Callback<DocGia>() {
            @Override
            public void onResponse(Call<DocGia> call, Response<DocGia> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DocGia data = response.body();
                    edtMaDocGia.setText(data.sMaDocGia);
                    edtHoTen.setText(data.sHoTen);
                    edtNgaySinh.setText(data.dNgaySinh != null ? data.dNgaySinh.toString() : "");
                    edtNgayCapNhat.setText(data.dNgayCapNhat != null ? data.dNgayCapNhat.toString() : "Chưa có");

                    String gt = "Khác";
                    if (data.iGioiTinh != null) {
                        if (data.iGioiTinh == 1) gt = "Nam";
                        else if (data.iGioiTinh == 0) gt = "Nữ";
                    }
                    edtGioiTinh.setText(gt);

                    edtEmail.setText(data.sEmail);
                    edtDienThoai.setText(data.sDienThoai);
                    edtDiaChi1.setText(data.sDiaChi1);
                    edtDiaChi2.setText(data.sDiaChi2);
                }
            }
            @Override
            public void onFailure(Call<DocGia> call, Throwable t) {
                Toast.makeText(ReaderDetailActivity.this, "Lỗi tải hồ sơ!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleUpdate() {
        DocGia updateData = new DocGia();
        updateData.sMaDocGia = maDG;
        updateData.sEmail = edtEmail.getText().toString().trim();
        updateData.sDienThoai = edtDienThoai.getText().toString().trim();
        updateData.sDiaChi1 = edtDiaChi1.getText().toString().trim();
        updateData.sDiaChi2 = edtDiaChi2.getText().toString().trim();

        if (updateData.sDienThoai.length() < 10 || updateData.sDienThoai.length() > 11) {
            Toast.makeText(this, "SĐT phải từ 10-11 số!", Toast.LENGTH_SHORT).show();
            return;
        }

        ReaderApiService api = ApiClient.getClient().create(ReaderApiService.class);
        api.updateReader(updateData).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ReaderDetailActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ReaderDetailActivity.this, "Lỗi lưu: Kiểm tra lại dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(ReaderDetailActivity.this, "Lỗi kết nối server!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}