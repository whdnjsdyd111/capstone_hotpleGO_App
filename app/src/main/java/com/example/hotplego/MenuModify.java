package com.example.hotplego;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MenuModify extends AppCompatActivity {
    public static final String PARAM_ORIGINAL = "original";

    public static final int RESULT_CODE_CANCEL = 0;
    public static final int RESULT_CODE_SAVE = 1;

    public static final String DATA_ORIGINAL = "original";
    public static final String DATA_AFTER = "after";

    private EditText edit_name;
    private EditText edit_price;
    private EditText edit_cnt;
    private ImageView edit_iv;
    private MenuData original;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_modify);

        edit_name = findViewById(R.id.modify_name);
        edit_price = findViewById(R.id.modify_price);
        edit_cnt = findViewById(R.id.modify_cnt);
        edit_iv = findViewById(R.id.modify_img);

        original = (MenuData) getIntent().getSerializableExtra(PARAM_ORIGINAL);

        edit_name.setText(original.getTitle());
        edit_price.setText(original.getPrice());
        edit_cnt.setText(original.getCnt());
        Uri uri = original.getImgUri();
        if(uri!=null) {
            edit_iv.setImageURI(original.getImgUri());
        } else {
            edit_iv.setImageResource(R.drawable.no_image);
        }

        /*취소하기 버튼 눌렀을 때*/
        Button bt_back = (Button) findViewById(R.id.menu_modify_canel);
        bt_back.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CODE_CANCEL);
                finish();
            }
        });

        /*저장하기 버튼 눌렀을 때*/
        Button bt_submit = (Button) findViewById(R.id.menu_modify_save);
        bt_submit.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuData after = new MenuData(
                        edit_name.getText().toString(),
                        edit_price.getText().toString(),
                        edit_cnt.getText().toString(),
                        0);
                after.setImgUri(original.getImgUri().toString());
                Intent intent = new Intent();
                intent.putExtra(DATA_ORIGINAL, original);
                intent.putExtra(DATA_AFTER, after);
                setResult(RESULT_CODE_SAVE, intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        setResult(RESULT_CODE_CANCEL);
        finish();
    }
}