package com.example.hotplego;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Menu extends AppCompatActivity {

    RecyclerView recyclerView;
    MenuAdapter menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_menu_add);

        recyclerView = findViewById(R.id.admin_item_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        menuAdapter = new MenuAdapter(this, getMenuList());
        recyclerView.setAdapter(menuAdapter);
    }

    private ArrayList<Model> getMenuList() {

        ArrayList<Model> models = new ArrayList<>();

        Model m = new Model();
        m.setTitle("후라이드 치킨");
        m.setPrice(27000);
        m.setImg(R.drawable.no_image);
        models.add(m);

        m = new Model();
        m.setTitle("간장 치킨");
        m.setPrice(27000);
        m.setImg(R.drawable.no_image);
        models.add(m);

        m = new Model();
        m.setTitle("양념 치킨");
        m.setPrice(27000);
        m.setImg(R.drawable.no_image);
        models.add(m);

        m = new Model();
        m.setTitle("반반 치킨");
        m.setPrice(27000);
        m.setImg(R.drawable.no_image);
        models.add(m);

        return models;
    }
}