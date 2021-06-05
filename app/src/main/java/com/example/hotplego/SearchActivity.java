package com.example.hotplego;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;

import com.example.hotplego.R;
import com.example.hotplego.ui.SearchAdapter;
import com.example.hotplego.ui.SearchData;
import com.example.hotplego.ui.board.BoardData;

public class SearchActivity extends AppCompatActivity {
    SearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_hotple);
        init();
        initDataset();
        getData();


    }

    private void init() {
        RecyclerView recyclerView = findViewById(R.id.recycler_search);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new SearchAdapter();
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new HotpleItemDecoration(this));

    }

    private void getData() {
    }

    public class HotpleItemDecoration extends RecyclerView.ItemDecoration{
        private int size10;
        private int size5;

        public HotpleItemDecoration(Context context) {
            size10 = dpToPx(context, 10);
            size5 = dpToPx(context, 5);
        }

        private int dpToPx(Context context, int dp) {
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());

        }

        @Override
        public void getItemOffsets
                (@NonNull Rect outRect,  @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            int position = parent.getChildAdapterPosition(view);
            int itemCount = state.getItemCount();

            if(position == 0 || position == 1) {
                outRect.top = size10;
                outRect.bottom = size10;
            } else {
                outRect.bottom = size10;
            }

            GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
            int spanIndex = lp.getSpanIndex();

            if (spanIndex == 0) {
                outRect.left = size10;
                outRect.right = size5;
            } else if(spanIndex == 1) {
                outRect.left = size5;
                outRect.right = size10;
            }
        }

    }


    private void initDataset() {
        adapter.addItem(new SearchData(R.drawable.board, "상호명", "5.0"));
        adapter.addItem(new SearchData(R.drawable.board, "상호명", "5.0"));
        adapter.addItem(new SearchData(R.drawable.board, "상호명", "5.0"));
        adapter.addItem(new SearchData(R.drawable.board, "상호명", "5.0"));
        adapter.addItem(new SearchData(R.drawable.board, "상호명", "5.0"));
    }
}