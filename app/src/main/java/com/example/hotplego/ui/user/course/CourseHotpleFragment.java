package com.example.hotplego.ui.user.course;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.hotplego.PostRun;
import com.example.hotplego.databinding.CourseHotpleInfoBinding;
import com.example.hotplego.domain.CourseInfoVO;

import org.jetbrains.annotations.NotNull;

public class CourseHotpleFragment extends Fragment {
    private CourseHotpleInfoBinding binding;
    private CourseInfoVO vo;
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = CourseHotpleInfoBinding.inflate(inflater, container, false);

        // TODO 로컬 이미지로 대체
        if (vo.getUuid() == null) Glide.with(this).load(PostRun.DOMAIN + "/images/logo.jpg").into(binding.courseHotpleImg);
        else Glide.with(this).load(PostRun.DOMAIN + vo.getUploadPath() + vo.getUuid() + "_" + vo.getFileName()).into(binding.courseHotpleImg);
        binding.courseIndex.setText(String.valueOf(vo.getCiIndex()));
        binding.courseHotpleAddress.setText(vo.getHtAddr());
        binding.courseHotpleTitle.setText(vo.getBusnName());

        return binding.getRoot();
    }

    public CourseHotpleFragment(CourseInfoVO vo) {
        this.vo = vo;
    }
}
