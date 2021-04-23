package com.example.hotplego;

import android.app.Activity;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostRun extends Thread implements Runnable {
    private String url = "http://192.168.1.28:8000/android/";
    private Map<String, String> map;
    private Activity activity;
    private Runnable run;
    public JSONObject obj;


    public PostRun(String url, Activity activity) {
        this.url += url;
        this.activity = activity;
        map = new HashMap<>();
    }

    public void setRunUI(Runnable run) {
        this.run = run;
    }

    public void addData(String k, String v) {
        map.put(k, v);
    }



    @Override
    public void run() {



        try {
            HttpClient http = new DefaultHttpClient();
            ArrayList<NameValuePair> postData = new ArrayList<>();

            map.forEach((k, v) -> postData.add(new BasicNameValuePair(k, v)));    // post 방식으로 전달할 값들 맵 -> 데이터

            // URI encoding 이 필요한 한글, 특수문자 값들 인코딩
            UrlEncodedFormEntity request = new UrlEncodedFormEntity(postData, "utf-8");

            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(request);    // http 에 인코딩 세팅
            HttpResponse response = http.execute(httpPost); // post 방식 전달 후 response 에 저장

            String body = EntityUtils.toString(response.getEntity());   // response text 를 String 으로 변환
            this.obj = new JSONObject(body);  // JSON 객체로 변환
            activity.runOnUiThread(run);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
