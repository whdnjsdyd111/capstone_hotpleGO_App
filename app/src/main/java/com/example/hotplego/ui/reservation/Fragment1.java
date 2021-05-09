package com.example.hotplego.ui.reservation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotplego.R;

import java.util.ArrayList;

public class Fragment1 extends Fragment {
    private ArrayList<Fragment1_Data> items = new ArrayList<Fragment1_Data>();

    public Fragment1() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1, viewGroup, false);

        initDataset();

        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_re_pro);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        Reservation_Com_Adapter adapter = new Reservation_Com_Adapter(items, context);
        recyclerView.setAdapter(adapter);

        ArrayList<String> items = new ArrayList<>();
        return view;

    }

    private void initDataset() {
        items.clear();

        items.add(new Fragment1_Data(R.drawable.board,"210506","1515", "진행 중", "1"));
        items.add(new Fragment1_Data(R.drawable.board,"210506","1515", "진행 중", "1"));
        items.add(new Fragment1_Data(R.drawable.board,"210506","1515", "진행 중", "1"));
        items.add(new Fragment1_Data(R.drawable.board,"210506","1515", "진행 중", "1"));


    }

}