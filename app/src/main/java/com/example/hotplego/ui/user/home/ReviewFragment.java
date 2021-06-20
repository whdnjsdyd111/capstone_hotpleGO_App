package com.example.hotplego.ui.user.home;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotplego.R;
import com.example.hotplego.databinding.HotpleReviewMainBinding;
import com.example.hotplego.domain.ReviewVO;
import com.example.hotplego.ui.user.home.adapter.ReviewAdapter;
import com.example.hotplego.ui.ReviewData;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = HotpleReviewMainBinding.inflate(inflater, container, false);

        barChart = binding.lineChart2;

        init();
        if (list.size() != 0) initLineView(barChart);

        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initLineView(HorizontalBarChart mChart) {
        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(false);
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
        xl.setEnabled(true);
        xl.setLabelCount(5);
        xl.setValueFormatter(new ValueFormatter() {
            private final String[] value = new String[] {"1 ＊", "2 ＊", "3 ＊", "4 ＊", "5 ＊"};

            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return this.value[(int) value];
            }

            @Override
            public String getFormattedValue(float value) {
                return this.value[(int) value];
            }
        });

        YAxis yl = mChart.getAxisLeft();
        yl.setEnabled(false);
        yl.setMaxWidth(100f);
        yl.setMinWidth(0f);

        YAxis rl = mChart.getAxisRight();
        rl.setDrawAxisLine(false);
        rl.setDrawGridLines(false);
        rl.setEnabled(false);


        ArrayList<BarEntry> yVal = new ArrayList<>();
        for (float i = 0f; i < 5f; i++) {
            BarEntry entry = new BarEntry(i, 0f);
            yVal.add(entry);
        }

        list.forEach(v -> {
            BarEntry entry = yVal.get(v.getRvRating() - 1);
            entry.setY(entry.getY() + 1f);
        });

        BarDataSet dataSet = new BarDataSet(yVal, null);
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return String.valueOf((int) value);
            }

            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) value);
            }
        });
        BarData data = new BarData(dataSet);
        data.setValueTextSize(10f);
        mChart.setData(data);
        mChart.invalidate();
        mChart.animateY(1000);
    }

    private void init() {
        RecyclerView recyclerView = binding.reRecyclerview;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new ReviewAdapter(list);
        recyclerView.setAdapter(adapter);

    }
}
