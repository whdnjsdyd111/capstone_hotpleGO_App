package com.example.hotplego.ui.user.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotplego.databinding.FragmentPickPlaceBinding;
import com.example.hotplego.domain.HotpleVO;
import com.example.hotplego.ui.user.mypage.adapter.PickAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HotpleRecoFragment extends Fragment {

    private List<HotpleVO> recos = null;
    private FragmentPickPlaceBinding binding;
    private RecyclerView recyclerView;
    private PickAdapter pickAdapter = null;

    public HotpleRecoFragment() {}

    public HotpleRecoFragment(List<HotpleVO> recos) {
        this.recos = recos;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = FragmentPickPlaceBinding.inflate(inflater, container, false);

        recyclerView = binding.pickList;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        pickAdapter = new PickAdapter(recos, this::onPlaceItemClick);
        recyclerView.setAdapter(pickAdapter);

        return binding.getRoot();
    }

    private void onPlaceItemClick(HotpleVO pickData) {
        Intent intent = new Intent(getContext(), HotpleActivity.class);
        intent.putExtra("hotple", pickData);
        startActivity(intent);
    }
}
