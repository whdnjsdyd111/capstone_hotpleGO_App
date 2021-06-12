package com.example.hotplego;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.example.hotplego.domain.HotpleVO;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class TMapSetting {
    private TMapView tMapView = null;
    private Activity activity;
    private List<TMapMarkerItem> markers = new Vector<>();
    private List<TMapPoint> points = new Vector<>();

    public static final Integer[] CATECORY = {
            R.color.red_700,
            R.color.teal_700,
            R.color.green_700,
            R.color.yellow_200,
            R.color.red_200,
            R.color.gray_500
    };

    public TMapSetting(TMapView tMapView) {
        this.tMapView = tMapView;
        tMapView.setSKTMapApiKey("l7xxcd9bdd0942b542fd8c7be931fc11a3b4");
    }

    public TMapSetting(TMapView tMapView, Activity activity) {
        this.tMapView = tMapView;
        this.activity = activity;
        tMapView.setSKTMapApiKey("l7xxcd9bdd0942b542fd8c7be931fc11a3b4");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Bitmap createViewToBitmap(String index, int color) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        View view = activity.getLayoutInflater().inflate(R.layout.course_index, null);
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        TextView textView = view.findViewById(R.id.cours_hotple_index);
        textView.setText(index);
        textView.setBackgroundTintList(ContextCompat.getColorStateList(activity, color));
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();

        return view.getDrawingCache();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Bitmap createViewToBitmap(int color) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        View view = activity.getLayoutInflater().inflate(R.layout.course_index, null);
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        TextView textView = view.findViewById(R.id.cours_hotple_index);
        textView.setBackgroundTintList(ContextCompat.getColorStateList(activity, color));
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();

        return view.getDrawingCache();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void hotpleMark(List<HotpleVO> list) {
        for (HotpleVO vo : list) {
            TMapPoint point = new TMapPoint(vo.getHtLat(), vo.getHtLng());
            points.add(point);
            TMapMarkerItem marker = new TMapMarkerItem();
            marker.setTMapPoint(point);
            marker.setIcon(createViewToBitmap(CATECORY[vo.getCategory().intValue() / 10]));
            markers.add(marker);
            tMapView.addMarkerItem(String.valueOf(vo.getHtId()), marker);
        }
    }
}
