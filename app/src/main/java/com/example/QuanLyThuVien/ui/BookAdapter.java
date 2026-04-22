package com.example.QuanLyThuVien.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.QuanLyThuVien.model.DauSach;
import com.example.QuanLyThuVien.R;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<DauSach> listSach;

    public BookAdapter(List<DauSach> listSach) {
        this.listSach = listSach;
    }

    public void updateData(List<DauSach> newList) {
        this.listSach = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Nạp layout CardView bạn vừa cung cấp
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        DauSach sach = listSach.get(position);
        if (sach == null) return;

        // 1. Ánh xạ dữ liệu văn bản
        holder.tvTitle.setText(sach.getsTenDauSach());
        holder.tvAuthor.setText("Nhà xuất bản: " + sach.getsMaNhaXuatBan());

        // 2. Xử lý giải mã hình ảnh Base64
        String base64String = sach.getsAnhBia();

        if (base64String != null && !base64String.isEmpty()) {
            try {
                // Giải mã chuỗi Base64 thành mảng byte
                byte[] decodedString = android.util.Base64.decode(base64String, android.util.Base64.DEFAULT);
                // Chuyển mảng byte thành Bitmap
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                if (decodedByte != null) {
                    holder.imgBook.setImageBitmap(decodedByte);
                } else {
                    // Nếu giải mã ra null (chuỗi không phải định dạng ảnh)
                    holder.imgBook.setImageResource(R.drawable.ic_book2);
                }
            } catch (Exception e) {
                // Nếu chuỗi không phải Base64 hợp lệ, Catch lỗi và hiện ảnh mặc định
                e.printStackTrace();
                holder.imgBook.setImageResource(R.drawable.ic_book2);
            }
        } else {
            // Nếu chuỗi null hoặc rỗng
            holder.imgBook.setImageResource(R.drawable.ic_book2);
        }

        // 3. Xử lý sự kiện nút "Mượn ngay"
        holder.btnBorrow.setOnClickListener(v -> {
            Toast.makeText(v.getContext(),
                    "Đã gửi yêu cầu mượn: " + sach.getsTenDauSach(),
                    Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return listSach != null ? listSach.size() : 0;
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBook;
        TextView tvTitle, tvAuthor;
        Button btnBorrow;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ đúng ID từ XML CardView
            imgBook = itemView.findViewById(R.id.imgBook);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            btnBorrow = itemView.findViewById(R.id.btnBorrow);
        }
    }
}