package com.example.QuanLyThuVien;

import com.example.QuanLyThuVien.api.PhieuMuonApiService;
import com.example.QuanLyThuVien.model.SachDangMuon;

import org.junit.Before;
import org.junit.Test;

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

/**
 * Unit test cho chức năng tải danh sách sách đang mượn.
 *
 * Mục tiêu:
 * - Kiểm tra phản hồi thành công có dữ liệu
 * - Kiểm tra phản hồi thành công nhưng danh sách rỗng
 * - Kiểm tra lỗi server HTTP
 * - Kiểm tra lỗi mất kết nối mạng
 *
 * Sử dụng Mockito để giả lập Retrofit API và callback response.
 */
public class SachDangMuonActivityTest {

    private PhieuMuonApiService mockApi;
    private Call<List<SachDangMuon>> mockCall;

    /**
     * Khởi tạo mock object trước mỗi test case.
     */
    @Before
    public void setUp() {
        mockApi = mock(PhieuMuonApiService.class);
        mockCall = mock(Call.class);
    }

    /**
     * TC1.1
     * Kiểm tra API trả về thành công với 2 bản ghi.
     *
     * Kỳ vọng:
     * - response thành công
     * - body khác null
     * - số lượng phần tử = 2
     */
    @Test
    public void testTaiDuLieu_ThanhCong_CoDuLieu() {
        List<SachDangMuon> mockList = new ArrayList<>();
        mockList.add(new SachDangMuon());
        mockList.add(new SachDangMuon());

        Response<List<SachDangMuon>> mockResponse = Response.success(mockList);

        when(mockApi.layDanhSachSachDangMuon("DG001")).thenReturn(mockCall);

        doAnswer(invocation -> {
            Callback<List<SachDangMuon>> callback = invocation.getArgument(0);
            callback.onResponse(mockCall, mockResponse);
            return null;
        }).when(mockCall).enqueue(any(Callback.class));

        mockCall.enqueue(new Callback<List<SachDangMuon>>() {
            @Override
            public void onResponse(Call<List<SachDangMuon>> call,
                                   Response<List<SachDangMuon>> response) {
                assertTrue(response.isSuccessful());
                assertNotNull(response.body());
                assertEquals(2, response.body().size());

                System.out.println("TC1.1 Passed: Nhận đúng 2 cuốn sách");
            }

            @Override
            public void onFailure(Call<List<SachDangMuon>> call, Throwable t) {
                fail("Không được gọi onFailure trong test case thành công");
            }
        });
    }

    /**
     * TC1.2
     * Kiểm tra API trả về thành công nhưng danh sách rỗng.
     *
     * Kỳ vọng:
     * - response thành công
     * - số lượng phần tử = 0
     */
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
            public void onResponse(Call<List<SachDangMuon>> call,
                                   Response<List<SachDangMuon>> response) {
                assertTrue(response.isSuccessful());
                assertEquals(0, response.body().size());

                System.out.println("TC1.2 Passed: Trả về danh sách rỗng");
            }

            @Override
            public void onFailure(Call<List<SachDangMuon>> call, Throwable t) {
                fail("Không được gọi onFailure");
            }
        });
    }

    /**
     * TC1.3
     * Kiểm tra trường hợp server trả về lỗi HTTP 500.
     *
     * Kỳ vọng:
     * - response không thành công
     * - mã lỗi = 500
     */
    @Test
    public void testTaiDuLieu_LoiServer_HTTP500() {
        ResponseBody errorBody = ResponseBody.create(
                MediaType.parse("application/json"),
                "{\"Lỗi\":\"Server hỏng\"}"
        );

        Response<List<SachDangMuon>> errorResponse =
                Response.error(500, errorBody);

        when(mockApi.layDanhSachSachDangMuon("DG001")).thenReturn(mockCall);

        doAnswer(invocation -> {
            Callback<List<SachDangMuon>> callback = invocation.getArgument(0);
            callback.onResponse(mockCall, errorResponse);
            return null;
        }).when(mockCall).enqueue(any(Callback.class));

        mockCall.enqueue(new Callback<List<SachDangMuon>>() {
            @Override
            public void onResponse(Call<List<SachDangMuon>> call,
                                   Response<List<SachDangMuon>> response) {
                assertFalse(response.isSuccessful());
                assertEquals(500, response.code());

                System.out.println("TC1.3 Passed: Bắt được lỗi HTTP 500");
            }

            @Override
            public void onFailure(Call<List<SachDangMuon>> call, Throwable t) {
                fail("Không được gọi onFailure");
            }
        });
    }

    /**
     * TC1.4
     * Kiểm tra trường hợp mất kết nối mạng.
     *
     * Kỳ vọng:
     * - callback onFailure được gọi
     * - thông báo lỗi đúng
     */
    @Test
    public void testTaiDuLieu_MatKetNoiMang() {
        Throwable networkError =
                new java.net.UnknownHostException("Không có kết nối mạng");

        when(mockApi.layDanhSachSachDangMuon("DG001")).thenReturn(mockCall);

        doAnswer(invocation -> {
            Callback<List<SachDangMuon>> callback = invocation.getArgument(0);
            callback.onFailure(mockCall, networkError);
            return null;
        }).when(mockCall).enqueue(any(Callback.class));

        mockCall.enqueue(new Callback<List<SachDangMuon>>() {
            @Override
            public void onResponse(Call<List<SachDangMuon>> call,
                                   Response<List<SachDangMuon>> response) {
                fail("Mất mạng nên không được vào onResponse");
            }

            @Override
            public void onFailure(Call<List<SachDangMuon>> call, Throwable t) {
                assertNotNull(t);
                assertEquals("Không có kết nối mạng", t.getMessage());

                System.out.println("TC1.4 Passed: Bắt được lỗi mất mạng");
            }
        });
    }
}