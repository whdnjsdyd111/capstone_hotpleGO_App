package com.example.hotplego.ui.user.board;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hotplego.PostRun;
import com.example.hotplego.R;
import com.example.hotplego.domain.BoardVO;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ItemViewHolder> {

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView board_item_map;
        TextView board_item_title;
        TextView board_item_name;
        TextView board_item_count;
        TextView board_item_comm;
        TextView board_item_recoy;
        TextView board_item_recon;
        TextView board_item_time;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            board_item_map = itemView.findViewById(R.id.board_item_map);
            board_item_name = itemView.findViewById(R.id.board_item_name);
            board_item_title = itemView.findViewById(R.id.board_item_title);
            board_item_count = itemView.findViewById(R.id.board_item_count);
            board_item_comm = itemView.findViewById(R.id.board_item_comm);
            board_item_recoy = itemView.findViewById(R.id.board_item_recoy);
            board_item_recon = itemView.findViewById(R.id.board_item_recon);
            board_item_time = itemView.findViewById(R.id.board_item_time);
        }

        public void onBind(BoardVO boardData) {
            board_item_title.setText(boardData.getBdTitle());
            board_item_name.setText(boardData.getNick());
            board_item_count.setText("" + boardData.getBdRdCount());
            board_item_comm.setText("" + boardData.getCommCnt());
            board_item_recoy.setText("" + boardData.getBdRecy());
            board_item_recon.setText("" + boardData.getBdRecn());
            String img = getImgSrc(boardData.getBdCont());
            if (img.isEmpty()) board_item_map.setImageResource(R.drawable.ic_no_image);
            else Glide.with(itemView).load(PostRun.DOMAIN + img).into(board_item_map);
            board_item_time.setText(PostRun.beforeTime(boardData.getBdCode()));
        }

        private String getImgSrc(String str) {
            Pattern nonValidPattern = Pattern
                    .compile("(?i)< *[IMG][^\\>]*[src] *= *[\"\']{0,1}([^\"\'\\ >]*)");
            int imgCnt = 0;
            String content = "";
            Matcher matcher = nonValidPattern.matcher(str);
            while (matcher.find()) {
                content = matcher.group(1);
                imgCnt++;
                if(imgCnt == 1){
                    break;
                }
            }
            return content.isEmpty() ? "" : content.substring(0, PostRun.IMAGE_URL.length()) + "s_" + content.substring(PostRun.IMAGE_URL.length());
        }
    }

    public interface  OnItemClickListener {
        void onItemClick(View v, int position, BoardVO board);
    }

    private OnItemClickListener listener;
    private List<BoardVO> list;
    public BoardAdapter(OnItemClickListener listener) { this.listener = listener; }

    public void setData(List<BoardVO> data) {
        list = data;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.board_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        BoardVO vo = list.get(position);
        holder.onBind(list.get(position));
        holder.itemView.setOnClickListener(v -> {
            listener.onItemClick(v, position, vo);
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
}
