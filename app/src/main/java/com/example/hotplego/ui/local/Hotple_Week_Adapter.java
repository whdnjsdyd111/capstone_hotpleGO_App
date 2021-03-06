package com.example.hotplego.ui.local;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotplego.R;

import java.util.ArrayList;

public class Hotple_Week_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Hotple_Week_Data> wData;
    private LayoutInflater wInflater;
    private Context wContext;

    public Hotple_Week_Adapter(ArrayList<Hotple_Week_Data> items, Context wContext) {
        this.wData = items;
        this.wInflater = LayoutInflater.from(wContext);
        this.wContext = wContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = wInflater.inflate(R.layout.local_hotple_item, viewGroup, false);
        ViewHolderWeek viewHolderWeek = new ViewHolderWeek(view);
        return viewHolderWeek;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolderWeek)holder).onBind(wData.get(position));
    }

    @Override
    public int getItemCount() {
        return wData.size();
    }

    public void addItem(Hotple_Week_Data data) {
        wData.add(data);
        notifyDataSetChanged();
    }

    public static class ViewHolderWeek extends RecyclerView.ViewHolder {
        public static TextView chart_num;
        public static TextView hp_name;
        public static TextView hp_intro;
        public static TextView hp_position;
        public static ImageView sh_image;

        public ViewHolderWeek(@NonNull View itemView) {
            super(itemView);

            chart_num = (TextView) itemView.findViewById(R.id.chart_num);
            hp_name = (TextView) itemView.findViewById(R.id.hotplace_name);
            hp_intro = (TextView) itemView.findViewById(R.id.hotplace_intro);
            hp_position = (TextView) itemView.findViewById(R.id.hotplace_position);
            sh_image = (ImageView) itemView.findViewById(R.id.sh_image);
        }

        public void onBind(Hotple_Week_Data data) {
            chart_num.setText(data.getChart_num());
            hp_name.setText(data.getHp_name());
            hp_intro.setText(data.getHp_intro());
            hp_position.setText(data.getHp_position());
            sh_image.setImageResource(data.getSh_image());
        }
    }
}
