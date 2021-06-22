package com.example.hotplego.ui.user.course.recyclerview;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hotplego.PostRun;
import com.example.hotplego.R;
import com.example.hotplego.domain.CourseInfoVO;
import com.example.hotplego.ui.user.course.CourseFragment;

import org.json.JSONException;

import java.util.List;
import java.util.function.Predicate;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    public class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView index;
        ImageView image;
        TextView address;
        public Button remove;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);

            index = itemView.findViewById(R.id.course_index);
            image = itemView.findViewById(R.id.course_image);
            address = itemView.findViewById(R.id.course_address);
            String kind = CourseAdapter.this.kind;
            if (kind != null && (kind.equals("usedCourse") || kind.equals("dibs") || kind.equals("notMine"))) {
                itemView.findViewById(R.id.course_remove).setVisibility(View.GONE);
            }
            remove = itemView.findViewById(R.id.course_remove);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void onBind(CourseInfoVO info) {
            index.setText(String.valueOf(info.getCiIndex()));
            index.setBackgroundTintList(ContextCompat.getColorStateList(itemView.getContext(),
                    CourseFragment.COURSE_COLORS[info.getCiIndex() - 1]));
            if (info.getUuid() != null) Glide.with(itemView).load(PostRun.getImageUrl(info.getUploadPath(), info.getUuid(), info.getFileName())).into(image);
            else if (info.getGoImg() != null) Glide.with(itemView).load(info.getGoImg()).into(image);
            else Glide.with(itemView).load(PostRun.DOMAIN + "/images/logo.jpg").into(image);

            address.setText(info.getHtAddr());
        }
    }

    private List<CourseInfoVO> list;
    private final InitData init;
    private final Activity activity;
    private String kind;

    public interface InitData {
        void initView();
    }

    public void setData(List<CourseInfoVO> data) {
        list = data;
        notifyDataSetChanged();
    }

    public CourseAdapter(Activity activity, InitData init) {
        this.activity = activity;
        this.init = init;
    }
    public CourseAdapter(List<CourseInfoVO> list, String kind, Activity activity, InitData init) {
        this.list = list;
        this.kind = kind;
        this.activity = activity;
        this.init = init;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item, parent, false);
        return new CourseViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        CourseInfoVO vo = list.get(position);
        holder.onBind(vo);
        holder.remove.setOnClickListener(v -> {
            PostRun postRun = new PostRun("delete-hotple-in-course", activity, PostRun.DATA);
            postRun.setRunUI(() -> {
                try {
                    if (Boolean.parseBoolean(postRun.obj.getString("message"))) {
                        list.remove(position);
                        notifyDataSetChanged();
                        init.initView();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
            postRun.addData("csCode", vo.getCsCode())
                    .addData("htId", String.valueOf(vo.getHtId()))
                    .start();
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
}
