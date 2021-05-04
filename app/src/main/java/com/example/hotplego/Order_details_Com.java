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

public class Order_details_Com extends Fragment {
    private ArrayList<Order_details_Com_Data> items = new ArrayList<>();

    public Order_details_Com() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_details_com, viewGroup, false);

        initDataset();

        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.order_details_com);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        Order_details_Com_Adapter adapter = new Order_details_Com_Adapter(items, context);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void initDataset() {
        items.clear();

        items.add(new Order_details_Com_Data(R.drawable.lank_f,"육회비빔밥 외 1개", "평균평점 4.5", "20:30"));
        items.add(new Order_details_Com_Data(R.drawable.lank_s,"육회비빔밥 외 1개", "평균평점 4.4", "21:30"));
        items.add(new Order_details_Com_Data(R.drawable.lank_s,"육회비빔밥 외 1개", "평균평점 4.4", "22:30"));
        items.add(new Order_details_Com_Data(R.drawable.lank_t,"육회비빔밥 외 1개", "평균평점 4.3", "23:30"));
        items.add(new Order_details_Com_Data(R.drawable.lank_t,"육회비빔밥 외 1개", "평균평점 4.3", "22:30"));
        items.add(new Order_details_Com_Data(R.drawable.lank_t,"육회비빔밥 외 1개", "평균평점 4.3", "21:30"));
    }
}