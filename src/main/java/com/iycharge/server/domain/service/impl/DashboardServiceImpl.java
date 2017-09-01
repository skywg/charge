package com.iycharge.server.domain.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.common.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.admin.cache.RCharger;
import com.iycharge.server.admin.cache.RConnector;
import com.iycharge.server.ccu.msg.entity.ChargerRealtimeData;
import com.iycharge.server.ccu.repository.ChargerRealtimeDataRepository;
import com.iycharge.server.domain.entity.IndexBean;
import com.iycharge.server.domain.entity.charger.Charger;
import com.iycharge.server.domain.entity.charger.ChargerStatus;
import com.iycharge.server.domain.entity.station.Station;
import com.iycharge.server.domain.entity.utils.RedisUtil;
import com.iycharge.server.domain.repository.DashboardRepository;
import com.iycharge.server.domain.repository.OrderItemRepository;
import com.iycharge.server.domain.repository.OrderRepository;
import com.iycharge.server.domain.service.DashboardService;
@Service
public class DashboardServiceImpl implements DashboardService{
    @Autowired
	private DashboardRepository dashboardRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ChargerRealtimeDataRepository chargerRealtimeDataRepository;
    
    @Autowired
    private RedisUtil redisUtil;
    
	@Override
	public Map<String,String> countUser() {
		//人数
		String num = dashboardRepository.countUser();
		//每天
		String day = dashboardRepository.countUserDay();
		//每月
		String month = dashboardRepository.countUserMonth();
		Map<String,String> map = new HashMap<String,String>();
		if(StringUtils.isNotEmpty(num)){
			map.put("numUser", num);
		}else{
			map.put("numUser", "0");
		}
		if(StringUtils.isNotEmpty(day)){
			map.put("dayUser", day);
		}else{
			map.put("dayUser", "0");
		}
		if(StringUtils.isNotEmpty(month)){
			map.put("monthUser", month);
		}else{
			map.put("monthUser", "0");
		}
		return map;
	}

	@Override
	public Map<String,String> countCharger() {
		//充电次数
		String num = orderRepository.countCharger();
		//每天
		String day = orderRepository.countChargerDay();
		//每月
		String month = orderRepository.countChargerMonth();
		Map<String,String> map = new HashMap<String,String>();
		if(StringUtils.isNotEmpty(num)){
			map.put("numCharger", num);
		}else{
			map.put("numCharger", "0");
		}
		if(StringUtils.isNotEmpty(day)){
			map.put("dayCharger", day);
		}else{
			map.put("dayCharger", "0");
		}
		if(StringUtils.isNotEmpty(month)){
			map.put("monthCharger", month);
		}else{
			map.put("monthCharger", "0");
		}
		return map;
	}

	@Override
	public Map<String,String> countElectricity() {
		//充电电量总数
		String num = orderRepository.countChargerDegree();
		//每天
		String day = orderRepository.countChargerDegreeDay();
		//每月
		String month = orderRepository.countChargerDegreeMonth();
		Map<String,String> map = new HashMap<String,String>();
		if(StringUtils.isNotEmpty(num)){
			map.put("numChargerDegree", num);
		}else{
			map.put("numChargerDegree", "0");
		}
		if(StringUtils.isNotEmpty(day)){
			map.put("dayChargerDegree", day);
		}else{
			map.put("dayChargerDegree", "0");
		}
		if(StringUtils.isNotEmpty(month)){
			map.put("monthChargerDegree", month);
		}else{
			map.put("monthChargerDegree", "0");
		}
		return map;
	}

	@Override
	public Map<String,String> countMoney() {
		//充电money总数
		String num = orderRepository.countChargerMoney();
		//每天
		String day = orderRepository.countChargerMoneyDay();
		//每月
		String month = orderRepository.countChargerMoneyMonth();
		Map<String,String> map = new HashMap<String,String>();
		if(StringUtils.isNotEmpty(num)){
			map.put("numChargerMoney", num);
		}else{
			map.put("numChargerMoney", "0");
		}
		if(StringUtils.isNotEmpty(day)){
			map.put("dayChargerMoney", day);
		}else{
			map.put("dayChargerMoney", "0");
		}
		if(StringUtils.isNotEmpty(month)){
			map.put("monthChargerMoney", month);
		}else{
			map.put("monthChargerMoney", "0");
		}
		return map;
	}

