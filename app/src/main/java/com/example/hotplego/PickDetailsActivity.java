package com.example.hotplego;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PickDetailsActivity extends AppCompatActivity {
    PickDetailsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.pick_course_details);

        ui();
        setDetails();
    }

    private void ui() {
        RecyclerView recyclerView = findViewById(R.id.pick_course_details);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new PickDetailsAdapter();
        recyclerView.setAdapter(adapter);

    }

    private void setDetails() {
        adapter.addItem(new PickDetailsData("코스 구성 업체명1", "코스 구성 업체 주소A"));
        adapter.addItem(new PickDetailsData("코스 구성 업체명2", "코스 구성 업체 주소B"));
        adapter.addItem(new PickDetailsData("코스 구성 업체명3", "코스 구성 업체 주소C"));
        adapter.addItem(new PickDetailsData("코스 구성 업체명4", "코스 구성 업체 주소D"));
        adapter.addItem(new PickDetailsData("코스 구성 업체명5", "코스 구성 업체 주소E"));
        adapter.addItem(new PickDetailsData("코스 구성 업체명6", "코스 구성 업체 주소F"));
    }
}