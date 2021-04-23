package com.example.hotplego;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONException;

import static com.example.hotplego.R.layout;

public class MainActivityLogin extends Activity implements View.OnClickListener {
    Spinner spinner;
    Button btnLogin;
    TextView btnSignup, btnSearch;
    EditText loginId, loginPw;
    String id, pw, Logout_Code;
    CheckBox checkBox;
    Intent intent;
    boolean saveLoginData;



    SharedPreferences autoLogin;
    SharedPreferences.Editor editor;

    public static String sloginId; // 기본키

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_login);
        loginId = (EditText) findViewById(R.id.etEmail);
        loginPw = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignup = (TextView) findViewById(R.id.tvRegister);
        btnSearch = (TextView) findViewById(R.id.tvRestore);
        checkBox = (CheckBox) findViewById(R.id.checkboxRemember);
        btnLogin.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
        btnSearch.setOnClickListener(this);




        checkDangerousPermissions();
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnLogin: //로그인 버튼
                id = loginId.getText().toString();
                pw = loginPw.getText().toString();

                if (id.length() == 0 || pw.length() == 0) { // 아이디 비번 입력 X
                    Toast.makeText(this, "아이디 또는 비밀번호를 입력하세요!", Toast.LENGTH_SHORT).show();
                    return;
                }




                Log.w("login", "로그인 하는중");
                try {

                    Log.w("앱에서 보낸값", id + ", " + pw);

                    PostRun pr = new PostRun("login", this);
                    pr.setRunUI(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String message = pr.obj.getString("message");
                                if(Boolean.parseBoolean(message)) {
                                    // TODO 유저 정보 가져와서 디비에 저장하기 (디비 저장, 유저 객체 생성)

                                    // 메인 액티비티 버튼 없애고 피니시
                                    finish();
                                } else {
                                    Toast.makeText(MainActivityLogin.this, message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    pr.addData("id", loginId.getText().toString());
                    pr.addData("pw", loginPw.getText().toString());
                    pr.start();

                } catch (Exception e) {
                    e.printStackTrace();
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

    private  void save() {
        SharedPreferences.Editor editor = autoLogin.edit();
        editor.putBoolean("SAVE_LOGIN_DATA", checkBox.isChecked());
        editor.putString("ID",id);
        editor.putString("PW",pw);

        editor.apply();
    }

    private void load() {
        // SharedPreferences 객체.get타입( 저장된 이름, 기본값 )
        // 저장된 이름이 존재하지 않을 시 기본값
        saveLoginData = autoLogin.getBoolean("SAVE_LOGIN_DATA", false);
        id = autoLogin.getString("ID", "");
        pw = autoLogin.getString("PWD", "");
    }

    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}