package com.example.hotplego;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.example.hotplego.domain.CourseInfoVO;
import com.example.hotplego.domain.Feature;
import com.example.hotplego.domain.HotpleVO;
import com.example.hotplego.domain.RoutePedestrian;
import com.example.hotplego.ui.user.course.CourseFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kakao.network.NetworkTask;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class TMapSetting {
    private TMapView tMapView = null;
    private Activity activity;
    private List<TMapMarkerItem> markers = new Vector<>();
    private List<TMapPoint> points = new Vector<>();
    private TextView distance;
    private static final String APIKEY = "l7xxcd9bdd0942b542fd8c7be931fc11a3b4";

    public static final Integer[] CATECORY = {
            R.color.red_700,
            R.color.teal_700,
            R.color.green_700,
            R.color.yellow_200,
            R.color.red_200,
            R.color.gray_500
    };

    public TMapSetting(TMapView tMapView, Activity activity) {
        this.tMapView = tMapView;
        this.activity = activity;
        tMapView.setSKTMapApiKey(APIKEY);
    }

    public void myLocation() {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Bitmap createViewToBitmap(String index, int color) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        @SuppressLint("InflateParams") View view = activity.getLayoutInflater().inflate(R.layout.course_index, null);
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        TextView textView = view.findViewById(R.id.cours_hotple_index);
        textView.setText(index);
        textView.setBackgroundTintList(ContextCompat.getColorStateList(activity, color));
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();

        return view.getDrawingCache();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Bitmap createViewToBitmap(int color) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        View view = activity.getLayoutInflater().inflate(R.layout.course_index, null);
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        TextView textView = view.findViewById(R.id.cours_hotple_index);
        textView.setBackgroundTintList(ContextCompat.getColorStateList(activity, color));
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();

        return view.getDrawingCache();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void hotpleMark(List<HotpleVO> list) {
        tMapView.removeAllMarkerItem();
        for (HotpleVO vo : list) {
            TMapPoint point = new TMapPoint(vo.getHtLat(), vo.getHtLng());
            points.add(point);
            TMapMarkerItem marker = new TMapMarkerItem();
            marker.setTMapPoint(point);
            marker.setIcon(createViewToBitmap(CATECORY[vo.getCategory().intValue() / 10]));
            markers.add(marker);
            marker.setCalloutTitle(vo.getBusnName());
            tMapView.addMarkerItem(String.valueOf(vo.getHtId()), marker);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void drawPath(TextView textView, List<CourseInfoVO> list) {
        distance = textView;
        for (int i = 0; i < list.size(); i++) {
            CourseInfoVO v = list.get(i);
            TMapMarkerItem item = new TMapMarkerItem();
            item.setTMapPoint(new TMapPoint(v.getHtLat(), v.getHtLng()));
            item.setIcon(createViewToBitmap(String.valueOf(v.getCiIndex()), CourseFragment.COURSE_COLORS[v.getCiIndex() - 1]));
            tMapView.addMarkerItem("" + v.getCiIndex(), item);
        }
        URL url = null;
        StringBuilder uu = new StringBuilder("https://apis.openapi.sk.com/tmap/routes/pedestrian?version=1&callback=result&appKey=" + APIKEY);
        HttpURLConnection urlConn = null;
        try {
            String startX = String.valueOf(list.get(0).getHtLng());
            String startY = String.valueOf(list.get(0).getHtLat());
            String endX = String.valueOf(list.get(list.size() - 1).getHtLng());
            String endY = String.valueOf(list.get(list.size() - 1).getHtLat());
            String reqCoordType = "WGS84GEO";
            String resCoordType = "EPSG3857";
            String startName = URLEncoder.encode("출발지", "UTF-8");
            String endName = URLEncoder.encode("목적지", "UTF-8");
            StringBuilder passList = new StringBuilder();

            for (int i = 1; i < list.size() - 1; i++) {
                CourseInfoVO vo = list.get(i);
                passList.append(vo.getHtLng()).append(",").append(vo.getHtLat());
                if (i < list.size() - 1) passList.append("_");
            }

            uu.append("&reqCoordType=WGS84GEO&resCoordType=WGS84GEO");
            uu.append("&startY=").append(startY);
            uu.append("&startX=").append(startX);
            uu.append("&endY=").append(endY);
            uu.append("&endX=").append(endX);
            uu.append("&startName=").append(URLEncoder.encode("출발지", "UTF-8"));
            uu.append("&endName=").append(URLEncoder.encode("도착지", "UTF-8"));
            uu.append("&passList=").append(passList);

            url = new URL(uu.toString());
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("POST");
            urlConn.setRequestProperty("Accept-Charset", "utf-8");
            urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            Log.i("통신 결과", urlConn.toString());
            NetworkPathTask networkTask = new NetworkPathTask(uu.toString(), null);
            networkTask.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        tMapView.setTMapPoint(list.get(0).getHtLat(), list.get(0).getHtLng());
        tMapView.setZoomLevel(14);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void drawPath1(List<CourseInfoVO> list) {
        CourseInfoVO v = list.get(0);
        TMapMarkerItem item = new TMapMarkerItem();
        item.setTMapPoint(new TMapPoint(v.getHtLat(), v.getHtLng()));
        item.setIcon(createViewToBitmap(String.valueOf(v.getCiIndex()), CourseFragment.COURSE_COLORS[v.getCiIndex() - 1]));
        tMapView.addMarkerItem("" + v.getCiIndex(), item);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void drawPath2(TextView textView, List<CourseInfoVO> list) {
        distance = textView;
        TMapData tMapData = new TMapData();
        for (int i = 0; i < 2; i++) {
            CourseInfoVO v = list.get(i);
            TMapMarkerItem item = new TMapMarkerItem();
            item.setTMapPoint(new TMapPoint(v.getHtLat(), v.getHtLng()));
            item.setIcon(createViewToBitmap(String.valueOf(v.getCiIndex()), CourseFragment.COURSE_COLORS[v.getCiIndex() - 1]));
            tMapView.addMarkerItem("" + v.getCiIndex(), item);
        }
        tMapData.findPathDataWithType(TMapData.TMapPathType.PEDESTRIAN_PATH,
                new TMapPoint(list.get(0).getHtLat(), list.get(0).getHtLng()),
                new TMapPoint(list.get(1).getHtLat(), list.get(1).getHtLng()),
                tMapPolyLine -> {
                    tMapView.addTMapPolyLine("0", tMapPolyLine);
                    printDistance(tMapPolyLine.getDistance());
                });
        tMapView.setTMapPoint(list.get(0).getHtLat(), list.get(0).getHtLng());
        tMapView.setZoomLevel(14);
    }

    @SuppressLint("SetTextI18n")
    private void printDistance(double distance) {
        activity.runOnUiThread(() -> {
            if (distance > 1000) this.distance.setText(Math.round(distance / 1000 * 10) / 10.0 + " km");
            else this.distance.setText(String.valueOf(Math.round(distance) + " m"));
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void drawPedestrian(RoutePedestrian pr) {
        double totalDistance = 0.0;
        for (int i = 0; i < pr.features.size(); i++) {
            Feature f = pr.features.get(i);
            if (f.geometry.type.equals("LineString")) {
                TMapPolyLine tMapPolyLine = new TMapPolyLine();
                for (int j = 0; j < f.geometry.coordinates.size(); j++) {
                    List<Double> list = (List<Double>) f.geometry.coordinates.get(j);
                    tMapPolyLine.addLinePoint(new TMapPoint(list.get(1), list.get(0)));
                }
                totalDistance += tMapPolyLine.getDistance();
                tMapView.addTMapPolyLine(String.valueOf(i * 10), tMapPolyLine);
            }
        }
        printDistance(totalDistance);
    }


    class NetworkPathTask extends AsyncTask<Void, Void, String> {
        private final String url;
        private final ContentValues values;
        public NetworkPathTask(String url, ContentValues values) {
            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result;
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values);
            return result;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject root = new JSONObject(s);

                RoutePedestrian rp = new Gson().fromJson(root.toString().toString(), new TypeToken<RoutePedestrian>() {}.getType());
                drawPedestrian(rp);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}



class RequestHttpURLConnection {

    public String request(String _url, ContentValues _params){

        // HttpURLConnection 참조 변수.
        HttpURLConnection urlConn = null;
        // URL 뒤에 붙여서 보낼 파라미터.
        StringBuffer sbParams = new StringBuffer();

        /**
         * 1. StringBuffer에 파라미터 연결
         * */
        // 보낼 데이터가 없으면 파라미터를 비운다.
        if (_params == null)
            sbParams.append("");
            // 보낼 데이터가 있으면 파라미터를 채운다.
        else {
            // 파라미터가 2개 이상이면 파라미터 연결에 &가 필요하므로 스위칭할 변수 생성.
            boolean isAnd = false;
            // 파라미터 키와 값.
            String key;
            String value;

            for(Map.Entry<String, Object> parameter : _params.valueSet()){
                key = parameter.getKey();
                value = parameter.getValue().toString();

                // 파라미터가 두개 이상일때, 파라미터 사이에 &를 붙인다.
                if (isAnd)
                    sbParams.append("&");

                sbParams.append(key).append("=").append(value);

                // 파라미터가 2개 이상이면 isAnd를 true로 바꾸고 다음 루프부터 &를 붙인다.
                if (!isAnd)
                    if (_params.size() >= 2)
                        isAnd = true;
            }
        }

        /**
         * 2. HttpURLConnection을 통해 web의 데이터를 가져온다.
         * */
        try{
            URL url = new URL(_url);
            urlConn = (HttpURLConnection) url.openConnection();

            // [2-1]. urlConn 설정.
            urlConn.setRequestMethod("POST"); // URL 요청에 대한 메소드 설정 : POST.
            urlConn.setRequestProperty("Accept-Charset", "UTF-8"); // Accept-Charset 설정.
            urlConn.setRequestProperty("Context_Type", "application/x-www-form-urlencoded;cahrset=UTF-8");

            // [2-2]. parameter 전달 및 데이터 읽어오기.
            String strParams = sbParams.toString(); //sbParams에 정리한 파라미터들을 스트링으로 저장. 예)id=id1&pw=123;
            OutputStream os = urlConn.getOutputStream();
            os.write(strParams.getBytes("UTF-8")); // 출력 스트림에 출력.
            os.flush(); // 출력 스트림을 플러시(비운다)하고 버퍼링 된 모든 출력 바이트를 강제 실행.
            os.close(); // 출력 스트림을 닫고 모든 시스템 자원을 해제.

            // [2-3]. 연결 요청 확인.
            // 실패 시 null을 리턴하고 메서드를 종료.
            if (urlConn.getResponseCode() != HttpURLConnection.HTTP_OK)

                return null;

            // [2-4]. 읽어온 결과물 리턴.
            // 요청한 URL의 출력물을 BufferedReader로 받는다.
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"));

            // 출력물의 라인과 그 합에 대한 변수.
            String line;
            String page = "";

            // 라인을 받아와 합친다.
            while ((line = reader.readLine()) != null){
                page += line;
            }

            return page;

        } catch (MalformedURLException e) { // for URL.
            e.printStackTrace();
        } catch (IOException e) { // for openConnection().
            e.printStackTrace();
        } finally {
            if (urlConn != null)
                urlConn.disconnect();
        }

        return null;

    }

}
