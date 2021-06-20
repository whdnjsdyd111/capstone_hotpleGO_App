package com.example.hotplego.ui.user.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.hotplego.PostRun;
import com.example.hotplego.R;
import com.example.hotplego.UserSharedPreferences;
import com.example.hotplego.databinding.HotpleMainBinding;
import com.example.hotplego.domain.CourseVO;
import com.example.hotplego.domain.HotpleVO;
import com.example.hotplego.domain.ReviewVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.util.List;

public class HotpleActivity extends AppCompatActivity {
    private HotpleMainBinding binding;
    private final int INFO = 1;
    private final int REVIEW = 2;
    private final int MENU = 3;
    private int selected = 1;
    private Boolean isPick = null;
    Fragment review;
    Fragment info;
    Fragment menu;

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = HotpleMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        HotpleVO vo = (HotpleVO) getIntent().getSerializableExtra("hotple");

        binding.review.setOnClickListener(v -> fragmentView(REVIEW));
        binding.info.setOnClickListener(v -> fragmentView(INFO));
        binding.menu.setOnClickListener(v -> fragmentView(MENU));

        binding.dibs.setOnClickListener(v -> {
            if (isPick != null) {
                PostRun postRun = new PostRun(isPick ? "pick-delete" : "pick-hotple", this, PostRun.DATA);
                postRun.setRunUI(() -> {
                    try {
                        if (Boolean.parseBoolean(postRun.obj.getString("message"))) {
                            Toast.makeText(this, isPick ? "찜 삭제하였습니다." : "찜 완료하였습니다.", Toast.LENGTH_SHORT).show();
                            isPick = !isPick;
                            changeDibs();
                        } else {
                            Toast.makeText(this, "다시 시도해주십시오.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
                postRun.addData("uCode", UserSharedPreferences.user.getUCode())
                        .addData("htId", String.valueOf(vo.getHtId()))
                        .start();
            }
        });

        binding.call.setOnClickListener(v -> {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + vo.getHtTel()));
                startActivity(intent);
        });

        binding.addCourse.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(HotpleActivity.this);
            AlertDialog dialog = builder.create();
            LayoutInflater inflater = LayoutInflater.from(HotpleActivity.this);
            LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.empty_linear, null);
            PostRun postRun = new PostRun("course_cn", this, PostRun.DATA);
            postRun.setRunUI(() -> {
                try {
                    List<CourseVO> course = new Gson().fromJson(postRun.obj.getString("courses"),
                            new TypeToken<List<CourseVO>>() {}.getType());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    params.topMargin = 10;
                    params.bottomMargin = 10;
                    course.forEach(co -> {
                        TextView textView = new TextView(getApplicationContext());
                        textView.setBackgroundTintList(ContextCompat.getColorStateList(HotpleActivity.this, R.color.red_500));
                        textView.setBackgroundColor(R.color.red_500);

                        textView.setLayoutParams(params);
                        textView.setPadding(10, 10, 10, 10);
                        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                        textView.setText(co.getCsTitle());
                        layout.addView(textView);

                        textView.setOnClickListener(e -> {
                            PostRun pr = new PostRun("add-in-course", HotpleActivity.this, PostRun.DATA);
                            pr.setRunUI(() -> {
                                try {
                                    if (Boolean.parseBoolean(pr.obj.getString("message"))) {
                                        Toast.makeText(this, "해당 코스에 추가하였습니다.", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    } else {
                                        Toast.makeText(this, "코스에 이미 존재하거나 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }
                            });
                            pr.addData("csCode", co.getCsCode())
                                    .addData("htId", String.valueOf(vo.getHtId()))
                                    .start();
                        });
                    });
                    dialog.setView(layout);
                    dialog.show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
            postRun.addData("uCode", UserSharedPreferences.user.getUCode())
                    .start();
        });

        binding.address.setText(vo.getHtAddr());
        binding.name.setText(vo.getBusnName());
        if (vo.getHtImg() != null) Glide.with(binding.hotpleImg).load(PostRun.getImageUrl(vo.getUploadPath(), vo.getHtImg(), vo.getFileName())).into(binding.hotpleImg);
        else if (vo.getGoImg() != null) Glide.with(binding.hotpleImg).load(vo.getGoImg()).into(binding.hotpleImg);
        else Glide.with(binding.hotpleImg).load(PostRun.DOMAIN + "/images/logo.jpg").into(binding.hotpleImg);

        PostRun postRun = new PostRun("hotpleReviews", this, PostRun.DATA);
        postRun.setRunUI(() -> {
            try {
                Gson gson = new Gson();
                List<ReviewVO> list = gson.fromJson(postRun.obj.getString("reviews"), new TypeToken<List<ReviewVO>>() {}.getType());
                if (postRun.obj.has("pick")) isPick = gson.fromJson(postRun.obj.getString("pick"), new TypeToken<Boolean>() {}.getType());
                review = new ReviewFragment(list);
                info = new InfoFragment(vo.getHtId());
                menu = new MenuFragment(vo.getHtId());
                fragmentView(selected);

                if (list.size() > 0) {
                    int sum = 0;
                    for (ReviewVO r : list) {
                        sum += r.getRvRating();
                    }
                    float avg = sum / (float) list.size();
                    binding.avgReview.setRating(avg);
                } else {
                    binding.avgReview.setRating(vo.getGoGrd() != null ? vo.getGoGrd().floatValue() : 0f);
                }

                changeDibs();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        postRun.addData("htId", String.valueOf(vo.getHtId()));

        if (UserSharedPreferences.user != null) {
            postRun.addData("uCode", UserSharedPreferences.user.getUCode())
                    .start();
        } else {
            postRun.start();
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void changeDibs() {
        Drawable img = getApplicationContext().getResources().getDrawable(isPick != null && isPick ? R.drawable.leftheart_on : R.drawable.leftheart_off);
        binding.dibs.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
    }

    private void fragmentView(int fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        selected = fragment;

        switch (selected) {
            case REVIEW:
                if (review != null) {
                    transaction.replace(binding.hotpleInfos.getId(), review);
                    transaction.commit();
                }
                break;

            case INFO:
                if (info != null) {
                    transaction.replace(binding.hotpleInfos.getId(), info);
                    transaction.commit();
                }
                break;

            case MENU:
                if (menu != null) {
                    transaction.replace(binding.hotpleInfos.getId(), menu);
                    transaction.commit();
                }
                break;
        }
    }
}
