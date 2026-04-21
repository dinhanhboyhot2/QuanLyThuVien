package com.example.QuanLyThuVien.ui;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.QuanLyThuVien.R;
import com.example.QuanLyThuVien.model.SachDangMuon;
import java.util.List;

public class SachDangMuonAdapter extends RecyclerView.Adapter<SachDangMuonAdapter.ViewHolder> {

    private List<SachDangMuon> danhSachSach;

    public SachDangMuonAdapter(List<SachDangMuon> danhSachSach) {
        this.danhSachSach = danhSachSach;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sach_dang_muon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SachDangMuon sach = danhSachSach.get(position);

        // Gọi hàm xử lý ngày tháng đã tạo trong Model
        sach.xuLyThoiGian();

        holder.tvTenSach.setText(sach.getsTenDauSach());
        holder.tvNgayMuon.setText("Ngày mượn: " + sach.getdNgayMuon());
        holder.tvNgayHenTra.setText("Hạn trả: " + sach.getdNgayHenTra());

        // Kiểm tra iTrangThai (0: Bình thường, 1: Quá hạn)
        if (sach.getiTrangThai() == 1) {
            holder.tvSoNgayConLai.setText("Trạng thái: Quá hạn " + Math.abs(sach.getiSoNgayConLai()) + " ngày");
            holder.tvSoNgayConLai.setTextColor(Color.RED);
        } else {
            holder.tvSoNgayConLai.setText("Còn lại: " + sach.getiSoNgayConLai() + " ngày");
            holder.tvSoNgayConLai.setTextColor(Color.parseColor("#4CAF50")); // Màu xanh lá
        }
    }

    @Override
    public int getItemCount() {
        return danhSachSach != null ? danhSachSach.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenSach, tvNgayMuon, tvNgayHenTra, tvSoNgayConLai;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenSach = itemView.findViewById(R.id.tvTenSach);
            tvNgayMuon = itemView.findViewById(R.id.tvNgayMuon);
            tvNgayHenTra = itemView.findViewById(R.id.tvNgayHenTra);
            tvSoNgayConLai = itemView.findViewById(R.id.tvSoNgayConLai);
        }
    }
}