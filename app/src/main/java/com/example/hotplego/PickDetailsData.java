package com.example.hotplego;

public class PickDetailsData {
    private String details_name;
    private String details_address;

    public PickDetailsData(String details_name, String details_address) {
        this.details_name = details_name;
        this.details_address = details_address;
    }

    public String getDetails_name() {
        return details_name;
    }

    public void setDetails_name(String details_name) {
        this.details_name = details_name;
    }

    public String getDetails_address() {
        return details_address;
    }

    public void setDetails_address(String details_address) {
        this.details_address = details_address;
    }
}
