package com.youdaoxsj.backstage.information.controller;


import com.youdaoxsj.backstage.information.bean.ExtendDevice;
import com.youdaoxsj.backstage.information.bean.ExtendInformation;
import com.youdaoxsj.backstage.information.bean.ZbqDevice;
import com.youdaoxsj.backstage.information.service.DriverInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@Controller
public class DrverInformationController {

    @Autowired
    private DriverInformationService driverInformationService;



//    @RequestMapping("/hello")
//    public String hello(Integer id, ModelMap modelMap){
//        ExtendDevice extendDevice = driverInformationService.searchDev(id);
//        modelMap.addAttribute("list",extendDevice);
//        return "index";
//
//    }

    @RequestMapping("/hello1")
    public String hello1(ModelMap modelMap){
        List<ExtendDevice> extendDevices = driverInformationService.searchDevs();
        modelMap.addAttribute("extendDevices",extendDevices);
        return "index";
    }
}
