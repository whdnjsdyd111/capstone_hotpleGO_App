package com.example.hotplego.ui.user.course;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hotplego.PostRun;
import com.example.hotplego.databinding.CourseUsingBinding;
import com.example.hotplego.domain.CourseInfoVO;
import com.example.hotplego.domain.CourseVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.skt.Tmap.TMapView;

import org.json.JSONException;

import java.util.List;

public class CourseUsingFragment extends Fragment {

    private CourseUsingBinding binding;
    private CourseAdapter adapter;
    private final String with = "함께하는 인원 : ";
    private final String num = "인원 : ";
    List<CourseInfoVO> infos = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CourseUsingBinding.inflate(inflater, container, false);

        TMapView tMapView = new TMapView(getContext());

        tMapView.setSKTMapApiKey("l7xxcd9bdd0942b542fd8c7be931fc11a3b4");
        binding.tmapUsing.addView(tMapView);

        adapter = new CourseAdapter();
        binding.courseRecyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        binding.courseRecyclerView.setLayoutManager(manager);

        PostRun postRun = new PostRun("myCourse", getActivity(), PostRun.DATA);
        postRun.addData("kind", "usingCourse")
                .addData("uCode", "whdnjsdyd111@naver.com/A/");
        // TODO 유저
        postRun.setRunUI(() -> {
            try {
                infos = new Gson().fromJson(postRun.obj.getString("coursesInfos"), new TypeToken<List< CourseInfoVO>>() {}.getType());
                adapter.setData(infos);
                Log.i("ddd", "" + infos);
                CourseVO vo = new Gson().fromJson(postRun.obj.getString("courses"), CourseVO.class);
                binding.courseWith.setText(with + vo.getCsWith());
                binding.courseNum.setText(num + vo.getCsNum());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapter.notifyDataSetChanged();
        });
        postRun.start();

        return binding.getRoot();
    }


}
