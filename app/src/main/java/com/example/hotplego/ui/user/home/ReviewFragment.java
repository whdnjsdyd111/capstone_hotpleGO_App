package com.example.hotplego.ui.user.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotplego.R;
import com.example.hotplego.databinding.HotpleReviewMainBinding;
import com.example.hotplego.domain.ReviewVO;
import com.example.hotplego.ui.user.home.adapter.ReviewAdapter;
import com.example.hotplego.ui.ReviewData;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ReviewFragment extends Fragment {
    ReviewAdapter adapter;
    int random = 12;
    private HotpleReviewMainBinding binding;
    private HorizontalBarChart barChart;
    private List<ReviewVO> list;

    public ReviewFragment() { }
    public ReviewFragment(List<ReviewVO> list) { this.list = list; }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = HotpleReviewMainBinding.inflate(inflater, container, false);

        barChart = binding.lineChart2;

        init();
        initLineView(barChart);

        return binding.getRoot();
    }

    private void initLineView(HorizontalBarChart mChart) {
        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);
        mChart.getDescription().setEnabled(false);
        mChart.setPinchZoom(false);
        mChart.setDrawGridBackground(false);
        mChart.getLegend().setEnabled(false);
        mChart.setDragEnabled(false);
        mChart.setScaleEnabled(false);

        XAxis xl = mChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(false);
        xl.setDrawGridLines(false);

        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            yVals1.add(new BarEntry(i, (i+1)*15));
        }

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(new BarDataSet(yVals1, null));
        BarData data = new BarData(dataSets);
        data.setValueTextSize(10f);
        mChart.setData(data);

    }

    private void init() {
        RecyclerView recyclerView = binding.reRecyclerview;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new ReviewAdapter(list);
        recyclerView.setAdapter(adapter);

    }
}
