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

public class Reservation_Pro_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Fragment2_Data> pData;
    private LayoutInflater pInflater;
    private Context pContext;

    public Reservation_Pro_Adapter(ArrayList<Fragment2_Data> items, Context pContext) {
        this.pData = items;
        this.pInflater = LayoutInflater.from(pContext);
        this.pContext = pContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = pInflater.inflate(R.layout.reservation_pro_item, viewGroup, false);
        ViewHolderPro viewHolderPro = new ViewHolderPro(view);
        return viewHolderPro;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolderPro)holder).onBind(pData.get(position));
    }

    @Override
    public int getItemCount() {
        return pData.size();
    }

    public void addItem(Fragment2_Data data) {
        pData.add(data);
        notifyDataSetChanged();
    }


    private static class ViewHolderPro extends RecyclerView.ViewHolder {
        public static TextView re_day;
        public static TextView re_time;
        public static TextView sh_name;
        public static TextView sh_addr;
        public static ImageView sh_image;

        public ViewHolderPro(@NonNull View itemView) {
            super(itemView);

            re_day = (TextView) itemView.findViewById(R.id.re_day);
            re_time = (TextView)itemView.findViewById(R.id.re_time);
            sh_image = (ImageView) itemView.findViewById(R.id.sh_image);
            sh_name = (TextView) itemView.findViewById(R.id.sh_name);
            sh_addr = (TextView) itemView.findViewById(R.id.sh_addr);
        }

        public void onBind(Fragment2_Data data) {
            re_day.setText(data.getRe_day());
            re_time.setText(data.getRe_time());
            sh_image.setImageResource(data.getSh_image());
            sh_name.setText(data.getSh_name());
            sh_addr.setText(data.getSh_addr());
        }
    }
}
