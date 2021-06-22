package com.example.hotplego.ui.user.home;


import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hotplego.PostRun;
import com.example.hotplego.R;
import com.example.hotplego.TMapSetting;
import com.example.hotplego.UserSharedPreferences;
import com.example.hotplego.databinding.CourseDetailBinding;
import com.example.hotplego.domain.CourseInfoVO;
import com.example.hotplego.domain.CourseVO;
import com.example.hotplego.ui.user.course.recyclerview.CourseAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.skt.Tmap.TMapView;

import org.json.JSONException;

import java.util.List;

public class CourseDetailActivity extends AppCompatActivity implements CourseAdapter.InitData {
    private CourseDetailBinding binding;
    private CourseAdapter adapter;
    private TMapView tMapView = null;
    private TMapSetting tMapSetting = null;
    List<CourseInfoVO> list = null;
    private String csCode = null;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = CourseDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        csCode = getIntent().getStringExtra("csCode");

        initView();

        binding.isCopy.setVisibility(View.VISIBLE);
        binding.courseWith.setVisibility(View.GONE);
        binding.courseNum.setVisibility(View.GONE);

        if (UserSharedPreferences.user == null) binding.copyCourse.setVisibility(View.GONE);

        binding.copyCourse.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            AlertDialog dialog = builder.create();
            LayoutInflater inflater = LayoutInflater.from(this);
            LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.write_course, null);
            EditText csWith = layout.findViewById(R.id.csWith);
            EditText csNum = layout.findViewById(R.id.csNum);
            EditText csTitle = layout.findViewById(R.id.csTitle);
            layout.findViewById(R.id.copy_btn).setOnClickListener(e -> {
                PostRun postRun = new PostRun("course-copy", this, PostRun.DATA);
                postRun.setRunUI(() -> {
                    try {
                        if (Boolean.parseBoolean(postRun.obj.getString("message"))) {
                            Toast.makeText(this, "복사 완료하였습니다.", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                });
                postRun.addData("csCode", csCode).addData("csWith", csWith.getText().toString())
                        .addData("csNum", csNum.getText().toString()).addData("csTitle", csTitle.getText().toString())
                        .addData("uCode", UserSharedPreferences.user.getUCode())
                        .start();
            });
            dialog.setView(layout);
            dialog.show();
        });

        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        binding.courseRecyclerView.setLayoutManager(manager);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initView() {
        PostRun postRun = new PostRun("get_course", this, PostRun.DATA);
        postRun.setRunUI(() -> {
            try {
                list = new Gson().fromJson(postRun.obj.getString("course_info"), new TypeToken<List<CourseInfoVO>>() {}.getType());
                adapter = new CourseAdapter(list, "notMine", this, this);
                binding.courseRecyclerView.setAdapter(adapter);
                tMapView = new TMapView(getApplicationContext());
                tMapSetting = new TMapSetting(tMapView, this);
                binding.tmapUsing.addView(tMapView);
                if (list.size() == 1) tMapSetting.drawPath1(list);
                else if (list.size() == 2) tMapSetting.drawPath2(binding.courseDistance, list);
                else tMapSetting.drawPath(binding.courseDistance, list);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        postRun.addData("csCode", csCode)
                .start();
    }
}
