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

public class Order_details_Com_Adapter extends RecyclerView.Adapter<Order_details_Com_Adapter.ComHolder> {
    private ArrayList<Order_details_Com_Data> cData;
    private LayoutInflater cInflater;
    private Context cContext;

    public Order_details_Com_Adapter(ArrayList<Order_details_Com_Data> items, Context cContext) {
        this.cData = items;
        this.cInflater = LayoutInflater.from(cContext);
        this.cContext = cContext;
    }

    @Override
    public void onBindViewHolder(@NonNull ComHolder holder, final int i) {
        Order_details_Com_Data item = cData.get(i);
        ComHolder.setItem(item);
    }

    public void addItem(Order_details_Com_Data item) {
        cData.add(item);
    }

    @NonNull
    @Override
    public ComHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = cInflater.inflate(R.layout.fragment_order_details_com_item, viewGroup, false);
        ComHolder comHolder = new ComHolder(view);
        return comHolder;
    }

    @Override
    public int getItemCount() {
        return cData.size();
    }

    public static class ComHolder extends RecyclerView.ViewHolder {
        public static TextView Com_name;
        public static TextView Com_review;
        public static TextView Com_time;
        public static ImageView Com_iv;

        public ComHolder(View itemView)  {
            super(itemView);

            Com_iv = (ImageView)itemView.findViewById(R.id.com_menu_img);
            Com_name = (TextView)itemView.findViewById(R.id.com_menu_name);
            Com_time = (TextView)itemView.findViewById(R.id.com_menu_time);
            Com_review = (TextView)itemView.findViewById(R.id.com_menu_rating);
        }

        public static void setItem(Order_details_Com_Data data) {
            Com_iv.setImageResource(data.getCom_iv());
            Com_name.setText(data.getCom_menu_name());
            Com_time.setText(data.getCom_menu_time());
            Com_review.setText(data.getCom_menu_review());
        }
    }
}