package com.example.hotplego.domain;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class CourseVO implements Serializable {
    // 코스 정보
    private String csCode;
    private String csTitle;
    private String csWith;
    private Byte csNum;
    private Double csTemp;
    private String csWt;
    private Double csHum;
    private String uCode;
}
