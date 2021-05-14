package com.example.hotplego;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.hotplego.domain.HotpleVO;
import com.example.hotplego.domain.UserVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class UserSharedPreferences {
    private static final UserSharedPreferences instance = new UserSharedPreferences();

    private UserSharedPreferences() { }

    public static UserSharedPreferences getInstance() {
        return instance;
    }

    public static UserVO user;
    public static HotpleVO hotple;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    public void logout(Activity activity) {
        preferences = activity.getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.remove("user");
        editor.apply();
        user = null;
    }

    public void login(Activity activity, UserVO user, HotpleVO hotple) {
        preferences = activity.getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        Gson gson = new Gson();
        editor.putString("user", gson.toJson(user));
        if (hotple != null) editor.putString("hotple", gson.toJson(hotple));
        editor.apply();
        UserSharedPreferences.user = user;
    }

    public void login(Activity activity) {
        preferences = activity.getSharedPreferences("user", Context.MODE_PRIVATE);
        String user = preferences.getString("user", null);
        String hotple = preferences.getString("hotple", null);
        Gson gson = new Gson();
        if (user != null) UserSharedPreferences.user = gson.fromJson(user, UserVO.class);
        if (hotple != null) UserSharedPreferences.hotple = gson.fromJson(hotple, HotpleVO.class);
    }
}
