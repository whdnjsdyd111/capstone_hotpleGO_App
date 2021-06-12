package com.example.hotplego.ui.user.course.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hotplego.PostRun;
import com.example.hotplego.R;
import com.example.hotplego.domain.CourseInfoVO;
import com.example.hotplego.ui.user.course.CourseFragment;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    public class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView index;
        ImageView image;
        TextView address;
        Button remove;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);

            index = itemView.findViewById(R.id.course_index);
            image = itemView.findViewById(R.id.course_image);
            address = itemView.findViewById(R.id.course_address);
            if (CourseAdapter.this.isUsed) itemView.findViewById(R.id.course_remove).setVisibility(View.GONE);
            remove = itemView.findViewById(R.id.course_remove);
        }

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
    private boolean isUsed;

    public void setData(List<CourseInfoVO> data) {
        list = data;
        notifyDataSetChanged();
    }

    public CourseAdapter() {}
    public CourseAdapter(List<CourseInfoVO> list, boolean isUsed) {
        this.list = list;
        this.isUsed = isUsed;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        CourseInfoVO vo = list.get(position);
        holder.onBind(vo);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
}
