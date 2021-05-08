package com.example.hotplego.ui.user.course;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hotplego.PostRun;
import com.example.hotplego.R;
import com.example.hotplego.databinding.CourseUsingBinding;
import com.example.hotplego.domain.CourseInfoVO;
import com.example.hotplego.domain.CourseVO;
import com.example.hotplego.ui.user.course.recyclerview.CourseAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;

import org.json.JSONException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CourseUsingFragment extends Fragment {

    private CourseUsingBinding binding;
    private CourseAdapter adapter;
    List<CourseInfoVO> infos = null;
    private TMapView tMapView = null;
    private final Set<TMapPolyLine> lines = new HashSet<>();
    private double distance = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CourseUsingBinding.inflate(inflater, container, false);

        tMapView = new TMapView(getActivity().getApplicationContext());

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
                CourseVO vo = new Gson().fromJson(postRun.obj.getString("courses"), CourseVO.class);
                binding.courseWith.setText("함께하는 인원 : " + vo.getCsWith());
                binding.courseNum.setText("인원 : " + vo.getCsNum());
                TMapData tMapData = new TMapData();
                for (int i = 0; i < infos.size(); i++) {
                    CourseInfoVO v = infos.get(i);
                    TMapMarkerItem item = new TMapMarkerItem();
                    item.setTMapPoint(new TMapPoint(v.getHtLat(), v.getHtLng()));
                    item.setIcon(createViewToBitmap(String.valueOf(v.getCiIndex()), CourseFragment.COURSE_COLORS[v.getCiIndex() - 1]));
                    tMapView.addMarkerItem("" + v.getCiIndex(), item);
                    if (i < infos.size() - 1) {
                        CourseInfoVO v2 = infos.get(i + 1);
                        // TODO url 요청으로 얻기
                        tMapData.findPathDataWithType(TMapData.TMapPathType.PEDESTRIAN_PATH,
                                new TMapPoint(v.getHtLat(), v.getHtLng()),
                                new TMapPoint(v2.getHtLat(), v2.getHtLng()),
                                new TMapData.FindPathDataListenerCallback() {
                                    @Override
                                    public void onFindPathData(TMapPolyLine tMapPolyLine) {
                                        lines.add(tMapPolyLine);
                                        if (lines.size() == infos.size() - 1) {
                                            addLines();
                                        }
                                    }
                                });
                    }
                };
                tMapView.setTMapPoint(infos.get(0).getHtLat(), infos.get(0).getHtLng());
                tMapView.setZoomLevel(14);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        postRun.start();

        return binding.getRoot();
    }

    private void addLines() {
        for (TMapPolyLine line : lines) {
            distance += line.getDistance();
            tMapView.addTMapPolyLine("line", line);
        }
    }

    private Bitmap createViewToBitmap(String index, int color) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        View view = getLayoutInflater().inflate(R.layout.course_index, null);
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        TextView textView = view.findViewById(R.id.cours_hotple_index);
        textView.setText(index);
        textView.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), color));
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();

        return view.getDrawingCache();
    }
}
