package com.iycharge.server.domain.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.iycharge.server.ccu.msg.entity.ChargerRealtimeData;
import com.iycharge.server.domain.entity.IndexBean;

public interface DashboardService {

	 public Map<String,String> countUser();
	 
	 public Map<String,String> countCharger();
	 
	 public Map<String,String> countElectricity();
	 
	 public Map<String,String> countMoney();
	 
	 public String countStationType();
	 
	 public Map<String,String> countChargerType();

	 public List<IndexBean> countAllCharger();
	 
	 public List<Object[]> findAllOrderItem();

	 String chargerDegreeDay(long chargerId);

	 List<ChargerRealtimeData> findByChargeNo(String chargerNo);
	 
	 List<ChargerRealtimeData> findByChargeNo(String chargerNo, Date startAt);
}
