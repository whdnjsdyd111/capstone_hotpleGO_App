package com.example.hotplego.ui;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotplego.R;
import com.github.mikephil.charting.charts.LineChart;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import im.dacer.androidcharts.LineView;


public class ReviewActivity extends AppCompatActivity {
    ReviewAdapter adapter;
    private LineView lineView;
    int randomint = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_main);
        LineView lineView = (LineView) findViewById(R.id.lineChart2);

        init();
        initDataset();
        getData();

        initLineView(lineView);
        randomSet(lineView);

    }

    private void initLineView(LineView lineView) {
        ArrayList<String> test = new ArrayList<String>();
        test.add("1월");
        test.add("2월");
        test.add("3월");
        test.add("4월");
        test.add("5월");
        test.add("6월");
        test.add("7월");
        test.add("8월");
        test.add("9월");
        test.add("10월");
        test.add("11월");
        test.add("12월");              //x값
        lineView.setBottomTextList(test);
        lineView.setColorArray(new int[] {
                Color.parseColor("#f09a3e")
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

    private void init() {
        RecyclerView recyclerView = findViewById(R.id.re_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new ReviewAdapter();
        recyclerView.setAdapter(adapter);

    }


    private void getData() { }

    private void initDataset() {
        adapter.addItem(new ReviewData("닉네임", "210512", "리뷰 내용"));
        adapter.addItem(new ReviewData("닉네임", "210518", "리뷰 내용"));
        adapter.addItem(new ReviewData("닉네임", "210524", "리뷰 내용"));
    }
}
