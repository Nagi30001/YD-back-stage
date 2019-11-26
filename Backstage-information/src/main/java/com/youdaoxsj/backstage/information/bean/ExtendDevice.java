package com.youdaoxsj.backstage.information.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExtendDevice {

    private ExtendInformation extendInformation;
    private ZbqDevice zbqDevice;
}
