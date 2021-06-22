package com.example.hotplego.ui.user.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hotplego.databinding.CourseUsedBinding;
import com.example.hotplego.domain.HotpleVO;
import com.example.hotplego.ui.user.home.CourseDetailActivity;
import com.example.hotplego.ui.user.home.adapter.CourseInfoAdapter;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class CourseFragment extends Fragment implements CourseInfoAdapter.OnItemClickListener {
    private CourseUsedBinding binding;
    private CourseInfoAdapter adapter;
    private Map<String, List<HotpleVO>> filteredCourse = null;

    public CourseFragment(Map<String, List<HotpleVO>> filteredCourse) {
        this.filteredCourse = filteredCourse;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = CourseUsedBinding.inflate(inflater, container, false);

        adapter = new CourseInfoAdapter(this);
        binding.courseInfoRecyclerView.setAdapter(adapter);
        adapter.setData(filteredCourse);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        binding.courseInfoRecyclerView.setLayoutManager(manager);

        return binding.getRoot();
    }

    @Override
    public void onItemClick(String key) {
        Intent intent = new Intent(getContext(), CourseDetailActivity.class);
        intent.putExtra("csCode", key);
        startActivity(intent);
    }
}
