package com.youdaoxsj.backstage.information.service;

import com.youdaoxsj.backstage.information.bean.ExtendDevice;
import com.youdaoxsj.backstage.information.bean.IccidMsg;
import com.youdaoxsj.backstage.information.bean.ZbqDevice;

import java.util.List;

public interface DriverInformationService {


    public void searchDevice();

    /**
     * 查询一个司机信息
     * @param id
     * @return
     */
    public ExtendDevice searchDev(Integer id);


    /**
     * 查询全部司机信息
     * @param name
     * @param type
     * @return
     */
    List<ExtendDevice> searchDevs(String name ,boolean type, Integer page);

    /**
     * 更新司机的扩展信息
     * @param zbqDeviceID
     * @param driverIccid
     * @param deviceNote
     * @return
     */
    Integer updateEI(Integer zbqDeviceID, String driverIccid, String deviceNote);

    /**
     * 根据查询条件获取到司机信息集合
     * @param zbqDevice
     * @param name
     * @param type
     * @return
     */
    List<ExtendDevice> searchDriverByzbq(ZbqDevice zbqDevice,String name,boolean type,Integer page);

    List<String> getIccids();

    Integer getDriverLenght();

    IccidMsg searchIccidMsg(String iccid);
}
