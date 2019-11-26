package com.youdaoxsj.backstage.information.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExtendInformation {

    private Integer id;
    private Integer deviceId;
    private String  deviceIccid;
    private String  iccidTraffic;
    private Boolean iccidOnLine;
    private String  iccidState;
    private Boolean iccidLimit;
    private Date    iccidActivate;
    private String  deviceNote;

}
