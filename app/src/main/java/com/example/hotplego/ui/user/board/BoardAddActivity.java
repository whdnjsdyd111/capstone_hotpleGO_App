package com.example.hotplego.ui.user.board;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hotplego.ImageGetterImpl;
import com.example.hotplego.PostRun;
import com.example.hotplego.UserSharedPreferences;
import com.example.hotplego.databinding.BoardAddBinding;
import com.example.hotplego.domain.BoardVO;
import com.google.gson.Gson;

import org.json.JSONException;

public class BoardAddActivity extends AppCompatActivity {
    private BoardAddBinding binding;
    private BoardVO vo;
    private final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = BoardAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        vo = (BoardVO) getIntent().getSerializableExtra("board");

        if (vo != null) {
            binding.boardTitle.setText(vo.getBdTitle());
            binding.boardContents.setText(Html.fromHtml(vo.getBdCont(), new ImageGetterImpl(this, binding.boardContents), null));
        }

        binding.btnSubmit.setOnClickListener(v -> {
            if (binding.boardTitle.getText().toString().isEmpty()) {
                Toast.makeText(this, "제목을 입력해주십시오.", Toast.LENGTH_SHORT).show();
                return;
            } else if (binding.boardContents.getText().toString().isEmpty()){
                Toast.makeText(this, "내용을 입력해주십시오.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (vo == null) {
                vo = new BoardVO();
                vo.setBdTitle(binding.boardTitle.getText().toString());
                vo.setBdCont(Html.toHtml(binding.boardContents.getText()).replaceAll(PostRun.DOMAIN, ""));
                // TODO 유저 정보 SharedPreferences 로 변경
                vo.setUCode(UserSharedPreferences.user.getUCode());
                PostRun postRun = new PostRun("insertBoard", this, PostRun.DATA);
                postRun.addData("board", new Gson().toJson(vo));
                postRun.setRunUI(() -> {
                    try {
                        if (postRun.obj.getBoolean("message")) {
                            Toast.makeText(this, "게시글 등록 완료하였습니다.", Toast.LENGTH_SHORT).show();
                            finish();
                        } else Toast.makeText(this, "등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
                postRun.start();
            } else {
                vo.setBdTitle(binding.boardTitle.getText().toString());
                vo.setBdCont(Html.toHtml(binding.boardContents.getText()).replaceAll(PostRun.DOMAIN, ""));
                PostRun postRun = new PostRun("updateBoard", this, PostRun.DATA);
                postRun.setRunUI(() -> {
                    try {
                        if (postRun.obj.getBoolean("message")) {
                            Toast.makeText(this, "게시글 수정 완료하였습니다.", Toast.LENGTH_SHORT).show();
                            finish();
                        } else Toast.makeText(this, "수정에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
                postRun.addData("board", new Gson().toJson(vo))
                        .addData("uCode", UserSharedPreferences.user.getUCode()) // TODO 유저 정보 SharedPreferences 로 변경
                        .start();
            }
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
                postRun.setRunUI(() -> {
                    try {
                        binding.boardContents.append(Html.fromHtml("<img src='" + PostRun.DOMAIN +  postRun.obj.getString("file") + "' /><br/>",
                                new ImageGetterImpl(getApplicationContext(), binding.boardContents), null));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                postRun.start();
            } else {
                Toast.makeText(this, "이미지를 선택하지 않았습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
