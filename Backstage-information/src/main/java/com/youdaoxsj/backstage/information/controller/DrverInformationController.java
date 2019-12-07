package com.youdaoxsj.backstage.information.controller;


import com.youdaoxsj.backstage.information.bean.ExtendDevice;
import com.youdaoxsj.backstage.information.bean.IccidMsg;
import com.youdaoxsj.backstage.information.bean.ZbqDevice;
import com.youdaoxsj.backstage.information.service.DriverInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class DrverInformationController {

    @Autowired
    private DriverInformationService driverInformationService;


    @RequestMapping("/hello")
    public String hello() {
//        ExtendDevice extendDevice = driverInformationService.searchDev(id);
//        modelMap.addAttribute("list",extendDevice);
        return "index";

    }

    /**
     * @param modelMap
     * @param name
     * @param type
     * @return
     */
    @RequestMapping("/hello1")
    public String hello1(ModelMap modelMap, String name, String type,@RequestParam(value = "page", defaultValue = "1") Integer page) {
        String name1 = "lastReportTime";
        boolean b = false;

        if (type == null || type == "") {
            b = true;
        } else if (type.equals("ESC")) {
            b = false;
        }
        if (name != null) {
            name1 = name;
        }


        List<ExtendDevice> extendDevices = driverInformationService.searchDevs(name1, b,page);
        Integer lenght = driverInformationService.getDriverLenght();
        modelMap.addAttribute("extendDevices", extendDevices);
        modelMap.addAttribute("lenght",lenght);
        modelMap.addAttribute("page",page);

        return "index";
    }

    @RequestMapping("/getDriver")
    @ResponseBody
    public ExtendDevice getDriver(Integer id) {
        ExtendDevice extendDevice = driverInformationService.searchDev(id);
        return extendDevice;
    }

    @RequestMapping("/saveEI")
    @ResponseBody
    public Integer saveEI(Integer id, String driverIccid, String deviceNote) {
        System.out.println("====" + id + "+++++" + driverIccid + "+++++" + deviceNote);
        Integer state = driverInformationService.updateEI(id, driverIccid, deviceNote);

        return state;
    }

    /**
     * 根据查询条件查询
     *
     * @param driverName
     * @param driverPhone
     * @param driverPlateNum
     * @param modelMap
     * @param name
     * @param type
     * @return
     */
    @RequestMapping("/searchDriver")
    public String searchDriver(String driverName, String driverPhone, String driverPlateNum, ModelMap modelMap, String name, boolean type,@RequestParam(value = "page", defaultValue = "1") Integer page) {
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
        name = "lastLoginTime";
        type = true;
        System.out.println(zbqDevice.toString());
        List<ExtendDevice> extendDevices = driverInformationService.searchDriverByzbq(zbqDevice, name, type,page);
        Integer lenght = driverInformationService.getDriverLenght();
        modelMap.addAttribute("extendDevices", extendDevices);
        modelMap.addAttribute("lenght",lenght);
        modelMap.addAttribute("page",page);


        return "index";
    }



    @RequestMapping("/getIccids")
    @ResponseBody
    public List<String> getIccids(){

     List<String> iccids = driverInformationService.getIccids();

        return iccids;
    }


    @RequestMapping("/getIccidMsg")
    @ResponseBody
    public IccidMsg getIccidMsg(String iccid){

       IccidMsg iccidMsg =  driverInformationService.searchIccidMsg(iccid);

       return iccidMsg;
    }


}
