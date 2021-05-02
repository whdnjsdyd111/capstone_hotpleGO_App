package com.example.hotplego.ui.user.board;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hotplego.PostRun;
import com.example.hotplego.R;
import com.example.hotplego.databinding.FragmentBoardBinding;
import com.example.hotplego.domain.BoardVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.util.List;

public class BoardFragment extends Fragment implements BoardAdapter.OnItemClickListener {

    private FragmentBoardBinding binding;
    private BoardAdapter adapter;
    private final int BOARD_ADD = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBoardBinding.inflate(inflater, container, false);

        adapter = new BoardAdapter(this);
        binding.boardRecyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        binding.boardRecyclerView.setLayoutManager(manager);
        PostRun postRun = new PostRun("getBoards", getActivity());
        postRun.setRunUI(() -> {
            try {
                adapter.setData(new Gson().fromJson(postRun.obj.getString("boards"), new TypeToken<List<BoardVO>>() {}.getType()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapter.notifyDataSetChanged();
        });
        postRun.start();
        binding.addBoardButton.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), BoardAddActivity.class);
            startActivityForResult(intent, BOARD_ADD);
        });

        return binding.getRoot();
    }

    @Override
    public void onItemClick(View v, int position, BoardVO board) {
        Intent intent = new Intent(getContext(), BoardDetailActivity.class);
        intent.putExtra("bdCode", board.getBdCode());
        startActivity(intent);
    }
}