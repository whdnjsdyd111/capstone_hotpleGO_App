package com.example.hotplego;

public class Order_details_New_Data {
    private int New_iv;
    private String New_menu_name;
    private String New_menu_price;

    public Order_details_New_Data(int New_iv, String new_menu_name, String new_menu_price) {
        this.New_iv = New_iv;
        New_menu_name = new_menu_name;
        New_menu_price = new_menu_price;
    }

    public int getNew_iv() {
        return New_iv;
    }

    public void setNew_iv(int new_iv) {
        this.New_iv = new_iv;
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