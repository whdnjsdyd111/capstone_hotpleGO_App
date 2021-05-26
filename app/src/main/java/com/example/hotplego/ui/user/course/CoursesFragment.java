package com.example.hotplego.ui.user.course;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hotplego.PostRun;
import com.example.hotplego.databinding.CourseUsedBinding;
import com.example.hotplego.domain.CourseInfoVO;
import com.example.hotplego.domain.CourseVO;
import com.example.hotplego.ui.user.course.recyclerview.CourseInfoAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class CoursesFragment extends Fragment implements CourseInfoAdapter.OnItemClickListener {
    private CourseUsedBinding binding;
    private CourseInfoAdapter adapter;
    private String kind;

    public CoursesFragment() {}

    public CoursesFragment(String kind) {
        this.kind = kind;
    }

    public static Fragment newInstance(String kind) {
        return new CoursesFragment(kind);
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
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        binding.courseInfoRecyclerView.setLayoutManager(manager);

        PostRun postRun = new PostRun("myCourse", getActivity(), PostRun.DATA);
        postRun.setRunUI(() -> {
            try {
                Gson gson = new Gson();
                adapter.setData(gson.fromJson(postRun.obj.getString("courses"), new TypeToken<List<CourseVO>>() {}.getType()),
                        gson.fromJson(postRun.obj.getString("courseInfos"), new TypeToken<Map<String, List<CourseInfoVO>>>() {}.getType()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        postRun.addData("kind", kind)
                .addData("uCode", "whdnjsdyd111@naver.com/A/")
                .start();
        // TODO 유저

        return binding.getRoot();
    }

    @Override
    public void onItemClick(CourseVO vo, List<CourseInfoVO> list) {
        Intent intent = new Intent(getContext(), CourseDetailActivity.class);
        intent.putExtra("course", vo);
        intent.putExtra("courseInfo", (Serializable) list);
        intent.putExtra("kind", kind);
        startActivity(intent);
    }
}
