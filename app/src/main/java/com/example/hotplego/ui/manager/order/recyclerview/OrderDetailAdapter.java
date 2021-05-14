package com.example.hotplego.ui.manager.order.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hotplego.PostRun;
import com.example.hotplego.R;
import com.example.hotplego.domain.ReservationAllVO;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.DetailViewHolder> {

    public class DetailViewHolder extends RecyclerView.ViewHolder {
        TextView details_name;
        TextView details_num;
        TextView details_pay;
        ImageView details_img;

        public DetailViewHolder(View itemView) {
            super(itemView);

            details_img = itemView.findViewById(R.id.order_manage_menu_img);
            details_name = itemView.findViewById(R.id.order_manage_menu_name);
            details_num = itemView.findViewById(R.id.order_manage_menu_num);
            details_pay = itemView.findViewById(R.id.order_manage_menu_pay);
        }

        public void onBind(ReservationAllVO vo) {
            if (vo.getUuid() != null) Glide.with(itemView).load(PostRun.getImageUrl(vo.getUploadPath(),
                    vo.getUuid(), vo.getFileName())).into(details_img);
            details_name.setText(vo.getMeName());
            details_num.setText(vo.getRsMeNum() + "개");
            details_pay.setText(new DecimalFormat("###,###,###").format(vo.getRsMeNum() * vo.getMePrice()));
        }
    }

    private List<ReservationAllVO> list;

    public OrderDetailAdapter(List<ReservationAllVO> list) {
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_detail_item, parent, false);
        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DetailViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
}
