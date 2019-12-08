package com.youdaoxsj.backstage.information.service;

import com.youdaoxsj.backstage.information.bean.ExtendDevice;
import com.youdaoxsj.backstage.information.bean.IccidMsg;
import com.youdaoxsj.backstage.information.bean.ZbqDevice;

import java.util.List;

public interface DriverInformationService {


    /**
     * 查询一个司机信息
     *
     * @param id 司机id
     * @return  司机信息
     */
    ExtendDevice searchDev(Integer id);


    /**
     * 分页排序查询司机信息
     *
     * @param sortName 排序字段
     * @param sortPage 页码
     * @return
     */
    List<ExtendDevice> searchDevs(String sortName, Integer sortPage);

    /**
     * 更新司机的扩展信息
     *
     * @param zbqDeviceID
     * @param driverIccid
     * @param deviceNote
     * @return
     */
    Integer updateEI(Integer zbqDeviceID, String driverIccid, String deviceNote);


    /**
     * 根据查询条件查询数据
     * @param driverName 司机姓名
     * @param driverPhone 司机电话
     * @param driverPlateNum 司机车牌号
     * @param sortName 排序字段
     * @param sortPage 排序页码
     * @return
     */
    List<ExtendDevice> searchDriverByzbq(String driverName, String driverPhone, String driverPlateNum, String sortName, Integer sortPage);

    /**
     * 根据iccid发送http请求查询数据
     * @param iccid
     * @return
     */
    IccidMsg searchIccidMsg(String iccid);

    /**
     * 获取设备列表总和排除禁用的
     *
     * @return 页数
     */
    Integer getZbqDeviceCount();

    /**
     * 查询条件下 总页数
     * @return
     */
    Integer getsearchDriverLenght();

    /**
     * 获取排序后的字段
     * @param sortName
     * @return
     */
    List<String> getSortName(String sortName);

    /**
     * 公众号用于车牌号查询时长
     * @param carNum 车牌号码
     * @return 司机信息对象
     */
    ZbqDevice getDriverByCarNum(String carNum);
}
