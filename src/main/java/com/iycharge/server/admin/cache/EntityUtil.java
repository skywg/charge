package com.iycharge.server.admin.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.util.StringUtils;

import com.iycharge.server.ccu.util.Utility;
import com.iycharge.server.domain.entity.admin.Menu;
import com.iycharge.server.domain.entity.charger.Charger;
import com.iycharge.server.domain.entity.charger.ChargerGun;
import com.iycharge.server.domain.entity.charger.ChargerStatus;
import com.iycharge.server.domain.entity.dict.DictCategory;
import com.iycharge.server.domain.entity.dict.DictData;
import com.iycharge.server.domain.entity.event.EventCode;
import com.iycharge.server.domain.entity.operator.Operator;
import com.iycharge.server.domain.entity.station.Station;
import com.iycharge.server.domain.entity.utils.RedisUtil;

/**
 *  实体对象缓存工具类
 * @author bwang
 */
public class EntityUtil {
    
    /**
     * 运营商集合 key： 运营商Id
     */
    private static Map<Long, Operator> operatorMap      =   new ConcurrentHashMap<>();
    
    /**
     * 充电站集合 key：充电站id
     */
    private static Map<Long, Station> stationMap        =   new ConcurrentHashMap<>();
    
    /**
     * 充电桩集合 key：充电桩id
     */
    private static Map<Long, Charger> chargerMap        =   new ConcurrentHashMap<>();
    
    /**
     * 充电桩编号与ID之间映射关系
     */
    private static Map<String, Long> chargerCode2IdMap  =   new ConcurrentHashMap<>();  
    
    /**
     * 字典值
     */
    private static Map<String,List<DictData>>  dictDataMap =  new ConcurrentHashMap<>();
    /**
     * 字典类型
     */
    private static Map<String,DictCategory> dictCategoryMap = new ConcurrentHashMap<>();
    /**
     * 充电桩与充电站之间的对应关系
     */
    private static Map<Long, Long> charger2stationMap   =   new ConcurrentHashMap<>();
    /**
     * 菜单   
     */
    public static Map<String,List<Menu>> menu = new ConcurrentHashMap<String,List<Menu>>();
    /**
     * 权限
     */
	public static Map<String,List<String>> buttonId = new ConcurrentHashMap<String,List<String>>();
	
	/**
	 * 故障码
	 */
	public static Map<Integer, EventCode> eventCodeMap = new ConcurrentHashMap<>();
	
    public static void addOperator(Operator operator) {
        operatorMap.put(operator.getId(), operator);
    }
    
    public static void addOperators(Collection<Operator> operators){
        if(operators == null) {
            return;
        }
        for(Operator operator : operators) {
            addOperator(operator);
        }
    }
    
    public static Operator getOperator(Long id) {
        if(id != null && operatorMap.containsKey(id)) {
            return operatorMap.get(id);
        }
        return null;
    }
    
    public static Collection<Operator> getAllOperators() {
        return operatorMap.values();
    }
    
    public static void removeOperator(Operator operator) {
        if(operator == null) {
            return;
        }
        if(operatorMap.containsKey(operator.getId())) {
            operatorMap.remove(operator.getId());
        }
    }
    
    public static void addStation(Station station) {
        stationMap.put(station.getId(), station);
    }
    
    public static void addStations(Collection<Station> stations){
        if(stations == null) {
            return;
        }
        for(Station station : stations) {
            addStation(station);
        }
    }
    
    public static Station getStation(Long id) {
        if(id != null && stationMap.containsKey(id)) {
            return stationMap.get(id);
        }
        return null;
    }
    
    public static Station getStationByChargerId(Long chargerId) {
        if(chargerId != null && charger2stationMap.containsKey(chargerId)) {
            return stationMap.get(charger2stationMap.get(chargerId));
        }
        return null;
    }
    
    public static Collection<Station> getAllStations() {
        return stationMap.values();
    }
    
    public static void removeStation(Station station) {
        if(station == null) {
            return;
        }
        if(stationMap.containsKey(station.getId())) {
            stationMap.remove(station.getId());
        }
    }
    
    public static void addCharger(Charger charger) {
        chargerMap.put(charger.getId(), charger);
        chargerCode2IdMap.put(charger.getCode(), charger.getId());
        charger2stationMap.put(charger.getId(), charger.getStation().getId());
        addChargerToRedis(charger);
    }
    
    public static void addChargers(Collection<Charger> chargers){
        if(chargers == null) {
            return;
        }
        for(Charger charger : chargers) {
            addCharger(charger);
        }
    }
    
    public static Charger getCharger(Long id) {
        if(id != null && chargerMap.containsKey(id)) {
            return chargerMap.get(id);
        }
        return null;
    }
    
    public static Charger getCharger(String code) {
        if(code != null && chargerCode2IdMap.containsKey(code)) {
            return getCharger(chargerCode2IdMap.get(code));
        }
        return null;
    }
    
    public static Collection<Charger> getAllChargers() {
        return chargerMap.values();
    }
    
    public static void removeCharger(Charger charger) {
        if(charger == null) {
            return;
        }
        if(chargerMap.containsKey(charger.getId())) {
            chargerMap.remove(charger.getId());
            chargerCode2IdMap.remove(charger.getCode());
            delChargerFromRedis(charger);
        }
    }
    
