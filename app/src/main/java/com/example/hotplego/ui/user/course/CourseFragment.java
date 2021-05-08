package com.example.hotplego.ui.user.course;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.hotplego.R;
import com.example.hotplego.databinding.FragmentCourseBinding;
import com.example.hotplego.ui.user.course.pager.CoursePagerAdapter;

public class CourseFragment extends Fragment {

    private FragmentCourseBinding binding;
    private CoursePagerAdapter adapter;
    public static final Integer[] COURSE_COLORS = {
            R.color.purple_200,
            R.color.teal_200,
            R.color.green_200,
            R.color.red_200,
            R.color.yellow_200,
            R.color.purple_500,
            R.color.purple_700,
            R.color.teal_700,
            R.color.green_500,
            R.color.green_700,
            R.color.red_500,
            R.color.red_700,
            R.color.yellow_500
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCourseBinding.inflate(inflater, container, false);

        adapter = new CoursePagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFrag(new CourseUsingFragment());
        adapter.addFrag(new CourseMakingFragment());
        adapter.addFrag(new CourseUsedFragment());

        binding.pager.setAdapter(adapter);
        binding.tabLayout.setupWithViewPager(binding.pager);
        binding.tabLayout.getTabAt(0).setText(R.string.course_using);
        binding.tabLayout.getTabAt(1).setText(R.string.course_making);
        binding.tabLayout.getTabAt(2).setText(R.string.course_used);
        return binding.getRoot();
    }
}