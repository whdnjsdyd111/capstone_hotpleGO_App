package com.example.hotplego.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class CourseInfoVO implements Serializable {
    // 코스 인덱스 정보
    private String csCode;
    private Long htId;
    private Byte ciIndex;
    private Timestamp ciArTime;
    private Byte ciScore;

    // 해당 핫플 정보
    private String busnName;
    private String htAddr;
    private String htCont;
    private String htTel;
    private String goImg;
    private Double htLat;
    private Double htLng;

    // 해당 핫플 이미지 정보
    private String uuid;
    private String uploadPath;
    private String fileName;
}
