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
import java.util.function.Consumer;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuHolder> {
    private ArrayList<MenuData> items;
    Context context;
    public MenuAdapter(ArrayList<MenuData> items){
        this.items = items;
    }

    private Consumer<MenuData> onMenuDataClicked;

    public void setOnMenuDataClicked(Consumer<MenuData> onMenuDataClicked) {
        this.onMenuDataClicked = onMenuDataClicked;
    }

    @NonNull
    @Override
    public MenuHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.menu_item, viewGroup, false);
        context=viewGroup.getContext();

        return new MenuHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuHolder menuHolder, int i) {
        MenuData menuData = items.get(i);

        menuHolder.itemView.setTag(i);
        menuHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //아이템 클릭 시 수정 화면으로 이동
                if (onMenuDataClicked == null) return;
                onMenuDataClicked.accept(menuData);
            }
        });

        menuHolder.menu_name.setText(menuData.getTitle());
        menuHolder.menu_price.setText(menuData.getPrice());
        menuHolder.menu_cnt.setText(menuData.getCnt());
    }

    public void addItem(ArrayList<MenuData> item) { // 아이템을 추가
        this.items = item;
    }

    @Override
    public int getItemCount() { // 어뎁터에서 관리하는 아이템 갯수 반환
        return (null != items ? items.size(): 0);
    }

    public void remove(int i) { //아이템 삭제
        try{
            items.remove(i);
            notifyItemRemoved(i);
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }

    public class MenuHolder extends RecyclerView.ViewHolder {
        ImageView menu_img;
        TextView menu_name;
        TextView menu_price;
        TextView menu_cnt;

        public MenuHolder(View itemView) {
            super(itemView);

            this.menu_name = (TextView) itemView.findViewById(R.id.menu_name);
            this.menu_price = (TextView) itemView.findViewById(R.id.menu_price);
            this.menu_cnt = (TextView) itemView.findViewById(R.id.menu_cnt);
            this.menu_img = (ImageView) itemView.findViewById(R.id.image_icon);
        }
    }
}