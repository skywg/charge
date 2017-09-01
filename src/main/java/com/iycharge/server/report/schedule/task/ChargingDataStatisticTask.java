package com.iycharge.server.report.schedule.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.domain.entity.charger.Charger;
import com.iycharge.server.domain.entity.order.Order;
import com.iycharge.server.domain.entity.order.PaidFrom;
import com.iycharge.server.domain.entity.station.Station;
import com.iycharge.server.domain.service.OrderService;
import com.iycharge.server.report.entity.ChargingData;
import com.iycharge.server.report.schedule.service.ChargingDataService;

/**
 * 充电数据统计任务
 * @author bwang
 */
@Component
public class ChargingDataStatisticTask extends AbstractTask {
    
    private static final String DC = "1";
    private static final String AC = "2";
    
    @Resource
    private ChargingDataService chargingDataService;
    
    @Resource
    private OrderService orderService;
    
    public ChargingDataStatisticTask() {    
        
    }
  
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
        Map<Long, ChargingData> chargingDataMap = new HashMap<>();
        
        //初始化chargingDataMap
        Collection<Station> stationList = EntityUtil.getAllStations();
        if(stationList != null && !stationList.isEmpty()) {
            for(Station station : stationList) {
                ChargingData chargingData = new ChargingData();
                chargingData.setProvince(station.getProvince());
                chargingData.setCity(station.getCity());
                chargingData.setDistrict(station.getDistrict());
                chargingData.setStationId(station.getId());
                chargingData.setStationName(station.getName());
                chargingData.setDay(getStartTime());
                
                chargingDataMap.put(station.getId(), chargingData);
            }
        }
        
        List<Order> orderList = orderService.findByPeriod(getStartTime(), getEndTime());
        if(orderList != null && !orderList.isEmpty()) {
            for(Order order : orderList) {
                Charger charger = order.getCharger();
                if(charger == null) {
                    continue;
                }
                Station station = EntityUtil.getStationByChargerId(charger.getId());
                if(station == null) {
                    continue;
                }
                
                if(chargingDataMap.containsKey(station.getId())) {
                    ChargingData chargingData = chargingDataMap.get(station.getId());
                    if(DC.equals(order.getIfType())) {
                        //直流充电
                        chargingData.setMoneyDC(chargingData.getMoneyDC().add(order.getMoney()));
                        chargingData.setElectricDC(chargingData.getElectricDC().add(order.getDegree()));
                        chargingData.setTimesDC(chargingData.getTimesDC() + 1);
                    } else if(AC.equals(order.getIfType())) {
                        //交流充电
                        chargingData.setMoneyAC(chargingData.getMoneyAC().add(order.getMoney()));
                        chargingData.setElectricAC(chargingData.getElectricAC().add(order.getDegree()));
                        chargingData.setTimesAC(chargingData.getTimesAC() + 1);
                    }
                    
                    if(PaidFrom.CARD.equals(order.getPaidFrom())) {
                        //刷卡充电
                        chargingData.setMoneyCard(chargingData.getMoneyCard().add(order.getMoney()));
                        chargingData.setElectricCard(chargingData.getElectricCard().add(order.getDegree()));
                        chargingData.setTimesCard(chargingData.getTimesCard() + 1);
                    } else {
                        //app充电
                        chargingData.setMoneyApp(chargingData.getMoneyApp().add(order.getMoney()));
                        chargingData.setElectricApp(chargingData.getElectricApp().add(order.getDegree()));
                        chargingData.setTimesApp(chargingData.getTimesApp() + 1);
                    }                   
                }
            }
        }
        
        chargingDataService.saveAll(new ArrayList<>(chargingDataMap.values()));
    }
}
