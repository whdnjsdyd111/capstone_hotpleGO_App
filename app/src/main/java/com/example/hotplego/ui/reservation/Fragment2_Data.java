package com.example.hotplego.ui.reservation;

public class Fragment2_Data {
    private int sh_image;
    private String re_day;
    private String re_time;
    private String sh_name;
    private String sh_addr;

    public Fragment2_Data(int sh_image, String re_day, String re_time, String sh_name, String sh_addr) {
        this.sh_image = sh_image;
        this.re_day = re_day;
        this.re_time = re_time;
        this.sh_name = sh_name;
        this.sh_addr = sh_addr;
    }

    public int getSh_image() {
        return sh_image;
    }

    public void setSh_image(int sh_image) {
        this.sh_image = sh_image;
    }

    public String getRe_day() {
        return re_day;
    }

    public void setRe_day(String re_day) {
        this.re_day = re_day;
    }

    public String getRe_time() {
        return re_time;
    }

    public void setRe_time(String re_time) {
        this.re_time = re_time;
    }

    public String getSh_name() {
        return sh_name;
    }

    public void setSh_name(String sh_name) {
        this.sh_name = sh_name;
    }

    public String getSh_addr() {
        return sh_addr;
    }

    public void setSh_addr(String sh_addr) {
        this.sh_addr = sh_addr;
    }
}
