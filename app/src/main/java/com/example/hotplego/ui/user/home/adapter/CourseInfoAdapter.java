package com.example.hotplego.ui.user.home.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.hotplego.R;
import com.example.hotplego.domain.HotpleVO;
import com.example.hotplego.ui.user.home.CourseHotpleFragment;
import com.example.hotplego.ui.user.course.pager.CourseHotplePagerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CourseInfoAdapter extends RecyclerView.Adapter<CourseInfoAdapter.CourseInfoHolder> {

    public class CourseInfoHolder extends RecyclerView.ViewHolder {

        ViewPager pager;
        CourseHotplePagerAdapter adapter;
        Button button;

        public CourseInfoHolder(@NonNull View itemView) {
            super(itemView);
            pager = itemView.findViewById(R.id.course_hotple_pager);

            adapter = new CourseHotplePagerAdapter(((AppCompatActivity) itemView.getContext()).getSupportFragmentManager());
            button = itemView.findViewById(R.id.detail_course);
        }

        @SuppressLint("SetTextI18n")
        public void onBind(List<HotpleVO> infos) {
            itemView.findViewById(R.id.course_all).getLayoutParams().height = 500;
            itemView.findViewById(R.id.course_info).setVisibility(View.GONE);
            button.setVisibility(View.VISIBLE);
            for (int i = 0; i < infos.size(); i++) {
                adapter.addFrag(new CourseHotpleFragment(infos.get(i), i));
            }
            pager.setId(++position);
            Log.i("position", "" + position);
            pager.setAdapter(adapter);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String key);
    }

    private Map<String, List<HotpleVO>> map;
    private List<String> list;
    private static int position;
    private CourseInfoAdapter.OnItemClickListener listener;

    public CourseInfoAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setData(Map<String, List<HotpleVO>> map) {
        this.map = map;
        list = new ArrayList<>();
        list.addAll(map.keySet());
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public CourseInfoHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_info_item, parent, false);
        return new CourseInfoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CourseInfoHolder holder, int position) {
        String key = list.get(position);
        List<HotpleVO> vos = map.get(key);
        holder.onBind(vos);
        holder.button.setOnClickListener(v -> {
            listener.onItemClick(key);
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
}
