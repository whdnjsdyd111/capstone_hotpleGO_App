package com.example.hotplego.ui.reservation;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotplego.R;

import java.util.ArrayList;

public class Fragment2 extends Fragment {
    private ArrayList<Fragment2_Data> items = new ArrayList<Fragment2_Data>();

    public Fragment2() {}

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2, viewGroup, false);

        initDataset();

        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_re_com);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        Reservation_Pro_Adapter adapter = new Reservation_Pro_Adapter(items, context);
        recyclerView.setAdapter(adapter);

        ArrayList<String> items = new ArrayList<>();

        return view;
    }

    private void initDataset() {
        items.clear();

        items.add(new Fragment2_Data(R.drawable.board,"210506", "1616", "완료된", "2"));
        items.add(new Fragment2_Data(R.drawable.board,"210506", "1616", "완료된", "2"));
        items.add(new Fragment2_Data(R.drawable.board,"210506", "1616", "완료된", "2"));
        items.add(new Fragment2_Data(R.drawable.board,"210506", "1616", "완료된", "2"));
    }

}