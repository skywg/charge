package com.iycharge.server.report.schedule.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.iycharge.server.report.entity.AcDcDataBean;
import com.iycharge.server.report.entity.ChargingData;
import com.iycharge.server.report.entity.DataBean;

/**
 * 充电消费数据操作业务接口类
 * @author bwang
 */
public interface ChargingDataService {
    
    /**
     * 充电消费统计数据单调存储
     * @param chargingData      
     * @return
     */
    ChargingData save(ChargingData chargingData);
    
    /**
     * 充电消费统计数据批量存储
     * @param list
     * @return
     */
    List<ChargingData> saveAll(List<ChargingData> list);
    
    public List<ChargingData> findByDayBetween(Date startdate,Date enddate);

	List<ChargingData> findAllSearch(String[] fields, ChargingData chargingData, Date start, Date end);

	List<DataBean> findBean(String type, String counttype, String datetype, String start, String end);

	List<String> findType(String type,String table, String datetype, String start, String end);

	List<AcDcDataBean> findAcDc(String type, String counttype, String datetype, String start, String end);

	List<String> findTypeSearch(String ids, String type, String table, String datetype, String start, String end);

	List<DataBean> findBeanSearch(String ids, String type, String counttype, String datetype, String start, String end);
	
}
