package com.iycharge.server.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.admin.cache.RCharger;
import com.iycharge.server.admin.cache.RConnector;
import com.iycharge.server.admin.cache.StationStatisticTask.RStationData;
import com.iycharge.server.domain.entity.BaseEntity;
import com.iycharge.server.domain.entity.account.favorite.Favorite;
import com.iycharge.server.domain.entity.charger.Charger;
import com.iycharge.server.domain.entity.charger.ChargerGun;
import com.iycharge.server.domain.entity.charger.ChargerStatus;
import com.iycharge.server.domain.entity.review.Review;

import com.iycharge.server.domain.entity.station.Station;
import com.iycharge.server.domain.entity.utils.RedisUtil;
import com.iycharge.server.domain.service.FavoriteService;
import com.iycharge.server.domain.service.StationService;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stations")
public class StationRestController {
    @Autowired
    private StationService stationService;
    
	@Autowired
	private FavoriteService favoriteService;
	
    @Autowired
    private RedisUtil redisUtil;
    
    @RequestMapping("/{stationId}")
    @JsonView(BaseEntity.Detail.class)
    public Station getById(@PathVariable Long stationId, @RequestParam(required=false) Long accountId) {
        Station station = stationService.findById(stationId);
        //=================枪数据=====================
        //总枪数
        int totalGuns = 0;
        //空闲数
        int totalIdelCung = 0;
        
        //=================枪数据=====================
        if(station == null) {
            return null;
        }
        
        Set<Charger> chargerSet = station.getChargers();
        Set<String> keys = new HashSet<>();
        //过滤掉已删除的桩
        for(Iterator<Charger> iter = chargerSet.iterator(); iter.hasNext(); ) {
            Charger charger = iter.next();
            if(charger.getDelStatus().equalsIgnoreCase("del")) {
                iter.remove();
            } else {
                keys.add(RedisUtil.PREFIX_CHARGER + charger.getCode());
            }
        }
        
        if(keys.size() > 0) {
            Collection rchargerList = (Collection)redisUtil.get(keys);
            Map<String, ChargerStatus> temp = new HashMap<>();
            for(Object obj : rchargerList) {
                RCharger item  = (RCharger)obj;
                temp.put(item.getCode(), item.getChargerStatus());
            }
            for(Charger charger : chargerSet) {
                charger.setStatus(temp.containsKey(charger.getCode()) ? 
                                        temp.get(charger.getCode()) : ChargerStatus.OFFLINE);
               //==============设置枪状态==============
                List<ChargerGun> cgs = charger.getGuns();
                for(Object obj : rchargerList) {
                    RCharger item  = (RCharger)obj;
                    if(!item.getCode().equals(charger.getCode())){
                    	continue;
                    }
                    List<RConnector> rConnectors = item.getConnectorList();
                    if(null!=rConnectors&&rConnectors.size()>0){
                    	for(int i = 0 ; i < rConnectors.size() ; i++){
                    		for(int j = 0 ; j < cgs.size() ; j++){
                    			if(cgs.get(j)==null||!cgs.get(j).getGunName().equals(rConnectors.get(i).getName())){
                    				continue;
                    			}
                    			cgs.get(j).setStatus(rConnectors.get(i).getStatus());
                    			totalGuns++;
                    			if(rConnectors.get(i).getStatus().name().equals("IDLE")){
                    				totalIdelCung++;
                    			}
                    		}
                    	}
                    	charger.setGuns(cgs);
                    }
                }
                
                charger.setGuns(cgs);
                //==============设置枪状态==============
           }           
        }
        
        RStationData rstation = (RStationData)redisUtil.get(RedisUtil.PREFIX_STATION + station.getId());
        if(rstation != null) {
            station.setStatus(rstation.getStatus());
            station.setTotalCount(rstation.getTotalNum());
            station.setIdleCount(rstation.getIdleNum());
        }
        station.setTotalGuns(totalGuns);
        station.setTotalIdelCung(totalIdelCung);
        if(accountId != null) { 
            Favorite favorite = favoriteService.findAccountFavorite(accountId, stationId);
            if(favorite != null) {
                station.setCollected("Y");
            }
        }
        return station;
    }
    
    @RequestMapping("/check")
    @JsonView(BaseEntity.Detail.class)
    public List<Review> getReview(@RequestParam("stationId") Long stationId) {
    	Station station	=stationService.findById(stationId);
    	Set<Review> reviews = station.getReviews();
    	List<Review> reviews1 = new LinkedList<>();
    	for (Review review : reviews) {
			if (review.getStatus()==1) {
				reviews1.add(review);
				
			}
		}
        return reviews1;
    }

}