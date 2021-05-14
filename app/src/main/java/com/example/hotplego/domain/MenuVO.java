package com.example.hotplego.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class MenuVO implements Serializable {
    private String meCode;
    private String meCate;
    private String meName;
    private long mePrice;
    private String meIntr;
    private String meHashTag;
    private String uuid;
    private String uploadPath;
    private String fileName;
}
