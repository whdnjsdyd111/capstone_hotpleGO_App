package com.example.hotplego;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PickAdapter extends RecyclerView.Adapter<PickAdapter.PickHolder> {
    TextView pick_place_time;
    TextView pick_place_name;
    TextView pick_place_address;
    RatingBar pick_place_rating;
    ImageView pick_place_img;

    private ArrayList<PickData> pickData = new ArrayList<>();

    @NonNull
    @Override
    public PickHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView =inflater.inflate(R.layout.pick_place_item, parent, false);

        return new PickHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PickHolder pickHolder, int position) {
        PickData data = pickData.get(position);
        pickHolder.setItem(data);
    }

    @Override
    public int getItemCount() {
        return pickData.size();
    }

    public void addItem(PickData data) {
        pickData.add(data);
    }

    class PickHolder extends RecyclerView.ViewHolder {
        public PickHolder (@NonNull View itemView) {
            super(itemView);

            pick_place_time = itemView.findViewById(R.id.pick_place_time);
            pick_place_name = itemView.findViewById(R.id.place_name);
            pick_place_address = itemView.findViewById(R.id.place_address);
            pick_place_rating = itemView.findViewById(R.id.place_rating);
            pick_place_img = itemView.findViewById(R.id.place_img);
        }

        public void setItem(PickData pickData) {
            pick_place_time.setText(pickData.getPlace_time());
            pick_place_name.setText(pickData.getPlace_name());
            pick_place_address.setText(pickData.getPlace_address());
            pick_place_img.setImageResource(pickData.getPlace_img());
            pick_place_rating.setRating(pickData.getPlace_rating());
        }
    }
}
