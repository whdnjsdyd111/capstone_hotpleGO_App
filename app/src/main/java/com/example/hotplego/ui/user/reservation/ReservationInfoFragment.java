package com.example.hotplego.ui.user.reservation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotplego.PostRun;
import com.example.hotplego.databinding.ReservationFragmentBinding;
import com.example.hotplego.domain.ReservationAllVO;
import com.example.hotplego.domain.ReservationHotpleVO;
import com.example.hotplego.ui.user.reservation.recyclerview.ReservationAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.util.List;
import java.util.Map;

public class ReservationInfoFragment extends Fragment {

    private ReservationFragmentBinding binding;
    private ReservationAdapter adapter;
    private List<ReservationHotpleVO> vo;
    public static Map<String, List<ReservationAllVO>> map;
    private boolean isProceed;

    public ReservationInfoFragment(List<ReservationHotpleVO> vo, boolean isProceed) {
        this.vo = vo;
        this.isProceed = isProceed;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = ReservationFragmentBinding.inflate(inflater, container, false);

        RecyclerView recyclerView = binding.reservationRecyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ReservationAdapter(vo, isProceed, getActivity());
        recyclerView.setAdapter(adapter);

        return binding.getRoot();
    }
}
