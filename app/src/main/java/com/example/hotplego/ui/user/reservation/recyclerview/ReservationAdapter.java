package com.example.hotplego.ui.user.reservation.recyclerview;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hotplego.PostRun;
import com.example.hotplego.R;
import com.example.hotplego.UserSharedPreferences;
import com.example.hotplego.domain.ReservationAllVO;
import com.example.hotplego.domain.ReservationHotpleVO;
import com.example.hotplego.domain.ReviewVO;
import com.example.hotplego.ui.user.reservation.ReservationInfoFragment;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder> {

    public class ReservationViewHolder extends RecyclerView.ViewHolder {

        ImageView res_img;
        TextView hotple_name;
        TextView hotple_addr;
        TextView res_time;
        AppCompatButton detail;
        AppCompatButton review;
        ReservationHotpleVO vo;
        boolean aleady;

        public ReservationViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            res_img = itemView.findViewById(R.id.res_img);
            hotple_name = itemView.findViewById(R.id.res_hotple_name);
            hotple_addr = itemView.findViewById(R.id.res_hotple_addr);
            res_time = itemView.findViewById(R.id.res_time);
            detail = itemView.findViewById(R.id.res_detail);
            review = itemView.findViewById(R.id.res_review);
        }

        public void onBind(ReservationHotpleVO vo) {
            this.vo = vo;
            if (vo.getUuid() != null) Glide.with(itemView).load(PostRun.getImageUrl(vo.getUploadPath(), vo.getUuid(), vo.getFileName())).into(res_img);
            else Glide.with(itemView).load(PostRun.DOMAIN + "/images/logo.jpg").into(res_img);
            hotple_name.setText(vo.getBusnName());
            hotple_addr.setText(vo.getHtAddr() + "\n" + vo.getHtAddrDet());
            res_time.setText(PostRun.timestampToStr(vo.getRiTime()));

            detail.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                LayoutInflater inflater = LayoutInflater.from(itemView.getContext());
                TableLayout table = (TableLayout) inflater.inflate(R.layout.reservation_detail, null);
                int sum = 0;
                for (ReservationAllVO all : ReservationInfoFragment.map.get(vo.getRiCode())) {
                    TableRow row = new TableRow(itemView.getContext());
                    TableLayout.LayoutParams tableLayout = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
                    tableLayout.setMargins(10, 10, 10, 10);
                    row.setLayoutParams(tableLayout);

                    TextView menu = new TextView(itemView.getContext());
                    menu.setText(all.getMeName());
                    menu.setLayoutParams(new TableRow.LayoutParams(
                            TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 2F));
                    menu.setGravity(View.TEXT_ALIGNMENT_GRAVITY);

                    TextView price = new TextView(itemView.getContext());
                    price.setText(new DecimalFormat("###,###,###").format(all.getMePrice()));
                    price.setLayoutParams(new TableRow.LayoutParams(
                            TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1F));
                    price.setGravity(View.TEXT_ALIGNMENT_GRAVITY);

                    TextView count = new TextView(itemView.getContext());
                    count.setText(String.valueOf(all.getRsMeNum()));
                    count.setLayoutParams(new TableRow.LayoutParams(
                            TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1F));
                    count.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
                    row.addView(menu);
                    row.addView(price);
                    row.addView(count);
                    table.addView(row, 1);
                    sum += all.getMePrice();
                }
                ((TextView) table.findViewById(R.id.total_price)).setText(new DecimalFormat("###,###,###").format(sum));
                builder.setView(table);
                builder.show();
            });

            if (isProceed) {
                review.setText(R.string.cancel);

            } else {
                review.setOnClickListener(v -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                    LayoutInflater inflater = LayoutInflater.from(itemView.getContext());
                    LinearLayout rating = (LinearLayout) inflater.inflate(R.layout.ratingbar, null);
                    PostRun postRun = new PostRun("get_review", activity, PostRun.DATA);
                    postRun.setRunUI(() -> {
                        try {
                            if (postRun.obj.getBoolean("message")) {
                                aleady = true;
                                ReviewVO review = new Gson().fromJson(postRun.obj.getString("review"), ReviewVO.class);
                                ((RatingBar) rating.findViewById(R.id.ratingbar)).setRating(review.getRvRating());
                                ((EditText) rating.findViewById(R.id.review_content)).setText(review.getRvCont());
                                if (review.getRvOwnCont() != null) {
                                    rating.findViewById(R.id.own).setVisibility(View.VISIBLE);
                                    ((TextView) rating.findViewById(R.id.review_own)).setText(review.getRvOwnCont());
                                }
                            } else aleady = false;
                            rating.findViewById(R.id.review_submit).setOnClickListener(l ->
                                    review(rating.findViewById(R.id.review_content), rating.findViewById(R.id.ratingbar)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        builder.setView(rating);
                        builder.show();
                    });
                    postRun.addData("riCode", vo.getRiCode()).start();
                });
            }
        }

        private void review(TextView review, RatingBar rating) {
            PostRun postRun = new PostRun(aleady ? "update_review" : "submit_review", activity, PostRun.DATA);
            postRun.setRunUI(() -> {
                try {
                    if (postRun.obj.getBoolean("message")) {
                        Toast.makeText(activity, aleady ? "수정 완료하였슨비다." : "등록 완료하였습니다.", Toast.LENGTH_SHORT).show();
                        aleady = true;
                    } else {
                        Toast.makeText(activity, "다시 시도해주십시오.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
            postRun.addData("riCode", vo.getRiCode())
                    .addData("rvRating", String.valueOf((int) rating.getRating()))
                    .addData("rvCont", review.getText().toString())
                    .addData("uCode", UserSharedPreferences.user.getUCode())
                    .start();
        }
    }

    private final List<ReservationHotpleVO> list;
    private final boolean isProceed;
    private final Activity activity;

    public ReservationAdapter(List<ReservationHotpleVO> list, boolean isProceed, Activity activity) {
        this.list = list;
        this.isProceed = isProceed;
        this.activity = activity;
    }
    @NonNull
    @NotNull
    @Override
    public ReservationAdapter.ReservationViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_item, parent, false);
        return new ReservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ReservationAdapter.ReservationViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
}
