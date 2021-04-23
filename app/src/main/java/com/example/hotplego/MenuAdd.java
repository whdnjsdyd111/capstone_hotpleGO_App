package com.example.hotplego;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MenuAdd extends AppCompatActivity {
    public static boolean add_item = false;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.menu_add);

        Intent intent = getIntent();
        String intent_name = intent.getStringExtra("name");
        String intent_price = intent.getStringExtra("price");
        String intent_cn = intent.getStringExtra("content");
        int intent_img = intent.getIntExtra("img",R.drawable.no_image);

        EditText et_name = (EditText) findViewById(R.id.edit_name);
        et_name.setText(intent_name);
        EditText et_price = (EditText) findViewById(R.id.edit_price);
        et_price.setText(intent_price);
        EditText et_cnt = (EditText) findViewById(R.id.edit_cnt);
        et_cnt.setText(intent_cn);
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
                add_item = true;

                EditText et_name = (EditText) findViewById(R.id.edit_name);
                EditText et_price = (EditText) findViewById(R.id.edit_price);
                EditText et_cnt = (EditText) findViewById(R.id.edit_cnt);

                String name = String.valueOf(et_name.getText());
                String price = String.valueOf(et_price.getText());
                String cnt = String.valueOf(et_cnt.getText());

                //아이템 정보 전달
                Intent intent = new Intent(MenuAdd.this, MenuActivity.class);
                intent.putExtra("메뉴명",name);
                intent.putExtra("가격",price);
                intent.putExtra("메뉴소개",cnt);
                startActivity(intent);
                finish();
            }
        });
    }
}

