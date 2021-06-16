package com.example.hotplego.ui.user.common;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hotplego.PostRun;
import com.example.hotplego.UserSharedPreferences;
import com.example.hotplego.databinding.ActivitySocialBinding;
import com.example.hotplego.domain.UserVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.util.regex.Pattern;

public class MainSocialSub extends AppCompatActivity {
    private ActivitySocialBinding binding;
    EditText name, pw, birth, phoneNum;
    String gender = null;
    RadioGroup radio;
    String tpw, tphoneNum, tbirth, tname;
    RadioButton radioButton;

    boolean nameCheck, phoneNumCheck;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySocialBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String email = getIntent().getStringExtra("email");
        String socialType = getIntent().getStringExtra("socialType");
        name = binding.InputName;
        phoneNum = binding.PhoneNumber;
        birth = binding.InputBirth;
        radio = binding.gendorSelect;
        binding.male.setOnClickListener(i -> gender = "M");
        binding.fmale.setOnClickListener(i -> gender = "W");



        binding.Joinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedId = radio.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);
                tphoneNum = phoneNum.getText().toString();
                tbirth = birth.getText().toString();
                tphoneNum = phoneNum.getText().toString();
                tname = name.getText().toString();

                nameCheck = Pattern.matches("^[0-9a-zA-Z가-힣]*$",tname);
                phoneNumCheck = Pattern.matches("^01(?:0|1[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", tphoneNum);

                if (tname.trim().length() == 0 || tbirth.trim().length() == 0 || tphoneNum.trim().length() == 0) {
                    Toast.makeText(MainSocialSub.this, "빈칸 없이 모두 입력하세요!", Toast.LENGTH_SHORT).show();
                    Log.d("Blank", "공백 발생");
                    return;
                } else if(!nameCheck) {
                    Toast.makeText(MainSocialSub.this,"숫자,영문자,한글만 포함 가능합니다.",Toast.LENGTH_SHORT).show();
                }
                  else if(!phoneNumCheck) {
                    Toast.makeText(MainSocialSub.this, "휴대폰번호 형식이 다릅니다!", Toast.LENGTH_SHORT).show();
                    return;
                } else if(gender == null) {
                    Toast.makeText(MainSocialSub.this, "성별을 선택해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    PostRun pr = new PostRun("socialRegister", MainSocialSub.this, PostRun.DATA);
                    pr.setRunUI(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String message = pr.obj.getString("message");
                                if (Boolean.parseBoolean(message)) {
                                    Toast.makeText(MainSocialSub.this, "환영합니다.", Toast.LENGTH_SHORT).show();
                                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss.SSS").create();
                                    UserVO vo = gson.fromJson(pr.obj.getString("user"), new TypeToken<UserVO>() {}.getType());
                                    UserSharedPreferences.getInstance().login(MainSocialSub.this, vo, null);
                                    finish();
                                } else {
                                    Toast.makeText(MainSocialSub.this, "다시 시도해주십시오.", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    pr.addData("phone", phoneNum.getText().toString())
                            .addData("email", email)
                            .addData("socialType", socialType)
                            .addData("birth_str", birth.getText().toString())
                            .addData("nick", name.getText().toString())
                            .addData("gender", gender);
                    pr.start();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
