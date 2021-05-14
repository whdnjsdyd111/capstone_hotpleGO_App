package com.example.hotplego.ui.manager.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hotplego.databinding.OrderInfoBinding;
import com.example.hotplego.domain.ReservationAllVO;
import com.example.hotplego.domain.ReviewVO;
import com.example.hotplego.ui.manager.order.recyclerview.OrderAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OrderInfo extends Fragment {
    private OrderInfoBinding binding;
    private OrderAdapter adapter;
    private Map<String, List<ReservationAllVO>> reservation;
    private Map<String, ReviewVO> review = null;

    public OrderInfo(Map<String, List<ReservationAllVO>> reservation) {
        this.reservation = reservation;
    }

    public OrderInfo(Map<String, List<ReservationAllVO>>reservation, Map<String, ReviewVO> review) {
        this.reservation = reservation;
        this.review = review;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = OrderInfoBinding.inflate(inflater, container, false);

        adapter = new OrderAdapter(reservation, review, getActivity());
        binding.orderDetailsCom.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.orderDetailsCom.setAdapter(adapter);

        return binding.getRoot();
    }
}
