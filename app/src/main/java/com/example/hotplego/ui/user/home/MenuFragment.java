package com.example.hotplego.ui.user.home;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotplego.PostRun;
import com.example.hotplego.R;
import com.example.hotplego.databinding.HotpleMenuMainBinding;
import com.example.hotplego.domain.MenuVO;
import com.example.hotplego.domain.ReservationAllVO;
import com.example.hotplego.ui.manager.menu.recyclerview.MenuAdapter;
import com.example.hotplego.ui.user.home.adapter.HotpleResAdapter;
import com.example.hotplego.ui.user.home.adapter.HotpleResCheckAdapter;
import com.example.hotplego.ui.user.home.adapter.MenuOrder;
import com.example.hotplego.ui.user.reservation.ReservationInfoFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iamport.sdk.data.sdk.IamPortRequest;
import com.iamport.sdk.data.sdk.PayMethod;
import com.iamport.sdk.domain.core.Iamport;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import kotlin.Unit;

public class MenuFragment extends Fragment implements MenuAdapter.OnItemClickListener, HotpleResAdapter.OnDeleteClick {
    private HotpleMenuMainBinding binding;
    private MenuAdapter adapter;
    private HotpleResAdapter resAdapter;
    private long htId;

    public MenuFragment() {

    }

    public MenuFragment(long htId) {
        this.htId = htId;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = HotpleMenuMainBinding.inflate(inflater, container, false);

        adapter = new MenuAdapter(this);
        binding.menuList.setAdapter(adapter);
        binding.menuList.setLayoutManager(new LinearLayoutManager(getContext()));

        resAdapter = new HotpleResAdapter(this);
        binding.resRecyclerview.setAdapter(resAdapter);
        binding.resRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        Iamport.INSTANCE.init(this);

//        binding.reservation.setOnClickListener(v -> {
////            IamportClient client = new IamportClient("3158229450476427", "0ZhM3lMpwifNyac3fGOR92EXeV26EAyEAPrDbd3Hwiu4tH8JnWAwZetdawjP7RtHHVlS9oAFH4KaLTT9");
//            IamPortRequest request = IamPortRequest.builder().pg("kakaopay").pay_method(PayMethod.card).escrow(true)
//                    .name("안드로이드 주문").merchant_uid("m_" + new Date().getTime()).amount("3000").buyer_name("조원용").build();
//
//            Iamport.INSTANCE.payment("imp81208754", request,
//                    iamPortApprove -> { return Unit.INSTANCE; },
//                    iamPortResponse -> { return Unit.INSTANCE; });
//        });

        binding.reservation.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            LinearLayout layout = (LinearLayout) layoutInflater.inflate(R.layout.hotple_res_dialog, null);
            ImageButton add = layout.findViewById(R.id.add_num);
            ImageButton minus = layout.findViewById(R.id.remove_num);
            TextView person = layout.findViewById(R.id.person);
            add.setOnClickListener(e -> {
                int num = Integer.parseInt(person.getText().toString());
                person.setText(String.valueOf(num + 1));
            });
            minus.setOnClickListener(e -> {
                int num = Integer.parseInt(person.getText().toString());
                if (num > 0) {
                    person.setText(String.valueOf(num + 1));
                }
            });

            RecyclerView recyclerView = layout.findViewById(R.id.check_recyclerview);
            HotpleResCheckAdapter adapter = new HotpleResCheckAdapter(resAdapter.getData());
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);

            builder.setView(layout);
            builder.show();
        });

        return binding.getRoot();
    }

    @Override
    public void onItemClick(MenuVO vo, int position) {
        resAdapter.addData(position);
        long sum = 0;
        for (MenuOrder o : resAdapter.list) {
            sum += o.getMenu().getMePrice() * o.getNum();
        }
        binding.totalPrice.setText(new DecimalFormat("###,###,###").format(sum));
    }

    @Override
    public void onDeleteMenu(int position) {
        long sum = 0;
        for (MenuOrder o : resAdapter.list) {
            sum += o.getMenu().getMePrice() * o.getNum();
        }
        binding.totalPrice.setText(new DecimalFormat("###,###,###").format(sum));
    }

    @Override
    public void onResume() {
        loadView();
        super.onResume();
    }

    private void loadView() {
        PostRun postRun = new PostRun("hotpleMenus", getActivity(), PostRun.DATA);
        postRun.setRunUI(() -> {
            try {
                List<MenuVO> list = new Gson().fromJson(postRun.obj.getString("menus"), new TypeToken<List<MenuVO>>() {}.getType());
                adapter.setData(list);
                resAdapter.setData(list);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        postRun.addData("htId", String.valueOf(htId))
                .start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Iamport.INSTANCE.close();
    }
}
