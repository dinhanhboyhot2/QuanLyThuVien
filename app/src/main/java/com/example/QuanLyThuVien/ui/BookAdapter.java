package com.example.QuanLyThuVien.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.QuanLyThuVien.model.CuonSach;
import com.example.QuanLyThuVien.R;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<CuonSach> listSach;

    public BookAdapter(List<CuonSach> listSach) {
        this.listSach = listSach;
    }

    public void updateData(List<CuonSach> newList) {
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
        CuonSach sach = listSach.get(position);
        if (sach == null) return;

        // 1. Ánh xạ dữ liệu văn bản
        holder.tvTitle.setText(sach.getsTenDauSach());

        // Vì trong Model CuonSach chỉ có mã NXB, ta tạm hiển thị mã NXB thay cho tác giả
        // hoặc bạn có thể cập nhật thêm field sTacGia vào Model nếu DB có trả về.
        holder.tvAuthor.setText("Nhà xuất bản: " + sach.getsMaNhaXuatBan());

        // 2. Xử lý hình ảnh (Nếu bạn có URL ảnh từ Server, hãy dùng Glide hoặc Picasso)
        // Hiện tại đang để mặc định theo src của bạn: @drawable/ic_book2
        holder.imgBook.setImageResource(R.drawable.ic_book2);

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