package com.example.hotplego;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MenuHolder extends RecyclerView.ViewHolder {

    ImageView me_img;
    TextView me_name, me_price;
    public MenuHolder(@NonNull View itemView) {
        super(itemView);

        this.me_img = itemView.findViewById(R.id.image_icon);
        this.me_name = itemView.findViewById(R.id.name_new_menu);
        this.me_price = itemView.findViewById(R.id.price_new_menu);
    }
}
