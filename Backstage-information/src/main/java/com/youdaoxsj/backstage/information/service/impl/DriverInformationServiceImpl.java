package com.youdaoxsj.backstage.information.service.impl;

import com.youdaoxsj.backstage.information.bean.*;
import com.youdaoxsj.backstage.information.mapper.DriverInformationMapper;
import com.youdaoxsj.backstage.information.service.DriverInformationService;
import com.youdaoxsj.backstage.information.util.DateUtils;
import com.youdaoxsj.backstage.information.util.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class DriverInformationServiceImpl implements DriverInformationService {


    @Autowired
    private DriverInformationMapper driverInformationMapper;

    private DateUtils dateUtils = new DateUtils();

    private Integer lenght = 1;

    /**
     * 每页多少行
     */
    private static Integer PAGE_NUM = 50;


    @Override
    public ExtendDevice searchDev(Integer id) {
        ZbqDevice zbqDevice = driverInformationMapper.selectDevice(id);

        zbqDevice.setOnlineTime(Double.parseDouble(String.format("%.2f", zbqDevice.getOnlineTime() / 3600)));


        ExtendInformation extendInformation = driverInformationMapper.selectEI(id);
        if (extendInformation == null) {
            extendInformation = new ExtendInformation();
            extendInformation.setDeviceId(id);
            driverInformationMapper.insertExtendInformation(extendInformation);
        }

        ExtendDevice extendDevice = new ExtendDevice(extendInformation, zbqDevice);
        return extendDevice;
    }

    @Override
    public List<ExtendDevice> searchDevs(String sortName, Integer sortPage) {
        if (sortName.isEmpty()) {
            sortName = "last_report_time_asc";
        }
        sortPage = sortPage * 50 - 50;
        List<ZbqDevice> zbqDevices = driverInformationMapper.selectDevices(sortName, sortPage, PAGE_NUM);
        List<ExtendDevice> extendDevices = getExtendDevice(zbqDevices);
        return extendDevices;
    }

    @Override
    public Integer updateEI(Integer zbqDeviceID, String driverIccid, String deviceNote) {

        ExtendInformation extendInformation = new ExtendInformation();
        extendInformation.setDeviceId(zbqDeviceID);
        if (driverIccid != "") {
            extendInformation.setDeviceIccid(driverIccid);
        }
        if (deviceNote != "") {
            extendInformation.setDeviceNote(deviceNote);
        }


        Integer state = driverInformationMapper.updateExtendInformation(extendInformation);

        return state;
    }

    @Override
    public List<ExtendDevice> searchDriverByzbq(String driverName, String driverPhone, String driverPlateNum, String sortName, Integer sortPage) {
        ZbqDevice zbqDevice = new ZbqDevice();
        if (!driverName.isEmpty()) {
            zbqDevice.setDriverName("%" + driverName + "%");
        }
        if (!driverPhone.isEmpty()) {
            zbqDevice.setMobile("%" + driverPhone + "%");
        }
        if (!driverPlateNum.isEmpty()) {
            zbqDevice.setCarNumber("%" + driverPlateNum + "%");
        }

        if (sortName.equals("时长排序")) {
            sortName = "online_time_asc";
        } else if (sortName.equals("上报时间")) {
            sortName = "last_report_time_asc";
        } else if (sortName.equals("安装时间")) {
            sortName = "record_time_asc";
        } else {
            sortName = "last_report_time_asc";
        }


        sortPage = sortPage * 50 - 50;

        List<ZbqDevice> zbqDevices = driverInformationMapper.searchDriverByzbq(zbqDevice, sortName, sortPage, PAGE_NUM);
        int integer = zbqDevices.size();
        if (integer % 50 == 0) {
            lenght = integer / 50;
        } else {
            lenght = integer / 50 + 1;
        }

        List<ExtendDevice> extendDevice = getExtendDevice(zbqDevices);

        return extendDevice;
    }


    @Override
    public IccidMsg searchIccidMsg(String iccid) {
        String status;
        IccidMsg iccidMsg = HttpRequest.sendGet(iccid);
        if (iccidMsg.getStatus() != null) {
            if (iccidMsg.getStatus().equals("ACTIVATED")) {
                status = "已激活";
            } else if (iccidMsg.getStatus().equals("DEACTIVATED")) {
                status = "已停用";
            } else if (iccidMsg.getStatus().equals("TEST_READY")) {
                status = "可测试";
            } else {
                status = "已失效";
            }
            iccidMsg.setStatus(status);
            iccidMsg.setCtdDataUsage(Double.parseDouble(String.format("%.2f", iccidMsg.getCtdDataUsage() / 1048576)));
            return iccidMsg;
        } else {
            iccidMsg = new IccidMsg();
            iccidMsg.setStatus("查询失败");
            iccidMsg.setCtdDataUsage(0.0);
            return iccidMsg;
        }
    }

    @Override
    public Integer getZbqDeviceCount() {
        Integer integer = driverInformationMapper.selectConutZbqDevice();
        if (integer % 50 == 0) {
            integer = integer / 50;
        } else {
            integer = integer / 50 + 1;
        }
        return integer;
    }

    @Override
    public Integer getsearchDriverLenght() {
        return lenght;
    }


    /**
     * 将PHP的时间戳转换成格式时间
     * 在线时长和平均时长转换
     *
     * @param zbqDevices
     * @return
     */
    public List<ZbqDevice> getFormatTime(List<ZbqDevice> zbqDevices) {
        //时间格式
        SimpleDateFormat df = new SimpleDateFormat("yyy/MM/dd HH:mm:ss");
        //获取当月已过多少小时，精确到小时
        Calendar calendar = Calendar.getInstance();
        int curHour24 = calendar.get(calendar.HOUR_OF_DAY);
        String data1 = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String[] data2 = data1.split("-");
        double nn = Integer.parseInt(data2[2]) + curHour24 / 24.0;

        for (ZbqDevice zz : zbqDevices) {
            zz.setLastLoginTime(df.format(Long.parseLong(zz.getLastLoginTime() + "000")));
            zz.setLastReportTime(df.format(Long.parseLong(zz.getLastReportTime() + "000")));
            zz.setRecordTime(df.format(Long.parseLong(zz.getRecordTime() + "000")));
            zz.setOnlineTime2(Double.parseDouble(String.format("%.2f", (zz.getOnlineTime() / 3600))));
            zz.setPjsj(Double.parseDouble(String.format("%.2f", zz.getOnlineTime2() / nn)));


        }
        return zbqDevices;


    }

    /**
     * 获取该列司机信息的拓展信息
     *
     * @param zbqDevices
     * @return
     */
    public List<ExtendDevice> getExtendDevice(List<ZbqDevice> zbqDevices) {

        List<ExtendDevice> extendDevices = new ArrayList<>();
        zbqDevices = getFormatTime(zbqDevices);
        for (ZbqDevice zbqDevice : zbqDevices) {
            ExtendInformation extendInformation = driverInformationMapper.selectEI(zbqDevice.getId());
            //判断拓展信息是否为null
            if (extendInformation == null) {
                extendInformation = new ExtendInformation();
            }
            if (extendInformation.getDeviceIccid() != null && !extendInformation.getDeviceIccid().trim().equals("---")) {
                //获取ICCID相关信息
                extendInformation = getExtendInformationByEX(extendInformation);
            }
            ExtendDevice extendDevice = new ExtendDevice();
            extendDevice.setExtendInformation(extendInformation);
            extendDevice.setZbqDevice(zbqDevice);
            extendDevices.add(extendDevice);
        }
        return extendDevices;
    }


    /**
     * 根据ICCID获取更多拓展信息
     *
     * @param extendInformation 查询对象
     * @return
     */
    public ExtendInformation getExtendInformationByEX(ExtendInformation extendInformation) {
        IccidMsg iccidMsg = HttpRequest.sendGet(extendInformation.getDeviceIccid().trim());
        String status;
        if (iccidMsg.getStatus() != null) {
            if (iccidMsg.getStatus().equals("ACTIVATED")) {
                status = "已激活";
            } else if (iccidMsg.getStatus().equals("DEACTIVATED")) {
                status = "已停用";
            } else if (iccidMsg.getStatus().equals("TEST_READY")) {
                status = "可测试";
            } else {
                status = "已失效";
            }
            extendInformation.setIccidState(status);
            extendInformation.setIccidTraffic(Double.parseDouble(String.format("%.2f", iccidMsg.getCtdDataUsage() / 1048576)));
        }
        return extendInformation;
    }

}

