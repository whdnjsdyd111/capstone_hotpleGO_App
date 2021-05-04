package com.example.hotplego;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hotplego.databinding.ActivityWelcomeBinding;
import com.example.hotplego.domain.UserSharedPreferences;

public class MainActivityLogout extends AppCompatActivity {
    private ActivityWelcomeBinding binding;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogout.setOnClickListener(v -> {
            UserSharedPreferences.getInstance().logout(this);
            finish();
        });
    }
}
