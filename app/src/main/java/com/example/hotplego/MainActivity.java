package com.example.hotplego;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.hotplego.domain.UserSharedPreferences;
import com.example.hotplego.domain.UserVO;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

public class MainActivity extends AppCompatActivity  {


    public Button button_notice;
    public static UserVO vo;
    private SharedPreferences preferences;
    Button btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        UserSharedPreferences.getInstance().login(this);

        button_notice = findViewById(R.id.button_notice);
        btnLogout = findViewById(R.id.btnLogout);
        button_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserSharedPreferences.vo == null) {
                    Intent intent = new Intent(getApplicationContext(), MainActivityLogin.class);
                    startActivity(intent);

                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivityLogout.class);
                    startActivity(intent);
                }
            }
        });

        findViewById(R.id.bn_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        // 로그아웃 성공시 수행하는 지점

                    }
                });
            }
        });

        // 데이터베이스 조회해서 vo 초기화

        // 폰 디비에 데이터 있을 때
        // 로그인 버튼 없애기

        // 폰 디비에 데이터 없으면
        // 로그인 버튼 생기게 하기

    }
}