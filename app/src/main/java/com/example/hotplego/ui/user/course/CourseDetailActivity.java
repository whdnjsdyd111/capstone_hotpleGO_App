package com.example.hotplego.ui.user.course;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hotplego.PostRun;
import com.example.hotplego.UserSharedPreferences;
import com.example.hotplego.databinding.CourseDetailBinding;
import com.example.hotplego.domain.CourseInfoVO;
import com.example.hotplego.domain.CourseVO;
import com.example.hotplego.ui.user.course.recyclerview.CourseAdapter;
import com.skt.Tmap.TMapView;

import org.json.JSONException;

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

        if (kind.equals("dibs")) binding.isDibs.setVisibility(View.VISIBLE);
        else binding.noDibs.setVisibility(View.VISIBLE);

        if (!kind.equals("myCourse")) {
            binding.useCourse.setVisibility(View.GONE);
            binding.deleteCourse.setVisibility(View.GONE);
        }

        binding.courseWith.setText("함께하는 인원 : " + vo.getCsWith());
        binding.courseNum.setText("인원 : " + vo.getCsNum());
        adapter = new CourseAdapter(list, kind, this);
        binding.courseRecyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        binding.courseRecyclerView.setLayoutManager(manager);

        binding.dibs.setOnClickListener(v -> {
            PostRun postRun = new PostRun("pick-course", this, PostRun.DATA);
            postRun.setRunUI(() -> {
                try {
                    if (Boolean.parseBoolean(postRun.obj.getString("message"))) {
                        Toast.makeText(this, "찜 완료하였습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "실패하였거나 이미 찜하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
            postRun.addData("uCode", UserSharedPreferences.user.getUCode())
                    .addData("csCode", vo.getCsCode())
                    .start();
        });

        binding.deleteDibs.setOnClickListener(v -> {
            PostRun postRun = new PostRun("pickCourse-delete", this, PostRun.DATA);
            postRun.setRunUI(() -> {
                try {
                    if (Boolean.parseBoolean(postRun.obj.getString("message"))) {
                        Toast.makeText(this, "찜 삭제하였습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "다시 시도해주십시오.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
            postRun.addData("uCode", UserSharedPreferences.user.getUCode())
                    .addData("csCode", vo.getCsCode())
                    .start();
        });

        binding.deleteCourse.setOnClickListener(v -> {
            PostRun postRun = new PostRun("delete-course", this, PostRun.DATA);
            postRun.setRunUI(() -> {
                try {
                    if (Boolean.parseBoolean(postRun.obj.getString("message"))) {
                        Toast.makeText(this, "코스를 삭제하였습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "다시 시도해주십시오.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
            postRun.addData("csCode", vo.getCsCode())
                    .start();
        });

        binding.useCourse.setOnClickListener(v -> {
            PostRun postRun = new PostRun("use-course", this, PostRun.DATA);
            postRun.setRunUI(() -> {
                try {
                    if (Boolean.parseBoolean(postRun.obj.getString("message"))) {
                        Toast.makeText(this, "코스를 사용 중으로 바꾸었습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "다시 시도해주십시오.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
            postRun.addData("csCode", vo.getCsCode())
                    .addData("uCode", UserSharedPreferences.user.getUCode())
                    .start();
        });
    }
}
