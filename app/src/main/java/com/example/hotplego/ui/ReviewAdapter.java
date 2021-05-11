package com.example.hotplego.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotplego.R;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    TextView user_name;
    TextView review_date;
    TextView review_content;

    private ArrayList<ReviewData> listData = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
       ViewHolderReview viewHolderReview = new ViewHolderReview(view);

       return viewHolderReview;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolderReview)holder).onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void addItem(ReviewData data) {
        listData.add(data);
        notifyDataSetChanged();
    }

    private class ViewHolderReview extends RecyclerView.ViewHolder {
        public ViewHolderReview(@NonNull View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.user_name);
            review_date = itemView.findViewById(R.id.review_date);
            review_content = itemView.findViewById(R.id.review_content);
        }
        public void onBind(ReviewData reviewData) {
            user_name.setText(reviewData.getName());
            review_date.setText(reviewData.getRedate());
            review_content.setText(reviewData.getRecont());

        }
    }
}
