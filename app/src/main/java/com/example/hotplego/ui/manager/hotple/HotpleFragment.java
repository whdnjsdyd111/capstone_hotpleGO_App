package com.example.hotplego.ui.manager.hotple;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hotplego.PostRun;
import com.example.hotplego.R;
import com.example.hotplego.databinding.ManagerHotpleBinding;
import com.example.hotplego.domain.HotpleVO;
import com.example.hotplego.domain.ManagerVO;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.w3c.dom.Text;

public class HotpleFragment extends Fragment {
    private ManagerHotpleBinding binding;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = ManagerHotpleBinding.inflate(inflater, container, false);

        Spinner bank = binding.managerBank;
        final ArrayAdapter<String> bank_adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_textview,
                getResources().getStringArray(R.array.bank_array));
        bank_adapter.setDropDownViewResource(R.layout.spinner_item);
        bank.setAdapter(bank_adapter);

        return binding.getRoot();
    }

    private void loadView() {
        PostRun postRun = new PostRun("manager_hotple", getActivity(), PostRun.DATA);
        postRun.setRunUI(() -> {
            try {
                Gson gson = new Gson();
                HotpleVO hotple = gson.fromJson(postRun.obj.getString("hotple"), HotpleVO.class);
                ManagerVO manager = gson.fromJson(postRun.obj.getString("manager"), ManagerVO.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        postRun.addData("uCode", "whdnjsdyd1111@naver.com/M/")
                .start();
    }

    @Override
    public void onResume() {
//        loadView();
        super.onResume();
    }
}
