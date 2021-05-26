package com.example.hotplego.ui;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotplego.R;


public class ReviewActivity extends AppCompatActivity {
    ReviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_main);
        getData();
        init();
        initDataset();


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
    }

}
