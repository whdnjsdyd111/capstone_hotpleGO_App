package com.example.hotplego.ui.manager.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hotplego.PostRun;
import com.example.hotplego.R;
import com.example.hotplego.databinding.OrderMainBinding;
import com.example.hotplego.domain.ReservationAllVO;
import com.example.hotplego.domain.ReviewVO;
import com.example.hotplego.ui.manager.order.pager.OrderPagerAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OrderFragment extends Fragment {
    private OrderMainBinding binding;
    private OrderPagerAdapter adapter;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = OrderMainBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    private void loadView() {
        adapter = new OrderPagerAdapter(getParentFragmentManager());

        PostRun postRun = new PostRun("orders", getActivity(), PostRun.DATA);
        postRun.setRunUI(() -> {
            try {
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss.S").create();
                Map<String, List<ReservationAllVO>> orders = gson.fromJson(postRun.obj.getString("orders"),
                        new TypeToken<Map<String, List<ReservationAllVO>>>() {}.getType());
                Map<String, ReviewVO> reviews = gson.fromJson(postRun.obj.getString("reviews"),
                        new TypeToken<Map<String, ReviewVO>>() {}.getType());

                Map<String, List<ReservationAllVO>> before = new HashMap<>();
                Map<String, List<ReservationAllVO>> current = new HashMap<>();

                Timestamp curTime = new Timestamp(System.currentTimeMillis());
                orders.forEach((k, v) -> {
                    if (curTime.before(v.get(0).getRiTime())) current.put(k, v);
                    else before.put(k, v);
                });

                adapter.addFrag(new OrderInfo(current));
                adapter.addFrag(new OrderInfo(before, reviews));
                binding.viewpager.setAdapter(adapter);
                binding.tab.setupWithViewPager(binding.viewpager);
                binding.tab.getTabAt(0).setText("신규 처리");
                binding.tab.getTabAt(1).setText("완료");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        postRun.addData("uCode", "whdnjsdyd1111@naver.com/M/")
                .start();
    }

    @Override
    public void onResume() {
        loadView();
        super.onResume();
    }
}
