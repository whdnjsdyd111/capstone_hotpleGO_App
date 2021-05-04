package com.example.hotplego.ui.board;

public class BoardData {
    private int img;
    private String title;
    private String name;
    private String content;
    private String count;

    public BoardData(int img, String title, String name, String content, String count) {
        this.img = img;
        this.title = title;
        this.name = name;
        this.content = content;
        this.count = count;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}

