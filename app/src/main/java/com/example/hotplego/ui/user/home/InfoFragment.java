package com.example.hotplego.ui.user.home;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.hotplego.PostRun;
import com.example.hotplego.databinding.HotpleInfoBinding;
import com.example.hotplego.domain.HotpleVO;
import com.example.hotplego.domain.OpenInfoVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.util.List;

public class InfoFragment extends Fragment {
    private HotpleInfoBinding binding;
    private HotpleVO vo;

    public InfoFragment() {}
    public InfoFragment(HotpleVO vo) { this.vo = vo; }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = HotpleInfoBinding.inflate(inflater, container, false);

        binding.adress.setText(vo.getHtAddr() + "\n" + vo.getHtAddrDet());
        binding.tel.setText(toTel(vo.getHtTel()));
        binding.description.setText(vo.getHtCont());
        loadView();
        TMapView tMapView = new TMapView(getContext());
        tMapView.setZoomLevel(15);
        tMapView.setTMapPoint(vo.getHtLat(), vo.getHtLng());
        TMapMarkerItem marker = new TMapMarkerItem();
        marker.setTMapPoint(new TMapPoint(vo.getHtLat(), vo.getHtLng()));
        tMapView.addMarkerItem("0", marker);
        binding.tmapUsing.addView(tMapView);

        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResume() {
        super.onResume();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void loadView() {
        PostRun postRun = new PostRun("get_open", getActivity(), PostRun.DATA);
        postRun.setRunUI(() -> {
            try {
                List<OpenInfoVO> list = new Gson().fromJson(postRun.obj.getString("openInfos"), new TypeToken<List<OpenInfoVO>>() {}.getType());
                Log.i("ddd", list.toString());
                TableLayout.LayoutParams rowParams = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                TableRow.LayoutParams textParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
                textParams.weight = 1;
                list.forEach(o -> {
                    TableRow row = new TableRow(getContext());
                    TextView day = new TextView(getContext());
                    day.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    day.setLayoutParams(textParams);

                    if (o.getTCode() != null) {
                        TextView time = new TextView(row.getContext());
                        time.setLayoutParams(textParams);
                        time.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        switch (o.getHtOb().replace("/", "")) {
                            case "WO":
                                day.setText("평일 오픈");
                                break;
                            case "WB":
                                day.setText("평일 휴식");
                                break;
                            case "TO":
                                day.setText("토요일 오픈");
                                break;
                            case "TB":
                                day.setText("토요일 휴식");
                                break;
                            case "SO":
                                day.setText("일요일 오픈");
                                break;
                            case "SB":
                                day.setText("일요일 휴식");
                                break;
                        }
                        time.setText(tCodeToTime(o.getTCode()));
                        row.addView(day);
                        row.addView(time);
                        binding.openInfo.addView(row, rowParams);
                    } else {
                        String str = "";
                        String[] ob = o.getHtOb().split("/");
                        switch (ob[0]) {
                            case "0":
                                str += "매주 ";
                                break;
                            case "1":
                                str += "첫째 주";
                                break;
                            case "2":
                                str += "둘째 주";
                                break;
                            case "3":
                                str += "셋째 주";
                                break;
                            case "4":
                                str += "넷째 주";
                                break;
                        }
                        switch (ob[1]) {
                            case "0":
                                str += "일요일";
                                break;
                            case "1":
                                str += "월요일";
                                break;
                            case "2":
                                str += "화요일";
                                break;
                            case "3":
                                str += "수요일";
                                break;
                            case "4":
                                str += "목요일";
                                break;
                            case "5":
                                str += "금요일";
                                break;
                            case "6":
                                str += "일토요일";
                                break;
                        }
                        day.setText(str);
                        binding.openHolyday.addView(day, rowParams);
                    }

                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        postRun.addData("htId", String.valueOf(vo.getHtId()))
                .start();
    }

    private String toTel(String str) {
        if (str == null) return null;
        StringBuffer sb = new StringBuffer(str);
        sb.insert(7, '-');
        sb.insert(3, '-');
        return sb.toString();
    }

    private String tCodeToTime(String tCode) {
        String[] str = tCode.split("/");
        for (int i = 0; i < 2; i++) {
            str[i] = new StringBuilder(str[i]).insert(2, ":").toString();
        }
        return str[0] + " ~ " + str[1];
    }
}
