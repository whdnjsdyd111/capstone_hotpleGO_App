package com.example.hotplego.ui.user.home;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.example.hotplego.GpsTracker;
import com.example.hotplego.PostRun;
import com.example.hotplego.R;
import com.example.hotplego.TMapSetting;
import com.example.hotplego.databinding.SearchHotpleBinding;
import com.example.hotplego.domain.HotpleVO;
import com.example.hotplego.ui.user.home.adapter.SearchAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import org.json.JSONException;

import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchAdapter.OnItemClickListener {
    private SearchAdapter adapter;
    private TMapView tMapView = null;
    private TMapSetting tMapSetting = null;
    private GpsTracker gps = null;
    private SearchHotpleBinding binding = null;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SearchHotpleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new SearchAdapter(this, this);
        tMapView = new TMapView(getApplicationContext());
        tMapSetting = new TMapSetting(tMapView, this);
        gps = new GpsTracker(getApplicationContext());

        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getHotples(double lat, double lng) {
        PostRun postRun = new PostRun("search_hotple", this, PostRun.DATA);
        postRun.setRunUI(() -> {
            try {
                List<HotpleVO> list = new Gson().fromJson(postRun.obj.getString("hotples"),
                        new TypeToken<List<HotpleVO>>() {}.getType());
                adapter.addItem(list);
                tMapSetting.hotpleMark(list);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        postRun.addData("keyword", getIntent().getStringExtra("keyword"))
                .addData("lat", String.valueOf(lat))
                .addData("lng", String.valueOf(lng))
                .start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setMyLocation() {
        getHotples(gps.getLatitude(), gps.getLongitude());
        tMapView.setTMapPoint(gps.getLatitude(), gps.getLongitude());
        tMapView.setZoomLevel(14);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init() {
        binding.searchMap.addView(tMapView);

        binding.myLocation.setOnClickListener(v -> setMyLocation());

        binding.setLocation.setOnClickListener(v -> {
            TMapPoint point = tMapView.getCenterPoint();
            getHotples(point.getLatitude(), point.getLongitude());
            tMapView.setZoomLevel(14);
        });

        setMyLocation();

        RecyclerView recyclerView = binding.recyclerSearch;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new HotpleItemDecoration(this));
    }

    public class HotpleItemDecoration extends RecyclerView.ItemDecoration{
        private int size10;
        private int size5;

        public HotpleItemDecoration(Context context) {
            size10 = dpToPx(context, 10);
            size5 = dpToPx(context, 5);
        }

        private int dpToPx(Context context, int dp) {
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
        }

        @Override
        public void getItemOffsets
                (@NonNull Rect outRect,  @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            int position = parent.getChildAdapterPosition(view);
            int itemCount = state.getItemCount();

            if(position == 0 || position == 1) {
                outRect.top = size10;
                outRect.bottom = size10;
            } else {
                outRect.bottom = size10;
            }

            GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
            int spanIndex = lp.getSpanIndex();

            if (spanIndex == 0) {
                outRect.left = size10;
                outRect.right = size5;
            } else if(spanIndex == 1) {
                outRect.left = size5;
                outRect.right = size10;
            }
        }
    }

    @Override
    public void onItemClick(HotpleVO vo) {
        Intent intent = new Intent(this, HotpleActivity.class);
        intent.putExtra("hotple", vo);
        startActivity(intent);
    }
}