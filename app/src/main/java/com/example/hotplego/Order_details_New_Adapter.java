package com.example.hotplego;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Order_details_New_Adapter extends RecyclerView.Adapter<Order_details_New_Adapter.OrderHolder> {
    private ArrayList<Order_details_New_Data> nData;
    private LayoutInflater nIflater;
    private Context nContext;

    public Order_details_New_Adapter(ArrayList<Order_details_New_Data> nData, Context nContext) {
        this.nContext = nContext;
        this.nIflater = LayoutInflater.from(nContext);
        this.nData = nData;
    }

    public void addItem(Order_details_New_Data item) {
        nData.add(item);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, final int position) {
        Order_details_New_Data new_data = nData.get(position);
        OrderHolder.setItem(new_data);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Order_Management_details.class);
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = nIflater.inflate(R.layout.fragment_order_details_new_item, parent, false);
        OrderHolder orderHolder = new OrderHolder(view);
        return orderHolder;
    }

    @Override
    public int getItemCount() {
        return nData.size();
    }

    public static class OrderHolder extends RecyclerView.ViewHolder {
        public static TextView New_name;
        public static TextView New_price;
        public static ImageView New_iv;

        public OrderHolder(View itemView) {
            super(itemView);

            New_iv = (ImageView) itemView.findViewById(R.id.new_menu_img);
            New_name = (TextView) itemView.findViewById(R.id.new_menu_name);
            New_price = (TextView) itemView.findViewById(R.id.new_menu_price);
        }

        public static void setItem(Order_details_New_Data item) {
            New_name.setText(item.getNew_menu_name());
            New_price.setText(item.getNew_menu_price());
            New_iv.setImageResource(item.getNew_iv());
        }
    }
}