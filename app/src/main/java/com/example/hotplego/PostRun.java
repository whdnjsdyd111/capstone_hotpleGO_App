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
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PostRun extends Thread implements Runnable {
    public static final String DOMAIN = "http://172.30.1.25:8000";
    public static final String IMAGE_URL = "/hotpleImage/0000/00/00/";
    private String url = DOMAIN + "/android/";
    private final Map<String, String> map;
    private final Activity activity;
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

    public void addJsonData(String k, String[] v_str, Object[] v_obj) {
        JSONObject obj = new JSONObject();
        try {
            for (int i = 0; i < v_str.length; i++) {
                obj.put(v_str[i], v_obj[i]);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        map.put(k, obj.toString());
    }

    public static String beforeTime(String code) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        String str = "";
        try {
            Date writtenTime = sdf.parse(code.split("/")[0]);
            Date cur = new Date();
            long before = cur.getTime() - writtenTime.getTime();
            long hour = before / 1000 / 60 / 60 % 24;
            long minute = before / 1000/ 60 % 60;
            long second = before / 1000 % 60;
            if (hour != 0) return hour + " 시간 전";
            if (minute != 0) return minute + " 분 전";
            return second + "초 전";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    @Override
    public void run() {
        try {
            HttpClient http = new DefaultHttpClient();
            ArrayList<NameValuePair> postData = new ArrayList<>();

            // post 방식으로 전달할 값들 맵 -> 데이터
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String k = entry.getKey();
                String v = entry.getValue();
                postData.add(new BasicNameValuePair(k, v));
            }

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
