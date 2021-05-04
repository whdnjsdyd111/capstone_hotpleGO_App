package com.example.hotplego.ui.board;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotplego.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

public class BoardActivity extends AppCompatActivity {
    BoardAdapter adapter;


    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_main);
        init();
        getData();

        Intent intent = new Intent(BoardActivity.this, DetailsActivity.class);
        startActivityForResult(intent, 1234);

        FloatingActionButton fab = findViewById(R.id.addBoardButton);
        fab.setOnClickListener(new FABClickListener());

    }

    private void init() {
        RecyclerView recyclerView = findViewById(R.id.board_recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new BoardAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void getData() {
        BoardData data = new BoardData(R.drawable.heart_on, "테스트", "이름", "1", "0");
        adapter.addItem(data);

        data = new BoardData(R.drawable.heart_off, "테스트", "이름", "2", "0");
        adapter.addItem(data);
    }

    private class FABClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data !=null) {
            String title = data.getStringExtra("title");
            String contents = data.getStringExtra("contents");
            adapter.addItem(new BoardData(R.drawable.board, title, contents, "0", "0"));
            adapter.notifyDataSetChanged();
        }
    }

}
