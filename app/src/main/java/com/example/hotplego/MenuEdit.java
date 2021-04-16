package com.example.hotplego;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;

public class MenuEdit extends AppCompatActivity {

    TextView menu_name, menu_price, menu_cnt;
    ImageView menu_img;

    Button edit_btn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_edit);

        menu_name = findViewById(R.id.menu_name);
        menu_price = findViewById(R.id.menu_price);
        menu_cnt = findViewById(R.id.menu_cnt);
        menu_img = findViewById(R.id.image_icon);

        edit_btn = findViewById(R.id.menu_edit_save);
        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
        });

        menu_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Intent.ACTION_PICK);
            }
        });
    }
}
