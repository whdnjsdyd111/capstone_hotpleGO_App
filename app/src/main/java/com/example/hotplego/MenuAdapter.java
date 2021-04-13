package com.example.hotplego;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuHolder> {
    ArrayList<Menu> items = new ArrayList<Menu>();

    @NonNull
    @Override
    public MenuHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemview = inflater.inflate(R.layout.admin_menu_item,viewGroup,false);

        return new MenuHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuHolder menuHolder, int i) {
        Menu item = items.get(i);
        menuHolder.setItem(item);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Menu item) {
        items.add(item);
    }

    public void setItems(ArrayList<Menu> items) {
        this.items = items;
    }

    public Menu getItem(int i) {
        return items.get(i);
    }

    public void setItem(int i, Menu item) {
        items.set(i,item);
    }

    public class MenuHolder extends RecyclerView.ViewHolder {
        TextView menu_name, menu_price, menu_cnt;
        ImageView menu_img;

        public MenuHolder(View itemView) {
            super(itemView);

            menu_name = itemView.findViewById(R.id.name_new_menu);
            menu_price = itemView.findViewById(R.id.price_new_menu);
            menu_cnt = itemView.findViewById(R.id.menu_cnt);
            menu_img = itemView.findViewById(R.id.image_icon);
        }

        public void setItem(Menu item) {
            menu_name.setText(item.getTitle());
            menu_price.setText(item.getPrice());
            menu_cnt.setText(item.getCnt());
            menu_img.setImageResource(item.getImg());
        }
    }

}
