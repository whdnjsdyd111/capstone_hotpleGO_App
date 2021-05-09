package com.example.hotplego.ui.local;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hotplego.R;

import java.util.ArrayList;


public class Fragment_Hotple_w extends Fragment {
    private ArrayList<Hotple_Week_Data> items = new ArrayList<Hotple_Week_Data>();

    public Fragment_Hotple_w() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__hotple_w, viewGroup, false);

        initDataset();

        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_hotple_w);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        Hotple_Week_Adapter adapter = new Hotple_Week_Adapter(items, context);
        recyclerView.setAdapter(adapter);

        ArrayList<String> items = new ArrayList<>();
        return view;
    }

    private void initDataset() {
        items.clear();
        items.add(new Hotple_Week_Data(R.drawable.board, "1","1핫플 이름", "1핫플 소개", "1핫플 위치"));
        items.add(new Hotple_Week_Data(R.drawable.board, "2","2핫플 이름", "2핫플 소개", "2핫플 위치"));
        items.add(new Hotple_Week_Data(R.drawable.board, "3","3핫플 이름", "3핫플 소개", "3핫플 위치"));
    }
}