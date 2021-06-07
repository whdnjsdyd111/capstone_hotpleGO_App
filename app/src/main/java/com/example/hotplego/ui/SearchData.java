package com.example.hotplego.ui;

public class SearchData {
    private int img;
    private String name;
    private String rating;

    public SearchData(int img, String name, String rating) {
        this.img = img;
        this.name = name;
        this.rating = rating;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
