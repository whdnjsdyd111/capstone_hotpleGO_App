package com.example.hotplego.ui.manager.sale;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.hotplego.PostRun;
import com.example.hotplego.R;
import com.example.hotplego.UserSharedPreferences;
import com.example.hotplego.domain.ReservationAllVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.SneakyThrows;

public class SalesFragment extends Fragment {
    private final int ALLF = 1;
    private final int MONTHF = 2;
    private final int WEEKF = 3;
    private SaleFragment weekFrag = null;
    private SaleFragment monthFrag = null;
    private SaleFragment allFrag = null;
    Map<String, List<ReservationAllVO>> map = null;
    Map<String, Integer> allSale = new LinkedHashMap<>();
    Map<String, Integer> month = new LinkedHashMap<>();
    Map<String, Integer> week = new LinkedHashMap<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SneakyThrows
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sales_admin, viewGroup, false);

        Date now = new Date();

        for (int i = 0; i < 7; i ++) {
            week.put(PostRun.AddDate(now, 0, 0, -i), 0);
        }

        for (int i = 0; i < 30; i ++) {
            month.put(PostRun.AddDate(now, 0, 0, -i), 0);
        }

        PostRun postRun = new PostRun("sales", getActivity(), PostRun.DATA);
        postRun.addData("uCode", UserSharedPreferences.user.getUCode()); // TODO
        postRun.setRunUI(() -> {
            try {
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss.S").create();
                map = gson.fromJson(postRun.obj.getString("sales"),
                        new TypeToken<Map<String, List<ReservationAllVO>>>() {}.getType());
                Log.i("dd", String.valueOf(map));

                for (String key : map.keySet()) {
                    List<ReservationAllVO> vo = map.get(key);
                    int price = 0;
                    for (ReservationAllVO v : vo) {
                        price += v.getMePrice();
                    }
                    allSale.put(key.replaceAll("-", ""), price);
                }

                for (String key : allSale.keySet()) {
                    if (month.containsKey(key)) month.replace(key, allSale.get(key));
                    if (week.containsKey(key)) week.replace(key, allSale.get(key));
                }

                allFrag = new SaleFragment(allSale);
                monthFrag = new SaleFragment(month);
                weekFrag = new SaleFragment(week);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        postRun.start();

        view.findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (weekFrag != null) FragmentView(WEEKF);
            }
        });

        view.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (monthFrag != null) FragmentView(MONTHF);
            }
        });
        view.findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (weekFrag != null) FragmentView(ALLF);
            }
        });
        
        return view;
    }

    private void FragmentView(int fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

        switch (fragment) {
            case 1:
                transaction.replace(R.id.fragment_container, allFrag);
                transaction.commit();
                break;

            case 2:
                transaction.replace(R.id.fragment_container, monthFrag);
                transaction.commit();
                break;

            case 3:
                transaction.replace(R.id.fragment_container, weekFrag);
                transaction.commit();
                break;
        }

    }
}
