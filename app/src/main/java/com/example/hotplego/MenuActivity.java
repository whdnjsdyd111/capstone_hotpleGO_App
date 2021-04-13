package com.example.hotplego;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_menu);

        RecyclerView recyclerView = findViewById(R.id.admin_item_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        MenuAdapter adapter = new MenuAdapter();

        adapter.addItem(new Menu("후라이드 치킨", "27000원", "후라이드 치킨", R.drawable.no_image));
        adapter.addItem(new Menu("양념 치킨", "27000원", "양념 치킨", R.drawable.no_image));
        adapter.addItem(new Menu("간장 치킨", "27000원", "간장 치킨", R.drawable.no_image));
        adapter.addItem(new Menu("반반 치킨", "27000원", "반반 치킨", R.drawable.no_image));
        adapter.addItem(new Menu("순살 치킨", "27000원", "순살 치킨", R.drawable.no_image));

        recyclerView.setAdapter(adapter);
    }
}