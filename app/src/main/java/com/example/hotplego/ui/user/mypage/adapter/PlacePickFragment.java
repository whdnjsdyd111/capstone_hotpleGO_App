package com.example.hotplego.ui.user.mypage.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotplego.PostRun;
import com.example.hotplego.R;
import com.example.hotplego.UserSharedPreferences;
import com.example.hotplego.databinding.FragmentPickPlaceBinding;
import com.example.hotplego.domain.HotpleVO;
import com.example.hotplego.ui.user.home.HotpleActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PlacePickFragment extends Fragment {

    private List<HotpleVO> picks = null;
    private FragmentPickPlaceBinding binding;
    private RecyclerView recyclerView;
    private PickAdapter pickAdapter = null;

    public PlacePickFragment() { }

    public static Fragment newInstance() {
        return new PlacePickFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPickPlaceBinding.inflate(inflater, container, false);

        recyclerView = binding.pickList;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        // 스와이프 시 삭제 정의
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull  RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                PostRun postRun = new PostRun("pick-delete", getActivity(), PostRun.DATA);
                postRun.setRunUI(() -> {
                    try {
                        if (Boolean.parseBoolean(postRun.obj.getString("message"))) {
                            picks.remove(position);
                            pickAdapter.notifyDataSetChanged();
                            Toast.makeText(getContext(), "찜 삭제 완료하였습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "다시 시도해주십시오.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
                postRun.addData("uCode", UserSharedPreferences.user.getUCode())
                        .addData("htId", String.valueOf(picks.get(position).getHtId()))
                        .start();
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        initView();
        super.onResume();
    }

    private void initView() {
        PostRun postRun = new PostRun("pick-list", getActivity(), PostRun.DATA);
        postRun.setRunUI(() -> {
            try {
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss.SSS").create();
                picks = gson.fromJson(postRun.obj.getString("picks"), new TypeToken<List<HotpleVO>>() {}.getType());
                pickAdapter = new PickAdapter(picks, this::onPlaceItemClick);
                recyclerView.setAdapter(pickAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        postRun.addData("uCode", UserSharedPreferences.user.getUCode())
                .start();
    }

    private void onPlaceItemClick(HotpleVO pickData) {
        Intent intent = new Intent(getContext(), HotpleActivity.class);
        intent.putExtra("hotple", pickData);
        startActivity(intent);
    }
}
