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

    private String nowSortName;


    /**
     * 获取司机信息
     * @param modelMap 返回数据
     * @param sortName 排序字段
     * @param sortPage 分页页码
     * @return index.html
     */
    @RequestMapping("/hello1")
    public String hello1(ModelMap modelMap,@RequestParam(value = "sortName", defaultValue = "last_report_time_asc") String sortName,@RequestParam(value = "sortPage", defaultValue = "1") Integer sortPage) {
        List<ExtendDevice> extendDevices = driverInformationService.searchDevs(sortName,sortPage);
        List<String> sortName1 = driverInformationService.getSortName(sortName);




        Integer lenght = driverInformationService.getZbqDeviceCount();
        //司机信息
        modelMap.addAttribute("extendDevices", extendDevices);
        //总页数
        modelMap.addAttribute("lenght",lenght);
        //当前页
        modelMap.addAttribute("sortPage",sortPage);
        //排序字段
        modelMap.addAttribute("sortName1",sortName1);
        //当前排序字段
        modelMap.addAttribute("sortName",sortName);
        return "index";
    }

    /**
     * 查询某一个司机信息
     * @param id 司机id
     * @return  返回司机信息
     */
    @RequestMapping("/getDriver")
    @ResponseBody
    public ExtendDevice getDriver(Integer id) {
        //某一个司机的信息
        ExtendDevice extendDevice = driverInformationService.searchDev(id);
        return extendDevice;
    }

    /**
     * 保存司机信息
     * @param id 司机id
     * @param driverIccid 司机iccid
     * @param deviceNote  司机备注
     * @return   状态码 1是成功 0是失败
     */
    @RequestMapping("/saveEI")
    @ResponseBody
    public Integer saveEI(Integer id, String driverIccid, String deviceNote) {
        //保存信息
        Integer state = driverInformationService.updateEI(id, driverIccid, deviceNote);

        return state;
    }

    /**
     * 根据查询条件查询
     *
     * @param driverName 司机姓名
     * @param driverPhone 司机电话
     * @param driverPlateNum 司机车牌号
     * @param modelMap 返回的数据
     * @param sortName 排序字段
     * @param sortPage 分页页码
     * @return 查询后的数据
     */
    @RequestMapping("/searchDriver")
    public String searchDriver(String driverName, String driverPhone, String driverPlateNum, ModelMap modelMap, String sortName,@RequestParam(value = "sortPage", defaultValue = "1") Integer sortPage) {
        List<ExtendDevice> extendDevices = driverInformationService.searchDriverByzbq(driverName,driverPhone,driverPlateNum, sortName, sortPage);
        Integer lenght = driverInformationService.getsearchDriverLenght();
        //返回查询结果数据
        modelMap.addAttribute("extendDevices", extendDevices);
        //返回查询结果页数
        modelMap.addAttribute("lenght",lenght);
        //返回查询结果当前页数
        modelMap.addAttribute("sortPage",sortPage);

        return "index";
    }


    /**
     * 获取ICCID信息
     * @param iccid iccid
     * @return  查询结果
     */
    @RequestMapping("/getIccidMsg")
    @ResponseBody
    public IccidMsg getIccidMsg(String iccid){

       IccidMsg iccidMsg =  driverInformationService.searchIccidMsg(iccid);

       return iccidMsg;
    }

    /**
     * 公众号用于车牌号查询时长
     * @param carNum
     * @return
     */
    @RequestMapping("/getDriverTime")
    @ResponseBody
    public ZbqDevice getDriverTime(String carNum){

        ZbqDevice zbqDevice = driverInformationService.getDriverByCarNum(carNum);
        return zbqDevice;
    }

    /**
     * 公众号查询时间页面展示
     * @return
     */
    @RequestMapping("/driverTime")
    public String showTimeDriver(){
        return "driverTime";
    }


}
