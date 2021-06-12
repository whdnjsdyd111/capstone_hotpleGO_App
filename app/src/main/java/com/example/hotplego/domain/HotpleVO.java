package com.example.hotplego.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class HotpleVO {
    private Long htId;
    private String busnNum;
    private String goId;
    private String busnName;
    private String htAddr;
    private String htAddrDet;
    private Long htZip;
    private String htCont;
    private Double goGrd;
    private String htTel;
    private String htImg;
    private String goImg;
    private String uploadPath;
    private String fileName;
    private Double htLat;
    private Double htLng;
    private String uCode;
    private Long category;
    private String ttKind;

    private Timestamp pickTime;
}
