package com.example.hotplego.ui.user.mypage;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hotplego.PostRun;
import com.example.hotplego.R;
import com.example.hotplego.UserSharedPreferences;
import com.example.hotplego.databinding.MypageMbtiBinding;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class MBTIActivity extends AppCompatActivity {
    private MypageMbtiBinding binding;

    private static Map<String, Integer> mbtis;
    private String selected = null;
    private String message =  "당신의 성격 - ";

    private void initMBTI() {
        mbtis = new HashMap<>();
        mbtis.put("ENFJ", R.id.ENFJ);
        mbtis.put("ENTJ", R.id.ENTJ);
        mbtis.put("ESTJ", R.id.ESTJ);
        mbtis.put("ESFJ", R.id.ESFJ);
        mbtis.put("ENFP", R.id.ENFP);
        mbtis.put("ENTP", R.id.ENTP);
        mbtis.put("ESTP", R.id.ESTP);
        mbtis.put("ESFP", R.id.ESFP);
        mbtis.put("INFP", R.id.INFP);
        mbtis.put("INTP", R.id.INTP);
        mbtis.put("ISTP", R.id.ISTP);
        mbtis.put("ISFP", R.id.ISFP);
        mbtis.put("INFJ", R.id.INFJ);
        mbtis.put("INTJ", R.id.INTJ);
        mbtis.put("ISTJ", R.id.ISTJ);
        mbtis.put("ISFJ", R.id.ISFJ);
    }

    private void initSelected() {
        // TODO 사용자 uCode + SharedPreferences 의 mbti 교체, DB 에서 조회 X
        PostRun postRun = new PostRun("getMbti", this, PostRun.DATA);
        postRun.addData("uCode", UserSharedPreferences.user.getUCode());
        postRun.setRunUI(() -> {
            try {
                selected = postRun.obj.getString("mbti");
                if (!selected.isEmpty()) {
                    binding.selected.setText(message + selected);
                } else {
                    selected = null;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        postRun.start();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MypageMbtiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (mbtis == null) initMBTI();
        initSelected();

        for (String mbti : mbtis.keySet()) {
            findViewById(mbtis.get(mbti)).setOnClickListener(v -> {
                if (selected == null) post(mbti);
                else if (!v.equals(findViewById(mbtis.get(selected)))) post(mbti);
            });
        }
    }

    private void post(String mbti) {
        // TODO 유저 정보
        PostRun postRun = new PostRun("saveMbti", this, PostRun.DATA);
        postRun.addData("uCode", UserSharedPreferences.user.getUCode())
                .addData("mbti", mbti);
        postRun.setRunUI(() -> {
            try {
                String message = postRun.obj.getString("message");
                if (mbti.equals(message)) {
                    // TODO SharedPreferences 갱신
                    binding.selected.setText(MBTIActivity.this.message + mbti);
                    message += "로 수정하였습니다.";
                    UserSharedPreferences.user.setMbti(mbti);
                    UserSharedPreferences.getInstance().update(this);
                }
                Toast.makeText(MBTIActivity.this, message, Toast.LENGTH_SHORT).show();
                selected = mbti;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        postRun.start();
    }
}