	@Override
	public String countStationType() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String chargerDegreeDay(long chargerId) {
		return orderRepository.chargerDegreeDay(chargerId);
	}
	
	/* (non-Javadoc)
	 * @see com.iycharge.server.domain.service.DashboardService#countChargerType()
	 */
	@Override
	public Map<String,String> countChargerType() {
	    Map<String,String> map = new HashMap<String,String>();
	    
	    //空闲桩数量
	    int idleCount     = 0;
	    //离线桩数量
	    int offlineCount  = 0;
	    //正在充电的电桩数量
	    int chargingCount = 0;
	    //故障桩数量
	    int fualtCount    = 0;
	    
	    Collection<Object> rchargerList = redisUtil.getByPattern(RedisUtil.PREFIX_CHARGER + "*");
	    if(rchargerList != null && rchargerList.size() > 0) {
	        for(Object item : rchargerList) {
	            RCharger rcharger = (RCharger)item;
	            if(rcharger.getId() == null || EntityUtil.getCharger(rcharger.getId()) == null) {
                    continue;
                }
	            
	            Charger charger = EntityUtil.getCharger(rcharger.getId());
	            if(!charger.getCode().equals(rcharger.getCode())) {
	                continue;
	            }
	            //获取充电抢
	            List<RConnector> ll = rcharger.getConnectorList();
	            for(RConnector rConnector:ll){
	            	if(rConnector.getStatus() == ChargerStatus.IDLE) {
	 	                idleCount ++;
	 	            } else if (rConnector.getStatus() == ChargerStatus.OFFLINE) {
	 	                offlineCount ++;
	 	            } else if (rConnector.getStatus() == ChargerStatus.CHARGING) {
	 	                chargingCount ++;
	 	            } else if (rConnector.getStatus() == ChargerStatus.REPAIR) {
	 	                fualtCount ++;
	 	            } else {
	 	                offlineCount ++;
	 	            }
	            }
	            /*
	            if(rcharger.getChargerStatus() == ChargerStatus.IDLE) {
	                idleCount ++;
	            } else if (rcharger.getChargerStatus() == ChargerStatus.OFFLINE) {
	                offlineCount ++;
	            } else if (rcharger.getChargerStatus() == ChargerStatus.CHARGING) {
	                chargingCount ++;
	            } else if (rcharger.getChargerStatus() == ChargerStatus.REPAIR) {
	                fualtCount ++;
	            } else {
	                offlineCount ++;
	            }*/
	        }
	    }
	   
		map.put("kongxian", String.valueOf(idleCount));
		map.put("lixian", String.valueOf(offlineCount));
		map.put("jianxiu", String.valueOf(fualtCount));
		map.put("chongdianzhong", String.valueOf(chargingCount));
		long nu = idleCount+offlineCount+fualtCount+chargingCount;
		map.put("charger", String.valueOf(nu));		
		//map.put("charger", String.valueOf(EntityUtil.getAllChargers().size()));		
		map.put("station", String.valueOf(EntityUtil.getAllStations().size()));
		
		return map;
	}
	
