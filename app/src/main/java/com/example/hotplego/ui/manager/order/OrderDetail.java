package com.example.hotplego.ui.manager.order;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hotplego.PostRun;
import com.example.hotplego.databinding.OrderDetailBinding;
import com.example.hotplego.domain.ReservationAllVO;
import com.example.hotplego.ui.manager.order.recyclerview.OrderDetailAdapter;

import java.text.DecimalFormat;
import java.util.List;

public class OrderDetail extends AppCompatActivity {
    private OrderDetailBinding binding;
    private OrderDetailAdapter adapter;
    private int sum = 0;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = OrderDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        List<ReservationAllVO> list = (List<ReservationAllVO>) getIntent().getSerializableExtra("orders");
        ReservationAllVO vo = list.get(0);

        adapter = new OrderDetailAdapter(list);
        binding.detailsList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.detailsList.setAdapter(adapter);

        binding.detailsNum.setText(vo.getRiPerson() + "명");
        binding.detailsOrderTime.setText(PostRun.timestampToStr(vo.getRiTime()));
        binding.detailsReq.setText(vo.getRiCont());
        binding.detailsResTime.setText(PostRun.toDateStr(vo.getRiCode()));
        list.forEach(v -> this.sum += v.getMePrice() * v.getRsMeNum());
        binding.detailsSum.setText(new DecimalFormat("###,###,###").format(sum));
    }
}
