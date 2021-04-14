package com.example.hotplego;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;


public class MainActivitySign extends Activity implements View.OnClickListener{

    EditText id, pw, pwConfirm, birth, name, phoneNum;
    String tid, tpw, tpwConfirm, tname, tbirth, tgender, tphoneNum;
    CheckBox inform_check;
    Intent intent;


    boolean pwCheck, idCheck, phoneNumCheck;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_signup);


        id = (EditText) findViewById(R.id.InputEmail);
        pw = (EditText) findViewById(R.id.InputPw);
        pwConfirm = (EditText) findViewById(R.id.InputConfirmPw);
        name = (EditText) findViewById(R.id.InputName);
        birth = (EditText) findViewById(R.id.InputBirth);
        phoneNum = (EditText) findViewById(R.id.PhoneNumber);


        Button joinBtn = (Button) findViewById(R.id.Joinbtn); // 회원가입

        joinBtn.setOnClickListener(this);



    } //onCreate() 종료


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Joinbtn: // 회원가입 버튼

                tid = id.getText().toString();
                tpw = pw.getText().toString();
                tpwConfirm = pwConfirm.getText().toString();
                tname = name.getText().toString();
                tbirth = birth.getText().toString();
                tphoneNum = phoneNum.getText().toString();
                pwCheck = Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%^&*()-])(?=.*[a-zA-Z]).{6,16}$", tpw);
                idCheck = Pattern.matches("^[_a-zA-Z0-9._%+-]+@[a-zA-z0-9.-]+\\.[a-zA-z]{2,6}$",tid);
                phoneNumCheck = Pattern.matches("^01(?:0|1[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$",tphoneNum);
                if(tid.trim().length() == 0 || tpw.trim().length() == 0 || tpwConfirm.trim().length() == 0 || tbirth.trim().length() == 0 || tname.trim().length() == 0 || tphoneNum.trim().length() == 0){
                    Toast.makeText(this, "빈칸 없이 모두 입력하세요!", Toast.LENGTH_SHORT).show();
                    Log.d("minsu", "공백 발생");
                    return;
                }

                if(!idCheck) {
                    Toast.makeText(this, "이메일 형식이 아닙니다!", Toast.LENGTH_SHORT).show();
                }

                else if(!tpw.equals(tpwConfirm)){
                    Toast.makeText(this, "비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show();
                }

                else if(!pwCheck){
                    Toast.makeText(this, "비밀번호는 6~16자 영문 대 소문자, 숫자, 특수문자의 조합을 사용하세요!", Toast.LENGTH_SHORT).show();

                }

                else if(spaceCheck(tpw)){
                    Toast.makeText(this, "비밀번호에 공백을 사용할 수 없습니다!", Toast.LENGTH_SHORT).show();
                }

                else if(!phoneNumCheck) {
                    Toast.makeText(this, "휴대폰번호 형식이 다릅니다!", Toast.LENGTH_SHORT).show();
                }

        }

    }

    public boolean spaceCheck(String spaceCheck) // 문자열 안에 스페이스 체크
    {
        for(int i = 0; i < spaceCheck.length(); i++)
        {

            if(spaceCheck.charAt(i) == ' ')
                return true;

        }
        return false;
    }

}