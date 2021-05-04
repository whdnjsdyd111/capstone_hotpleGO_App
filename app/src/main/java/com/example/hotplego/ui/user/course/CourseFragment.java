package com.example.hotplego.ui.user.course;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.hotplego.R;
import com.example.hotplego.databinding.FragmentCourseBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

public class CourseFragment extends Fragment {

    private FragmentCourseBinding binding;
    private CoursePagerAdapter adapter;
    private final String[] titles = new String[] { "이용 중", "코스 짜기", "이용 내역" };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCourseBinding.inflate(inflater, container, false);

        adapter = new CoursePagerAdapter(this);
        adapter.addFrag(new CourseUsingFragment());
        adapter.addFrag(new CourseUsingFragment());
        adapter.addFrag(new CourseUsingFragment());

        binding.pager.setAdapter(adapter);

        new TabLayoutMediator(binding.tabLayout, binding.pager, ((tab, position) -> tab.setText(titles[position]))).attach();

        return binding.getRoot();
    }
}