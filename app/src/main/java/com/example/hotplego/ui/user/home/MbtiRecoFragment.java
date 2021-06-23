package com.example.hotplego.ui.user.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotplego.databinding.MbtiRecoBinding;
import com.example.hotplego.domain.HotpleVO;
import com.example.hotplego.ui.user.home.adapter.CourseInfoAdapter;
import com.example.hotplego.ui.user.mypage.adapter.PickAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class MbtiRecoFragment extends Fragment implements CourseInfoAdapter.OnItemClickListener {
    private MbtiRecoBinding binding;
    private List<HotpleVO> list;
    Map<String, List<HotpleVO>> map;
    private RecyclerView hotple;
    private RecyclerView course;
    private PickAdapter adapterHotple;
    private CourseInfoAdapter adapterCourse;

    public MbtiRecoFragment() {}

    public MbtiRecoFragment(List<HotpleVO> list, Map<String, List<HotpleVO>> map) {
        this.list = list;
        this.map = map;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = MbtiRecoBinding.inflate(inflater, container, false);

        hotple = binding.pickList;;
        course = binding.courseInfoRecyclerView;

        hotple.setLayoutManager(new LinearLayoutManager(requireContext()));
        course.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapterHotple = new PickAdapter(list, this::onPlaceItemClick);
        hotple.setAdapter(adapterHotple);

        adapterCourse = new CourseInfoAdapter(this);
        adapterCourse.setData(map);
        course.setAdapter(adapterCourse);

        return binding.getRoot();
    }

    private void onPlaceItemClick(HotpleVO pickData) {
        Intent intent = new Intent(getContext(), HotpleActivity.class);
        intent.putExtra("hotple", pickData);
        startActivity(intent);
    }

    @Override
    public void onItemClick(String key) {
        Intent intent = new Intent(getContext(), CourseDetailActivity.class);
        intent.putExtra("csCode", key);
        startActivity(intent);
    }
}
