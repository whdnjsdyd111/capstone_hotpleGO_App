package com.example.hotplego.ui.user.board;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hotplego.ImageGetterImpl;
import com.example.hotplego.PostRun;
import com.example.hotplego.R;
import com.example.hotplego.databinding.BoardDetailsBinding;
import com.example.hotplego.domain.BoardVO;
import com.example.hotplego.domain.CommentVO;
import com.example.hotplego.domain.UserVO;
import com.example.hotplego.ui.user.board.recyclerview.BoardCommentAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BoardDetailActivity extends AppCompatActivity {
    private BoardDetailsBinding binding;
    private BoardVO vo;
    private BoardCommentAdapter adapter;
    private boolean bookmark = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = BoardDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new BoardCommentAdapter();
        binding.commentRecyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        binding.commentRecyclerView.setLayoutManager(manager);
        loadView();

        binding.bookmark.setOnClickListener(v -> {
            PostRun mark = new PostRun("bookmark", this, PostRun.DATA);
            mark.setRunUI(() -> {
                try {
                    if (mark.obj.getBoolean("message")) {
                        binding.bookmark.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_up_down));
                        bookmarkToggle(bookmark = !bookmark);
                    } else Toast.makeText(this, "다시 시도해주십시오.", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
            mark.addData("bookmark", String.valueOf(bookmark))
                    .addData("uCode", "whdnjsdyd111@naver.com/A/")
                    .addData("bdCode", vo.getBdCode())
                    .start();
        });

        binding.bButtonDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("정말 삭제하시겠습니까?");
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    PostRun delete = new PostRun("deleteBoard", BoardDetailActivity.this, PostRun.DATA);
                    delete.setRunUI(() -> {
                        try {
                            if (delete.obj.getBoolean("message")) {
                                Toast.makeText(BoardDetailActivity.this, "삭제 완료하였습니다.", Toast.LENGTH_SHORT).show();
                                finish();
                            } else Toast.makeText(BoardDetailActivity.this, "다시 시도해주십시오.", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                    delete.addData("bdCode", vo.getBdCode())
                            .addData("uCode", "whdnjsdyd111@naver.com/A/")
                            .start();
                }
            });
            builder.setNegativeButton("취소", null);
            builder.show();
        });

        binding.bButtonModify.setOnClickListener(v -> {
            Intent intent = new Intent(this, BoardAddActivity.class);
            intent.putExtra("board", vo);
            startActivity(intent);
        });
    }

    private void bookmarkToggle(boolean isMark) {
        if (isMark) {
            binding.bookmark.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.red_500));
        } else {
            binding.bookmark.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray_200));
        }
    }

    private void loadView() {
        PostRun postRun = new PostRun("getBoard", this, PostRun.DATA);
        postRun.setRunUI(() -> {
            try {
                Gson gson = new Gson();
                vo = gson.fromJson(postRun.obj.getString("board"), BoardVO.class);
                vo.setBdCont(vo.getBdCont().replaceAll("src=\"", "src=\"" + PostRun.DOMAIN));
                binding.boardUser.setText(vo.getNick());
                binding.boardContents.setText(vo.getBdTitle());
                binding.boardText.setText(Html.fromHtml(vo.getBdCont(), new ImageGetterImpl(this, binding.boardText), null));
                // TODO 유저 정보
                if (vo.getUCode().equals("whdnjsdyd111@naver.com/A/")) binding.boardMaster.setVisibility(View.VISIBLE);
                bookmark = postRun.obj.getBoolean("bookmark");
                bookmarkToggle(bookmark);

                adapter.setData(gson.fromJson(postRun.obj.getString("comment"), new TypeToken<List<CommentVO>>() {}.getType()),
                        gson.fromJson(postRun.obj.getString("comReco"), new TypeToken<Map<String, String>>() {}.getType()),
                        this);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        // TODO 유저 정보 SharedPreferences 로 변경
        postRun.addData("bdCode", getIntent().getStringExtra("bdCode")).addData("uCode", "whdnjsdyd111@naver.com/A/");
        postRun.start();
    }

    @Override
    protected void onResume() {
        loadView();
        super.onResume();
    }
}
