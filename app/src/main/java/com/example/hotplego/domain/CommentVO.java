package com.example.hotplego.domain;

import lombok.Data;

@Data
public class CommentVO {
    private String comCode;
    private String comCont;
    private int comRecy;
    private int comRecn;
    private String bdCode;
    private String uCode;
    private String replyCode;

    private String nick;
}
