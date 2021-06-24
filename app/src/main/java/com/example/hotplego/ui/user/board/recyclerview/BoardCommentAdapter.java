package com.example.hotplego.ui.user.board.recyclerview;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotplego.ImageGetterImpl;
import com.example.hotplego.PostRun;
import com.example.hotplego.R;
import com.example.hotplego.UserSharedPreferences;
import com.example.hotplego.domain.CommentVO;
import com.example.hotplego.ui.user.board.BoardDetailActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.util.List;
import java.util.Map;

public class BoardCommentAdapter extends RecyclerView.Adapter<BoardCommentAdapter.CommentViewHolder> {

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        TextView nick;
        AppCompatButton com_reco;
        int recoy;
        int recon;
        AppCompatButton com_nonReco;
        TextView com_cont;
        TextView com_time;
        EditText com_reply;
        String reco;
        String comCode;

        public CommentViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            nick = itemView.findViewById(R.id.nick);
            com_reco = itemView.findViewById(R.id.com_reco);
            com_nonReco = itemView.findViewById(R.id.com_nonReco);
            com_cont = itemView.findViewById(R.id.com_cont);
            com_time = itemView.findViewById(R.id.com_time);
            com_reply = itemView.findViewById(R.id.reply_write);
            itemView.findViewById(R.id.reply_btn).setOnClickListener(v -> {
                if (reply != null && ((LinearLayout) itemView.findViewById(R.id.reply)) == reply) {
                    if (reply.getVisibility() == View.VISIBLE) {
                        reply.setVisibility(View.GONE);
                        return;
                    } else {
                        reply.setVisibility(View.VISIBLE);
                        return;
                    }
                }
                if (reply != null) reply.setVisibility(View.GONE);
                reply = (LinearLayout) itemView.findViewById(R.id.reply);
                BoardCommentAdapter.this.com_reply = this.com_reply;
                reply.setVisibility(View.VISIBLE);
            });
            itemView.findViewById(R.id.comm_upload).setOnClickListener(v -> {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                activity.startActivityForResult(intent, PICK_IMAGE);
                BoardDetailActivity.reply_toggle = BoardDetailActivity.REPLY;
            });
        }

        public void onBind(CommentVO vo, String reco) {
            nick.setText(vo.getNick());
            com_reco.setText(String.valueOf(vo.getComRecy()));
            recoy = vo.getComRecy();
            recon = vo.getComRecn();
            com_nonReco.setText(String.valueOf(vo.getComRecn()));
            vo.setComCont(vo.getComCont().replaceAll("src=\"", "src=\"" + PostRun.DOMAIN));
            com_cont.setText(Html.fromHtml(vo.getComCont(), new ImageGetterImpl(itemView.getContext(), com_cont), null));
            com_time.setText(PostRun.beforeBigTime(vo.getComCode()));
            if (vo.getReplyCode() != null) {
                itemView.findViewById(R.id.reply_mark).setVisibility(View.VISIBLE);
                itemView.findViewById(R.id.comment).setBackgroundColor(ContextCompat.getColor(this.itemView.getContext(), R.color.gray_200));
            }
            this.reco = reco;
            comCode = vo.getComCode();
            drawColor();
            com_reco.setOnClickListener(v -> recommend(true));
            com_nonReco.setOnClickListener(v -> recommend(false));
            itemView.findViewById(R.id.comm_submit).setOnClickListener(v -> {
                if (com_reply.getText().toString().isEmpty()) return;
                PostRun postRun = new PostRun("com_submit", activity, PostRun.DATA);
                postRun.setRunUI(() -> {
                    try {
                        if (postRun.obj.getBoolean("message")) activity.loadView();
                        else Toast.makeText(activity, "등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
                postRun.addData("bdCode", vo.getBdCode())
                        .addData("uCode", UserSharedPreferences.user.getUCode())
                        .addData("comCont", Html.toHtml(com_reply.getText()).replaceAll(PostRun.DOMAIN, ""))
                        .addData("replyCode", vo.getReplyCode() == null ? vo.getComCode() : vo.getReplyCode())
                        .start();
            });
        }

        private void drawColor() {
            if (reco == null) {
                com_reco.getCompoundDrawables()[0].setTint(ContextCompat.getColor(this.itemView.getContext(), R.color.gray_500));
                com_nonReco.getCompoundDrawables()[0].setTint(ContextCompat.getColor(this.itemView.getContext(), R.color.gray_500));
            } else if (reco.equals("Y")) {
                com_reco.getCompoundDrawables()[0].setTint(ContextCompat.getColor(this.itemView.getContext(), R.color.blue_700));
                com_nonReco.getCompoundDrawables()[0].setTint(ContextCompat.getColor(this.itemView.getContext(), R.color.gray_500));
            } else {
                com_reco.getCompoundDrawables()[0].setTint(ContextCompat.getColor(this.itemView.getContext(), R.color.gray_500));
                com_nonReco.getCompoundDrawables()[0].setTint(ContextCompat.getColor(this.itemView.getContext(), R.color.red_700));
            }
        }

        private void recommend(boolean isReco) {
            PostRun postRun = new PostRun(isReco ? "com_reco" : "com_nonReco", BoardCommentAdapter.this.activity, PostRun.DATA);
            postRun.setRunUI(() -> {
                try {
                    if (reco == null) {
                        if (isReco) com_reco.setText(String.valueOf(++recoy));
                        else com_nonReco.setText(String.valueOf(++recon));
                    } else if (reco.equals("Y")) {
                        if (!isReco) com_nonReco.setText(String.valueOf(++recon));
                        com_reco.setText(String.valueOf(--recoy));
                    } else {
                        if (isReco) com_reco.setText(String.valueOf(++recoy));
                        com_nonReco.setText(String.valueOf(--recon));
                    }
                    String message = postRun.obj.getString("message");
                    if (message.isEmpty()) reco = null;
                    else reco = message;

                    drawColor();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
            postRun.addData("comCode", comCode)
                    .addData("uCode", UserSharedPreferences.user.getUCode())
                    .addData("reco", String.valueOf(reco != null && reco.equals("Y")))
                    .addData("nonReco", String.valueOf(reco != null && reco.equals("N")))
                    .start();
        }
    }

    private List<CommentVO> list;
    private Map<String, String> comReco;
    private LinearLayout reply;
    private BoardDetailActivity activity;
    public EditText com_reply;
    private final int PICK_IMAGE = 1;

    public void setData(List<CommentVO> data, Map<String, String> comReco, BoardDetailActivity activity) {
        list = data;
        this.comReco = comReco;
        this.activity = activity;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public BoardCommentAdapter.CommentViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.board_comment_item, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BoardCommentAdapter.CommentViewHolder holder, int position) {
        CommentVO vo = list.get(position);
        holder.onBind(vo, comReco.get(vo.getComCode()));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
}
