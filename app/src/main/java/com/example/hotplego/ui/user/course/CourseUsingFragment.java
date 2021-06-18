package com.example.hotplego.ui.user.course;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hotplego.PostRun;
import com.example.hotplego.R;
import com.example.hotplego.UserSharedPreferences;
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CourseUsingBinding.inflate(inflater, container, false);

        adapter = new CourseAdapter(getActivity());
        binding.courseRecyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        binding.courseRecyclerView.setLayoutManager(manager);

        binding.courseComplete.setOnClickListener(v -> {
            if (infos != null) {
                PostRun postRun = new PostRun("complete-course", getActivity(), PostRun.DATA);
                postRun.setRunUI(() -> {
                    try {
                        if (Boolean.parseBoolean(postRun.obj.getString("message"))) {
                            Toast.makeText(getContext(), "코스를 완료하였습니다.", Toast.LENGTH_SHORT).show();
                            initView();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
                postRun.addData("csCode", infos.get(0).getCsCode())
                        .start();
            }
        });

        binding.dropCourse.setOnClickListener(v -> {
            if (infos != null) {
                PostRun postRun = new PostRun("return-course", getActivity(), PostRun.DATA);
                postRun.setRunUI(() -> {
                    try {
                        if (Boolean.parseBoolean(postRun.obj.getString("message"))) {
                            Toast.makeText(getActivity(), "코스를 내렸습니다.", Toast.LENGTH_SHORT).show();
                            initView();
                        } else {
                            Toast.makeText(getActivity(), "다시 시도해주십시오.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
                postRun.addData("csCode", infos.get(0).getCsCode())
                        .start();
            }
        });

        binding.deleteCourse.setOnClickListener(v -> {
            if (infos != null) {
                PostRun postRun = new PostRun("delete-course", getActivity(), PostRun.DATA);
                postRun.setRunUI(() -> {
                    try {
                        if (Boolean.parseBoolean(postRun.obj.getString("message"))) {
                            Toast.makeText(getActivity(), "코스를 삭제하였습니다.", Toast.LENGTH_SHORT).show();
                            initView();
                        } else {
                            Toast.makeText(getActivity(), "다시 시도해주십시오.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
                postRun.addData("csCode", infos.get(0).getCsCode())
                        .start();
            }
        });

        return binding.getRoot();
    }

    private void addLines() {
        for (TMapPolyLine line : lines) {
            distance += line.getDistance();
            tMapView.addTMapPolyLine("line", line);
        }
    }

    private void initMap() {
        tMapView = new TMapView(getActivity().getApplicationContext());
        tMapView.setSKTMapApiKey("l7xxcd9bdd0942b542fd8c7be931fc11a3b4");
        binding.tmapUsing.addView(tMapView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void initView() {
        initMap();
        binding.courseWith.setText("");
        binding.courseNum.setText("");
        binding.courseDistance.setText("");
        PostRun postRun = new PostRun("myCourse", getActivity(), PostRun.DATA);
        // TODO 유저
        postRun.setRunUI(() -> {
            try {
                if (!postRun.obj.has("courses")) {
                    binding.pleaseUse.setVisibility(View.VISIBLE);
                    binding.recyclerScroll.setVisibility(View.GONE);
                    binding.noCourse.setVisibility(View.GONE);
                    return;
                } else {
                    binding.pleaseUse.setVisibility(View.GONE);
                    binding.recyclerScroll.setVisibility(View.VISIBLE);
                    binding.noCourse.setVisibility(View.VISIBLE);
                }
                CourseVO vo = new Gson().fromJson(postRun.obj.getString("courses"), CourseVO.class);
                infos = new Gson().fromJson(postRun.obj.getString("coursesInfos"), new TypeToken<List< CourseInfoVO>>() {}.getType());
                adapter.setData(infos);
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

        postRun.addData("kind", "usingCourse")
                .addData("uCode", UserSharedPreferences.user.getUCode())
                .start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onResume() {
        initView();
        Log.i("유징 코스", "onResume");
        super.onResume();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
