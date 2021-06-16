package com.example.hotplego.ui.user.home.adapter;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hotplego.R;
import com.example.hotplego.domain.MenuVO;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import lombok.NonNull;

public class HotpleResAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private TextView total;

    class HotpleResHolder extends RecyclerView.ViewHolder {

        public TextView menuName;
        public TextView menuNum;
        public ImageButton btn;

        public HotpleResHolder(@NonNull View itemView) {
            super(itemView);

            menuName = itemView.findViewById(R.id.menu_name);
            menuNum = itemView.findViewById(R.id.menu_num);
            btn = itemView.findViewById(R.id.menu_delete);
        }

        public void onBind(MenuOrder order) {
            menuName.setText(order.getMenu().getMeName());
            menuNum.setText(order.getNum() + "개");
            itemView.setVisibility(order.getNum() == 0 ? View.GONE : View.VISIBLE);
        }
    }

    public HotpleResAdapter(OnDeleteClick listener) {
        this.listener = listener;
    }

    public interface OnDeleteClick {
        void onDeleteMenu(int position);
    }

    private OnDeleteClick listener;

    public List<MenuOrder> list = new ArrayList<>();

    @androidx.annotation.NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@androidx.annotation.NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotple_res_item, parent, false);
        HotpleResHolder holder = new HotpleResHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        MenuOrder order = list.get(position);
        HotpleResHolder h = ((HotpleResHolder) holder);
        h.onBind(order);
        h.btn.setOnClickListener(v -> {
            order.setNum(order.getNum() - 1);
            listener.onDeleteMenu(position);
            this.notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addData(int position) {
        MenuOrder order = list.get(position);
        order.setNum(order.getNum() + 1);
        notifyDataSetChanged();
    }

    public void setData(List<MenuVO> list) {
        this.list = new ArrayList<>();
        for (MenuVO vo : list) {
            MenuOrder order = new MenuOrder();
            order.setMenu(vo);
            order.setNum(0);
            this.list.add(order);
        }
        notifyDataSetChanged();
    }

    public List<MenuOrder> getData() {
        return list;
    }
}
