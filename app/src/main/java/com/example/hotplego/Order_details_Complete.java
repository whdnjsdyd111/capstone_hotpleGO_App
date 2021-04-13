package com.example.hotplego;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Order_details_Complete extends Fragment {
    private View view;

    public static Order_details_Complete newInstance() {
        Order_details_Complete order_details_complete = new Order_details_Complete();

        return order_details_complete;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order_details_com, viewGroup,false);

        return view;
    }
}