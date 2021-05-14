package com.example.hotplego.ui.manager.menu.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hotplego.PostRun;
import com.example.hotplego.R;
import com.example.hotplego.domain.MenuVO;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    public static class MenuViewHolder extends RecyclerView.ViewHolder {
        ImageView menu_img;
        TextView menu_name;
        TextView menu_price;
        TextView menu_cnt;

        public MenuViewHolder(View itemView) {
            super(itemView);

            this.menu_name = (TextView) itemView.findViewById(R.id.menu_name);
            this.menu_price = (TextView) itemView.findViewById(R.id.menu_price);
            this.menu_cnt = (TextView) itemView.findViewById(R.id.menu_cnt);
            this.menu_img = (ImageView) itemView.findViewById(R.id.image_icon);
        }

        public void onBind(MenuVO menu) {
            menu_name.setText(menu.getMeName());
            menu_price.setText(String.valueOf(menu.getMePrice()));
            menu_cnt.setText(menu.getMeIntr());
            if (menu.getUuid() != null) Glide.with(itemView).load(PostRun.getImageUrl(menu.getUploadPath(),
                    menu.getUuid(), menu.getFileName())).into(menu_img);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(MenuVO vo);
    }

    private List<MenuVO> list;
    private OnItemClickListener listener;

    public MenuAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setData(List<MenuVO> data) { // 아이템을 추가
        list = data;
        notifyDataSetChanged();
    }

    public void remove(int position) {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        MenuVO vo = list.get(position);
        holder.onBind(vo);
        holder.itemView.setOnClickListener(v -> {
            listener.onItemClick(vo);
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
}