package com.iycharge.server.domain.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.iycharge.server.domain.entity.event.Event;
import com.iycharge.server.domain.entity.event.EventCode;
import com.iycharge.server.domain.entity.event.EventCodeQueryParam;
import com.iycharge.server.domain.entity.event.EventQueryParam;

/**
 *  
 * @author bwang
 */
public interface EventService {
	
	public Page<Event> findAll(Pageable  pageable);
 
	//public Page<Event> findAllSearch(String  fields[],Event event,Pageable pageable);

	Event findById(Long id);
	  
	Event save(Event event);
	
	/**
     * 按告警类型，统计时段内电桩的告警数
     * @param startTime     统计起始时间
     * @param endTime       统计结束时间
     * @return
     */
    List<Object[]> statisticByEventType(Date startTime, Date endTime);
    
    /**
     * 按告警级别，统计时段内电桩的告警数
     * @param startTime     统计起始时间
     * @param endTime       统计结束时间
     * @return
     */
    List<Object[]> statisticByEventLevel(Date startTime, Date endTime);
    Page<Event> find(EventQueryParam queryParam, Pageable pageable);
    
    List<Event> findByCondition(Map<String,String> field , Event event);

	List<Event> findByEventStatus();
}
