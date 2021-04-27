package com.example.hotplego.ui.course_recommend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotplego.R;

import java.util.ArrayList;
import java.util.List;

public class CourseRecommend extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    //RecyclerViewAdapter class
    RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_recommned);

        Button locationButton = (Button) findViewById(R.id.location_search_btn);
        TextView userLocation = (TextView) findViewById(R.id.user_location);
        Intent locationIntent = getIntent();

        String location = locationIntent.getExtras().getString("location"); /*String형*/
        userLocation.setText(location);

        recyclerView = findViewById(R.id.recommendRecyclerView);
        linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.addItemDecoration(
                new DividerItemDecoration(this,linearLayoutManager.getOrientation()));

        //지정된 레이아웃매니저를 RecyclerView에 Set
        recyclerView.setLayoutManager(linearLayoutManager);
        Button plusButton = (Button)findViewById(R.id.plus_btn);
        Button minusButton = (Button)findViewById(R.id.minus_btn);

        // ArrayList에 person 객체(이름과 번호) 넣기
        List<CourseItem> item = new ArrayList<>();
        plusButton.setOnClickListener(v -> {
            item.add(new CourseItem(item.size()+1));
        });
        minusButton.setOnClickListener(v -> {
            if(item.size()>1){
                item.remove(item.size());
            }
        });

        // Adapter생성
        recyclerViewAdapter = new RecyclerViewAdapter(this,item);
        recyclerView.setAdapter(recyclerViewAdapter);

        Button addressButton = (Button)findViewById(R.id.location_search_btn);

        addressButton.setOnClickListener(view -> {
            Intent intent = new Intent(
                    getApplicationContext(), // 현재 화면의 제어권자
                    CourseRecommendAddress.class); // 다음 넘어갈 클래스 지정
            startActivity(intent); // 다음 화면으로 넘어간다
        });

    }
}
