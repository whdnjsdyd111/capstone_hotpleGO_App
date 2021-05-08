package com.example.hotplego.ui.user.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hotplego.PostRun;
import com.example.hotplego.databinding.MypageBookmarkBinding;
import com.example.hotplego.domain.BoardVO;
import com.example.hotplego.ui.user.board.recyclerview.BoardAdapter;
import com.example.hotplego.ui.user.board.BoardDetailActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.util.List;

public class BookmarkActivity extends AppCompatActivity implements BoardAdapter.OnItemClickListener {

    private MypageBookmarkBinding binding;
    private BoardAdapter adapter;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MypageBookmarkBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new BoardAdapter(this);
        binding.boardRecyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        binding.boardRecyclerView.setLayoutManager(manager);

        loadView();
    }

    private void loadView() {
        PostRun postRun = new PostRun("getBookmark", this, PostRun.DATA);
        postRun.setRunUI(() -> {
            try {
                adapter.setData(new Gson().fromJson(postRun.obj.getString("boards"), new TypeToken<List<BoardVO>>() {}.getType()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.gc();
        });
        postRun.addData("uCode", "whdnjsdyd111@naver.com/A/").start();
        // TODO 유저
    }

    @Override
    public void onItemClick(View v, int position, BoardVO board) {
        Intent intent = new Intent(this, BoardDetailActivity.class);
        intent.putExtra("bdCode", board.getBdCode());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        loadView();
        super.onResume();
    }
}
