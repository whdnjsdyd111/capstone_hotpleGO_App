package com.example.hotplego;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MenuAdd extends AppCompatActivity {
    public static final int RESULT_CODE_CANCEL = 0;
    public static final int RESULT_CODE_ADD = 1;

    public static final String DATA_MENU_DATA = "menuData";

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.menu_add);

        /*취소하기 버튼 눌렀을 때*/
        Button bt_back = (Button) findViewById(R.id.menu_add_cancel);
        bt_back.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CODE_CANCEL);
                finish();
            }
        });

        EditText new_name = findViewById(R.id.new_name);
        EditText new_price = findViewById(R.id.new_price);
        EditText new_cnt = findViewById(R.id.new_cnt);

        /*저장하기 버튼 눌렀을 때*/
        Button bt_submit = (Button) findViewById(R.id.menu_add_submit);
        bt_submit.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuData menuData = new MenuData(
                        new_name.getText().toString(),
                        new_price.getText().toString(),
                        new_cnt.getText().toString(),
                        0);
                Intent result = new Intent();
                result.putExtra(DATA_MENU_DATA, menuData);
                setResult(RESULT_CODE_ADD, result);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CODE_CANCEL);
        super.onBackPressed();
    }
}