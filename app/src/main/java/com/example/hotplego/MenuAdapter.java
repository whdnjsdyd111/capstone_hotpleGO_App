package com.example.hotplego;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuHolder> {

    Context context;
    ArrayList<Menu> menus;

    public MenuAdapter(Context context, ArrayList<Menu> menus) {
        this.context = context;
        this.menus = menus;
    }

    @NonNull
    @Override
    public MenuHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.admin_menu_add,null);
        return new MenuHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuHolder menuholder, int i) {
        menuholder.me_name.setText(menus.get(i).getTitle());
        menuholder.me_price.setText(menus.get(i).getPrice());
        menuholder.me_img.setImageResource(menus.get(i).getImg());
    }

    @Override
    public int getItemCount() {
        return menus.size();
    }
}
