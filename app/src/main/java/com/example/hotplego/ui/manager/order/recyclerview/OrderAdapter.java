package com.example.hotplego.ui.manager.order.recyclerview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotplego.PostRun;
import com.example.hotplego.R;
import com.example.hotplego.domain.ReservationAllVO;
import com.example.hotplego.domain.ReviewVO;
import com.example.hotplego.ui.manager.order.OrderDetail;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView order_menu;
        TextView order_time;
        TextView reservation_time;
        TextView review_score;
        Button order_detail;
        Button order_review;

        public OrderViewHolder(View itemView)  {
            super(itemView);

            order_menu = itemView.findViewById(R.id.order_menu);
            order_time = itemView.findViewById(R.id.order_time);
            reservation_time = itemView.findViewById(R.id.reservation_time);
            order_detail = itemView.findViewById(R.id.order_detail);
            order_review = itemView.findViewById(R.id.order_review);
            review_score = itemView.findViewById(R.id.review_score);
        }

        public void onBind(List<ReservationAllVO> vo) {
            ReservationAllVO fir = vo.get(0);
            if (vo.size() == 1) order_menu.setText(fir.getMeName());
            else order_menu.setText(fir.getMeName() + " 외 " + (vo.size() - 1) + "개");
            order_time.setText(PostRun.toDateStr(fir.getRiCode()));
            reservation_time.setText(PostRun.timestampToStr(fir.getRiTime()));
            itemView.findViewById(R.id.order_detail).setOnClickListener(v -> {
                Intent intent = new Intent(activity, OrderDetail.class);
                intent.putExtra("orders", (Serializable) vo);
                activity.startActivity(intent);
            });
            if (OrderAdapter.this.review != null) {
                ReviewVO review = OrderAdapter.this.review.get(fir.getRiCode());
                if (review != null) {
                    itemView.findViewById(R.id.review).setVisibility(View.VISIBLE);
                    review_score.setText(review.getRvRating() + "점");
                    itemView.findViewById(R.id.order_review).setOnClickListener(v -> {
                        AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                        LayoutInflater inflater = LayoutInflater.from(itemView.getContext());
                        LinearLayout rating = (LinearLayout) inflater.inflate(R.layout.ratingbar_manager, null);
                        ((RatingBar) rating.findViewById(R.id.ratingbar)).setRating(review.getRvRating());
                        ((TextView) rating.findViewById(R.id.review_content)).setText(review.getRvCont());
                        ((EditText) rating.findViewById(R.id.review_own)).setText(review.getRvOwnCont());
                        rating.findViewById(R.id.review_submit).setOnClickListener(l -> {
                            PostRun postRun = new PostRun("manager_review", activity, PostRun.DATA);
                            postRun.setRunUI(() -> {
                                try {
                                    if (postRun.obj.getBoolean("message")) Toast.makeText(itemView.getContext(), "수정 완료하였습니다.", Toast.LENGTH_SHORT).show();
                                    else Toast.makeText(itemView.getContext(), "다시 시도해주십시오.", Toast.LENGTH_SHORT).show();
                                    review.setRvOwnCont(((EditText) rating.findViewById(R.id.review_own)).getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            });
                            postRun.addData("riCode", fir.getRiCode())
                                    .addData("rvOwnCont", ((EditText) rating.findViewById(R.id.review_own)).getText().toString())
                                    .start();
                        });
                        builder.setView(rating);
                        builder.show();
                    });
                }
            }
        }
    }

    private List<String> list = new ArrayList<>();
    private final Map<String, List<ReservationAllVO>> map;
    private final Map<String, ReviewVO> review;
    private final Activity activity;

    public OrderAdapter(Map<String, List<ReservationAllVO>> map, Map<String, ReviewVO> reviews, Activity activity) {
        this.map = map;
        this.review = reviews;
        this.activity = activity;
        map.forEach((k, v) -> list.add(k));
    }

    @NonNull
    @NotNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull OrderViewHolder holder, int position) {
        holder.onBind(map.get(list.get(position)));
    }

    @Override
    public int getItemCount() {
        return map == null ? 0 : map.size();
    }
}
