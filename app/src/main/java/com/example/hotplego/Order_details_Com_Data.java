package com.example.hotplego;

public class Order_details_Com_Data {
    private int Com_iv;
    private String Com_menu_name;
    private String Com_menu_review;
    private String Com_menu_time;

    public Order_details_Com_Data(int Com_iv, String com_menu_name, String com_menu_review, String com_menu_time) {
        this.Com_iv = Com_iv;
        Com_menu_name = com_menu_name;
        Com_menu_review = com_menu_review;
        Com_menu_time = com_menu_time;
    }

    public int getCom_iv() {
        return Com_iv;
    }

    public void setCom_iv(int com_iv) {
        Com_iv = com_iv;
    }

    public String getCom_menu_name() {
        return Com_menu_name;
    }

    public void setCom_menu_name(String com_menu_name) {
        Com_menu_name = com_menu_name;
    }

    public String getCom_menu_review() {
        return Com_menu_review;
    }

    public void setCom_menu_review(String com_menu_review) {
        Com_menu_review = com_menu_review;
    }

    public String getCom_menu_time() {
        return Com_menu_time;
    }

    public void setCom_menu_time(String com_menu_time) {
        Com_menu_time = com_menu_time;
    }
}
