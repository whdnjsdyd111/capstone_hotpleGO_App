package com.example.hotplego;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_ADD = 0;
    private static final int REQUEST_CODE_MODIFY = 1;

    private ArrayList<MenuData> items;
    private MenuAdapter menuAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        recyclerView = (RecyclerView) findViewById(R.id.item_list);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = (LinearLayoutManager) new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        items = new ArrayList<>();
        menuAdapter = new MenuAdapter(items);
        recyclerView.setAdapter(menuAdapter);

        Button add_btn = (Button)findViewById(R.id.menu_insert_btn);
        add_btn.setOnClickListener(new View.OnClickListener() { //추가하기 버튼 클릭 시 화면 전환
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MenuAdd.class);

                startActivityForResult(intent, REQUEST_CODE_ADD);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN,ItemTouchHelper.START|ItemTouchHelper.END) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                AlertDialog.Builder ad= new AlertDialog.Builder(MenuActivity.this); //아이템 삭제 여부에 대한 다이얼로그 생성
                ad.setIcon(R.drawable.ic_delete);
                ad.setTitle("삭제");
                ad.setMessage("메뉴를 삭제하시겠습니까?");

                ad.setPositiveButton("예", new DialogInterface.OnClickListener() { // 클릭 시 아이템 삭제
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        menuAdapter.remove(viewHolder.getAdapterPosition());
                        menuAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                ad.setNegativeButton("아니오", new DialogInterface.OnClickListener() { // 클릭 시 아이템 삭제 취소
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

        menuAdapter.setOnMenuDataClicked(menuData -> {
            Intent intent = new Intent(MenuActivity.this, MenuModify.class);
            intent.putExtra(MenuModify.PARAM_ORIGINAL, menuData);
            startActivityForResult(intent, REQUEST_CODE_MODIFY);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) { // 메뉴 추가 및 수정 기능
            case REQUEST_CODE_ADD:
                if (resultCode == MenuAdd.RESULT_CODE_ADD) { // 메뉴 추가
                    MenuData newMenuData = (MenuData) data.getSerializableExtra(MenuAdd.DATA_MENU_DATA); // MenuAdd에서 입력했던 데이터를 전달받음
                    items.add(newMenuData); // 메뉴 추가
                    menuAdapter.notifyDataSetChanged(); // 갱신
                }
                break;
            case REQUEST_CODE_MODIFY:
                if (resultCode == MenuModify.RESULT_CODE_SAVE) { // 메뉴 수정
                    MenuData original = (MenuData) data.getSerializableExtra(MenuModify.DATA_ORIGINAL); // 기존에 있던 수정 전 메뉴
                    MenuData after = (MenuData) data.getSerializableExtra(MenuModify.DATA_AFTER); // 수정 후 메뉴

                    int position = items.indexOf(original);
                    items.remove(position); // 기존에 있던 메뉴 삭제
                    items.add(position, after); // 수정된 메뉴 추가
                    menuAdapter.notifyDataSetChanged(); // 갱신
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}