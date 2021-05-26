package com.example.hotplego.ui.sales;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hotplego.R;

import java.util.ArrayList;

import im.dacer.androidcharts.LineView;


public class Sales_Fragment3 extends Fragment {
    public Sales_Fragment3() {}
    int randomint = 5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sales_2, viewGroup, false);
        final LineView lineView = (LineView) view.findViewById(R.id.lineChart);
        initView(lineView);
        randomSet(lineView);
        return view;
    }

    private void initView(LineView lineView) {
        ArrayList<String> test = new ArrayList<String>();
        test.add("1");test.add("2");test.add("3");test.add("4");test.add("5");
        //x값
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