	@Override
	public List<IndexBean> countAllCharger(){

		Map<Long, IndexBean> map = new HashMap<>();
		
		Collection<Station> stationList  = EntityUtil.getAllStations();
		for(Station station : stationList) {
		    IndexBean indexBean = new IndexBean();
		    indexBean.setStationId(String.valueOf(station.getId()));
		    indexBean.setStationName(station.getName());
		    indexBean.setLatitude(station.getLatitude());
		    indexBean.setLongitude(station.getLongitude());
		    indexBean.setStationStatus(ChargerStatus.OFFLINE.name());
		    
		    Map<String, Object> params = new HashMap<>();
		    params.put("idleCount", 0);
		    params.put("offlineCount", 0);
		    params.put("chargingCount", 0);
		    params.put("fualtCount", 0);
		    indexBean.setMap(params);
		    
		    map.put(station.getId(), indexBean);
		}

		Collection<Object> rchargerList = redisUtil.getByPattern(RedisUtil.PREFIX_CHARGER + "*");
        if(rchargerList != null && rchargerList.size() > 0) {
            Map<Long, Set<ChargerStatus>> stationStatusMap = new HashMap<>();
            for(Object item : rchargerList) {
                RCharger rcharger = (RCharger)item;
                if(rcharger.getId() == null || EntityUtil.getCharger(rcharger.getId()) == null) {
                    continue;
                }
                
                Charger charger = EntityUtil.getCharger(rcharger.getId());
                if(!charger.getCode().equals(rcharger.getCode())) {
                    continue;
                }
                
                Station station = EntityUtil.getStationByChargerId(rcharger.getId()); 
                if(station != null) {
                    Set<ChargerStatus> statusSet = stationStatusMap.get(station.getId());
                    if(statusSet == null) {
                        statusSet = new HashSet<>();
                        stationStatusMap.put(station.getId(), statusSet);
                    }
                    //通过抢判断桩状态
                    List<RConnector> rclist = rcharger.getConnectorList();
                    if(rclist!=null){
                    	for(RConnector rConnector:rclist){
                    		statusSet.add(rConnector.getStatus());
                    	}
                    }
                    //statusSet.add(rcharger.getChargerStatus());
                    
                    IndexBean indexBean = map.get(station.getId());
                    if(indexBean != null) {    
                        Map<String, Object> params = indexBean.getMap();
                        //获取充电抢
        	            List<RConnector> ll = rcharger.getConnectorList();
        	            for(RConnector rConnector:ll){
        	                if(rConnector.getStatus() == ChargerStatus.IDLE) {
                            	params.put("idleCount", (int)params.get("idleCount") + 1);
	                        } else if (rConnector.getStatus() == ChargerStatus.OFFLINE) {
	                            params.put("offlineCount", (int)params.get("offlineCount") + 1);
	                        } else if (rConnector.getStatus() == ChargerStatus.CHARGING) {
	                            params.put("chargingCount", (int)params.get("chargingCount") + 1);
	                        } else if (rConnector.getStatus() == ChargerStatus.REPAIR) {
	                            params.put("fualtCount", (int)params.get("fualtCount") + 1);
	                        } else {
	                            params.put("offlineCount", (int)params.get("offlineCount") + 1);
	                        }
        	            }
                       /* if(rcharger.getChargerStatus() == ChargerStatus.IDLE) {
                            params.put("idleCount", (int)params.get("idleCount") + 1);
                        } else if (rcharger.getChargerStatus() == ChargerStatus.OFFLINE) {
                            params.put("offlineCount", (int)params.get("offlineCount") + 1);
                        } else if (rcharger.getChargerStatus() == ChargerStatus.CHARGING) {
                            params.put("chargingCount", (int)params.get("chargingCount") + 1);
                        } else if (rcharger.getChargerStatus() == ChargerStatus.REPAIR) {
                            params.put("fualtCount", (int)params.get("fualtCount") + 1);
                        } else {
                            params.put("offlineCount", (int)params.get("offlineCount") + 1);
                        }*/
                    }
                }
            }
            
            /**
             * 根据桩状态计算站的状态 
             */
            if(stationStatusMap.size() > 0 ) {
                for(Long stationId : map.keySet()) {
                    if(stationStatusMap.containsKey(stationId)) {
                        Set<ChargerStatus> statusSet = stationStatusMap.get(stationId);                       
                        if (statusSet.contains(ChargerStatus.REPAIR)) {
                            map.get(stationId).setStationStatus(ChargerStatus.REPAIR.name());
                        } else if (statusSet.contains(ChargerStatus.CHARGING)) {
                            map.get(stationId).setStationStatus(ChargerStatus.CHARGING.name());
                        }  else if (statusSet.contains(ChargerStatus.IDLE)) {
                            map.get(stationId).setStationStatus(ChargerStatus.IDLE.name());
                        } else if(statusSet.contains(ChargerStatus.OFFLINE)) {
                            map.get(stationId).setStationStatus(ChargerStatus.OFFLINE.name());
                        }
                    }
                }
            }
        }
        
		return new ArrayList<IndexBean>(map.values());
	}

	/**
	 * 获取图片信息
	 */
	@Override
	public List<Object[]> findAllOrderItem() {
		return orderItemRepository.findAllOrderItem();
	}
	
	@Override
	public List<ChargerRealtimeData> findByChargeNo(String chargeNo) {
		return  chargerRealtimeDataRepository.findByChargerNoOrderByIdDesc(chargeNo);
	}

    @Override
    public List<ChargerRealtimeData> findByChargeNo(String chargerNo, Date startAt) {
        // TODO Auto-generated method stub
        return chargerRealtimeDataRepository.findByChargerNo(chargerNo, startAt);
    }
	
	
}
