package com.example.hotplego.ui.manager.management.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.hotplego.PostRun;
import com.example.hotplego.R;
import com.example.hotplego.databinding.FragmentManagementWeekdayBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.util.Map;

public class ManagementTime extends Fragment {

    String kind = "평일";
    private FragmentManagementWeekdayBinding binding;

    public ManagementTime() { }

    public ManagementTime(String kind) {
        this.kind = kind;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentManagementWeekdayBinding.inflate(inflater, container, false);

        PostRun postRun = new PostRun("manager_management", getActivity(), PostRun.DATA);
        postRun.setRunUI(() -> {
            try {
                Map<String, String[]> map = new Gson().fromJson(postRun.obj.getString("openInfo"),
                        new TypeToken<Map<String, String[]>>() {}.getType());
                Log.i("ddd", map.toString());
                switch (kind) {
                    case "평일":
                        binding.worktimeStart.setText(PostRun.toTime(map.get("W/O")[0]));
                        binding.worktimeEnd.setText(PostRun.toTime(map.get("W/O")[1]));
                        binding.breaktimeStart.setText(PostRun.toTime(map.get("W/B")[0]));
                        binding.breaktimeEnd.setText(PostRun.toTime(map.get("W/B")[1]));
                        break;
                    case "토요일":
                        binding.worktimeStart.setText(PostRun.toTime(map.get("T/O")[0]));
                        binding.worktimeEnd.setText(PostRun.toTime(map.get("T/O")[1]));
                        binding.breaktimeStart.setText(PostRun.toTime(map.get("T/B")[0]));
                        binding.breaktimeEnd.setText(PostRun.toTime(map.get("T/B")[1]));
                        break;
                    case "일요일":
                        binding.worktimeStart.setText(PostRun.toTime(map.get("S/O")[0]));
                        binding.worktimeEnd.setText(PostRun.toTime(map.get("S/O")[1]));
                        binding.breaktimeStart.setText(PostRun.toTime(map.get("S/B")[0]));
                        binding.breaktimeEnd.setText(PostRun.toTime(map.get("S/B")[1]));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        postRun.addData("uCode", "whdnjsdyd@naver.com/M/");
        postRun.start();

        binding.save.setOnClickListener(v -> {
            PostRun pr = new PostRun("manger_management_update", getActivity(), PostRun.DATA);
            pr.setRunUI(() -> {
                try {
                    if (Boolean.parseBoolean(pr.obj.getString("message")))
                        Toast.makeText(getContext(), "수정 완료하였습니다.", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
            pr.addData("wost", binding.worktimeStart.getText().toString())
                    .addData("woet", binding.worktimeEnd.getText().toString())
                    .addData("wbst", binding.breaktimeStart.getText().toString())
                    .addData("wbet", binding.breaktimeEnd.getText().toString())
                    .addData("uCode", "whdnjsdyd@naver.com/M/") // TODO
                    .addData("kind", kind)
                    .start();
        });

        return binding.getRoot();
    }
}
