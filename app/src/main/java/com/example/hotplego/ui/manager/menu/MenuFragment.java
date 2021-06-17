package com.example.hotplego.ui.manager.menu;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotplego.PostRun;
import com.example.hotplego.R;
import com.example.hotplego.UserSharedPreferences;
import com.example.hotplego.databinding.MenuMainBinding;
import com.example.hotplego.domain.MenuVO;
import com.example.hotplego.ui.manager.menu.recyclerview.MenuAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.util.List;

public class MenuFragment extends Fragment implements MenuAdapter.OnItemClickListener {
    private MenuMainBinding binding;
    private MenuAdapter adapter;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = MenuMainBinding.inflate(inflater, container, false);

        adapter = new MenuAdapter(this);
        binding.menuList.setAdapter(adapter);
        binding.menuList.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.menuInsert.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), MenuInfo.class));
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP|ItemTouchHelper.DOWN,ItemTouchHelper.START|ItemTouchHelper.END) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                AlertDialog.Builder ad= new AlertDialog.Builder(getContext()); //아이템 삭제 여부에 대한 다이얼로그 생성
                ad.setIcon(R.drawable.ic_delete);
                ad.setTitle("삭제");
                ad.setMessage("메뉴를 삭제하시겠습니까?");

                ad.setPositiveButton("예", new DialogInterface.OnClickListener() { // 클릭 시 아이템 삭제
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.remove(viewHolder.getAdapterPosition());
                        dialog.dismiss();
                    }
                });
                ad.setNegativeButton("아니오", new DialogInterface.OnClickListener() { // 클릭 시 아이템 삭제 취소
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                ad.show();
            }
        });

        itemTouchHelper.attachToRecyclerView(binding.menuList);

        return binding.getRoot();
    }

    private void loadView() {
        PostRun postRun = new PostRun("get_menus", getActivity(), PostRun.DATA);
        postRun.setRunUI(() -> {
            try {
                adapter.setData(new Gson().fromJson(postRun.obj.getString("menus"), new TypeToken<List<MenuVO>>() {}.getType()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        postRun.addData("uCode", UserSharedPreferences.user.getUCode())
                .start();
    }

    @Override
    public void onResume() {
        loadView();
        super.onResume();
    }

    @Override
    public void onItemClick(MenuVO vo, int position) {
        Intent intent = new Intent(getContext(), MenuInfo.class);
        intent.putExtra("menu", vo);
        startActivity(intent);
    }
}
