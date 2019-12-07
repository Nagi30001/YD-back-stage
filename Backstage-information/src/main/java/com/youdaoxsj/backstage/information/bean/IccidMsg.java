package com.youdaoxsj.backstage.information.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IccidMsg {


    private String iccid;
    private String imsi;
    private String msisdn;
    private String imei;
    private String status;
    private String ratePlan;
    private String communicationPlan;
    private Double ctdDataUsage;
    private String ctdSMSUsage;
    private String ctdVoiceUsage;
    private String ctdSessionCount;
    private String overageLimitReached;
    private String overageLimitOverride;

}
