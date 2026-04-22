package com.example.QuanLyThuVien.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log; // Dùng để debug log
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

    // Khai báo các thành phần giao diện
    private EditText edtUser, edtPass;
    private Button btnLogin;

    // TAG dùng để lọc log trong Logcat
    private static final String TAG = "LOGIN_DEBUG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Gán layout cho Activity
        setContentView(R.layout.activity_login);

        // Debug: xác nhận màn hình đã mở
        Log.d(TAG, "onCreate: LoginActivity started");

        // Ánh xạ view từ XML sang Java
        edtUser = findViewById(R.id.etStudentNameLogin);
        edtPass = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // Bắt sự kiện click nút đăng nhập
        btnLogin.setOnClickListener(v -> {

            // Lấy dữ liệu người dùng nhập
            String user = edtUser.getText().toString().trim();
            String pass = edtPass.getText().toString().trim();

            // Debug: kiểm tra dữ liệu nhập vào
            Log.d(TAG, "Button clicked");
            Log.d(TAG, "Username = " + user);
            Log.d(TAG, "Password = " + pass);

            // Gọi hàm xử lý đăng nhập
            validateLogin(user, pass);
        });
    }

    private void validateLogin(String user, String pass) {

        // Debug: xác nhận đã vào hàm validate
        Log.d(TAG, "validateLogin called");

        // Kiểm tra dữ liệu rỗng
        if (user.isEmpty() || pass.isEmpty()) {
            Log.e(TAG, "Input is empty");

            Toast.makeText(
                    this,
                    "Vui lòng nhập đủ thông tin",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        // Tạo service để gọi API
        Log.d(TAG, "Creating API service");
        AuthService service = ApiClient.getClient().create(AuthService.class);

        // Tạo object request gửi lên server
        LoginRequest request = new LoginRequest(user, pass);

        // Debug: bắt đầu gọi API
        Log.d(TAG, "Calling login API");

        service.login(request).enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(
                    Call<LoginResponse> call,
                    Response<LoginResponse> response
            ) {

                // Debug: nhận phản hồi từ server
                Log.d(TAG, "API response received");
                Log.d(TAG, "HTTP code = " + response.code());

                // Kiểm tra response thành công và có dữ liệu
                if (response.isSuccessful() && response.body() != null) {

                    // Lấy vai trò từ JSON response
                    String role = response.body().getMaVaiTro();

                    // Debug: kiểm tra role
                    Log.d(TAG, "Login success");
                    Log.d(TAG, "Role = " + role);

                    // Lưu trạng thái đăng nhập vào bộ nhớ máy (Đồng bộ với MainActivity CŨ)
                    SharedPreferences pref =
                            getSharedPreferences("UserPrefs", MODE_PRIVATE);

                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.putString("username", user);
                    editor.putString("role", role);

                    // QUAN TRỌNG: Vẫn phải lưu mã độc giả để dùng cho chức năng Sách Đang Mượn
                    editor.putString("sMaDocGia", user);

                    editor.apply();

                    Log.d(TAG, "Saved login session");

                    Toast.makeText(
                            LoginActivity.this,
                            "Đăng nhập thành công!",
                            Toast.LENGTH_SHORT
                    ).show();

                    Intent intent;

                    // Điều hướng theo vai trò
                    if ("STUDENT".equalsIgnoreCase(role)) {

                        Log.d(TAG, "Navigate to MainActivity");

                        intent = new Intent(
                                LoginActivity.this,
                                MainActivity.class
                        );

                    } else if ("LIBRARIAN".equalsIgnoreCase(role)) {

                        Log.d(TAG, "Navigate to AdminActivity");

                        intent = new Intent(
                                LoginActivity.this,
                                AdminActivity.class
                        );

                    } else {

                        // Debug: role lỗi
                        Log.e(TAG, "Invalid role = " + role);

                        Toast.makeText(
                                LoginActivity.this,
                                "Vai trò không hợp lệ!",
                                Toast.LENGTH_SHORT
                        ).show();
                        return;
                    }

                    // Chuyển màn hình
                    startActivity(intent);

                    // Đóng màn hình login
                    finish();

                } else {

                    // Debug: sai tài khoản hoặc server trả lỗi
                    Log.e(TAG, "Login failed");
                    Log.e(TAG, "Response code = " + response.code());

                    Toast.makeText(
                            LoginActivity.this,
                            "Sai tài khoản hoặc mật khẩu!",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(
                    Call<LoginResponse> call,
                    Throwable t
            ) {

                // Debug: lỗi mạng / server tắt / sai URL
                Log.e(TAG, "API call failed", t);

                Toast.makeText(
                        LoginActivity.this,
                        "Không kết nối được server",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}