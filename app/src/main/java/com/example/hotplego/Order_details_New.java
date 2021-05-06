package com.example.hotplego;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Order_details_New extends Fragment {
    private ArrayList<Order_details_New_Data> items = new ArrayList<>();

    public Order_details_New() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_details_new, viewGroup, false);

        initDataset();

        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.order_details_new);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        Order_details_New_Adapter adapter = new Order_details_New_Adapter(items, context);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void initDataset() { // 더미 데이터
        items.clear();

        items.add(new Order_details_New_Data(R.drawable.lank_f, "통새우버거 3개 외 1개", "23400원"));
        items.add(new Order_details_New_Data(R.drawable.lank_s, "육회비빔밥 2개 외 1개", "27000원"));
        items.add(new Order_details_New_Data(R.drawable.lank_s, "육회비빔밥 1개 외 1개", "17900원"));
        items.add(new Order_details_New_Data(R.drawable.lank_t, "인크레더블버거 1개 외 1개", "14000원"));
        items.add(new Order_details_New_Data(R.drawable.lank_t, "통새우버거 2개 외 1개", "16000원"));
        items.add(new Order_details_New_Data(R.drawable.lank_t, "치즈스틱 2개 외 1개", "22000원"));
    }
}