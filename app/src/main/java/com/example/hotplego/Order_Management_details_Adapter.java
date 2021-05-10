package com.example.hotplego;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Order_Management_details_Adapter extends RecyclerView.Adapter<Order_Management_details_Adapter.ManageHolder> {
    ArrayList<Order_Management_details_Data> items = new ArrayList<>();

    @NonNull
    @Override
    public ManageHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.order_management_item, viewGroup, false);

        return new ManageHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ManageHolder manageHolder, int i) {
        Order_Management_details_Data data = items.get(i);
        manageHolder.setItem(data);
    }

    public void addItem(Order_Management_details_Data data) {
        items.add(data);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ManageHolder extends RecyclerView.ViewHolder {
        TextView details_name;
        TextView details_num;
        TextView details_pay;
        ImageView details_img;

        public ManageHolder(View itemView) {
            super(itemView);

            details_img = itemView.findViewById(R.id.order_manage_menu_img);
            details_name = itemView.findViewById(R.id.order_manage_menu_name);
            details_num = itemView.findViewById(R.id.order_manage_menu_num);
            details_pay = itemView.findViewById(R.id.order_manage_menu_pay);
        }

        public void setItem(Order_Management_details_Data data) {
            details_img.setImageResource(data.getOrder_manage_img());
            details_name.setText(data.getOrder_manage_name());
            details_num.setText(data.getOrder_manage_num());
            details_pay.setText(data.getOrder_manage_pay());
        }
    }
}