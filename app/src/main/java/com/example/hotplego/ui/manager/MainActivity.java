package com.example.hotplego.ui.manager;

import android.os.Bundle;
import android.view.Menu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.hotplego.R;
import com.example.hotplego.databinding.ManagerActivityBinding;
import com.example.hotplego.ui.manager.hotple.HotpleFragment;
import com.example.hotplego.ui.manager.menu.MenuFragment;
import com.example.hotplego.ui.manager.order.OrderFragment;

public class MainActivity extends AppCompatActivity {
    private ManagerActivityBinding binding;
    private ManagerPagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ManagerActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new ManagerPagerAdapter(this.getSupportFragmentManager());

        adapter.addFrag(new Fragment());
        adapter.addFrag(new OrderFragment());
        adapter.addFrag(new MenuFragment());
        adapter.addFrag(new Fragment());
        adapter.addFrag(new HotpleFragment());

        binding.pager.setAdapter(adapter);
        binding.tabLayout.setupWithViewPager(binding.pager);
        binding.tabLayout.getTabAt(0).setText(R.string.manage_sale);
        binding.tabLayout.getTabAt(1).setText(R.string.res_history);
        binding.tabLayout.getTabAt(2).setText(R.string.manage_menu);
        binding.tabLayout.getTabAt(3).setText(R.string.manage);
        binding.tabLayout.getTabAt(4).setText(R.string.manage_hotple);
    }
}
