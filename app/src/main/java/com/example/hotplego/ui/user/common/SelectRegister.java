package com.example.hotplego.ui.user.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hotplego.R;

public class SelectRegister extends Activity implements View.OnClickListener {
    Button btnMember;
    Button btnVendor;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_register);

        btnMember = (Button) findViewById(R.id.register_member);
        btnVendor = (Button) findViewById(R.id.register_vendor);

        btnMember.setOnClickListener(this);
        btnVendor.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.register_member: // 일반사용자 버튼
              intent = new Intent(getApplicationContext(), MainActivitySign.class);
                startActivity(intent);
                break;

            case R.id.register_vendor: // 사업자
                intent = new Intent(getApplicationContext(), MainActivitySignMg.class);
                startActivity(intent);
                break;
        }
    }
}

