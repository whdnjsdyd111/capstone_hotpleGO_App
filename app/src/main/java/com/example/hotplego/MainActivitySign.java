package com.example.hotplego;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivitySign extends Activity implements View.OnClickListener{

    EditText email, pw, pwConfirm, birth, name, phoneNum;
    String tid, tpw, tpwConfirm, tname, tbirth, tgender, tphoneNum;

    Intent intent;
    RadioGroup gendor;
    RadioButton radioButton, male, fmale;
    boolean pwCheck;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        gendor = (RadioGroup)findViewById(R.id.gendor_select);

        email = (EditText) findViewById(R.id.InputEmail);
        pw = (EditText) findViewById(R.id.InputPw);
        pwConfirm = (EditText) findViewById(R.id.InputConfirmPw);



        birth = (EditText) findViewById(R.id.InputBirth);
        name = (EditText) findViewById(R.id.PhoneNumber);

        Button joinbtn = (Button) findViewById(R.id.Joinbtn);
        joinbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.Joinbtn:

                int selectedId = gendor.getCheckedRadioButtonId();

                tid = email.getText().toString();
                tpw = pw.getText().toString();
                tpwConfirm = pwConfirm.getText().toString();
                tname = name.getText().toString();
                tbirth = birth.getText().toString();

                pwCheck = Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%^&*()-])(?=.*[a-zA-Z]).{6,16}$", tpw);

                if(tid.trim().length() == 0 || tpw.trim().length() == 0 || tpwConfirm.trim().length() == 0 || tbirth.trim().length() == 0 || tname.trim().length() == 0 || tphoneNum.trim().length() == 0){
                    Toast.makeText(this, "빈칸 없이 모두 입력하세요!", Toast.LENGTH_SHORT).show();
                    Log.d("minsu", "공백 발생");
                    return;
                }
                if(selectedId==-1) {
                    Toast.makeText(this, "다시선택", Toast.LENGTH_SHORT).show();
                }

        }
    }
}
