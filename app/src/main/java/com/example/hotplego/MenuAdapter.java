package com.example.hotplego;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuHolder> {
    private ArrayList<MenuData> items;
    public MenuAdapter(ArrayList<MenuData> items){
        this.items = items;
    }

    @NonNull
    @Override
    public MenuHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemview = inflater.inflate(R.layout.admin_menu_item,viewGroup,false);

        return new MenuHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuHolder menuHolder, int i) {
        menuHolder.menu_name.setText(items.get(i).getTitle());
        menuHolder.menu_price.setText(items.get(i).getPrice());
        menuHolder.menu_cnt.setText(items.get(i).getCnt());
        menuHolder.menu_img.setImageResource(items.get(i).getImg());

        menuHolder.itemView.setTag(i);
        menuHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //아이템 클릭시 액션
                String curName = menuHolder.menu_name.getText().toString();
                Toast.makeText(v.getContext(), curName, Toast.LENGTH_SHORT).show();
            }
        });

        menuHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) { //길게 눌렀을 시 액션
                return true;
            }
        });
    }

    public void addItem(ArrayList<MenuData> item) { // 아이템을 한개씩 추가
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
        protected ImageView menu_img;
        protected TextView menu_name;
        protected TextView menu_price;
        protected TextView menu_cnt;

        public MenuHolder(View itemView) {
            super(itemView);

            this.menu_name = (TextView) itemView.findViewById(R.id.menu_name);
            this.menu_price = (TextView) itemView.findViewById(R.id.menu_price);
            this.menu_cnt = (TextView) itemView.findViewById(R.id.menu_cnt);
            this.menu_img = (ImageView) itemView.findViewById(R.id.image_icon);
        }
    }
}