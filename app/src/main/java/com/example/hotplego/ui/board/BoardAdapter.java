package com.example.hotplego.ui.board;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotplego.R;

import java.util.ArrayList;

public class BoardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // adapter에 들어갈 list 입니다.
    private ArrayList<BoardData> listData = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.board_item, parent, false);
        return new ViewHolderBoard(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolderBoard)holder).onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void addItem(BoardData data) {
        listData.add(data);
    }

    private class ViewHolderBoard extends RecyclerView.ViewHolder {
        ImageView board_item_map;
        TextView board_item_title;
        TextView board_item_name;
        TextView board_item_content;
        TextView board_item_count;

        public ViewHolderBoard(@NonNull View itemView) {
            super(itemView);

            board_item_map = itemView.findViewById(R.id.board_item_map);
            board_item_content = itemView.findViewById(R.id.board_item_content);
            board_item_name = itemView.findViewById(R.id.board_item_name);
            board_item_title = itemView.findViewById(R.id.board_item_title);
            board_item_count = itemView.findViewById(R.id.board_item_count);

        }

        public void onBind(BoardData boardData) {
            board_item_title.setText(boardData.getTitle());
            board_item_name.setText(boardData.getName());
            board_item_content.setText(boardData.getContent());
            board_item_count.setText(boardData.getCount());
            board_item_map.setImageResource(boardData.getImg());
        }
    }
}
