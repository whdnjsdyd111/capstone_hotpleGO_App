package com.example.hotplego.ui.user.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hotplego.PostRun;
import com.example.hotplego.databinding.HotpleInfoBinding;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

public class InfoFragment extends Fragment {
    private HotpleInfoBinding binding;
    private long htId;

    public InfoFragment() {}
    public InfoFragment(long htId) { this.htId = htId; }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = HotpleInfoBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        loadView();
        super.onResume();
    }

    private void loadView() {
//        PostRun postRun = new PostRun("hotpleInfos", getActivity(), PostRun.DATA);
//        postRun.setRunUI(() -> {
//            try {
//
//            } catch (JSONException e) {
//
//            }
//        });
    }
}
