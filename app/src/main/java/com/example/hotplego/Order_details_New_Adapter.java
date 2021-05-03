package com.example.hotplego;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Order_details_New_Adapter extends RecyclerView.Adapter<Order_details_New_Adapter.OrderHolder> {
    private ArrayList<Order_details_New_Data> mData;
    private LayoutInflater inflater;
    private Context context;

    public Order_details_New_Adapter(ArrayList<Order_details_New_Data> items, Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.mData = items;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, final int position) {
        Order_details_New_Data item = mData.get(position);
        OrderHolder.setItem(item);
    }

    public void addItem(Order_details_New_Data item) {
        mData.add(item);
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_order_details_new_item, parent, false);
        OrderHolder orderHolder = new OrderHolder(view);
        return orderHolder;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class OrderHolder extends RecyclerView.ViewHolder {
        public static TextView tv_name;
        public static TextView tv_price;
        public static ImageView iv_icon;

        public OrderHolder(View itemView) {
            super(itemView);

            iv_icon = (ImageView) itemView.findViewById(R.id.new_menu_img);
            tv_name = (TextView) itemView.findViewById(R.id.new_menu_name);
            tv_price = (TextView) itemView.findViewById(R.id.new_menu_price);
        }

        public static void setItem(Order_details_New_Data item) {
            tv_name.setText(item.getNew_menu_name());
            tv_price.setText(item.getNew_menu_price());
            iv_icon.setImageResource(item.getIv());
        }
    }
}