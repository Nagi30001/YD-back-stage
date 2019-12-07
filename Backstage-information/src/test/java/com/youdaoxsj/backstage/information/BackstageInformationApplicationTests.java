package com.youdaoxsj.backstage.information;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.util.Auth;
import com.youdaoxsj.backstage.information.mapper.DriverInformationMapper;
import com.youdaoxsj.backstage.information.util.Qiniu;
import com.youdaoxsj.backstage.information.util.SmsManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;


@SpringBootTest
class BackstageInformationApplicationTests {


//    @Autowired
//    private DruidDataSource druidDataSource;

    @Autowired
    private DriverInformationMapper driverInformationMapper;

    String uri = "https://api.10646.cn/rws/api/v1/devices/89860619050012701123/ctdUsages";

    String accessKey = Qiniu.SMS_ACCESS_KEY;
    String secretKey = Qiniu.SMS_SECRET_KEY;

    Auth auth = Auth.create(accessKey, secretKey);

    SmsManager smsManager = new SmsManager(auth);
    @Test
    public void contextLoads() throws QiniuException {
//        校正设备管理中的12月时长
//        List<ZbqReport> zbqDevices = driverInformationMapper.selectOnLineTime();
//        for (ZbqReport zbqDevice : zbqDevices) {
//            System.out.println(zbqDevice.toString());
//            Integer integer = driverInformationMapper.updateZbqDevice(zbqDevice);
//            System.out.println(integer);
//        }


        String[] str = {"17695796277"};

        Map<String, String> map = new HashMap<>();
        map.put("driverName", "任康");
        map.put("onlinTome", "123");
        Response response = smsManager.sendMessage("1202791626972340224", str, map);
        System.out.println("response===="+response);

    }


}
