package com.example.QuanLyThuVien.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.QuanLyThuVien.R;
import com.example.QuanLyThuVien.model.Violation;
import java.util.List;

public class ViolationAdapter extends RecyclerView.Adapter<ViolationAdapter.ViolationViewHolder> {

    private List<Violation> violationList;

    public ViolationAdapter(List<Violation> violationList) {
        this.violationList = violationList;
    }

    @NonNull
    @Override
    public ViolationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_violation, parent, false);
        return new ViolationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViolationViewHolder holder, int position) {
        Violation violation = violationList.get(position);
        holder.tvUserName.setText(violation.getsHoTen());
        holder.tvViolationDetail.setText(violation.getsNoiDungViPham() + " - " + violation.getdNgayViPham());
        holder.tvFineAmount.setText(String.format("%,.0f đ", violation.getfSoTienPhat()));
        
        if (violation.getiLoaiViPham() == 0) {
            holder.imgIconType.setImageResource(R.drawable.ic_calendar);
        } else {
            holder.imgIconType.setImageResource(R.drawable.ic_warning);
        }
    }

    @Override
    public int getItemCount() {
        return violationList == null ? 0 : violationList.size();
    }

    public static class ViolationViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserName, tvViolationDetail, tvFineAmount;
        ImageView imgIconType;

        public ViolationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvViolationDetail = itemView.findViewById(R.id.tvViolationDetail);
            tvFineAmount = itemView.findViewById(R.id.tvFineAmount);
            imgIconType = itemView.findViewById(R.id.imgIconType);
        }
    }
}
