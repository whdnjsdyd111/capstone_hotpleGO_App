package com.example.hotplego;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.hotplego.domain.UserVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class UserSharedPreferences {
    private static final UserSharedPreferences instance = new UserSharedPreferences();

    private UserSharedPreferences() { }

    public static UserSharedPreferences getInstance() {
        return instance;
    }

    public static UserVO vo;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    public void logout(Activity activity) {
        preferences = activity.getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.remove("user");
        editor.apply();
        vo = null;
    }

    public void login(Activity activity, UserVO vo) {
        preferences = activity.getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        Gson gson = new Gson();
        String user = gson.toJson(vo);
        editor.putString("user", user);
        editor.apply();
        UserSharedPreferences.vo = vo;
    }

    public void login(Activity activity) {
        preferences = activity.getSharedPreferences("user", Context.MODE_PRIVATE);
        String user = preferences.getString("user", null);
        if (user != null) {
            Gson gson = new Gson();
            vo = gson.fromJson(user, new TypeToken<UserVO>() { }.getType());
        }
    }
}
