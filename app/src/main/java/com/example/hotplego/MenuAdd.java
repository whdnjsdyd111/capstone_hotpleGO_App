package com.example.hotplego;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class MenuAdd extends AppCompatActivity {
    ImageView iv;

    public static final int REQUEST_CODE = 0;

    public static final int RESULT_CODE_CANCEL = 0;
    public static final int RESULT_CODE_ADD = 1;

    public static final String DATA_MENU_DATA = "menuData";

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.menu_add);

        /*취소하기 버튼 클릭 시*/
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

        /*저장하기 버튼 클릭 시*/
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

        /*갤러리 요청코드*/
        iv = findViewById(R.id.new_img);
        iv.setOnClickListener(new View.OnClickListener() { //이미지뷰를 클릭 시 액션
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(); // 인텐트를 통해 요청코드를 보냄
                gallery.setType(MediaStore.Images.Media.CONTENT_TYPE);
                /*gallery.setType("image/*");*/
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(gallery, REQUEST_CODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            try{
                Uri uri = data.getData();
                Glide.with(getApplicationContext()).load(uri).into(iv); // 이미지 사진 넣기
            } catch (Exception e) {

            }
        } else if(requestCode == REQUEST_CODE && resultCode == RESULT_CANCELED) { // 취소 시 호출할 행동

        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CODE_CANCEL);
        super.onBackPressed();
    }
}