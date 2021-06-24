package com.example.hotplego.ui.user.course;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hotplego.GpsTracker;
import com.example.hotplego.PostRun;
import com.example.hotplego.R;
import com.example.hotplego.TMapSetting;
import com.example.hotplego.UserSharedPreferences;
import com.example.hotplego.databinding.CourseUsingBinding;
import com.example.hotplego.databinding.SearchHotpleBinding;
import com.example.hotplego.domain.CourseInfoVO;
import com.example.hotplego.domain.CourseVO;
import com.example.hotplego.ui.user.course.recyclerview.CourseAdapter;
import com.example.hotplego.ui.user.home.adapter.SearchAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapGpsManager;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;

import org.json.JSONException;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class CourseUsingFragment extends Fragment implements CourseAdapter.InitData {
    private CourseUsingBinding binding;
    private CourseAdapter adapter;
    List<CourseInfoVO> infos = null;
    private TMapView tMapView = null;
    private TMapSetting tMapSetting = null;
    TMapGpsManager gpsManager = null;
    private final Set<TMapPolyLine> lines = new HashSet<>();

    private final LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {

            double longitude = location.getLongitude(); //경도
            double latitude = location.getLatitude();   //위도

            tMapView.setLocationPoint(longitude, latitude);
            tMapView.setCenterPoint(longitude, latitude);
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CourseUsingBinding.inflate(inflater, container, false);

        adapter = new CourseAdapter(getActivity(), this);
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

        binding.goHotple.setOnClickListener(v -> {
            tMapView.setCompassMode(true);
            tMapView.setSightVisible(true);
            tMapView.setIconVisibility(true);
            final LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자
                    1000, // 통지사이의 최소 시간간격 (miliSecond)
                    3, // 통지사이의 최소 변경거리 (m)
                    mLocationListener);
        });

        return binding.getRoot();
    }

    private void initMap() {
        tMapView = new TMapView(getActivity().getApplicationContext());
        tMapSetting = new TMapSetting(tMapView, getActivity());
        binding.tmapUsing.addView(tMapView);
    }

    @Override
    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void initView() {
        initMap();
        binding.courseWith.setText("");
        binding.courseNum.setText("");
        binding.courseDistance.setText("");
        PostRun postRun = new PostRun("myCourse", getActivity(), PostRun.DATA);

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

                if (infos.size() == 1) tMapSetting.drawPath1(infos);
                else if (infos.size() == 2) tMapSetting.drawPath2(binding.courseDistance, infos);
                else tMapSetting.drawPath(binding.courseDistance, infos);

                binding.goHotple.setVisibility(View.VISIBLE);
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
        super.onResume();
    }
}
