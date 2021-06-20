package com.example.hotplego.ui.user.mypage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hotplego.PostRun;
import com.example.hotplego.R;
import com.example.hotplego.domain.HotpleVO;

import java.util.List;

public class PickAdapter extends RecyclerView.Adapter<PickAdapter.PickHolder> {

    class PickHolder extends RecyclerView.ViewHolder {
        private final TextView pick_place_time;
        private final TextView pick_place_name;
        private final TextView pick_place_address;
        private final RatingBar pick_place_rating;
        private final ImageView pick_place_img;

        public PickHolder (@NonNull View itemView) {
            super(itemView);

            pick_place_time = itemView.findViewById(R.id.pick_place_time);
            pick_place_name = itemView.findViewById(R.id.place_name);
            pick_place_address = itemView.findViewById(R.id.place_address);
            pick_place_rating = itemView.findViewById(R.id.place_rating);
            pick_place_img = itemView.findViewById(R.id.place_img);
        }

        public void setItem(HotpleVO vo) {
            if (vo.getPickTime() != null) pick_place_time.setText(vo.getPickTime().toString());
            pick_place_name.setText(vo.getBusnName());
            pick_place_address.setText(vo.getHtAddr());
            if (vo.getHtImg() != null) Glide.with(pick_place_img).load(PostRun.getImageUrl(vo.getUploadPath(), vo.getHtImg(), vo.getFileName())).into(pick_place_img);
            else if (vo.getGoImg() != null) Glide.with(pick_place_img).load(vo.getGoImg()).into(pick_place_img);
            else Glide.with(pick_place_img).load(PostRun.DOMAIN + "/images/logo.jpg").into(pick_place_img);
            pick_place_rating.setRating(vo.getGoGrd() == null ? 0f : vo.getGoGrd().floatValue());
            itemView.setOnClickListener(v -> onPickDataClickListener.onPickDataClick(vo));
        }
    }

    private final List<HotpleVO> pickData;
    private final OnPickDataClickListener onPickDataClickListener;

    public PickAdapter(List<HotpleVO> pickData, OnPickDataClickListener onPickDataClickListener) {
        this.pickData = pickData;
        this.onPickDataClickListener = onPickDataClickListener;
    }

    @NonNull
    @Override
    public PickHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView =inflater.inflate(R.layout.pick_place_item, parent, false);

        return new PickHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull PickHolder pickHolder, int position) {
        HotpleVO data = pickData.get(position);
        pickHolder.setItem(data);
    }

    @Override
    public int getItemCount() {
        return pickData == null ? 0 : pickData.size();
    }

    public interface OnPickDataClickListener {
        void onPickDataClick(HotpleVO data);
    }
}