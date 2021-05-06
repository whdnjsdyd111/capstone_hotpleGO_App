package com.example.hotplego.ui.user.course;

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

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView index;
        ImageView image;
        TextView address;
        Button remove;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);

            index = itemView.findViewById(R.id.course_index);
            image = itemView.findViewById(R.id.course_image);
            address = itemView.findViewById(R.id.course_address);
            remove = itemView.findViewById(R.id.course_remove);
        }

        public void onBind(CourseInfoVO info) {
            index.setText(String.valueOf(info.getCiIndex()));
            index.setBackgroundTintList(ContextCompat.getColorStateList(itemView.getContext(),
                    CourseFragment.COURSE_COLORS[info.getCiIndex() - 1]));
            // TODO 사진 없을 때 /image/logo.jpg 대체
            if (info.getUuid() == null) Glide.with(itemView).load(PostRun.DOMAIN + "/images/logo.jpg").into(image);
            else Glide.with(itemView).load(PostRun.DOMAIN + info.getUploadPath() + info.getUuid() + "_" + info.getFileName()).into(image);
            address.setText(info.getHtAddr());

        }
    }

    private List<CourseInfoVO> list;

    public void setData(List<CourseInfoVO> data) {
        list = data;
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
