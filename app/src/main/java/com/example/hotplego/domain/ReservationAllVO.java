package com.example.hotplego.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ReservationAllVO {
    // 예약 정보
    private String riCode;
    private Long htId;
    private Timestamp riTime;
    private Short riPerson;
    private String riNoShow;
    private String riOdNum;
    private String riCont;
    private String uCode;

    // 예약 상태
    private Byte rsMeNum;

    // 메뉴
    private String meCode;
    private String meCate;
    private String meName;
    private Integer mePrice;
    private String meIntr;
    private String meHashTag;
    private String uuid;
    private String uploadPath;
    private String fileName;
}
