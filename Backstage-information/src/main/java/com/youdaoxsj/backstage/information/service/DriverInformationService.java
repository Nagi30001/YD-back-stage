package com.youdaoxsj.backstage.information.service;

import com.youdaoxsj.backstage.information.bean.ExtendDevice;
import com.youdaoxsj.backstage.information.bean.ExtendInformation;
import com.youdaoxsj.backstage.information.bean.ZbqDevice;

import java.util.List;

public interface DriverInformationService {


    public void searchDevice();

    public ExtendDevice searchDev(Integer id);


    List<ExtendDevice> searchDevs();
}
