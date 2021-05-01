package com.example.hotplego.ui.user.board;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.hotplego.PostRun;
import com.example.hotplego.R;
import com.example.hotplego.databinding.BoardDetailsBinding;
import com.example.hotplego.domain.BoardVO;
import com.google.gson.Gson;

import org.json.JSONException;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BoardDetailActivity extends AppCompatActivity {
    private BoardDetailsBinding binding;
    private String bdCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = BoardDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bdCode = getIntent().getStringExtra("bdCode");
        PostRun postRun = new PostRun("getBoard", this);
        postRun.addData("bdCode", bdCode);
        postRun.setRunUI(() -> {
            BoardVO vo = null;
            try {
                vo = new Gson().fromJson(postRun.obj.getString("board"), BoardVO.class);
                vo.setBdCont(vo.getBdCont().replaceAll("src=\"", "src=\"" + PostRun.DOMAIN));
                Log.i("zzz", "" + vo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            binding.boardUser.setText(vo.getNick());
            binding.boardContents.setText(vo.getBdTitle());
            binding.boardText.setText(Html.fromHtml(vo.getBdCont(), new Html.ImageGetter() {
                @Override
                public Drawable getDrawable(String source) {
                    Bitmap x;

                    HttpURLConnection connection =
                            (HttpURLConnection) new URL(url).openConnection();
                    connection.connect();
                    InputStream input = connection.getInputStream();

                    x = BitmapFactory.decodeStream(input);
                    return new BitmapDrawable(getRex);

                    return d;
                }
            }, null));
        });
        postRun.start();
    }
}
