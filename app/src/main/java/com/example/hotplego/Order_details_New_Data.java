package com.example.hotplego;

public class Order_details_New_Data {
    private int iv;
    private String New_menu_name;
    private String New_menu_price;

    public Order_details_New_Data(int iv, String new_menu_name, String new_menu_price) {
        this.iv = iv;
        New_menu_name = new_menu_name;
        New_menu_price = new_menu_price;
    }

    public int getIv() {
        return iv;
    }

    public void setIv(int iv) {
        this.iv = iv;
    }

    public String getNew_menu_name() {
        return New_menu_name;
    }

    public void setNew_menu_name(String new_menu_name) {
        New_menu_name = new_menu_name;
    }

    public String getNew_menu_price() {
        return New_menu_price;
    }

    public void setNew_menu_price(String new_menu_price) {
        New_menu_price = new_menu_price;
    }
}