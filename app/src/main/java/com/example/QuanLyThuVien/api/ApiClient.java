package com.example.QuanLyThuVien.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    // Lưu ý: 10.0.2.2 là địa chỉ localhost của máy tính khi dùng Emulator
//    Nguyen
   // private static final String BASE_URL = "http://192.168.102.8:8081/";
    private static final String BASE_URL = "http://10.0.2.2:8081/"; //HUY
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}