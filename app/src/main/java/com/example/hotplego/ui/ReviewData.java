package com.example.hotplego.ui;

public class ReviewData {
    private String name;
    private String redate;
    private String recont;

    public ReviewData(String name, String redate, String recont) {
        this.name = name;
        this.redate = redate;
        this.recont = recont;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRedate() {
        return redate;
    }

    public void setRedate(String redate) {
        this.redate = redate;
    }

    public String getRecont() {
        return recont;
    }

    public void setRecont(String recont) {
        this.recont = recont;
    }
}
