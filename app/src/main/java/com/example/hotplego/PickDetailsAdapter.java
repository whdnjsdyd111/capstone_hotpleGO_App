package com.example.hotplego;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PickDetailsAdapter extends RecyclerView.Adapter<PickDetailsAdapter.PickDetailsHolder> {
    TextView details_name;
    TextView details_address;

    private ArrayList<PickDetailsData> detailsData = new ArrayList<>();

    @NonNull
    @Override
    public PickDetailsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.pick_course_details_item, parent,false);

        return new PickDetailsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PickDetailsHolder holder, int position) {
        PickDetailsData DetailsData = detailsData.get(position);
        holder.setItem(DetailsData);
    }

    @Override
    public int getItemCount() {
        return detailsData.size();
    }

    public void addItem(PickDetailsData data) {
        detailsData.add(data);
    }

    class PickDetailsHolder extends RecyclerView.ViewHolder {
        public PickDetailsHolder(@NonNull View itemView) {
            super(itemView);

            details_name = itemView.findViewById(R.id.pick_details_name);
            details_address = itemView.findViewById(R.id.pick_details_address);
        }
        public void setItem(PickDetailsData detailsData) {
            details_name.setText(detailsData.getDetails_name());
            details_address.setText(detailsData.getDetails_address());
        }
    }
}
