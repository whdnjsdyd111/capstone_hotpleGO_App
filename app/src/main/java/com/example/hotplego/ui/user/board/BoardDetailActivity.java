package com.example.hotplego.ui.user.board;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hotplego.ImageGetterImpl;
import com.example.hotplego.PostRun;
import com.example.hotplego.databinding.BoardDetailsBinding;
import com.example.hotplego.domain.BoardVO;
import com.example.hotplego.domain.UserVO;
import com.google.gson.Gson;

import org.json.JSONException;

import java.sql.Timestamp;
import java.util.Date;

public class BoardDetailActivity extends AppCompatActivity {
    private BoardDetailsBinding binding;
    private String bdCode;
    private UserVO user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = BoardDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // TODO 유저 정보 SharedPreferences 로 변경
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

        bdCode = getIntent().getStringExtra("bdCode");
        PostRun postRun = new PostRun("getBoard", this);
        postRun.addData("bdCode", bdCode);
        postRun.setRunUI(() -> {
            BoardVO vo = null;
            Log.i("aaa", postRun.obj.toString());
            try {
                vo = new Gson().fromJson(postRun.obj.getString("board"), BoardVO.class);
                vo.setBdCont(vo.getBdCont().replaceAll("src=\"", "src=\"" + PostRun.DOMAIN));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            binding.boardUser.setText(vo.getNick());
            binding.boardContents.setText(vo.getBdTitle());
            binding.boardText.setText(Html.fromHtml(vo.getBdCont(), new ImageGetterImpl(this, binding.boardText), null));
            if (vo.getUCode().equals(user.getUCode())) binding.boardMaster.setVisibility(View.VISIBLE);
        });
        postRun.start();
    }
}
