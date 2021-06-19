package com.example.hotplego.ui.user.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.hotplego.PostRun;
import com.example.hotplego.UserSharedPreferences;
import com.example.hotplego.databinding.UserMypageBinding;
import com.example.hotplego.domain.UserVO;
import com.example.hotplego.ui.manager.MainActivity;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyPageFragment extends Fragment {

    private UserMypageBinding binding;

    UserVO user;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = UserMypageBinding.inflate(inflater, container, false);

        initView();

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
                postRun.addData("uCode", UserSharedPreferences.user.getUCode())
                        .addData("nick", nick);
                postRun.setRunUI(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            int status = postRun.obj.getInt("status");
                            if (status == 200) {
                                UserSharedPreferences.user.setNick(nick);
                                UserSharedPreferences.getInstance().update(getActivity());
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

        binding.dibs.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), PickActivity.class));
        });

        if (user.getUCode().split("/")[1].equals("M")) binding.managerMode.setVisibility(View.VISIBLE);

        binding.managerIn.setOnClickListener(v -> startActivity(new Intent(getActivity(), MainActivity.class)));

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        initView();
        super.onResume();
    }

    private void initView() {
        user = UserSharedPreferences.user;

        binding.uCode.setText(user.getUCode().split("/")[0]);
        binding.regDate.setText(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(user.getRegDate().getTime())));
        binding.nick.setText(user.getNick());
//        binding.birth.setText(new SimpleDateFormat("yyyy-MM-dd").format(user.getBirth()));
        Log.i("유저", user.toString());
        binding.mbti.setText(user.getMbti());
        Glide.with(this).load(user.getProfileImg()).into(binding.userProfile);
    }
}