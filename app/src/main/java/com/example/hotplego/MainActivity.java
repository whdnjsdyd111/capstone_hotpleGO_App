package com.example.hotplego;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.hotplego.domain.UserVO;

public class MainActivity extends AppCompatActivity  {

    public static Button button_notice;
    public static UserVO vo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        button_notice = findViewById(R.id.button_notice);

        button_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivityLogin.class);
                startActivity(intent);
            }
        });

        // 데이터베이스 조회해서 vo 초기화

        // 폰 디비에 데이터 있을 때
        // 로그인 버튼 없애기

        // 폰 디비에 데이터 없으면
        // 로그인 버튼 생기게 하기

    }
}