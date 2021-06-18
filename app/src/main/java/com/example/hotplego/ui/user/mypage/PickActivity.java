package com.example.hotplego.ui.user.mypage;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.hotplego.R;
import com.example.hotplego.ui.user.mypage.adapter.PlacePickFragment;
import com.example.hotplego.ui.user.course.CoursesFragment;
import com.example.hotplego.ui.user.course.pager.CoursePagerAdapter;

public class PickActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private CoursePagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pick_list);

        // ViewPager를 이용해 장소별/코스별 목록 표시
        viewPager = findViewById(R.id.viewPager);
        adapter = new CoursePagerAdapter(getSupportFragmentManager());
        adapter.addFrag(PlacePickFragment.newInstance());
        adapter.addFrag(CoursesFragment.newInstance("dibs"));
        viewPager.setAdapter(adapter);

        // 각 버튼이 눌렸을 때 적절한 화면 표시
        findViewById(R.id.place_btn).setOnClickListener(v -> viewPager.setCurrentItem(0));
        findViewById(R.id.course_btn).setOnClickListener(v -> viewPager.setCurrentItem(1));
    }

    @Override
    protected void onResume() {
        adapter.getItem(viewPager.getCurrentItem()).onResume();
        super.onResume();
    }
}