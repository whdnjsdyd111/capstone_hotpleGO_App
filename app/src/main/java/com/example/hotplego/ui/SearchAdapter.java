package com.example.hotplego.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotplego.R;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ImageView search_item_img;
    TextView search_item_name;
    TextView search_item_rating;

    private ArrayList<SearchData> listData = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotple_item, parent, false);
        ViewHolderSearch viewHolderSearch = new ViewHolderSearch(view);
        return viewHolderSearch;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolderSearch)holder).onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void addItem(SearchData data) {
        listData.add(data);
        notifyDataSetChanged();
    }

    class ViewHolderSearch extends RecyclerView.ViewHolder{

        public ViewHolderSearch(@NonNull View itemView) {
            super(itemView);

            search_item_img = itemView.findViewById(R.id.search_imageView);
            search_item_name = itemView.findViewById(R.id.search_textView1);
            search_item_rating = itemView.findViewById(R.id.search_textView2);
        }

        public void onBind(SearchData searchData) {
             search_item_img.setImageResource(searchData.getImg());
             search_item_name.setText(searchData.getName());
             search_item_rating.setText(searchData.getRating());
        }
    }
}
