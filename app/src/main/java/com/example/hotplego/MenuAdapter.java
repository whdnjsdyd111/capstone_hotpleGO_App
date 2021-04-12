package com.example.hotplego;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuHolder> {

    Context c;
    ArrayList<Model> models;

    public MenuAdapter(Context c, ArrayList<Model> models) {
        this.c = c;
        this.models = models;
    }

    @NonNull
    @Override
    public MenuHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.admin_item_add, null);
        return new MenuHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuHolder menuholder, int i) {
        menuholder.title.setText(models.get(i).getTitle());
        menuholder.price.setText(models.get(i).getPrice());
        menuholder.imgView.setImageResource(models.get(i).getImg());
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
