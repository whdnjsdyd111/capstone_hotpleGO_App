package com.example.hotplego.ui.user.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.hotplego.PostRun;
import com.example.hotplego.databinding.FragmentMypageBinding;
import com.example.hotplego.domain.UserVO;

import org.json.JSONException;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyPageFragment extends Fragment {

    private FragmentMypageBinding binding;

    UserVO user;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMypageBinding.inflate(inflater, container, false);

        user = new UserVO();
        user.setUCode("whdnjsdyd111@naver.com/A/");
        user.setPw("argjnerjgnerntklen");
        user.setNick("월롱");
        user.setProfileImg("https://lh3.googleusercontent.com/a-/AOh14Gjm6G76xvDpjc6mgtYGeAlFU4erv5XYw8inNWjReg=s96-c");
        user.setBirth(new Date());
        user.setGender('M');
        user.setPhone("01068480083");
        user.setPoint(0L);
        user.setMbti("ENFJ");
        user.setRegDate(new Timestamp(1619423443624L));

        binding.uCode.setText(user.getUCode().split("/")[0]);
        binding.regDate.setText(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(user.getRegDate().getTime())));
        binding.nick.setText(user.getNick());
        binding.birth.setText(new SimpleDateFormat("yyyy-MM-dd").format(user.getBirth()));
        binding.mbti.setText(user.getMbti());
        Glide.with(this).load(user.getProfileImg()).into(binding.userProfile);
        binding.mbtiPage.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), MBTIActivity.class));
        });
        binding.tastePage.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), TasteActivity.class));
        });
        binding.updateNick.setOnClickListener(v -> {
            String nick = binding.nick.getText().toString();
            if (!nick.isEmpty()) {
                PostRun postRun = new PostRun("changeNick", this.getActivity(), PostRun.DATA);
                postRun.addData("uCode", "whdnjsdyd111@naver.com/A/")
                        .addData("nick", nick);
                postRun.setRunUI(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            int status = postRun.obj.getInt("status");
                            if (status == 200) {
                                // TODO SharedPreferences 의 유저 정보 갱신
                            }
                            Toast.makeText(MyPageFragment.this.getContext(), postRun.obj.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                postRun.start();
            } else {
                Toast.makeText(MyPageFragment.this.getContext(), "닉네임을 입력해 주십시오.", Toast.LENGTH_SHORT).show();
            }
        });
        binding.bookmark.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), BookmarkActivity.class));
        });

        return binding.getRoot();
    }
}