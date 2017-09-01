package com.iycharge.server.report.schedule.service;

import java.util.List;

import com.iycharge.server.report.entity.DataBean;
import com.iycharge.server.report.entity.EventData;

/**
 * 告警数据操作业务接口类
 * @author bwang
 */
public interface EventDataService {
    
    /**
     * 告警统计数据单条保存
     * @param eventData
     * @return
     */
    boolean save(EventData eventData);
    
    /**
     * 告警统计数据批量保存
     * @param eventDataList
     * @return
     */
    boolean saveAll(List<EventData> eventDataList);

	List<DataBean> findEventType(String countname, String counttype, String table, String start, String end);

	List<DataBean> findEventNum(String type, String datetype, String start, String end);

	List<DataBean> findType(String countname, String counttype, String datetype, String table, String start,
			String end);

}
