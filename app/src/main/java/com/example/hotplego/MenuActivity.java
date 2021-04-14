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

        adapter.addItem(new Menu("후라이드 치킨", "27000원", "후라이드 치킨", R.drawable.no_image));
        adapter.addItem(new Menu("양념 치킨", "27000원", "양념 치킨", R.drawable.no_image));
        adapter.addItem(new Menu("간장 치킨", "27000원", "간장 치킨", R.drawable.no_image));
        adapter.addItem(new Menu("반반 치킨", "27000원", "반반 치킨", R.drawable.no_image));
        adapter.addItem(new Menu("순살 치킨", "27000원", "순살 치킨", R.drawable.no_image));

        recyclerView.setAdapter(adapter);

        add_btn = (Button)findViewById(R.id.menu_add_btn);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MenuAdd.class);
                startActivity(intent);
            }
        });
    }
}
