package com.example.hotplego.ui.manager.sale;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.hotplego.R;

import java.util.ArrayList;
import java.util.Map;

import im.dacer.androidcharts.LineView;

public class SaleFragment extends Fragment {
    Map<String, Integer> map = null;
    View view = null;

    public SaleFragment() {}

    public SaleFragment(Map<String, Integer> map) {
        this.map = map;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sales, viewGroup, false);
        final LineView lineView = (LineView) view.findViewById(R.id.lineChart);

        initView(lineView);
        randomSet(lineView);

        int sum = 0;
        int best = 0;
        int wast = Integer.MAX_VALUE;
        double avg = 0.0;

        for (Integer key : map.values()) {
            sum += key;
            best = Integer.max(key, best);
            wast = Integer.min(key, wast);
        }

        avg = (double ) sum / map.size();

        ((TextView)view.findViewById(R.id.allSale)).setText(String.format("%,d", sum));
        ((TextView)view.findViewById(R.id.avgSale)).setText(String.format("%,.0f", avg));
        ((TextView)view.findViewById(R.id.bestSale)).setText(String.format("%,d", best));
        ((TextView)view.findViewById(R.id.wastSale)).setText(String.format("%,d", wast));


        return view;
    }

    private void initView(LineView lineView) {
        ArrayList<String> test = new ArrayList<>(map.keySet());
        lineView.setBottomTextList(test);
        lineView.setColorArray(new int[] {
                Color.parseColor("#1f55de")
        });
        lineView.setDrawDotLine(true);
        lineView.setShowPopup(LineView.SHOW_POPUPS_NONE);
    }

    private void randomSet(LineView lineView) {
        ArrayList<Integer> dataList = new ArrayList<>(map.values());

        ArrayList<ArrayList<Integer>> dataLists = new ArrayList<>();
        dataLists.add(dataList);

        lineView.setDataList(dataLists);
    }
}