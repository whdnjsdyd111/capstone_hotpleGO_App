package com.example.hotplego.ui.user.mypage.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotplego.R;
import com.example.hotplego.domain.HotpleVO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

// 장소별 목록을 보여주는 프래그먼트
public class PlacePickFragment extends Fragment {
    // 장소 데이터
    private static final List<HotpleVO> placePickData = new ArrayList<>();

    static {
        HotpleVO vo = new HotpleVO();
        vo.setPickTime(new Timestamp(System.nanoTime()));
        vo.setBusnName("가게1");
        vo.setHtAddr("대구광역시");
        vo.setGoGrd(5.0);
        placePickData.add(vo);
        placePickData.add(vo);
        placePickData.add(vo);
    }

    private final PickAdapter pickAdapter = new PickAdapter(placePickData, this::onPlaceItemClick);

    public PlacePickFragment() { }

    public static Fragment newInstance() {
        return new PlacePickFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pick_place, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.pick_list);
        recyclerView.setAdapter(pickAdapter);

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
                placePickData.remove(position);
                pickAdapter.notifyDataSetChanged();
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void onPlaceItemClick(HotpleVO pickData) {
    }
}
