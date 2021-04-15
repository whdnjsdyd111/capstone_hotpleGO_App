package com.example.hotplego;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MenuActivity extends AppCompatActivity {

    Button add_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_menu);

        RecyclerView recyclerView = findViewById(R.id.admin_item_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        MenuAdapter adapter = new MenuAdapter();

        adapter.addItem(new MenuData("후라이드 치킨", 27000, "후라이드 치킨 27000원", R.drawable.preview_sample_chicken01));
        adapter.addItem(new MenuData("양념 치킨", 27000, "양념 치킨 27000원", R.drawable.preview_sample_chicken02));
        adapter.addItem(new MenuData("간장 치킨", 27000, "간장 치킨 27000원", R.drawable.preview_sample_chicken03));
        adapter.addItem(new MenuData("반반 치킨", 27000, "반반 치킨 27000원", R.drawable.preview_sample_chicken04));
        adapter.addItem(new MenuData("순살 치킨", 27000, "순살 치킨 27000원", R.drawable.preview_sample_chicken05));

        recyclerView.setAdapter(adapter);

        add_btn = (Button)findViewById(R.id.menu_insert_btn);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MenuInsert.class);
                startActivity(intent);
            }
        });
    }
}