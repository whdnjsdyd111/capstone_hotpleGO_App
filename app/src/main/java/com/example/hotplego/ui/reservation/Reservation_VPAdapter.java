package com.example.hotplego.ui.reservation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class Reservation_VPAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> items;
    private ArrayList<String> title = new ArrayList<String>();

    public Reservation_VPAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        items = new ArrayList<Fragment>();
        items.add(new Fragment1());
        items.add(new Fragment2());

        title.add("진행 중인");
        title.add("완료된");
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }
}
