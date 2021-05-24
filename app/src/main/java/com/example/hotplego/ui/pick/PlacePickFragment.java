package com.example.hotplego.ui.pick;

import android.content.Intent;
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

import com.example.hotplego.PickAdapter;
import com.example.hotplego.PickData;
import com.example.hotplego.PickDetailsActivity;
import com.example.hotplego.R;

import java.util.ArrayList;
import java.util.List;

// 장소별 목록을 보여주는 프래그먼트
public class PlacePickFragment extends Fragment {
    // 장소 데이터
    private static final List<PickData> placePickData = new ArrayList<>();

    static {
        placePickData.add(new PickData("yy-mm-dd hh:mm:ss", "핫플장소명A", "핫플주소G", 5.0f, R.drawable.point));
        placePickData.add(new PickData("yy-mm-dd hh:mm:15", "핫플장소명B", "핫플주소H", 4.5f, R.drawable.point));
        placePickData.add(new PickData("yy-mm-dd hh:03:15", "핫플장소명C", "핫플주소I", 4.0f, R.drawable.point));
        placePickData.add(new PickData("yy-mm-dd 15:03:15", "핫플장소명D", "핫플주소J", 3.5f, R.drawable.point));
        placePickData.add(new PickData("yy-mm-02 15:03:15", "핫플장소명E", "핫플주소K", 3.0f, R.drawable.point));
        placePickData.add(new PickData("yy-04-02 15:03:15", "핫플장소명F", "핫플주소L", 2.5f, R.drawable.point));
        placePickData.add(new PickData("21-04-02 15:03:15", "핫플장소명G", "핫플주소M", 2.0f, R.drawable.point));
    }

    private final PickAdapter pickAdapter = new PickAdapter(placePickData, this::onPlaceItemClick);

    public PlacePickFragment() { }

    public static Fragment newInstance() {
        return new com.example.hotplego.ui.pick.PlacePickFragment();
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

    private void onPlaceItemClick(PickData pickData) {
    }
}
