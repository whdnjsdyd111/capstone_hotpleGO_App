package com.example.hotplego.domain;

import java.sql.Timestamp;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class UserVO {
    private String uCode;
    private String pw;
    private String nick;
    private Date birth;
    private Character gender;
    private String phone;
    private String profileImg;
    private Long point;
    private String mbti;
    private Timestamp regDate;
}
