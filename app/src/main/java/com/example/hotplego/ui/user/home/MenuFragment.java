package com.example.hotplego.ui.user.home;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotplego.PostRun;
import com.example.hotplego.R;
import com.example.hotplego.UserSharedPreferences;
import com.example.hotplego.databinding.HotpleMenuMainBinding;
import com.example.hotplego.domain.MenuVO;
import com.example.hotplego.ui.manager.menu.recyclerview.MenuAdapter;
import com.example.hotplego.ui.user.home.adapter.HotpleResAdapter;
import com.example.hotplego.ui.user.home.adapter.HotpleResCheckAdapter;
import com.example.hotplego.ui.user.home.adapter.MenuOrder;
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
import java.util.stream.Collectors;

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

    @RequiresApi(api = Build.VERSION_CODES.N)
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

        binding.reservation.setOnClickListener(v -> {
            List<MenuOrder> orders = resAdapter.getData().stream().filter(vo -> vo.getNum() > 0).collect(Collectors.toList());
            if (orders.size() == 0) {
                Toast.makeText(getContext(), "메뉴를 선택해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            AlertDialog ad = builder.create();
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            LinearLayout layout = (LinearLayout) layoutInflater.inflate(R.layout.hotple_res_dialog, null);
            ImageButton add = layout.findViewById(R.id.add_num);
            ImageButton minus = layout.findViewById(R.id.remove_num);
            TextView person = layout.findViewById(R.id.person);
            Button btn = layout.findViewById(R.id.reservation_btn);
            TextView require = layout.findViewById(R.id.require);
            TextView date = layout.findViewById(R.id.date);
            TextView name = layout.findViewById(R.id.name);
            add.setOnClickListener(e -> {
                int num = Integer.parseInt(person.getText().toString());
                person.setText(String.valueOf(num + 1));
            });
            minus.setOnClickListener(e -> {
                int num = Integer.parseInt(person.getText().toString());
                if (num > 0) {
                    person.setText(String.valueOf(num - 1));
                }
            });
            btn.setOnClickListener(e -> {
                if (require.getText().toString().isEmpty() || date.getText().toString().isEmpty() || name.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "모두 작성해주십시오.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Integer.parseInt(person.getText().toString()) == 0) {
                    Toast.makeText(getContext(), "인원 수를 제대로 작성해주십시오.", Toast.LENGTH_SHORT).show();
                    return;
                }

                int sum = 0;
                String order_name = "";

                if (orders.size() > 1) {
                    for (MenuOrder order : orders) {
                        sum += order.getMenu().getMePrice();
                    }
                    order_name += orders.get(0).getMenu().getMeName() + " 외 " + (orders.size() - 1) + "개";
                } else {
                    MenuOrder order = resAdapter.getData().get(0);
                    sum += order.getMenu().getMePrice();
                    order_name += order.getMenu().getMeName();
                }

                String mCode = "m_" + new Date().getTime();

                IamPortRequest request = IamPortRequest.builder().pg("kakaopay").pay_method(PayMethod.card).escrow(true)
                        .name(order_name).merchant_uid(mCode).amount(String.valueOf(sum)).buyer_name(name.getText().toString()).build();

                Iamport.INSTANCE.payment("imp81208754", request,
                        iamPortApprove -> {
                            Toast.makeText(getContext(), "예약 실패 또는 취소하였습니다.", Toast.LENGTH_SHORT).show();
                            return Unit.INSTANCE;
                        },
                        iamPortResponse -> {
                            if (iamPortResponse.getImp_success()) {
                                PostRun postRun = new PostRun("res_order", getActivity(), PostRun.DATA);
                                postRun.setRunUI(() -> {
                                    try {
                                        if (Boolean.parseBoolean(postRun.obj.getString("message")))
                                            Toast.makeText(getContext(), "예약 완료하였습니다.", Toast.LENGTH_SHORT).show();
                                        else {

                                        }
                                    } catch (JSONException ex) {
                                        ex.printStackTrace();
                                    }
                                });
                                postRun.addData("menuOrders", new Gson().toJson(orders))
                                        .addData("riTime", date.getText().toString())
                                        .addData("riPerson", person.getText().toString())
                                        .addData("riOdNum", mCode)
                                        .addData("riCont", require.getText().toString())
                                        .addData("uCode", UserSharedPreferences.user.getUCode())
                                        .start();
                            }
                            return Unit.INSTANCE;
                        });

                ad.dismiss();
            });

            RecyclerView recyclerView = layout.findViewById(R.id.check_recyclerview);
            HotpleResCheckAdapter adapter = new HotpleResCheckAdapter(resAdapter.getData());
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);

            ad.setView(layout);
            ad.show();
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
