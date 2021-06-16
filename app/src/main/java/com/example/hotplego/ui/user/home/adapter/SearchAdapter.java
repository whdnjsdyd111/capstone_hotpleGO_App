package com.example.hotplego.ui.user.home.adapter;

import android.app.Activity;
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
import com.example.hotplego.domain.HotpleVO;
import com.example.hotplego.domain.MenuVO;
import com.example.hotplego.ui.manager.menu.recyclerview.MenuAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ImageView search_item_img;
    TextView search_item_name;
    TextView search_item_rating;

    private List<HotpleVO> list = null;
    private Activity activity;
    private OnItemClickListener listen;

    public interface OnItemClickListener {
        void onItemClick(HotpleVO vo);
    }

    public SearchAdapter(Activity activity, OnItemClickListener listen) {
        this.activity = activity;
        this.listen = listen;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotple_item, parent, false);
        ViewHolderSearch viewHolderSearch = new ViewHolderSearch(view);
        return viewHolderSearch;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HotpleVO vo = list.get(position);
        ((ViewHolderSearch)holder).onBind(vo);
        holder.itemView.setOnClickListener(v -> {
            listen.onItemClick(vo);
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void addItem(List<HotpleVO> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    class ViewHolderSearch extends RecyclerView.ViewHolder{

        public ViewHolderSearch(@NonNull View itemView) {
            super(itemView);

            search_item_img = itemView.findViewById(R.id.search_imageView);
            search_item_name = itemView.findViewById(R.id.search_textView1);
            search_item_rating = itemView.findViewById(R.id.search_textView2);
        }

        public void onBind(HotpleVO hotple) {
            if (hotple.getHtImg() != null) Glide.with(itemView).load(PostRun.getImageUrl(
                    hotple.getUploadPath(), hotple.getHtImg(), hotple.getFileName())).into(search_item_img);
            else if (hotple.getGoImg() != null) Glide.with(itemView).load(hotple.getGoImg()).into(search_item_img);
            else Glide.with(itemView).load(PostRun.DOMAIN + "/images/logo.jpg").into(search_item_img);
             search_item_name.setText(hotple.getBusnName());
             search_item_rating.setText(String.valueOf(hotple.getGoGrd()));
        }
    }
}
