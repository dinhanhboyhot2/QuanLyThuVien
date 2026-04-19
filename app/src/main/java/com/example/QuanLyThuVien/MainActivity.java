package com.example.QuanLyThuVien;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

// 1. Định nghĩa Interface gọi API đúng với cấu hình Backend (/api/login)
interface ApiService {
    @FormUrlEncoded
    @POST("api/login")
    Call<String> checkLogin(@Field("email") String email, @Field("password") String password);
}

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 2. Khai báo các thành phần giao diện
        EditText edtEmail = findViewById(R.id.edtEmail);
        EditText edtPass = findViewById(R.id.edtPass);
        Button btnTest = findViewById(R.id.btnTest);

        // 3. Cấu hình Retrofit (Trỏ về máy tính cá nhân qua IP 10.0.2.2)
        Retrofit rf = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8081/") // Cổng 8081 khớp với application.properties
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        ApiService api = rf.create(ApiService.class);

        // 4. Xử lý sự kiện khi nhấn nút
        btnTest.setOnClickListener(v -> {
            String email = edtEmail.getText().toString().trim();
            String password = edtPass.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đủ Email và Pass", Toast.LENGTH_SHORT).show();
                return;
            }

            api.checkLogin(email, password).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        // Thành công (Code 200) - Hiển thị tên người dùng từ Server
                        Toast.makeText(MainActivity.this, response.body(), Toast.LENGTH_LONG).show();
                    } else {
                        // Lỗi logic Server (Ví dụ: 404 sai path hoặc 500 lỗi SQL)
                        Toast.makeText(MainActivity.this, "Server báo lỗi: " + response.code(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    // Lỗi kết nối vật lý (Rớt mạng, Server chưa bật, hoặc Firewall chặn)
                    Toast.makeText(MainActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}