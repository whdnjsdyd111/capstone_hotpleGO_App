package com.example.hotplego.ui;

public class PointData {
    private String mposition;
    private String ptime;
    private String point;
    private String pointday;
    private String pointtype;

    public PointData(String pointtype, String mposition,String pointday, String ptime, String point) {
        this.mposition = mposition;
        this.ptime = ptime;
        this.point = point;
        this.pointday = pointday;
        this.pointtype = pointtype;
    }

    public String getMposition() {
        return mposition;
    }

    public void setMposition(String mposition) {
        this.mposition = mposition;
    }

    public String getMtime() {
        return ptime;
    }

    public void setMtime(String mtime) {
        this.ptime = mtime;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getPointtype() {
        return pointtype;
    }

    public void setPointtype(String pointtype) {
        this.pointtype = pointtype;
    }


    public String getPointday() {
        return pointday;
    }

    public void setPointday(String pointday) {
        this.pointday = pointday;
    }
}
