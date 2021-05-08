package com.example.hotplego.ui.local;

public class Hotple_Total_Data {
    private int sh_image;
    public String chart_num;
    private String hp_name;
    private String hp_intro;
    private String hp_position;

    public Hotple_Total_Data(int sh_image, String chart_num, String hp_name, String hp_intro, String hp_position) {
        this.sh_image = sh_image;
        this.chart_num = chart_num;
        this.hp_name = hp_name;
        this.hp_intro = hp_intro;
        this.hp_position = hp_position;
    }

    public int getSh_image() {
        return sh_image;
    }

    public void setSh_image(int sh_image) {
        this.sh_image = sh_image;
    }

    public String getChart_num() {
        return chart_num;
    }

    public void setChart_num(String chart_num) {
        this.chart_num = chart_num;
    }

    public String getHp_name() {
        return hp_name;
    }

    public void setHp_name(String hp_name) {
        this.hp_name = hp_name;
    }

    public String getHp_intro() {
        return hp_intro;
    }

    public void setHp_intro(String hp_intro) {
        this.hp_intro = hp_intro;
    }

    public String getHp_position() {
        return hp_position;
    }

    public void setHp_position(String hp_position) {
        this.hp_position = hp_position;
    }
}
