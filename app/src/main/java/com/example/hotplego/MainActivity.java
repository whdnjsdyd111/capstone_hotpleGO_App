package com.example.hotplego;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    MenuAdapter menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.admin_item_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        menuAdapter = new MenuAdapter(this, getMenuList());
        mRecyclerView.setAdapter(menuAdapter);
    }

    private ArrayList<Menu> getMenuList() {

        ArrayList<Menu> menus = new ArrayList<>();

        Menu m = new Menu();
        m.setTitle("후라이드 치킨");
        m.setPrice(21000);
        m.setImg(R.drawable.no_image);
        menus.add(m);

        m = new Menu();
        m.setTitle("양념 치킨");
        m.setPrice(29000);
        m.setImg(R.drawable.no_image);
        menus.add(m);

        m = new Menu();
        m.setTitle("간장 치킨");
        m.setPrice(25000);
        m.setImg(R.drawable.no_image);
        menus.add(m);

        m = new Menu();
        m.setTitle("반반 치킨");
        m.setPrice(23000);
        m.setImg(R.drawable.no_image);
        menus.add(m);

        return menus;
    }
}