package com.example.hotplego;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

public class MainActivitySearch extends Activity {

    EditText searchName, searchBirth, searchId, searchNamePw, searchBirthPw;
    String tsearchName, tsearchBirth, tsearchId, tsearchNamePw, tsearchBirthPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchId = (EditText) findViewById(R.id.SearchId); // 비밀번호 찾기의 아이디 입력 란
        searchNamePw = (EditText) findViewById(R.id.SearchNamePw); // 비밀번호 찾기의 이름 입력 란
        searchBirthPw = (EditText) findViewById(R.id.SearchBirthPw); // 비밀번호 찾기의 생년월일 입력 란

        Button searchPwbtn = (Button) findViewById(R.id.SearchPwbtn); // 비밀번호 찾기 버튼

        searchPwbtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               tsearchId = searchId.getText().toString();
               tsearchNamePw = searchNamePw.getText().toString();
               tsearchBirthPw = searchBirthPw.getText().toString();

               if(tsearchId.length()==0 || tsearchNamePw.length() == 0 || tsearchBirthPw.length() == 0) {
                   Toast.makeText(MainActivitySearch.this, "빈칸 없이 모두 입력하세요!", Toast.LENGTH_SHORT).show();
                   Log.d("search","비밀번호 찾기 공백 발생");
                   return;
               }

               Log.i("SearchPw", "비밀번호 찾는 중");
               try {

                   Log.w("앱에서 보낸값", tsearchId + ", " + tsearchNamePw + ", "+ tsearchBirthPw);

                   PostRun pr = new PostRun("userJoin", MainActivitySearch.this);
                   pr.setRunUI(new Runnable() {
                       @Override
                       public void run() {
                           try {
                               String message = pr.obj.getString("message");
                               if(Boolean.parseBoolean(message)) {
                                   Toast.makeText(MainActivitySearch.this, message, Toast.LENGTH_SHORT).show();
                                   finish();
                               } else {
                                   Toast.makeText(MainActivitySearch.this, message, Toast.LENGTH_SHORT).show();
                               }


                           } catch (JSONException e) {
                               e.printStackTrace();
                           }
                       }
                   });
                   pr.addData("uCode", searchId.getText().toString());
                   pr.addData("birthpw", searchBirthPw.getText().toString());
                   pr.addData("nick",searchNamePw.getText().toString());
                   pr.start();

               } catch (Exception e) {
                   e.printStackTrace();
               }
           }
       });
    }
}
