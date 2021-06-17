package com.example.hotplego.ui.user.reservation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.hotplego.PostRun;
import com.example.hotplego.R;
import com.example.hotplego.UserSharedPreferences;
import com.example.hotplego.databinding.UserReservationBinding;
import com.example.hotplego.domain.ReservationAllVO;
import com.example.hotplego.domain.ReservationHotpleVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReservationFragment extends Fragment {
    private UserReservationBinding binding;
    private final int PROCEEDING = 1;
    private final int COMPLETED = 2;
    private int selected = 1;
    Fragment proceeding;
    Fragment completed;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = UserReservationBinding.inflate(inflater, container, false);

        binding.proceeding.setOnClickListener(v -> fragmentView(PROCEEDING));
        binding.completed.setOnClickListener(v -> fragmentView(COMPLETED));

        PostRun postRun = new PostRun("reservation", getActivity(), PostRun.DATA);
        postRun.setRunUI(() -> {
            try {
                Gson gson = new Gson();
                List<ReservationHotpleVO> vo = gson.fromJson(
                        postRun.obj.getString("reservation"), new TypeToken<List<ReservationHotpleVO>>() {}.getType());
                List<ReservationHotpleVO> proceeding = new ArrayList<>();
                List<ReservationHotpleVO> completed = new ArrayList<>();
                for (ReservationHotpleVO v : vo) {
                    if (reservBol(v.getRiTime())) proceeding.add(v);
                    else completed.add(v);
                }
                ReservationInfoFragment.map = gson.fromJson(
                        postRun.obj.getString("reservations"), new TypeToken<Map<String, List<ReservationAllVO>>>() {}.getType());
                this.proceeding = new ReservationInfoFragment(proceeding, true);
                this.completed = new ReservationInfoFragment(completed, false);
                fragmentView(selected);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        postRun.addData("uCode", UserSharedPreferences.user.getUCode()).start();

        return binding.getRoot();
    }

    private boolean reservBol(Timestamp time) {
        Timestamp curTime = new Timestamp(System.currentTimeMillis());
        return curTime.before(time);
    }

    private void fragmentView(int fragment) {

        selected = fragment;

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        switch (fragment) {
            case 1:
                if (proceeding != null) {
                    transaction.replace(R.id.fragment_container, proceeding);
                    transaction.commit();
                }
                break;

            case 2:
                if (completed != null) {
                    transaction.replace(R.id.fragment_container, completed);
                    transaction.commit();
                }
                break;
        }
    }
}