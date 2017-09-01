package com.iycharge.server.report.schedule.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.domain.entity.station.Station;
import com.iycharge.server.domain.service.EventService;
import com.iycharge.server.report.entity.EventData;
import com.iycharge.server.report.schedule.service.EventDataService;

/**
 * 告警数据统计任务  
 * @author bwang
 */
@Component
public class EventDataStatisticTask extends AbstractTask {

    @Resource
    private EventDataService eventDataService;
    
    @Resource
    private EventService eventService;
    
    @Override
    public void run() {
        try {
            while(check()) {
                doWork();
                
                this.getTask().setFlag(true);
                updateTask();
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getTask().setFlag(false);
            updateTask();
        }
    }

    @Override
    public void doWork() {
        List<EventData> eventDataList = new ArrayList<>();
        
        EventData eventData = null;
        Map<String, EventData> temp = null;
        String key = null;
        //按告警类型统计
        List<Object[]> statisticDatas = eventService.statisticByEventType(getStartTime(), getEndTime());
        if(statisticDatas != null && !statisticDatas.isEmpty()) {
            temp = new HashMap<>();
            for(Object[] item : statisticDatas) {
                Station station = EntityUtil.getStationByChargerId((Long)item[0]);
                if(station == null) {
                    continue;
                }
                key = station.getId() + "_" + item[1];
                if(temp.containsKey(key)) {
                    eventData = temp.get(key);
                } else {
                    eventData = new EventData();
                    eventData.setProvince(station.getProvince());
                    eventData.setCity(station.getCity());
                    eventData.setDistrict(station.getDistrict());
                    eventData.setStationId(station.getId());
                    eventData.setStationName(station.getName());
                    eventData.setDay(getStartTime());
                    eventData.setEventType((String)item[1]);
                    
                    temp.put(key, eventData);
                }
                
                eventData.setEventTypeNum(eventData.getEventTypeNum() + (long)item[2]);               
            }
            
            eventDataList.addAll(temp.values());
        }
        
        //按告警级别统计
        statisticDatas = eventService.statisticByEventLevel(getStartTime(), getEndTime());
        if(statisticDatas != null && !statisticDatas.isEmpty()) {
            temp = new HashMap<>();
            for(Object[] item : statisticDatas) {
                Station station = EntityUtil.getStationByChargerId((Long)item[0]);
                if(station == null) {
                    continue;
                }
                
                key = station.getId() + "_" + item[1];
                if(temp.containsKey(key)) {
                    eventData = temp.get(key);
                } else {
                    eventData = new EventData();
                    eventData.setProvince(station.getProvince());
                    eventData.setCity(station.getCity());
                    eventData.setDistrict(station.getDistrict());
                    eventData.setStationId(station.getId());
                    eventData.setStationName(station.getName());
                    eventData.setDay(getStartTime());
                    eventData.setEventLevel((String)item[1]);
                    
                    temp.put(key, eventData);
                }
                
                eventData.setEventLevelNum(eventData.getEventLevelNum() + (long)item[2]);
            }
            
            eventDataList.addAll(temp.values());
        }
        
        eventDataService.saveAll(eventDataList);
    }
}
