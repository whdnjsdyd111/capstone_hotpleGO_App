package com.example.hotplego.ui.course_recommend;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotplego.MainActivity;
import com.example.hotplego.R;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Activity activity;
    private List<CourseItem> item;
    private MainActivity ac;
    String[] arr = {"먹거리","디저트","놀이/취미","음주","보기","걷기"};

    public RecyclerViewAdapter(Activity activity, List<CourseItem> item) {
        this.activity = activity;
        //MainActivity의 recyclerViewAdapter = new RecyclerViewAdapter(this,person); person 연관
        this.item = item;
    }

    //data 갯수 반환
    @Override
    public int getItemCount() {
        return item.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textOrder;
        Spinner spinner;



        public ViewHolder(View itemView) {
            super(itemView);
            textOrder = (TextView) itemView.findViewById(R.id.textViewOrder);
            spinner = (Spinner) itemView.findViewById(R.id.spinner);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, arr);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }
    }

    //뷰 홀더를 생성하고 뷰를 붙여주는 부분
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    // 재활용 되는 View가 호출, Adapter가 해당 position에 해당하는 데이터를 결합
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        CourseItem data = item.get(position);

        // 데이터 결합
        holder.textOrder.setText(data.getOrder());
    }

    private void removeItemView(int position) {
        item.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, item.size()); // 지워진 만큼 다시 채워넣기.
    }
}