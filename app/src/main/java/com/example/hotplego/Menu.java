package com.example.hotplego;

public class Menu {

    private String title, price, cnt;
    private int img;

    public Menu (String title, String price, String cnt, int img) {
        this.title = title;
        this.price = price;
        this.cnt = cnt;
        this.img  = img;
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
