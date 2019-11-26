package com.youdaoxsj.backstage.information;

import com.youdaoxsj.backstage.information.mapper.DriverInformationMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class BackstageInformationApplicationTests {


//    @Autowired
//    private DruidDataSource druidDataSource;

    @Autowired
    private DriverInformationMapper driverInformationMapper;

    @Test
    public void contextLoads() {
//        Integer id2 = 655;
//
//        List<Device> devices = driverInformationMapper.selectDevice(id2);
//
//        for (Device device : devices){
//            System.out.println(device.toString());
//        }


    }

}
