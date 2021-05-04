package com.example.hotplego;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.hotplego.domain.UserSharedPreferences;
import com.example.hotplego.domain.UserVO;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import static com.example.hotplego.R.layout;

public class MainActivityLogin extends Activity implements View.OnClickListener {

    Button btnLogin;
    TextView btnSignup, btnSearch;
    EditText loginId, loginPw;
    String id, pw;
    Intent intent;

    private SharedPreferences preferences;
    private CallbackManager callbackManager;
    private LoginCallback mLoginCallback;
    private LoginButton login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_login);



        loginId = (EditText) findViewById(R.id.etEmail);
        loginPw = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        login = (LoginButton) findViewById(R.id.btnFacebook);
        btnSignup = (TextView) findViewById(R.id.tvRegister);
        btnSearch = (TextView) findViewById(R.id.tvRestore);

        btnSignup.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        mLoginCallback = new LoginCallback();
        callbackManager = CallbackManager.Factory.create();

        login.setReadPermissions(Arrays.asList("public_profile","email"));
        login.registerCallback(callbackManager, mLoginCallback);
        getHashKey();
    }

    private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
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
                                Log.i("asd", pr.obj.toString());
                                if(Boolean.parseBoolean(message)) {
                                    // TODO 유저 정보 가져와서 디비에 저장하기 (디비 저장, 유저 객체 생성)
                                    JSONArray arr = new JSONArray(pr.obj.getString("user"));
                                    JSONObject obj = new JSONObject(arr.getJSONObject(0).toString());
                                    UserVO vo = new UserVO();
                                    vo.setUCode(obj.getString("UCode"));
                                    vo.setNick(obj.getString("nick"));
                                    vo.setGender(obj.getString("gender").charAt(0));
                                    vo.setPhone(obj.getString("phone"));
                                    try {
                                        vo.setProfileImg(obj.getString("profileImg"));
                                    } catch (JSONException e) {}
                                    vo.setPoint(obj.getLong("point"));
                                    try {
//                                        vo.setBirth(new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH).parse(obj.getString("birth")));
                                        vo.setRegDate(new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(obj.getString("regDate")).getTime()));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    UserSharedPreferences.getInstance().login(MainActivityLogin.this, vo);
                                    Toast.makeText(MainActivityLogin.this, "로그인 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                    Log.i("users", vo.toString());
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
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode , resultCode, data);
    }
}