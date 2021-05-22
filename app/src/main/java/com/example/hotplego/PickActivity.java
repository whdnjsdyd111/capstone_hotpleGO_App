package com.example.hotplego;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PickActivity extends AppCompatActivity {
    PickAdapter ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pick_list);

        init();
        initData();
    }

    private void init() {
        RecyclerView recyclerView = findViewById(R.id.pick_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ad = new PickAdapter();
        recyclerView.setAdapter(ad);
    }

    private void initData() {
        ad.addItem(new PickData("yy-mm-dd hh:mm:ss", "핫플장소명A", "핫플주소G", 5.0f, R.drawable.point));
        ad.addItem(new PickData("yy-mm-dd hh:mm:15", "핫플장소명B", "핫플주소H", 4.5f, R.drawable.point));
        ad.addItem(new PickData("yy-mm-dd hh:03:15", "핫플장소명C", "핫플주소I", 4.0f, R.drawable.point));
        ad.addItem(new PickData("yy-mm-dd 15:03:15", "핫플장소명D", "핫플주소J", 3.5f, R.drawable.point));
        ad.addItem(new PickData("yy-mm-02 15:03:15", "핫플장소명E", "핫플주소K", 3.0f, R.drawable.point));
        ad.addItem(new PickData("yy-04-02 15:03:15", "핫플장소명F", "핫플주소L", 2.5f, R.drawable.point));
        ad.addItem(new PickData("21-04-02 15:03:15", "핫플장소명G", "핫플주소M", 2.0f, R.drawable.point));
    }
}