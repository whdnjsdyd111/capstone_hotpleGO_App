package com.example.hotplego.ui.user.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.hotplego.PostRun;
import com.example.hotplego.databinding.HotpleMainBinding;
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
    Fragment review;
    Fragment info;
    Fragment menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = HotpleMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        HotpleVO vo = (HotpleVO) getIntent().getSerializableExtra("hotple");

        binding.review.setOnClickListener(v -> fragmentView(REVIEW));
        binding.info.setOnClickListener(v -> fragmentView(INFO));
        binding.menu.setOnClickListener(v -> fragmentView(MENU));

        binding.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"));
                startActivity(intent);
            }
        });

        binding.address.setText(vo.getHtAddr());
        binding.name.setText(vo.getBusnName());
        if (vo.getHtImg() != null) Glide.with(binding.hotpleImg).load(PostRun.getImageUrl(vo.getUploadPath(), vo.getHtImg(), vo.getFileName())).into(binding.hotpleImg);
        else if (vo.getGoImg() != null) Glide.with(binding.hotpleImg).load(vo.getGoImg()).into(binding.hotpleImg);
        else Glide.with(binding.hotpleImg).load(PostRun.DOMAIN + "/images/logo.jpg").into(binding.hotpleImg);

        PostRun postRun = new PostRun("hotpleReviews", this, PostRun.DATA);
        postRun.setRunUI(() -> {
            try {
                List<ReviewVO> list = new Gson().fromJson(postRun.obj.getString("reviews"), new TypeToken<List<ReviewVO>>() {}.getType());
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
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        postRun.addData("htId", String.valueOf(vo.getHtId()))
                .start();
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
