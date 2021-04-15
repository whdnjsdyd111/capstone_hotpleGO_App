package com.example.hotplego;

public class MenuData {

    private String title, cnt, price;
    private int img;

    public MenuData(String title, String price, String cnt, int img) {
        this.title = title; //메뉴명
        this.price = price; //메뉴 가격
        this.cnt = cnt; //메뉴 소개
        this.img  = img; // 이미지
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

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}