package com.example.hotplego.ui.user.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.hotplego.ui.user.MainActivity;
import com.example.hotplego.UserSharedPreferences;
import com.example.hotplego.databinding.UserHomeBinding;
import com.example.hotplego.ui.user.common.MainActivityLogin;
import com.example.hotplego.ui.user.common.MainActivityLogout;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

public class HomeFragment extends Fragment {

    private UserHomeBinding binding;
    private SharedPreferences preferences;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = UserHomeBinding.inflate(inflater, container, false);

        Toolbar myToolbar = binding.toolbar;
        ((MainActivity) this.getActivity()).setSupportActionBar(myToolbar);
        ((MainActivity) this.getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((MainActivity) this.getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        UserSharedPreferences.getInstance().login(this.getActivity());

        binding.buttonNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserSharedPreferences.user == null) {
                    Intent intent = new Intent(getContext(), MainActivityLogin.class);
                    startActivity(intent);

                } else {
                    Intent intent = new Intent(getContext(), MainActivityLogout.class);
                    startActivity(intent);
                }
            }
        });

        binding.bnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        // 로그아웃 성공시 수행하는 지점

                    }
                });
            }
        });

        return binding.getRoot();
    }
}