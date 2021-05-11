package com.example.hotplego.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotplego.R;

import java.util.ArrayList;

public class MyPointAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    TextView move_position;
    TextView point_day;
    TextView point_time;
    TextView save_point;
    TextView point_type;

    private ArrayList<PointData> listData = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mypoint_item,parent,false);
        ViewHolderPoint viewHolderPoint = new ViewHolderPoint(view);

        return viewHolderPoint;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolderPoint)holder).onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void addItem(PointData data) {
        listData.add(data);
        notifyDataSetChanged();
    }

    class ViewHolderPoint extends RecyclerView.ViewHolder {

        public ViewHolderPoint(@NonNull View itemView) {
            super(itemView);

            point_day = itemView.findViewById(R.id.point_day);
            move_position = itemView.findViewById(R.id.move_position);
            save_point = itemView.findViewById(R.id.save_point);
            point_time = itemView.findViewById(R.id.point_time);
            point_type = itemView.findViewById(R.id.point_type);

        }
     public void onBind(PointData pointData) {
            point_day.setText(pointData.getPointday());
            move_position.setText(pointData.getMposition());
            point_time.setText(pointData.getMtime());
            save_point.setText(pointData.getPoint());
            point_type.setText(pointData.getPointtype());

       }
    }
}
