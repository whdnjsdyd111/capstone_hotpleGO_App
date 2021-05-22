package com.example.hotplego;

public class PickData {
    private String place_time;
    private String place_name;
    private String place_address;
    private float place_rating;
    private int place_img;

    public PickData(String place_time, String place_name, String place_address, float place_rating, int place_img) {
        this.place_time = place_time;
        this.place_name = place_name;
        this.place_address = place_address;
        this.place_rating = place_rating;
        this.place_img = place_img;
    }

    public String getPlace_time() {
        return place_time;
    }

    public void setPlace_time(String place_time) {
        this.place_time = place_time;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public String getPlace_address() {
        return place_address;
    }

    public void setPlace_address(String place_address) {
        this.place_address = place_address;
    }

    public float getPlace_rating() {
        return place_rating;
    }

    public void setPlace_rating(float place_rating) {
        this.place_rating = place_rating;
    }

    public int getPlace_img() {
        return place_img;
    }

    public void setPlace_img(int place_img) {
        this.place_img = place_img;
    }
}
