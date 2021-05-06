package com.example.hotplego.ui.user.course;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hotplego.PostRun;
import com.example.hotplego.databinding.CourseMakingBinding;
import com.example.hotplego.domain.CourseInfoVO;
import com.example.hotplego.domain.CourseVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.util.List;
import java.util.Map;

public class CourseMakingFragment extends Fragment {

    private CourseMakingBinding binding;
    private CourseInfoAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CourseMakingBinding.inflate(inflater, container, false);

        adapter = new CourseInfoAdapter();
        binding.courseInfoRecyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        binding.courseInfoRecyclerView.setLayoutManager(manager);

        PostRun postRun = new PostRun("myCourse", getActivity(), PostRun.DATA);
        postRun.setRunUI(() -> {
            try {
                Gson gson = new Gson();
                adapter.setData(gson.fromJson(postRun.obj.getString("courses"), new TypeToken<List<CourseVO>>() {}.getType()),
                        gson.fromJson(postRun.obj.getString("courseInfos"), new TypeToken<Map<String, List<CourseInfoVO>>>() {}.getType()));
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        postRun.addData("kind", "myCourse")
                .addData("uCode", "whdnjsdyd111@naver.com/A/")
                .start();
        // TODO 유저

        return binding.getRoot();
    }
}
