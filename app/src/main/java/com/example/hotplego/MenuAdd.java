package com.example.hotplego;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MenuAdd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.menu_add);
    }

    @Override
    protected void onResume() {
        super.onResume();

        /*취소하기 버튼 눌렀을 때*/
        Button bt_back = (Button) findViewById(R.id.menu_add_cancel);
        bt_back.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*저장하기 버튼 눌렀을 때*/
        Button bt_submit = (Button) findViewById(R.id.menu_add_submit);
        bt_submit.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}

