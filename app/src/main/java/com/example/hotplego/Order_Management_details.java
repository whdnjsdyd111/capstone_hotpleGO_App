package com.example.hotplego;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Order_Management_details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_management);

        ActionBar ac = getSupportActionBar();
        ac.setTitle("주문 상세");

        RecyclerView recyclerView = findViewById(R.id.details_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        Order_Management_details_Adapter details_adapter = new Order_Management_details_Adapter();

        details_adapter.addItem(new Order_Management_details_Data(R.drawable.lank_f, "통새우버거", "3개", "18,300원"));
        details_adapter.addItem(new Order_Management_details_Data(R.drawable.lank_f, "인크레더블버거", "1개", "5,100원"));

        recyclerView.setAdapter(details_adapter);
    }
}