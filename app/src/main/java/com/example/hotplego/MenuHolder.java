package com.example.hotplego;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MenuHolder extends RecyclerView.ViewHolder {

    ImageView imgView;
    TextView title, price;
    public MenuHolder(@NonNull View itemView) {
        super(itemView);

        this.imgView = itemView.findViewById(R.id.image_icon);
        this.title = itemView.findViewById(R.id.name_new_menu);
        this.price = itemView.findViewById(R.id.price_new_menu);
    }
}
