package com.example.hotplego;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.android.internal.http.multipart.MultipartEntity;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PostRun extends Thread implements Runnable {
    public static final String DOMAIN = "http://172.26.3.39:8000";
    public static final String IMAGE_URL = "/hotpleImage/0000/00/00/";
    public static final int DATA = 0;
    public static final int IMAGES = 1;
    private String url = DOMAIN + "/android/";
    private Map<String, String> map;
    private MultipartEntityBuilder builder;
    private final Activity activity;
    private Runnable run;
    public JSONObject obj;

    public PostRun(String url, Activity activity, int upload_kind) {
        this.url += url;
        this.activity = activity;
        if (upload_kind == DATA) map = new HashMap<>();
        else if (upload_kind == IMAGES) builder = MultipartEntityBuilder.create();
    }

    public PostRun(String url, Activity activity) {
        this.url += url;
        this.activity = activity;
    }

    public void setRunUI(Runnable run) {
        this.run = run;
    }

    public PostRun addData(String k, String v) {
        if (map != null) map.put(k, v);
        if (builder != null) builder.addTextBody(k, v);
        return this;
    }

    public PostRun addJsonData(String k, String[] v_str, Object[] v_obj) {
        JSONObject obj = new JSONObject();
        try {
            for (int i = 0; i < v_str.length; i++) {
                obj.put(v_str[i], v_obj[i]);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (map != null) map.put(k, obj.toString());
        if (builder != null) builder.addTextBody(k, obj.toString());
        return this;
    }

    public PostRun addImage(String k, Context ctx, Uri uri) {
        if (builder != null) builder.addPart(k, new FileBody(new File(getPath(ctx, uri))));
        return this;
    }

    @Override
    public void run() {
        try {
            HttpClient http = new DefaultHttpClient();
            HttpPost method = new HttpPost(url);

            if (map != null) {

                ArrayList<NameValuePair> postData = new ArrayList<>();

                // post 방식으로 전달할 값들 맵 -> 데이터
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    String k = entry.getKey();
                    String v = entry.getValue();
                    postData.add(new BasicNameValuePair(k, v));
                }

                // URI encoding 이 필요한 한글, 특수문자 값들 인코딩
                UrlEncodedFormEntity request = new UrlEncodedFormEntity(postData, "utf-8");

                method.setEntity(request);    // http 에 인코딩 세팅
            } else if (builder != null) {
                builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                builder.setCharset(StandardCharsets.UTF_8);
                method.setEntity(builder.build());
            }

            HttpResponse response = http.execute(method); // post 방식 전달 후 response 에 저장
            String body = EntityUtils.toString(response.getEntity());   // response text 를 String 으로 변환
            this.obj = new JSONObject(body);  // JSON 객체로 변환
            activity.runOnUiThread(run);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public static String getPath(Context ctx, Uri uri) {
        String fullPath = null;
        final String column = "_data";
        Cursor cursor = ctx.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            String document_id = cursor.getString(0);
            if (document_id == null) {
                for (int i=0; i < cursor.getColumnCount(); i++) {
                    if (column.equalsIgnoreCase(cursor.getColumnName(i))) {
                        fullPath = cursor.getString(i);
                        break;
                    }
                }
            } else {
                document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
                cursor.close();

                final String[] projection = {column};
                try {
                    cursor = ctx.getContentResolver().query(
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            projection, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
                    if (cursor != null) {
                        cursor.moveToFirst();
                        fullPath = cursor.getString(cursor.getColumnIndexOrThrow(column));
                    }
                } finally {
                    if (cursor != null) cursor.close();
                }
            }
        }
        return fullPath;
    }
}