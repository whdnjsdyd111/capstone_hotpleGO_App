package com.example.hotplego.ui.user.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hotplego.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import lombok.NonNull;

public class HotpleResCheckAdapter extends RecyclerView.Adapter<HotpleResCheckAdapter.HotpleCheckHolder> {

    public class HotpleCheckHolder extends RecyclerView.ViewHolder {

        TextView orderName;
        TextView orderPrice;
        TextView orderNum;

        public HotpleCheckHolder(@NonNull View itemView) {
            super(itemView);

            orderName = itemView.findViewById(R.id.order_name);
            orderPrice = itemView.findViewById(R.id.order_price);
            orderNum = itemView.findViewById(R.id.order_num);
        }

        public void onBind(MenuOrder order) {
            orderName.setText(order.getMenu().getMeName());
            orderPrice.setText(String.valueOf(order.getMenu().getMePrice() * order.getNum()));
            orderNum.setText(String.valueOf(order.getNum()));
        }
    }

    public HotpleResCheckAdapter(List<MenuOrder> orders) {
        this.orders = orders;
    }

    List<MenuOrder> orders = null;

    @Override
    public int getItemCount() {
        return orders != null ? orders.size() : 0;
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull @NotNull HotpleCheckHolder holder, int position) {
        MenuOrder order = orders.get(position);
        holder.onBind(order);
    }

    @androidx.annotation.NonNull
    @NotNull
    @Override
    public HotpleCheckHolder onCreateViewHolder(@androidx.annotation.NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotple_res_check, parent, false);
        HotpleCheckHolder holder = new HotpleCheckHolder(view);
        return holder;
    }
}
