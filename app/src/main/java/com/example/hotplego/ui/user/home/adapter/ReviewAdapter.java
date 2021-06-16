package com.example.hotplego.ui.user.home.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotplego.PostRun;
import com.example.hotplego.R;
import com.example.hotplego.domain.ReviewVO;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    TextView user_name;
    TextView review_date;
    TextView review_content;
    RatingBar ratingBar;
    TextView review_own;

    private List<ReviewVO> list = null;

    public ReviewAdapter(List<ReviewVO> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotple_review_item, parent, false);
       ViewHolderReview viewHolderReview = new ViewHolderReview(view);

       return viewHolderReview;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolderReview)holder).onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    private class ViewHolderReview extends RecyclerView.ViewHolder {
        public ViewHolderReview(@NonNull View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.user_name);
            review_date = itemView.findViewById(R.id.review_date);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            review_content = itemView.findViewById(R.id.review_content);
            review_own = itemView.findViewById(R.id.review_own_content);
        }
        public void onBind(ReviewVO vo) {
            user_name.setText(vo.getUCode().split("/")[0]);
            ratingBar.setRating(vo.getRvRating().floatValue());
            review_date.setText(PostRun.toDateStr(vo.getRiCode()));
            review_content.setText(vo.getRvCont());
            if (vo.getRvOwnCont() != null) {
                review_own.setVisibility(View.VISIBLE);
                review_own.setText("사장님 답변\n" + vo.getRvOwnCont());
            }
        }
    }
}
