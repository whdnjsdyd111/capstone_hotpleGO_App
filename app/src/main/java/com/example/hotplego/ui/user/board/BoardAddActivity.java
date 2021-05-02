package com.example.hotplego.ui.user.board;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.SpannedString;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hotplego.PostRun;
import com.example.hotplego.databinding.BoardAddBinding;
import com.example.hotplego.domain.BoardVO;
import com.example.hotplego.domain.UserVO;
import com.google.gson.Gson;

import org.json.JSONException;

import java.sql.Timestamp;
import java.util.Date;

public class BoardAddActivity extends AppCompatActivity {
    private BoardAddBinding binding;
    private UserVO user;
    private final int REQUEST_CODE_ADDED = 1;
    private final int REQUEST_CODE_FAILURE = 2;
    private final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = BoardAddBinding.inflate(getLayoutInflater());
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

        binding.btnSubmit.setOnClickListener(v -> {
            if (binding.boardTitle.getText().toString().isEmpty()) {
                Toast.makeText(this, "제목을 입력해주십시오.", Toast.LENGTH_SHORT).show();
                return;
            } else if (binding.boardContents.getText().toString().isEmpty()){
                Toast.makeText(this, "내용을 입력해주십시오.", Toast.LENGTH_SHORT).show();
                return;
            }
            BoardVO vo = new BoardVO();
            vo.setBdTitle(binding.boardTitle.getText().toString());
            vo.setBdCont(Html.toHtml(new SpannedString(binding.boardContents.getText().toString())));
            vo.setUCode(user.getUCode());
            PostRun postRun = new PostRun("insertBoard", this);
            postRun.addData("board", new Gson().toJson(vo));
            postRun.setRunUI(() -> {
                try {
                    if (postRun.obj.getBoolean("message")) {
                        Toast.makeText(this, "게시글 등록 완료하였습니다.", Toast.LENGTH_SHORT).show();
                    } else Toast.makeText(this, "등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
            postRun.start();
        });
        binding.cameraButton.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, PICK_IMAGE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            if (resultCode == RESULT_OK) {
                PostRun postRun = new PostRun("upload", this, PostRun.IMAGES);
                postRun.addImage("upload", getApplicationContext(), data.getData());
                postRun.start();
            } else {
                Toast.makeText(this, "이미지를 선택하지 않았습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
