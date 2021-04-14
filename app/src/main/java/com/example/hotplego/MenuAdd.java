package com.example.hotplego;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MenuAdd extends AppCompatActivity {
    ImageView imgView;
    TextView menu_add;
    Intent intent;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.admin_menu_add);

        imgView = (ImageView)findViewById(R.id.new_menu_img);
        menu_add = (TextView)findViewById(R.id.new_menu_add);
    }
}
