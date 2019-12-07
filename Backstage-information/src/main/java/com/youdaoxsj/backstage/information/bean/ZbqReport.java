package com.youdaoxsj.backstage.information.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZbqReport {

    private Integer deviceId;
    private String driverName;
    private Integer onlineTime;
}
