package com.example.hotplego;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;

import java.util.regex.Pattern;


public class MainActivitySign extends Activity implements View.OnClickListener{

    EditText email, pw, pwConfirm, birth, name, phoneNum;
    String tid, tpw, tpwConfirm, tname, tbirth, tphoneNum;
    CheckBox inform_check;
    Intent intent;

    boolean pwCheck, idCheck, phoneNumCheck;
    boolean validate = false;
    boolean isCheck = false;
    String gender = null;
    RadioGroup radio;
    RadioButton radiobutton,rb1,rb2;
    Button joinBtn, validateButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);



        email = (EditText) findViewById(R.id.InputEmail); // 이메일 입력
        pw = (EditText) findViewById(R.id.InputPw); // 패스워드 입력
        pwConfirm = (EditText) findViewById(R.id.InputConfirmPw); // 패스워드 확인
        name = (EditText) findViewById(R.id.InputName); // 닉네임
        birth = (EditText) findViewById(R.id.InputBirth); // 생일
        phoneNum = (EditText) findViewById(R.id.PhoneNumber); // 폰 번호
        inform_check = (CheckBox) findViewById(R.id.inform_check);
        inform_check.setOnClickListener(this);

        radio = (RadioGroup) findViewById(R.id.gendor_select); // 성별 선택(라디오 그룹)
        rb1 = (RadioButton) findViewById(R.id.male); // 성별(버튼)
        rb2 = (RadioButton) findViewById(R.id.fmale); // 성별(버튼)
        joinBtn = (Button) findViewById(R.id.Joinbtn); // 회원가입
        joinBtn.setOnClickListener(this);

        validateButton = (Button) findViewById(R.id.validateButton);
        validateButton.setOnClickListener(this);

        rb1.setOnClickListener(i -> gender = "M");
        rb2.setOnClickListener(i -> gender = "W");
    } //onCreate() 종료


    @Override
    public void onClick(View v) {
        int selectedId = radio.getCheckedRadioButtonId();
        radiobutton = (RadioButton) findViewById(selectedId);

        switch (v.getId()) {


            case R.id.Joinbtn: // 회원가입 버튼

                tid = email.getText().toString();
                tpw = pw.getText().toString();
                tpwConfirm = pwConfirm.getText().toString();
                tname = name.getText().toString();
                tbirth = birth.getText().toString();
                tphoneNum = phoneNum.getText().toString();
                pwCheck = Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%^&*()-])(?=.*[a-zA-Z]).{6,16}$", tpw);
                idCheck = Pattern.matches("^[_a-zA-Z0-9._%+-]+@[a-zA-z0-9.-]+\\.[a-zA-z]{2,6}$", tid);
                phoneNumCheck = Pattern.matches("^01(?:0|1[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", tphoneNum);


                if (tid.trim().length() == 0 || tpw.trim().length() == 0 || tpwConfirm.trim().length() == 0 || tbirth.trim().length() == 0 || tname.trim().length() == 0 || tphoneNum.trim().length() == 0) {
                    Toast.makeText(this, "빈칸 없이 모두 입력하세요!", Toast.LENGTH_SHORT).show();
                    Log.d("Blank", "공백 발생");
                    return;
                }

                if (!idCheck) {
                    Toast.makeText(this, "이메일 형식이 아닙니다!", Toast.LENGTH_SHORT).show();
                }

                else if (selectedId == -1) {
                    Toast.makeText(MainActivitySign.this, "성별을 선택해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!tpw.equals(tpwConfirm)) {
                    Toast.makeText(this, "비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show();
                } else if (!pwCheck) {
                    Toast.makeText(this, "비밀번호는 6~16자 영문 대 소문자, 숫자, 특수문자의 조합을 사용하세요!", Toast.LENGTH_SHORT).show();

                } else if (spaceCheck(tpw)) {
                    Toast.makeText(this, "비밀번호에 공백을 사용할 수 없습니다!", Toast.LENGTH_SHORT).show();
                } else if (!phoneNumCheck) {
                    Toast.makeText(this, "휴대폰번호 형식이 다릅니다!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (gender == null) {
                    Toast.makeText(this, "성별을 선택해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }  else if (!isCheck) {
                    Toast.makeText(this, "이메일 중복확인을 하십시오.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!validate) {
                    Toast.makeText(this, "이미 이메일이 존재합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.i("Join", "회원가입 하는중");
                try {

                    Log.w("앱에서 보낸값", email + ", " + pw);

                    PostRun pr = new PostRun("userJoin", this);
                    pr.setRunUI(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String message = pr.obj.getString("message");
                                if(Boolean.parseBoolean(message)) {
                                    Toast.makeText(MainActivitySign.this, message, Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(MainActivitySign.this, message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    pr.addData("email", email.getText().toString());
                    pr.addData("pw", pw.getText().toString());
                    pr.addData("phone",phoneNum.getText().toString());
                    pr.addData("uCode", email.getText().toString());
                    pr.addData("birth_str", birth.getText().toString());
                    pr.addData("nick", name.getText().toString());
                    pr.addData("gender", gender);

                    pr.start();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.validateButton:
                PostRun pr = new PostRun("check_email", this);
                pr.setRunUI(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Boolean bol = pr.obj.getBoolean("message");

                            if (bol) {
                                Toast.makeText(MainActivitySign.this, "사용 가능한 이메일입니다.", Toast.LENGTH_SHORT).show();
                                validate = true;
                                isCheck = true;
                            } else {
                                Toast.makeText(MainActivitySign.this, "이미 존재하는 이메일입니다.", Toast.LENGTH_SHORT).show();
                                validate = false;
                                isCheck = false;
                            }
                        } catch (Exception e) {
                            Toast.makeText(MainActivitySign.this, "에러 발생!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                pr.addData("email", email.getText().toString());
                pr.start();
        }
    }


    public boolean spaceCheck(String spaceCheck) // 문자열 안에 스페이스 체크
    {
        for(int i = 0; i < spaceCheck.length(); i++) {

            if(spaceCheck.charAt(i) == ' ')
                return true;

        }
        return false;
    }
}

