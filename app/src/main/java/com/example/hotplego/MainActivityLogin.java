package com.example.hotplego;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivityLogin extends Activity implements View.OnClickListener {
    Spinner spinner;
    Button btnLogin;
    TextView btnSignup, btnSearch;
    EditText loginId, loginPw;
    String id, pw, Logout_Code;
    CheckBox checkBox;
    Intent intent;
    Cursor cursor;
    SharedPreferences autoLogin;
    SharedPreferences.Editor editor;

    public static String sloginId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginId = (EditText) findViewById(R.id.etEmail);
        loginPw = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignup = (TextView) findViewById(R.id.tvRegister);
        btnSearch = (TextView) findViewById(R.id.tvRestore);
        checkBox = (CheckBox) findViewById(R.id.checkboxRemember);
        btnLogin.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
        btnSearch.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnLogin: //로그인 버튼
                id = loginId.getText().toString();
                pw = loginPw.getText().toString();

                if(id.length() == 0 || pw.length()== 0) { // 아이디 비번 입력 X
                    Toast.makeText(this, "아이디 또는 비밀번호를 입력하세요!", Toast.LENGTH_SHORT).show();
                    return;
                }
               break;

            case R.id.tvRegister: // 회원가입 버튼
                intent = new Intent(getApplicationContext(), SelectRegister.class);
                startActivity(intent);
                break;

            case R.id.tvRestore: // 비밀번호 찾기 버튼
                intent = new Intent(getApplicationContext(), MainActivitySearch.class);
                startActivity(intent);
                break;
        }
    }
}