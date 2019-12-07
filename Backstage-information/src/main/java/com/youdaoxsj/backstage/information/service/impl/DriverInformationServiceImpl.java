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

    private Integer lenght = 0;

    private Integer index = 1;


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
    public List<ExtendDevice> searchDevs(String name, boolean type,Integer page) {
        List<ZbqDevice> zbqDevices = driverInformationMapper.selectZbqDevices();
        List<ExtendDevice> extendDevices = getExtendDevices(zbqDevices, name, type,page);
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
    public List<ExtendDevice> searchDriverByzbq(ZbqDevice zbqDevice, String name, boolean type, Integer page) {


        List<ZbqDevice> zbqDevices = driverInformationMapper.searchDriverByzbq(zbqDevice);
        System.out.println(zbqDevices.toString());
        List<ExtendDevice> extendDevices = getExtendDevices(zbqDevices, name, type, page);


        return extendDevices;
    }

    @Override
    public List<String> getIccids() {

        List<ExtendInformation> extendInformations = driverInformationMapper.selectExtendInformation();
        List<String> iccids = new ArrayList<>();
        for (ExtendInformation extendInformation : extendInformations) {
            if (extendInformation.getDeviceIccid() != null && extendInformation.getDeviceIccid() != "---") {
                iccids.add(extendInformation.getDeviceIccid());
            }

        }

        driverInformationMapper.selectExtendInformation();

        return iccids;
    }

    @Override
    public Integer getDriverLenght() {
        return lenght;
    }

    @Override
    public IccidMsg searchIccidMsg(String iccid) {
        String status;
        IccidMsg iccidMsg = HttpRequest.sendGet(iccid);
        if (iccidMsg.getStatus() != null){
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


    public DataICCID searchICCID() {


        return null;
    }

    public List<ExtendDevice> getExtendDevices(List<ZbqDevice> zbqDevices, String name, boolean type, Integer page) {


        List<ExtendDevice> extendDevices = new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("yyy/MM/dd HH:mm:ss");

        List<ZbqDevice> zbqDevices2 = new ArrayList<>();

        for (ZbqDevice zz : zbqDevices) {
            if (zz.getStatus() == 1) {
                zbqDevices2.add(zz);
            }
        }


//        System.err.println(zbqDevices.toString());
        for (ZbqDevice zz : zbqDevices2) {
//            System.out.println(zz.toString());
            if (zz.getLastLoginTime().isEmpty() || zz.getLastReportTime().isEmpty() || zz.getRecordTime().isEmpty() || zz.getStatus() != 1) {
                break;
            }
//            zz.setInitTime(df.format(Long.parseLong(zz.getInitTime() + "000")));
            zz.setLastLoginTime(df.format(Long.parseLong(zz.getLastLoginTime() + "000")));
            zz.setLastReportTime(df.format(Long.parseLong(zz.getLastReportTime() + "000")));
            zz.setRecordTime(df.format(Long.parseLong(zz.getRecordTime() + "000")));

//            zz.setLastLoginTime2(df.format(Long.parseLong(zz.getLastLoginTime() + "000")));

//            zz.setOnlineTime(String.format("%.2f",Double.parseDouble(zz.getOnlineTime())/3600));


            zz.setOnlineTime(Double.parseDouble(String.format("%.2f", zz.getOnlineTime() / 3600)));

//            ExtendInformation extendInformation = new ExtendInformation();
//            extendInformation = driverInformationMapper.selectEI(zz.getId());


            Calendar calendar = Calendar.getInstance();
            int curHour24 = calendar.get(calendar.HOUR_OF_DAY);
            String data1 = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String[] data2 = data1.split("-");

            double nn = Integer.parseInt(data2[2]) + curHour24 / 24.0;


//            zz.setPjsj(String.format("%.2f",Double.parseDouble(zz.getOnlineTime())/nn));

            zz.setPjsj(Double.parseDouble(String.format("%.2f", zz.getOnlineTime() / nn)));

//

        }


        dateUtils.sort(zbqDevices2, name, type);

        if (zbqDevices2.size()%50 == 0){
            lenght = zbqDevices2.size()/50;
        } else {
            lenght = zbqDevices2.size()/50 +1;
        }
        List<ZbqDevice> zbqDeviceList = new ArrayList<>();
        if (page == 0) {
            page = 1;
        }
        for (int i = (page - 1)*50 ; i < page * 50 ; i++) {
            try {
                zbqDeviceList.add(zbqDevices2.get(i));
            } catch (Exception e){
                break;
            }

        }


        for (ZbqDevice zz : zbqDeviceList) {


//            try {
//                if (index%15 == 0)
//                index ++;
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            ExtendInformation extendInformation = driverInformationMapper.selectEI(zz.getId());

            if (extendInformation == null) {
                extendInformation = new ExtendInformation();
            }

//            System.out.println(extendInformation.toString());
            if (extendInformation.getDeviceIccid() != null && !extendInformation.getDeviceIccid().trim().equals("---")) {
                System.out.println(extendInformation.toString());
                IccidMsg iccidMsg = HttpRequest.sendGet(extendInformation.getDeviceIccid().trim());
                System.out.println(iccidMsg.toString());
                String status = null;
                if (iccidMsg.getStatus() != null){
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


            }
            ExtendDevice extendDevice = new ExtendDevice();
            extendDevice.setExtendInformation(extendInformation);
            extendDevice.setZbqDevice(zz);
            extendDevices.add(extendDevice);
        }

        return extendDevices;
    }

    public void getIccidMsg() {


    }

}

