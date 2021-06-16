package com.example.hotplego.ui.user.common;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hotplego.PostRun;
import com.example.hotplego.R;
import com.example.hotplego.UserSharedPreferences;
import com.example.hotplego.domain.HotpleVO;
import com.example.hotplego.domain.UserVO;
import com.example.hotplego.ui.user.MainActivity;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static com.example.hotplego.R.layout;

public class MainActivityLogin extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin, kakaoLogout, btnGoogle;
    TextView btnSignup, btnSearch;
    EditText loginId, loginPw;
    String id, pw;
    Intent intent;
    SignInButton signInButton;
    GoogleSignInClient mGoogleSignInClient;
    static final int RC_SIGN_IN = 0;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private SharedPreferences preferences;
    private CallbackManager callbackManager;
    private LoginCallback mLoginCallback;
    private LoginButton login;
    private ISessionCallback mSessionCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_login);

        signInButton = (SignInButton) findViewById(R.id.btnGoogle);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()) {
                    case R.id.btnGoogle:
                        siginIn();
                        break;
                }
            }
        });

        mSessionCallback = new ISessionCallback() {

            @Override
            public void onSessionOpened() {
                // 로그인 요청
                UserManagement.getInstance().me(new MeV2ResponseCallback() {

                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        // 로그인 실패
                        Toast.makeText(MainActivityLogin.this, "로그인 도중 오류가 발생", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                        // 세션이 닫힘
                        Toast.makeText(MainActivityLogin.this, "세션이 닫혔습니다.. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(MeV2Response result) {

                        String nickname = result.getKakaoAccount().getProfile().getNickname();
                        long id = result.getId();
                        String email = result.getKakaoAccount().getEmail();
                        String profileImagePath = result.getProfileImagePath();

//                        Map<String, String> user = new HashMap<>();
//                        user.put("email",result.getKakaoAccount().getEmail());

                        Log.i("nickname :", "nickname /" + nickname + "");
                        Log.i("id : ", "id /" + id + "");
                        Log.i("email :", "email /" + email + "");
                        Log.i("profileImg : ", "profileImg /" + profileImagePath + "");

                        PostRun pr = new PostRun("socialLogin", MainActivityLogin.this, PostRun.DATA);
                        pr.setRunUI(() -> {
                            try {
                                UserVO vo = null;
                                if (Boolean.parseBoolean(pr.obj.getString("message"))) {
                                    Log.i("boolan", pr.obj.getString("message"));
                                    vo = new Gson().fromJson(pr.obj.getString("user"),
                                            new TypeToken<HotpleVO>() {
                                            }.getType());
                                    Log.i("vo", vo.toString());
                                } else {
                                    Intent intent = new Intent(MainActivityLogin.this, MainSocialSub.class);
                                    intent.putExtra("email", email);
                                    intent.putExtra("socialType", "KA");
                                    startActivity(intent);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        });
                        pr.addData("email", email)
                                .addData("socialType", "KA")
                                .start();

//                        PostRun postRun = new PostRun("KakaoLogin", MainActivityLogin.this, PostRun.DATA);
//                        postRun.setRunUI(new Runnable() {
//                            @Override
//                            public void run() {
//                                try {
//                                    String message = postRun.obj.getString("message");
//                                    if (Boolean.parseBoolean(message)) {
//                                        JSONObject obj = new JSONObject(postRun.obj.getString("user"));
//                                        UserVO user = new UserVO();
//                                        user.setUCode(obj.getString("UCode"));
//                                        user.setNick(obj.getString("nick"));
//                                        try {
//                                            user.setProfileImg(obj.getString("profileImg"));
//                                        } catch (JSONException e) {
//                                        }
//                                        user.setPoint(obj.getLong("point"));
//                                        try {
//                                            user.setRegDate(new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(obj.getString("regDate")).getTime()));
//                                        } catch (Exception e) {
//                                            e.printStackTrace();
//                                        }
//                                        HotpleVO hotple = null;
//                                        if (postRun.obj.getString("hotple") != null)
//                                            hotple = new Gson().fromJson(postRun.obj.getString("hotple"), HotpleVO.class);
//                                        UserSharedPreferences.getInstance().login(MainActivityLogin.this, user, hotple);
//                                        Toast.makeText(MainActivityLogin.this, "로그인 성공하였습니다.", Toast.LENGTH_SHORT).show();
//                                        Log.i("users", user.toString());
//                                        finish();
//                                    } else {
//                                        Toast.makeText(MainActivityLogin.this, message, Toast.LENGTH_SHORT).show();
//                                    }
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
//                        postRun.addData("id", loginId.getText().toString())
//                                .addData("pw", loginPw.getText().toString());
//                        postRun.start();
//
//                        // 로그인 성공
//                        Intent intent = new Intent(MainActivityLogin.this, MainActivity.class);
//                        intent.putExtra("name", result.getKakaoAccount().getProfile().getNickname());
//                        intent.putExtra("profileImg", result.getKakaoAccount().getProfile().getProfileImageUrl());
//                        intent.putExtra("email", result.getKakaoAccount().getEmail());
//                        startActivity(intent);

                        Toast.makeText(MainActivityLogin.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                    }


                });
            }

            @Override
            public void onSessionOpenFailed(KakaoException exception) {
                Toast.makeText(MainActivityLogin.this, "onSessionOpenFailed", Toast.LENGTH_SHORT).show();
            }
        };

        Session.getCurrentSession().addCallback(mSessionCallback);
        Session.getCurrentSession().checkAndImplicitOpen();

        Session.getCurrentSession().addCallback(mSessionCallback);
        Session.getCurrentSession().checkAndImplicitOpen();

        loginId = (EditText) findViewById(R.id.etEmail);
        loginPw = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        kakaoLogout = (Button) findViewById(R.id.kakao_logout);
        btnSignup = (TextView) findViewById(R.id.tvRegister);
        btnSearch = (TextView) findViewById(R.id.tvRestore);

        btnSignup.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnLogin.setOnClickListener(this);


        mLoginCallback = new LoginCallback();
        callbackManager = CallbackManager.Factory.create();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(mSessionCallback);
    }

   /* // 해시키 값 얻어오기
    private void getHashKey() {
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
    }*/


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

                    PostRun pr = new PostRun("login", this, PostRun.DATA);
                    pr.setRunUI(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String message = pr.obj.getString("message");
                                if (Boolean.parseBoolean(message)) {
                                    JSONObject obj = new JSONObject(pr.obj.getString("user"));
                                    UserVO user = new UserVO();
                                    user.setUCode(obj.getString("UCode"));
                                    user.setNick(obj.getString("nick"));
                                    user.setGender(obj.getString("gender").charAt(0));
                                    user.setPhone(obj.getString("phone"));
                                    try {
                                        user.setProfileImg(obj.getString("profileImg"));
                                    } catch (JSONException e) {
                                    }
                                    user.setPoint(obj.getLong("point"));
                                    try {
//                                        vo.setBirth(new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH).parse(obj.getString("birth")));
                                        user.setRegDate(new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(obj.getString("regDate")).getTime()));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    HotpleVO hotple = null;
                                    if (pr.obj.getString("hotple") != null)
                                        hotple = new Gson().fromJson(pr.obj.getString("hotple"), HotpleVO.class);
                                    UserSharedPreferences.getInstance().login(MainActivityLogin.this, user, hotple);
                                    Toast.makeText(MainActivityLogin.this, "로그인 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                    Log.i("users", user.toString());
                                    finish();
                                } else {
                                    Toast.makeText(MainActivityLogin.this, message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    pr.addData("id", loginId.getText().toString())
                            .addData("pw", loginPw.getText().toString());
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

    private void siginIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {

        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Toast.makeText(this,"로그인 성공",Toast.LENGTH_SHORT).show();

            String email = account.getEmail();
            PostRun pr = new PostRun("socialLogin", MainActivityLogin.this, PostRun.DATA);
            pr.setRunUI(() -> {
                try {
                    UserVO vo = null;
                    if (Boolean.parseBoolean(pr.obj.getString("message"))) {
                        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss.SSS").create();
                        vo = gson.fromJson(pr.obj.getString("user"),
                                new TypeToken<UserVO>() {
                                }.getType());
                        UserSharedPreferences.getInstance().login(MainActivityLogin.this, vo, null);
                    } else {
                        Intent intent = new Intent(MainActivityLogin.this, MainSocialSub.class);
                        intent.putExtra("email", email);
                        intent.putExtra("socialType", "GO");
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
            pr.addData("email", email)
                    .addData("socialType", "GO")
                    .start();
            Log.i("email",email);
        } catch (ApiException e) {
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(MainActivityLogin.this);

        if(account!=null) {
            Toast.makeText(this, "로그인 상태",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivityLogin.this,MainActivity.class);
            startActivity(intent);
        }
    }
}


