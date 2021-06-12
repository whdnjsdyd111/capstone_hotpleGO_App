package com.example.hotplego.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotplego.R;

import java.util.ArrayList;

public class MyPointActivity extends AppCompatActivity {
    MyPointAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypoint);
        getData();
        init();
       initDataset();
    }

    private void init() {
        RecyclerView recyclerView = findViewById(R.id.point_recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new MyPointAdapter();
        recyclerView.setAdapter(adapter);
    }


    private void getData() { }

    private void initDataset() {
        adapter.addItem(new PointData("만보기","대구 복현동", "21.05.10", "15:15", "32"));
    }
}
