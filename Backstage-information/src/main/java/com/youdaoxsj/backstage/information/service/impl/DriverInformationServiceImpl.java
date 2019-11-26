package com.youdaoxsj.backstage.information.service.impl;

import com.youdaoxsj.backstage.information.bean.DataICCID;
import com.youdaoxsj.backstage.information.bean.ExtendDevice;
import com.youdaoxsj.backstage.information.bean.ExtendInformation;
import com.youdaoxsj.backstage.information.bean.ZbqDevice;
import com.youdaoxsj.backstage.information.mapper.DriverInformationMapper;
import com.youdaoxsj.backstage.information.service.DriverInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class DriverInformationServiceImpl implements DriverInformationService {


    @Autowired
    private DriverInformationMapper driverInformationMapper;


    @Override
    public void searchDevice() {
//        List<Device> devices = driverInformationMapper.selectDriverInformation();

//        for (Device d : devices) {
//            if (d.getId().equals(655)){
//                System.out.println(d.toString());
//            }
//        }
    }

    @Override
    public ExtendDevice searchDev(Integer id) {
        ZbqDevice zbqDevice = driverInformationMapper.selectDevice(id);

        SimpleDateFormat df = new SimpleDateFormat("yyy/MM/dd HH:mm:ss");

        zbqDevice.setInitTime(df.format(Long.parseLong(zbqDevice.getInitTime() + "000")));
        zbqDevice.setLastLoginTime(df.format(Long.parseLong(zbqDevice.getLastLoginTime() + "000")));
        zbqDevice.setLastReportTime(df.format(Long.parseLong(zbqDevice.getLastReportTime() + "000")));
        zbqDevice.setRecordTime(df.format(Long.parseLong(zbqDevice.getRecordTime() + "000")));

        ExtendInformation extendInformation = driverInformationMapper.selectEI(zbqDevice.getId());

        ExtendDevice extendDevice = new ExtendDevice(extendInformation, zbqDevice);
        return extendDevice;
    }

    @Override
    public List<ExtendDevice> searchDevs() {
        List<ExtendDevice> extendDevices = new ArrayList<>();
        ExtendInformation extendInformation;
        SimpleDateFormat df = new SimpleDateFormat("yyy/MM/dd HH:mm:ss");
        List<ZbqDevice> zbqDevices = driverInformationMapper.selectZbqDevices();
//        System.err.println(zbqDevices.toString());
        for (ZbqDevice zz : zbqDevices) {
            if (zz.getInitTime().isEmpty() || zz.getLastLoginTime().isEmpty() || zz.getLastReportTime().isEmpty() || zz.getRecordTime().isEmpty()) {
                break;
            }
            zz.setInitTime(df.format(Long.parseLong(zz.getInitTime() + "000")));
            zz.setLastLoginTime(df.format(Long.parseLong(zz.getLastLoginTime() + "000")));
            zz.setLastReportTime(df.format(Long.parseLong(zz.getLastReportTime() + "000")));
            zz.setRecordTime(df.format(Long.parseLong(zz.getRecordTime() + "000")));


            extendInformation = driverInformationMapper.selectEI(zz.getId());
            ExtendDevice extendDevice = new ExtendDevice();
            extendDevice.setExtendInformation(extendInformation);
            extendDevice.setZbqDevice(zz);
            extendDevices.add(extendDevice);

        }
        return extendDevices;
    }


    public DataICCID searchICCID() {


        return null;
    }


}

