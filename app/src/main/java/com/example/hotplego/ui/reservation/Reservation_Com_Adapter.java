package com.example.hotplego.ui.reservation;

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

public class Reservation_Com_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Fragment1_Data> cData;
    private LayoutInflater cInflater;
    private Context cContext;

    public Reservation_Com_Adapter(ArrayList<Fragment1_Data> items, Context cContext) {
        this.cData = items;
        this.cInflater = LayoutInflater.from(cContext);
        this.cContext = cContext;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = cInflater.inflate(R.layout.reservation_com_item, viewGroup, false);
        ViewHolderCom  viewHolderCom = new ViewHolderCom(view);
        return viewHolderCom;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolderCom)holder).onBind(cData.get(position));
    }

    @Override
    public int getItemCount() {
        return cData.size();
    }

    public void addItem(Fragment1_Data data) {
        cData.add(data);
        notifyDataSetChanged();
    }

    private static class ViewHolderCom extends RecyclerView.ViewHolder{
        public static TextView re_day;
        public static TextView re_time;
        public static TextView sh_name;
        public static TextView sh_addr;
        public static ImageView sh_image;

        public ViewHolderCom(@NonNull View itemView) {
            super(itemView);

             re_day = (TextView) itemView.findViewById(R.id.re_day);
             re_time = (TextView)itemView.findViewById(R.id.re_time);
             sh_image = (ImageView) itemView.findViewById(R.id.sh_image);
             sh_name = (TextView) itemView.findViewById(R.id.sh_name);
             sh_addr = (TextView) itemView.findViewById(R.id.sh_addr);
        }

        public void onBind(Fragment1_Data data) {
            re_day.setText(data.getRe_day());
            re_time.setText(data.getRe_time());
            sh_image.setImageResource(data.getSh_image());
            sh_name.setText(data.getSh_name());
            sh_addr.setText(data.getSh_addr());
        }
    }
}
