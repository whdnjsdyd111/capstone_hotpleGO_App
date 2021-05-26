package com.example.hotplego.ui.sales;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hotplego.R;

import java.util.ArrayList;

import im.dacer.androidcharts.BarView;
import im.dacer.androidcharts.LineView;

public class Sales_Fragment1 extends Fragment {
    public Sales_Fragment1() {}
    int randomint = 7;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sales_1, viewGroup, false);
        final LineView lineView = (LineView) view.findViewById(R.id.lineChart);

        initView(lineView);
        randomSet(lineView);
        return view;
    }

    private void initView(LineView lineView) {
        ArrayList<String> test = new ArrayList<String>();
        test.add("월");
        test.add("화");
        test.add("수");
        test.add("목");
        test.add("금");
        test.add("토");
        test.add("일");//x값
        lineView.setBottomTextList(test);
        lineView.setColorArray(new int[] {
                Color.parseColor("#1f55de")
        });
        lineView.setDrawDotLine(true);
        lineView.setShowPopup(LineView.SHOW_POPUPS_NONE);
    }

    private void randomSet(LineView lineView) {
        ArrayList<Integer> dataList = new ArrayList<>();
        float random = (float) (Math.random() * 9 + 1);
        for (int i = 0; i < randomint; i++) {
            dataList.add((int) (Math.random() * random));
        }

        ArrayList<ArrayList<Integer>> dataLists = new ArrayList<>();
        dataLists.add(dataList);

        lineView.setDataList(dataLists); //y값
    }
}