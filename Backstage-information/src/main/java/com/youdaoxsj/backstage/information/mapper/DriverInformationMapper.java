package com.youdaoxsj.backstage.information.mapper;

import com.youdaoxsj.backstage.information.bean.ExtendDevice;
import com.youdaoxsj.backstage.information.bean.ZbqDevice;
import com.youdaoxsj.backstage.information.bean.ExtendInformation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DriverInformationMapper {

    /**
     * 获取司机信息
     * @return  返回司机信息集合
     */
    public List<ZbqDevice> selectDriverInformation();

    /**
     * 根据司机ID获取对呀的扩展信息
     * @param id
     * @return
     */
    public ExtendInformation selectSIMInformation(Integer id);

    /**
     * 通过司机id修改对应的信息
     * @param extendInformation 更新的数据
     * @param id    司机id
     * @return
     */
    public Integer updateExtendInformation(@Param("e") ExtendInformation extendInformation, @Param("id") Integer id);


    /**
     * 插入新信息
     * @param extendInformation 新数据信息
     * @return
     */
    public Integer insertExtendInformation(ExtendInformation extendInformation);


    public ZbqDevice selectDevice(Integer id);


    public ExtendInformation selectEI(Integer id);

    List<ZbqDevice> selectZbqDevices();
}