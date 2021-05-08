package com.example.hotplego.ui.user.board;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hotplego.PostRun;
import com.example.hotplego.databinding.FragmentBoardBinding;
import com.example.hotplego.domain.BoardVO;
import com.example.hotplego.ui.user.board.recyclerview.BoardAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.util.List;

public class BoardFragment extends Fragment implements BoardAdapter.OnItemClickListener {

    private FragmentBoardBinding binding;
    private BoardAdapter adapter;
    private final int BOARD_ADD = 1;
    private String keyword = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBoardBinding.inflate(inflater, container, false);

        adapter = new BoardAdapter(this);
        binding.boardRecyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        binding.boardRecyclerView.setLayoutManager(manager);
        binding.addBoardButton.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), BoardAddActivity.class));
        });
        binding.boardSearch.setOnFocusChangeListener((v, b) -> {
            if (!b) {
                keyword = binding.boardSearch.getText().toString();
                if (!keyword.isEmpty()) {
                    loadView(keyword);
                } else {
                    loadView();
                }
            }
        });
        loadView();
        return binding.getRoot();
    }

    @Override
    public void onItemClick(View v, int position, BoardVO board) {
        Intent intent = new Intent(getContext(), BoardDetailActivity.class);
        intent.putExtra("bdCode", board.getBdCode());
        startActivity(intent);
    }

    private void loadView() {
        PostRun postRun = new PostRun("getBoards", getActivity());
        postRun.setRunUI(() -> {
            try {
                adapter.setData(new Gson().fromJson(postRun.obj.getString("boards"), new TypeToken<List<BoardVO>>() {}.getType()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapter.notifyDataSetChanged();
            System.gc();
        });
        postRun.start();
    }

    private void loadView(String keyword) {
        PostRun postRun = new PostRun("getBoards", getActivity(), PostRun.DATA);
        postRun.addData("keyword", keyword);
        postRun.setRunUI(() -> {
            try {
                adapter.setData(new Gson().fromJson(postRun.obj.getString("boards"), new TypeToken<List<BoardVO>>() {}.getType()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.gc();
        });
        postRun.start();
    }

    @Override
    public void onResume() {
        if (keyword.isEmpty()) loadView();
        else loadView(keyword);
        super.onResume();
    }
}