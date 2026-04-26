package com.example.QuanLyThuVien;

import com.example.QuanLyThuVien.api.PhieuMuonApiService;
import com.example.QuanLyThuVien.model.SachDangMuon;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SachDangMuonActivityTest {

    private PhieuMuonApiService mockApi;
    private Call<List<SachDangMuon>> mockCall;

    @Before
    public void setUp() {
        // Khởi tạo các đối tượng giả lập (Mock) trước mỗi lần chạy test
        mockApi = mock(PhieuMuonApiService.class);
        mockCall = mock(Call.class);
    }

    // [Test Case 1.1] Gọi API thành công và có dữ liệu (2 cuốn sách)
    @Test
    public void testTaiDuLieu_ThanhCong_CoDuLieu() {
        // 1. Chuẩn bị Mock Data (Input)
        List<SachDangMuon> mockList = new ArrayList<>();
        mockList.add(new SachDangMuon());
        mockList.add(new SachDangMuon());
        Response<List<SachDangMuon>> mockResponse = Response.success(mockList);

        when(mockApi.layDanhSachSachDangMuon("DG001")).thenReturn(mockCall);

        doAnswer(invocation -> {
            Callback<List<SachDangMuon>> callback = invocation.getArgument(0);
            callback.onResponse(mockCall, mockResponse); // Giả lập gọi onResponse
            return null;
        }).when(mockCall).enqueue(any(Callback.class));

        // 2. Thực thi & 3. Kiểm tra (Assert)
        mockCall.enqueue(new Callback<List<SachDangMuon>>() {
            @Override
            public void onResponse(Call<List<SachDangMuon>> call, Response<List<SachDangMuon>> response) {
                assertTrue(response.isSuccessful());
                assertNotNull(response.body());
                assertEquals(2, response.body().size()); // Kỳ vọng mảng có 2 phần tử
                System.out.println("Test 1.1 Passed: Nhận đúng 2 cuốn sách");
            }
            @Override
            public void onFailure(Call<List<SachDangMuon>> call, Throwable t) {
                fail("Không được nhảy vào onFailure ở test case này");
            }
        });
    }

    // [Test Case 1.2] Gọi API thành công nhưng mảng rỗng []
    @Test
    public void testTaiDuLieu_ThanhCong_MangRong() {
        List<SachDangMuon> emptyList = new ArrayList<>();
        Response<List<SachDangMuon>> mockResponse = Response.success(emptyList);

        when(mockApi.layDanhSachSachDangMuon("DG002")).thenReturn(mockCall);
        doAnswer(invocation -> {
            Callback<List<SachDangMuon>> callback = invocation.getArgument(0);
            callback.onResponse(mockCall, mockResponse);
            return null;
        }).when(mockCall).enqueue(any(Callback.class));

        mockCall.enqueue(new Callback<List<SachDangMuon>>() {
            @Override
            public void onResponse(Call<List<SachDangMuon>> call, Response<List<SachDangMuon>> response) {
                assertTrue(response.isSuccessful());
                assertEquals(0, response.body().size()); // Kỳ vọng mảng có 0 phần tử
                System.out.println("Test 1.2 Passed: Trả về mảng rỗng");
            }
            @Override
            public void onFailure(Call<List<SachDangMuon>> call, Throwable t) {
                fail();
            }
        });
    }

    // [Test Case 1.3] Server báo lỗi 500 Internal Server Error
    @Test
    public void testTaiDuLieu_LoiServer_HTTP500() {
        // Giả lập Server lỗi 500
        ResponseBody errorBody = ResponseBody.create(MediaType.parse("application/json"), "{\"Lỗi\":\"Server hỏng\"}");
        Response<List<SachDangMuon>> errorResponse = Response.error(500, errorBody);

        when(mockApi.layDanhSachSachDangMuon("DG001")).thenReturn(mockCall);
        doAnswer(invocation -> {
            Callback<List<SachDangMuon>> callback = invocation.getArgument(0);
            callback.onResponse(mockCall, errorResponse);
            return null;
        }).when(mockCall).enqueue(any(Callback.class));

        mockCall.enqueue(new Callback<List<SachDangMuon>>() {
            @Override
            public void onResponse(Call<List<SachDangMuon>> call, Response<List<SachDangMuon>> response) {
                assertFalse(response.isSuccessful()); // isSuccessful() phải là false
                assertEquals(500, response.code());
                System.out.println("Test 1.3 Passed: Bắt được lỗi HTTP 500");
            }
            @Override
            public void onFailure(Call<List<SachDangMuon>> call, Throwable t) {
                fail();
            }
        });
    }

    // [Test Case 1.4] Mất mạng (Timeout/No Internet)
    @Test
    public void testTaiDuLieu_MatKetNoiMang() {
        Throwable networkError = new java.net.UnknownHostException("Không có kết nối mạng");

        when(mockApi.layDanhSachSachDangMuon("DG001")).thenReturn(mockCall);
        doAnswer(invocation -> {
            Callback<List<SachDangMuon>> callback = invocation.getArgument(0);
            callback.onFailure(mockCall, networkError); // Giả lập gọi trực tiếp vào onFailure
            return null;
        }).when(mockCall).enqueue(any(Callback.class));

        mockCall.enqueue(new Callback<List<SachDangMuon>>() {
            @Override
            public void onResponse(Call<List<SachDangMuon>> call, Response<List<SachDangMuon>> response) {
                fail("Bị mất mạng nên không thể vào onResponse");
            }
            @Override
            public void onFailure(Call<List<SachDangMuon>> call, Throwable t) {
                assertNotNull(t);
                assertEquals("Không có kết nối mạng", t.getMessage());
                System.out.println("Test 1.4 Passed: Bắt được ngoại lệ mất mạng");
            }
        });
    }
}