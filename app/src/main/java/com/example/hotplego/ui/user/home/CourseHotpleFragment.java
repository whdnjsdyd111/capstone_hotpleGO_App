package com.example.hotplego.ui.user.home;

import android.annotation.SuppressLint;
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
import com.example.hotplego.domain.HotpleVO;

import org.jetbrains.annotations.NotNull;

public class CourseHotpleFragment extends Fragment {
    private CourseHotpleInfoBinding binding;
    private HotpleVO vo = null;
    private int index;

    @SuppressLint("SetTextI18n")
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = CourseHotpleInfoBinding.inflate(inflater, container, false);

        if (vo.getHtImg() != null) Glide.with(this).load(PostRun.getImageUrl(vo.getUploadPath(), vo.getHtImg(), vo.getFileName())).into(binding.courseHotpleImg);
        else if (vo.getGoImg() == null) Glide.with(this).load(vo.getGoImg()).into(binding.courseHotpleImg);
        else Glide.with(this).load(PostRun.DOMAIN + "/images/logo.jpg").into(binding.courseHotpleImg);
        binding.courseIndex.setText(index + "번");
        binding.courseHotpleAddress.setText(vo.getHtAddr());
        binding.courseHotpleTitle.setText(vo.getBusnName());

        return binding.getRoot();
    }

    public CourseHotpleFragment(HotpleVO vo, int index) {
        this.vo = vo;
        this.index = index + 1;
    }
}
