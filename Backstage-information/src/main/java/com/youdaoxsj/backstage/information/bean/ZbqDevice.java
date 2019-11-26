package com.youdaoxsj.backstage.information.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZbqDevice {
    private Integer id;
    private String device;
    private String code;
    private String driverName;
    private String mobile;
    private Integer districtId;
    private String onlineTime;
    private String initTime;
    private Integer advertId;
    private String lastLoginTime;
    private String lastReportTime;
    private String reportIp;
    private String recordTime;
    private Integer status;
    private String carNumber;
    private String bankNo;
    private String bankName;
    private String longitude;
    private String latitude;
    private Integer driverId;
    private Integer pushCode;
}
