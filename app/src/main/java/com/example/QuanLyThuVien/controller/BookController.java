package com.example.QuanLyThuVien.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import com.example.QuanLyThuVien.api.ApiClient;
import com.example.QuanLyThuVien.api.BookApiService;
import com.example.QuanLyThuVien.model.CuonSach;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookController {
    private BookApiService apiService;

    public BookController() {
        this.apiService = ApiClient.getClient().create(BookApiService.class);
    }

    // Kiểm tra dữ liệu đầu vào
    public boolean validateBookData(String name, String bookId, String quantity) {
        // Cần kiểm tra thêm Mã sách (PK) không được trống
        if (name.isEmpty() || bookId.isEmpty()) return false;
        try {
            return Integer.parseInt(quantity) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Gửi dữ liệu sách lên Server
    public void insertBookData(Context context, CuonSach book, Uri imageUri, BookCallback callback) {

        // SỬA LỖI: Kiểm tra Uri trước khi chuyển đổi để tránh NullPointerException
        if (imageUri != null) {
            String base64Image = convertUriToBase64(context, imageUri);
            book.setsAnhBia(base64Image);
        } else {
            book.setsAnhBia(""); // Hoặc để null tùy thiết kế DB
        }

        apiService.addBook(book).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess("Thêm sách thành công");
                } else {
                    // Trả về thông báo lỗi cụ thể từ Server nếu có
                    callback.onError("Server trả về lỗi: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onError("Lỗi kết nối máy chủ: " + t.getMessage());
            }
        });
    }

    public String convertUriToBase64(Context context, Uri imageUri) {
        if (imageUri == null) return "";

        try {
            InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            if (bitmap == null) return "";

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            // Nén ảnh xuống mức phù hợp để tránh lỗi "Request Entity Too Large" (413) của Server
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, outputStream);
            byte[] imageBytes = outputStream.toByteArray();

            return Base64.encodeToString(imageBytes, Base64.NO_WRAP); // Dùng NO_WRAP để chuỗi Base64 liền mạch
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public void getTrendingBooks(BookListCallback callback) {
        apiService.getTrendingBooks().enqueue(new Callback<List<CuonSach>>() {
            @Override
            public void onResponse(Call<List<CuonSach>> call, Response<List<CuonSach>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onReceived(response.body());
                } else {
                    callback.onError("Không thể lấy dữ liệu");
                }
            }

            @Override
            public void onFailure(Call<List<CuonSach>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public interface BookCallback {
        void onSuccess(String message);
        void onError(String error);
    }

    public interface BookListCallback {
        void onReceived(List<CuonSach> books);
        void onError(String error);
    }
}