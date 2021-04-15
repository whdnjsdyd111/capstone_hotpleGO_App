package com.example.hotplego;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MenuEdit extends AppCompatActivity {

    TextView menu_name, menu_price, menu_cnt;
    ImageView menu_img;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_menu_edit);

        menu_name = findViewById(R.id.menu_name);
        menu_price = findViewById(R.id.menu_price);
        menu_cnt = findViewById(R.id.menu_cnt);
        menu_img = findViewById(R.id.image_icon);
    }
}
