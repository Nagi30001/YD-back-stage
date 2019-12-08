package com.youdaoxsj.backstage.information.mapper;

import com.youdaoxsj.backstage.information.bean.ExtendInformation;
import com.youdaoxsj.backstage.information.bean.ZbqDevice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DriverInformationMapper {




    /**
     * 通过司机id修改对应的信息
     *
     * @param extendInformation 更新的数据
     * @param
     * @return
     */
    int updateExtendInformation(@Param("e") ExtendInformation extendInformation);


    /**
     * 插入新信息
     *
     * @param extendInformation 新数据信息
     * @return
     */
    int insertExtendInformation(@Param("ei") ExtendInformation extendInformation);


    /**
     * 根据id查询司机信息
     * @param id 司机ID
     * @return 司机信息
     */
    ZbqDevice selectDevice(Integer id);

    /**
     * 根据司机id查询拓展信息
     * @param id 司机id
     * @return 拓展信息
     */
    ExtendInformation selectEI(Integer id);

    /**
     * 根据查询条件查询信息
     * @param zbqDevice 查询条件
     * @param sortName 排序字段
     * @param sortPage 页码码数
     * @param num   每页num行
     * @return  返回数据
     */
    List<ZbqDevice> searchDriverByzbq(@Param("zz") ZbqDevice zbqDevice,@Param("sortName") String sortName,@Param("sortPage") Integer sortPage,@Param("num") int num);

    /**
     * 查询所以拓展信息
     * @return 拓展信息集合
     */
    List<ExtendInformation> selectExtendInformation();

    /**
     * 司机信息及拓展信息
     * @param sortName 排序字段
     * @param sortPage 分页从sortPage开始计算
     * @param num      每页num行
     * @return 结果集合
     */
    List<ZbqDevice> selectDevices(@Param("sortName") String sortName,@Param("sortPage") Integer sortPage,@Param("num") int num);

    /**
     * 查询司机信息正常使用的数量
     * @return 司机数量
     */
    Integer selectConutZbqDevice();

    /**
     * 获取某一月时间的司机各总在线时长/秒
     * @param integer PHP时间戳 1号0时0分0秒
     * @return 查询结果
     */
    List<ZbqDevice> selectOnLineTime(Integer integer);

    /**
     * 1号重制司机当前月时长
     * @param deviceId 司机id
     * @return 1-成功 0-失败
     */
    Integer updateZbqDevice(Integer deviceId);

    /**
     * 根据查询条件获取查询结果长度
     * @param zbqDevice 查询数据
     * @return 长度
     */
    Integer getSearchLSize(@Param("zz")  ZbqDevice zbqDevice);

}
