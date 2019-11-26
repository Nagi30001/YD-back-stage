package com.youdaoxsj.backstage.information.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataICCID {
    private Integer totalCount;
    private Boolean success;
    private String  messages;
    private String  resultDate;
    private String  data;
    private Integer errorCode;
    private String message;

}
