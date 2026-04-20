package com.example.QuanLyThuVien.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.QuanLyThuVien.MainActivity;
import com.example.QuanLyThuVien.R;
import com.example.QuanLyThuVien.api.ApiClient;
import com.example.QuanLyThuVien.api.AuthService;
import com.example.QuanLyThuVien.model.LoginRequest;
import com.example.QuanLyThuVien.model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText edtUser, edtPass;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUser = findViewById(R.id.etStudentNameLogin);
        edtPass = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String user = edtUser.getText().toString().trim();
            String pass = edtPass.getText().toString().trim();

            // Thực hiện AuthenticationUser
            validateLogin(user, pass);
        });
    }

    private void validateLogin(String user, String pass) {
        if (user.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        AuthService service = ApiClient.getClient().create(AuthService.class);
        LoginRequest request = new LoginRequest(user, pass);

        service.login(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    String role = response.body().getMaVaiTro();

                    // Lưu trạng thái đăng nhập
                    SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.putString("username", user);
                    editor.putString("role", role);
                    editor.apply();

                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                    Intent intent;

                    // Điều hướng theo role
                    if ("VT_DOCGIA".equalsIgnoreCase(role)) {
                        intent = new Intent(LoginActivity.this, MainActivity.class);
                    } else if ("VT_ADMIN".equalsIgnoreCase(role) || "VT_THUTHU".equalsIgnoreCase(role)) {
                        intent = new Intent(LoginActivity.this, AdminActivity.class);
                    } else {
                        Toast.makeText(LoginActivity.this, "Vai trò không hợp lệ!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(LoginActivity.this, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }
}