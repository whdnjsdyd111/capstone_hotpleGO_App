package com.example.hotplego;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {
    private final String logout_Code = "f";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Database.getInstance().open(this); // 데이터 베이스 오픈
        Handler hd = new Handler();
        hd.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = (new Intent(SplashActivity.this,MainActivityLogin.class));
                intent.putExtra("Logout_Code", logout_Code);
                startActivity(intent);
                finish();
            }
        }, 3000); // 3초 후 이미지를 닫습니다

    }


}
