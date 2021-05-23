package com.example.hotplego;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.hotplego.pick.CoursePickFragment;
import com.example.hotplego.pick.PlacePickFragment;

public class PickActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pick_list);

        // ViewPager를 이용해 장소별/코스별 목록 표시
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        FragmentStateAdapter fragmentStateAdapter = new FragmentStateAdapter(this) {
            @Override
            public int getItemCount() {
                return 2;
            }

            @NonNull
            @Override
            public Fragment createFragment(int position) {
                if (position == 0) {
                    return PlacePickFragment.newInstance();
                } else {
                    return CoursePickFragment.newInstance();
                }
            }
        };
        viewPager.setAdapter(fragmentStateAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setUserInputEnabled(false);

        // 각 버튼이 눌렸을 때 적절한 화면 표시
        findViewById(R.id.place_btn).setOnClickListener(v -> viewPager.setCurrentItem(0));
        findViewById(R.id.course_btn).setOnClickListener(v -> viewPager.setCurrentItem(1));
    }
}