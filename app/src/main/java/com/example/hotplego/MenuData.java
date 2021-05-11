package com.example.hotplego;

import android.net.Uri;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class MenuData implements Serializable {

    private String title;
    private String cnt;
    private String price;
    private String imgUri;

    public MenuData(String title, String price, String cnt, int img) {
        this.title = title; //메뉴명
        this.price = price; //메뉴 가격
        this.cnt = cnt; //메뉴 소개
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    public Uri getImgUri() {
        if(imgUri!=null) {
            return Uri.parse(imgUri);
        }
        return null;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof MenuData)) return false;
        MenuData o = (MenuData) obj;
        return title.equals(o.title) && cnt.equals(o.cnt) && price.equals(o.price);
    }
}