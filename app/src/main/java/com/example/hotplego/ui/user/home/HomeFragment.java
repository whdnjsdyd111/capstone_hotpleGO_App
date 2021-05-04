package com.example.hotplego.ui.user.home;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.hotplego.MainActivity;
import com.example.hotplego.R;
import com.example.hotplego.UserSharedPreferences;
import com.example.hotplego.databinding.ActivityMainBinding;
import com.example.hotplego.databinding.FragmentHomeBinding;
import com.example.hotplego.domain.UserVO;
import com.example.hotplego.ui.user.common.MainActivityLogin;
import com.example.hotplego.ui.user.common.MainActivityLogout;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private SharedPreferences preferences;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        Toolbar myToolbar = binding.toolbar;
        ((MainActivity) this.getActivity()).setSupportActionBar(myToolbar);
        ((MainActivity) this.getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((MainActivity) this.getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        UserSharedPreferences.getInstance().login(this.getActivity());

        binding.buttonNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserSharedPreferences.vo == null) {
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