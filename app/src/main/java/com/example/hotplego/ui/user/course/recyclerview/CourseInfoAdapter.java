package com.example.hotplego.ui.user.course.recyclerview;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.hotplego.PostRun;
import com.example.hotplego.R;
import com.example.hotplego.domain.CourseInfoVO;
import com.example.hotplego.domain.CourseVO;
import com.example.hotplego.ui.user.course.CourseHotpleFragment;
import com.example.hotplego.ui.user.course.pager.CourseHotplePagerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class CourseInfoAdapter extends RecyclerView.Adapter<CourseInfoAdapter.CourseInfoHolder> {

    public class CourseInfoHolder extends RecyclerView.ViewHolder {

        TextView time;
        TextView title;
        TextView num;
        TextView with;
        ViewPager pager;
        CourseHotplePagerAdapter adapter;

        public CourseInfoHolder(@NonNull View itemView) {
            super(itemView);

            time = itemView.findViewById(R.id.course_time);
            title = itemView.findViewById(R.id.course_title);
            num = itemView.findViewById(R.id.course_num);
            with = itemView.findViewById(R.id.course_with);
            pager = itemView.findViewById(R.id.course_hotple_pager);

            adapter = new CourseHotplePagerAdapter(((AppCompatActivity) itemView.getContext()).getSupportFragmentManager());
        }

        @SuppressLint("SetTextI18n")
        public void onBind(CourseVO info, List<CourseInfoVO> infos) {
            time.setText(PostRun.toDateStr(info.getCsCode()));
            title.setText(info.getCsTitle());
            num.setText("인원 : " + info.getCsNum());
            with.setText(info.getCsWith());

            for (CourseInfoVO vo : infos) {
                adapter.addFrag(new CourseHotpleFragment(vo));
            }
            pager.setId(++position);
            Log.i("position", "" + position);
            pager.setAdapter(adapter);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(CourseVO vo, List<CourseInfoVO> list);
    }

    private List<CourseVO> list;
    private Map<String, List<CourseInfoVO>> map;
    private static int position;
    private OnItemClickListener  listener;

    public CourseInfoAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setData(List<CourseVO> list, Map<String, List<CourseInfoVO>> map) {
        this.list = list;
        this.map = map;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public CourseInfoAdapter.CourseInfoHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_info_item, parent, false);
        return new CourseInfoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CourseInfoAdapter.CourseInfoHolder holder, int position) {
        CourseVO vo = list.get(position);
        List<CourseInfoVO> vos = map.get(vo.getCsCode());
        holder.onBind(vo, vos);
        holder.itemView.setOnClickListener(v -> {
            listener.onItemClick(vo, vos);
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
}
