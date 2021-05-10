package com.example.hotplego;

public class Order_Management_details_Data {
    private int order_manage_img;
    private String order_manage_name;
    private String order_manage_num;
    private String order_manage_pay;

    public Order_Management_details_Data(int order_manage_img, String order_manage_name, String order_manage_num, String order_manage_pay) {
        this.order_manage_img = order_manage_img;
        this.order_manage_name = order_manage_name;
        this.order_manage_num = order_manage_num;
        this.order_manage_pay = order_manage_pay;
    }

    public int getOrder_manage_img() {
        return order_manage_img;
    }

    public void setOrder_manage_img(int order_manage_img) {
        this.order_manage_img = order_manage_img;
    }

    public String getOrder_manage_name() {
        return order_manage_name;
    }

    public void setOrder_manage_name(String order_manage_name) {
        this.order_manage_name = order_manage_name;
    }

    public String getOrder_manage_num() {
        return order_manage_num;
    }

    public void setOrder_manage_num(String order_manage_num) {
        this.order_manage_num = order_manage_num;
    }

    public String getOrder_manage_pay() {
        return order_manage_pay;
    }

    public void setOrder_manage_pay(String order_manage_pay) {
        this.order_manage_pay = order_manage_pay;
    }
}