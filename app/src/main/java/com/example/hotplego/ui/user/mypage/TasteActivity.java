package com.example.hotplego.ui.user.mypage;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hotplego.PostRun;
import com.example.hotplego.R;
import com.example.hotplego.databinding.MypageTasteBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TasteActivity extends AppCompatActivity {

    private MypageTasteBinding binding;
    private int[][] linears = {
            { R.id.food, R.id.food_taste },
            { R.id.dessert, R.id.dessert_taste },
            { R.id.play, R.id.play_taste },
            { R.id.alcohol, R.id.alcohol_taste },
            { R.id.watch, R.id.watch_taste },
            { R.id.walk, R.id.walk_taste }
    };

    private static Map<Integer, Integer> tastes;

    private Set<Integer> selected;

    private void initTastes() {
        tastes = new HashMap<>();
        tastes.put(0, R.id.korean);
        tastes.put(1, R.id.bunsick);
        tastes.put(2, R.id.japanese);
        tastes.put(3, R.id.west);
        tastes.put(4, R.id.chinese);
        tastes.put(5, R.id.fastfood);
        tastes.put(6, R.id.buffet);
        tastes.put(7, R.id.midnight_snack);
        tastes.put(10, R.id.coffee);
        tastes.put(11, R.id.drink);
        tastes.put(12, R.id.bread);
        tastes.put(13, R.id.ice);
        tastes.put(20, R.id.game);
        tastes.put(21, R.id.escape);
        tastes.put(22, R.id.vr);
        tastes.put(23, R.id.karaoke);
        tastes.put(24, R.id.sports);
        tastes.put(25, R.id.shopping);
        tastes.put(26, R.id.workshop);
        tastes.put(27, R.id.amusementpark);
        tastes.put(30, R.id.beer);
        tastes.put(31, R.id.sogue);
        tastes.put(32, R.id.mackuli);
        tastes.put(33, R.id.wine);
        tastes.put(40, R.id.movie);
        tastes.put(41, R.id.exhibition);
        tastes.put(42, R.id.library);
        tastes.put(43, R.id.stage);
        tastes.put(44, R.id.watch_sports);
        tastes.put(50, R.id.themapark);
        tastes.put(51, R.id.cultural_heritage);
        tastes.put(52, R.id.market);
        tastes.put(53, R.id.park);
    }

    private void initSelected() {
        // postRun 으로 해당 사용자의 취향 가져옴
        selected = new HashSet<>();
        PostRun postRun = new PostRun("getTaste", this, PostRun.DATA);
        postRun.addData("uCode", "whdnjsdyd111@naver.com/A/"); // TODO 현재 로그인된 아이디 가져오기
        postRun.setRunUI(() ->{
            try {
                Log.i("result", postRun.obj.get("tastes").toString());
                JSONArray jsonArray = postRun.obj.getJSONArray("tastes");
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Integer taste = Integer.valueOf(jsonArray.get(i).toString());
                        selected.add(taste);
                        findViewById(tastes.get(taste)).setBackgroundTintList(ColorStateList.valueOf(0xFFBB86FC));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        postRun.start();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MypageTasteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.closeBtn.setOnClickListener(v -> finish());
        for (int[] linear : linears) {
            LinearLayout linearLayout = binding.getRoot().findViewById(linear[1]);
            binding.getRoot().findViewById(linear[0]).setOnClickListener(v -> {
                if (linearLayout.getVisibility() == View.GONE) {
                    linearLayout.setVisibility(View.VISIBLE);
                } else {
                    linearLayout.setVisibility(View.GONE);
                }
            });
        }

        if (tastes == null) initTastes();
        initSelected();

        for (Integer taste : tastes.keySet()) {
            Button btn = findViewById(tastes.get(taste));
            btn.setOnClickListener(v -> {
                if (selected.contains(taste)) {
                    selected.remove(taste);
                    btn.setBackgroundTintList(ColorStateList.valueOf(0x000000));
                } else {
                    selected.add(taste);
                    btn.setBackgroundTintList(ColorStateList.valueOf(0xFFBB86FC));
                }
            });
        }

        binding.saveTaste.setOnClickListener(v -> {
            PostRun postRun = new PostRun("saveTaste", this);
            JSONObject jsonObject = new JSONObject();
            // TODO 로그인된 아이디
            postRun.addData("uCode", "whdnjsdyd111@naver.com/A/")
                    .addJsonData("tastes", new String[] {"tastes"}, new Object[] { selected });
            postRun.setRunUI(() -> {
                try {
                    Toast.makeText(TasteActivity.this, postRun.obj.get("message").toString(), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
            postRun.start();
        });
    }
}
