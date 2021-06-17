package com.example.hotplego.ui.user.course;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hotplego.databinding.CourseDetailBinding;
import com.example.hotplego.domain.CourseInfoVO;
import com.example.hotplego.domain.CourseVO;
import com.example.hotplego.ui.user.course.recyclerview.CourseAdapter;
import com.skt.Tmap.TMapView;

import java.util.List;

public class CourseDetailActivity extends AppCompatActivity {

    private CourseDetailBinding binding;
    private CourseAdapter adapter;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = CourseDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TMapView tMapView = new TMapView(getApplicationContext());

        tMapView.setSKTMapApiKey("l7xxcd9bdd0942b542fd8c7be931fc11a3b4");
        binding.tmapUsing.addView(tMapView);

        CourseVO vo = (CourseVO) getIntent().getSerializableExtra("course");
        List<CourseInfoVO> list = (List<CourseInfoVO>) getIntent().getSerializableExtra("courseInfo");
        String kind = getIntent().getStringExtra("kind");

        if (kind.equals("myCourse")) binding.onlyMaking.setVisibility(View.VISIBLE);
        else binding.onlyUsed.setVisibility(View.VISIBLE);

        binding.courseWith.setText("함께하는 인원 : " + vo.getCsWith());
        binding.courseNum.setText("인원 : " + vo.getCsNum());
        adapter = new CourseAdapter(list, kind.equals("used"), this);
        binding.courseRecyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        binding.courseRecyclerView.setLayoutManager(manager);

    }
}