    private static void addChargerToRedis(Charger charger) {
        RedisUtil redisUtil = (RedisUtil)Utility.getBean(RedisUtil.class);
        RCharger rcharger = new RCharger();
        rcharger.setId(charger.getId());
        rcharger.setCode(charger.getCode());
        rcharger.setName(charger.getName());
        rcharger.setChargerStatus(ChargerStatus.OFFLINE);
        
        List<ChargerGun> guns  = charger.getGuns();
        if(guns != null) {
            List<RConnector> connList = new ArrayList<>();
            
            for(ChargerGun gun : guns) {
                RConnector conn = new RConnector();
                conn.setCode(gun.getGunNo());
                conn.setName(gun.getGunName());
                conn.setType(gun.getChargeIf());
                conn.setStatus(ChargerStatus.OFFLINE);
                
                connList.add(conn);
            }
            rcharger.setConnectorList(connList);
        }
        redisUtil.set(RedisUtil.PREFIX_CHARGER + charger.getCode(), rcharger);
    }
    
    private static void delChargerFromRedis(Charger charger) {
        RedisUtil redisUtil = (RedisUtil)Utility.getBean(RedisUtil.class);
        redisUtil.delete(RedisUtil.PREFIX_CHARGER + charger.getCode());
    }
    
    public static void addDictDatas(Collection<DictData> dictDatas){
    	for(DictData dictData : dictDatas){
    		DictCategory dc = dictData.getDictCategory();
    		if(null!=dc && null != dictDataMap.get(dc.getName())){
    			List<DictData> dt = dictDataMap.get(dc.getName());
    			dt.add(dictData);
    		}else{
    			List<DictData> dts = new ArrayList<>();
    			dts.add(dictData);
    			dictDataMap.put(dc.getName(), dts);
    		}
    	}
    	
    }
    
    public static void addDictData(DictData dictData){
    	DictCategory dc = dictData.getDictCategory();
    	if(null!=dc && null != dictDataMap.get(dc.getName())){
			List<DictData> dt = dictDataMap.get(dc.getName());
			dt.add(dictData);
		}else{
			List<DictData> dts = new ArrayList<>();
			dts.add(dictData);
			dictDataMap.put(dc.getName(), dts);
		}
    }
    
    public static void removeDictData(DictData dictData){
    	DictCategory dc = dictData.getDictCategory();
    	if(null!=dc && null != dictDataMap.get(dc.getName())){
    		List<DictData> dts = dictDataMap.get(dc.getName());
    		if(dts.contains(dictData)){
    			dts.remove(dts.indexOf(dictData));
    		}
    		
    	}
    }
    
    public static void updateDictData(DictData dictData){
    	removeDictData(dictData);
    	addDictData(dictData);
    }
    
    public static List<DictData> getDictDatas(String name){
    	return sort(dictDataMap.get(name));
    }

	public static void addDictCategorys(List<DictCategory> dictCategorys) {
		for(DictCategory dictCategory : dictCategorys){
			String key = dictCategory.getName();
			dictCategoryMap.put(key, dictCategory);			
		}
	}

	
    
	public static DictCategory getDictCategory(String name){
		DictCategory dictCategory = dictCategoryMap.get(name);
		return dictCategory;
	}
	
	private static List<DictData> sort(List<DictData> dictDatas){
		if(dictDatas==null){
			return dictDatas;
		}
		Collections.sort(dictDatas, new Comparator<DictData>(){  
			@Override
			public int compare(DictData o1, DictData o2) {
				// TODO Auto-generated method stub
				if(o1.getSortNo() > o2.getSortNo()){  
                    return 1;  
                }  
                if(o1.getSortNo() == o2.getSortNo()){  
                    return 0;  
                }  
                return -1;
			}  
        });   
		return dictDatas;
	}
	private static String  toJson(String jsonString ,List<DictData> lst){
		if(lst == null){
			return "";
		}
		jsonString+="[{";
		for(int i = 0 ; i < lst.size() ; i ++){
			DictData dictData = lst.get(i);	
			if(dictData.getDictDataList().size()==0){
				if(i<lst.size()-1){
					jsonString+="\""+i+"\":"+"\""+dictData.getDictValue()+"\""+",";
				}else{
					jsonString+="\""+i+"\":"+"\""+dictData.getDictValue()+"\"";
				}
				
			}else{
				jsonString+="\""+dictData.getDictValue()+"\""+":";
				String tmp = "";
				tmp+=toJson(jsonString,dictData.getDictDataList());
				jsonString = tmp;
			}
			if(i==lst.size()-1){
				jsonString+="}],";
			}
		}
		return jsonString;
	}
	public static String  toJsonString(List<DictData> lst){
		String tmp = toJson("",lst);
		tmp=tmp.substring(0,tmp.lastIndexOf(",")-3);
		tmp+="}]";
		return tmp;
	}
	
	public static void addEventCode(EventCode eventCode) {
	    if(eventCode != null) {
	        if(!eventCode.isActive() && eventCodeMap.containsKey(eventCode.getCode())) {
	            eventCodeMap.remove(eventCode.getCode());
	        } else {
	            eventCodeMap.put(eventCode.getCode(), eventCode);
	        }	        
	    }
	}
	
	public static void addEventCodes(Collection<EventCode> eventCodeList) {
	    if(eventCodeList != null && eventCodeList.size() > 0) {
	        for(EventCode eventCode : eventCodeList) {
	            addEventCode(eventCode);
	        }
	    }
	}
	
	public static EventCode getEventCode(Integer code) {
	   if(code == null) {
	       return null;
	   }
	   return eventCodeMap.get(code);
	}
	
	public static Collection<EventCode> getAllEventCodes() {
	    return new ArrayList<>(eventCodeMap.values());
	}
	
	public static String getDictTile(String category, String dictVal) {
	    if(!StringUtils.isEmpty(category)) {
	        List<DictData> dictDatas = getDictDatas(category);
	        if(dictDatas != null && dictDatas.size() > 0) {
	            for(DictData dictData : dictDatas) {
	                if(dictData.getDictValue().equals(dictVal)) {
	                    return dictData.getDescr();
	                }
	            }
	        }
	    }
	    return null;
	}
}
