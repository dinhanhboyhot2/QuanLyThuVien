package com.example.QuanLyThuVien.ui;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.QuanLyThuVien.R;
import com.example.QuanLyThuVien.controller.BookController;
import com.example.QuanLyThuVien.model.CuonSach;

public class AddBookActivity extends AppCompatActivity {

    private EditText etBookName, etAuthorName, etBookId, etCategory, etQuantity, etPublisher, etPublishYear;
    private Button btnSaveBook;
    private ImageButton btnBack;
    private BookController bookController;
    private ImageView imgPreview;
    private View layoutSelectImage;
    private Uri selectedImageUri;
    private Uri tempCameraUri;
    // Bộ đăng ký xử lý chọn ảnh từ thư viện
    private final ActivityResultLauncher<String> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    selectedImageUri = uri;
                    imgPreview.setImageURI(uri); // Hiển thị ảnh lên giao diện
                }
            }
    );

    // Bộ đăng ký xử lý chụp ảnh bằng Camera
    private final ActivityResultLauncher<Uri> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.TakePicture(),
            success -> {
                if (success) {
                    selectedImageUri = tempCameraUri;
                    imgPreview.setImageURI(selectedImageUri);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        initViews();
        bookController = new BookController();

        // Xử lý quay lại
        btnBack.setOnClickListener(v -> finish());

        // Khi nhấn vào vùng "Dấu cộng" (layoutSelectImage)
        layoutSelectImage.setOnClickListener(v -> showImagePickOptions());

        // Xử lý lưu sách
        btnSaveBook.setOnClickListener(v -> handleSaveBook());
    }

    private void initViews() {
        etBookName = findViewById(R.id.etBookName);
        etAuthorName = findViewById(R.id.etAuthorName);
        etBookId = findViewById(R.id.etBookId);
        etCategory = findViewById(R.id.etCategory);
        etQuantity = findViewById(R.id.etQuantity);
        etPublisher = findViewById(R.id.etPublisher);
        etPublishYear = findViewById(R.id.etPublishYear);
        btnSaveBook = findViewById(R.id.btnSaveBook);
        btnBack = findViewById(R.id.btnBack);
        imgPreview = findViewById(R.id.imgPreview);
        layoutSelectImage = findViewById(R.id.layoutSelectImage);
    }
    private void showImagePickOptions() {
        String[] options = {"Chụp ảnh", "Chọn từ thư viện"};
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Chọn ảnh bìa sách")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        // Tạo một Uri tạm để chứa ảnh chụp
                        tempCameraUri = createTempImageUri();
                        cameraLauncher.launch(tempCameraUri);
                    } else {
                        galleryLauncher.launch("image/*");
                    }
                }).show();
    }

    private Uri createTempImageUri() {
        String fileName = "temp_image_" + System.currentTimeMillis() + ".jpg";
        android.content.ContentValues values = new android.content.ContentValues();
        values.put(MediaStore.Images.Media.TITLE, fileName);
        return getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    private void handleSaveBook() {
        CuonSach book = new CuonSach();
        // 1. Lấy dữ liệu từ giao diện
        String tenSach = etBookName.getText().toString().trim();
        String maSach = etBookId.getText().toString().trim();
        String nxb = etPublisher.getText().toString().trim();
        String namXB = etPublishYear.getText().toString().trim();
        String soLuong = etQuantity.getText().toString().trim();

        // Gán ngày hiện tại theo định dạng ISO 8601 (Spring Boot mặc định hiểu định dạng này)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            book.setdNgayTao(java.time.LocalDateTime.now().toString());
        }

        // 2. Kiểm tra tính hợp lệ qua Controller
        if (!bookController.validateBookData(tenSach, maSach, soLuong)) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ và đúng định dạng dữ liệu!", Toast.LENGTH_SHORT).show();
            return;
        }

        // 3. Đóng gói dữ liệu vào Model khớp với Database
        book.setsMaDauSach(maSach);
        book.setsTenDauSach(tenSach);
        book.setsMaNhaXuatBan(nxb);
        book.setiNamXuatBan(Integer.parseInt(namXB));
        // Giả sử URL ảnh đã được xử lý qua bước chọn ảnh
        //book.setsHinhAnh("default_book_cover.png");
        // Xử lý ảnh Base64
        if (selectedImageUri != null) {
            String base64Image = bookController.convertUriToBase64(this, selectedImageUri);
            book.setsAnhBia(base64Image); // Gán chuỗi Base64 vào đối tượng
        }

        // 4. Gửi yêu cầu qua Controller
        bookController.insertBookData(this, book, selectedImageUri, new BookController.BookCallback() {
            @Override
            public void onSuccess(String message) {
                Toast.makeText(AddBookActivity.this, message, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(AddBookActivity.this, error, Toast.LENGTH_LONG).show();
            }
        });
    }
}
