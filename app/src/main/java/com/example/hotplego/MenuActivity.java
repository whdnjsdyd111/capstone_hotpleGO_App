package com.example.hotplego;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    private ArrayList<MenuData> items;
    private MenuAdapter menuAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    Button add_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        recyclerView = (RecyclerView) findViewById(R.id.item_list);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = (LinearLayoutManager) new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager); // 리니어 레이아웃 매니저 설정

        items = new ArrayList<>();
        menuAdapter = new MenuAdapter(items);
        recyclerView.setAdapter(menuAdapter);

        add_btn = (Button)findViewById(R.id.menu_insert_btn);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuData menuData = new MenuData("메뉴명", "가격", "메뉴 소개글", R.drawable.no_image);

                items.add(menuData);
                menuAdapter.notifyDataSetChanged();
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN,ItemTouchHelper.START|ItemTouchHelper.END) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                AlertDialog.Builder ad= new AlertDialog.Builder(MenuActivity.this); //다이얼로그 생성
                //아이템 삭제 여부 결정
                ad.setIcon(R.mipmap.ic_launcher);
                ad.setTitle("삭제");
                ad.setMessage("메뉴를 삭제하시겠습니까?");

                ad.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        menuAdapter.remove(viewHolder.getAdapterPosition());
                        menuAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                ad.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        menuAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                ad.show();
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}