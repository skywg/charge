package com.iycharge.server.admin.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iycharge.server.admin.cache.RCharger;
import com.iycharge.server.admin.cache.RConnector;
import com.iycharge.server.ccu.msg.entity.ChargerRealtimeData;
import com.iycharge.server.domain.entity.IndexBean;
import com.iycharge.server.domain.entity.charger.Charger;
import com.iycharge.server.domain.entity.charger.ChargerStatus;
import com.iycharge.server.domain.entity.station.Station;
import com.iycharge.server.domain.entity.utils.RedisUtil;
import com.iycharge.server.domain.service.CarService;
import com.iycharge.server.domain.service.ChargerService;
import com.iycharge.server.domain.service.ContentService;
import com.iycharge.server.domain.service.DashboardService;
import com.iycharge.server.domain.service.EventService;
import com.iycharge.server.domain.service.FeedBackService;
import com.iycharge.server.domain.service.ReviewService;
import com.iycharge.server.domain.service.StationService;

@Controller
@RequestMapping("/css")
public class DashboardCssController {
	@Autowired
	private DashboardService dashboardService;
	@Autowired
	private StationService stationService;
	@Autowired
	private ChargerService chargerService;
	@Autowired
    private RedisUtil redisUtil;
	@Autowired
	private EventService eventService;
	@Autowired
    private FeedBackService feedBackService;
	@Autowired
	private ContentService contentService;
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private CarService carService;
	
	/**
     * 获取地图充电桩数据
     */
    @RequestMapping("/countAllCharger")
	@ResponseBody  
    public List<IndexBean> countAllCharger(Model model) {
        return dashboardService.countAllCharger();
    }
	
	/**
     * 统计充电桩，在用桩，离线桩，空闲桩，故障桩
     */
    @RequestMapping("/countChargerType")
    @ResponseBody  
    public Map<String,String> countChargerType(Model model) {
    	//model.addAttribute("charger", new Charger());
        return dashboardService.countChargerType();
    }
    /**
     * 统计实时电压数据
     */
    @RequestMapping("/countChargeringDetail")
    @ResponseBody  
    public Map countChargeringDetail(Model model,HttpServletRequest request) {
    	Map map = new HashMap();
    	List dylist = new ArrayList();
    	List dllist = new ArrayList();
    	List time = new ArrayList();
    	map.put("dl", 0);
    	map.put("gl", 0);
    	map.put("dy", 0);
    	map.put("dylist", dllist);
    	map.put("dllist", dllist);
    	map.put("time", time);
    	
    	String chargeNo = request.getParameter("chargeNo");
    	
    	RCharger rcharger = (RCharger)redisUtil.get(RedisUtil.PREFIX_CHARGER + chargeNo);
    	Date startAt = new Date();
    	if(rcharger != null) {
    	    List<RConnector> connList = rcharger.getConnectorList();
    	    if(connList != null && connList.size() >0) {
    	        RConnector conn = connList.get(0);
    	        if(conn.getChargingStartTime() != null) {
    	            startAt = conn.getChargingStartTime();
    	        }
    	    }
    	}
    	List<ChargerRealtimeData> list = dashboardService.findByChargeNo(chargeNo, startAt);
    	
    	if(list!=null&&list.size()>0){
    		ChargerRealtimeData ch = list.get(0);
    		//电池侧充电电压测量值
    		map.put("dy", ch.getOutputVoltage());
    		 //电池侧充电电流测量值
    		map.put("dl", ch.getOutputCurrent() * -1);
    		double dd = Double.valueOf(ch.getOutputCurrent()*ch.getOutputVoltage()*-1)/1000;
    		//功率
    		map.put("gl",dd );
    		int size = list.size();
    		for(int i =size-1;i>=0;i--){
    			dylist.add(list.get(i).getOutputVoltage());
    			dllist.add(list.get(i).getOutputCurrent() * -1);
    			time.add(list.get(i).getCreatedAt().toString());
    		}
    		map.put("dylist", dylist);
        	map.put("dllist", dllist);
        	map.put("time", time);
    	}
    	return map;
    }
    /**
     * 统计用户数量总数，本月，今天
     */
    @RequestMapping("/countChargerDetail")
    @ResponseBody  
    public Map countChargerDetail(Model model,HttpServletRequest request,@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
    	Map map = new HashMap();
    	map.put("coll", null);
    	//次数
    	map.put("num", 0);
    	//电量
		map.put("degree", 0);
    	map.put("CHARGING", 0);
		map.put("OFFLINE", 0);
		map.put("REPAIR", 0);
		map.put("IDLE", 0);
		//交流
		map.put("type01", 0);
		//直流
		map.put("type02", 0);
    	
    	//次数
		long num=0l;
    	double degree=0l;
    	String stationId = request.getParameter("stationId");
	   	 Station station = stationService.findById(Long.valueOf(stationId));
	   	 if(station!=null){
	   		 String stationname = station.getName();
	   		 map.put("stationname", stationname);
	   		 List<Charger> list = chargerService.findByStation(station);
	   		 if(list!=null&&list.size()>0){
	   			 Collection<String> keys = new HashSet<>();
	   			 for(Charger ch:list){
	   				 keys.add(RedisUtil.PREFIX_CHARGER + ch.getCode()); 
	   				 String object = dashboardService.chargerDegreeDay(ch.getId());
	   				 String[] dat = object.split(",");
	   				 if(dat[0]!=null&&!dat[0].equals("null")){
	   					num+=Long.valueOf(dat[0]);
	   				 }
	   				 if(dat[1]!=null&&!dat[1].equals("null")){
	   					degree +=Double.valueOf(dat[1]);
	   				 }
	   				 //List<Order> listOrder  =orderService.findByCharger(ch);
	   				/* if(listOrder!=null&&listOrder.size()>0){
	   					num+=listOrder.size();
	   					for(Order order:listOrder){
	   						degree += order.getDegree();
	   					}
	   				 }*/
	   			 }
	   			map.put("num", num);
	   			map.put("degree", degree);
	   			Collection<Object> coll = redisUtil.get(keys);
	   			Collection<RCharger> listnull = new ArrayList<RCharger>();
	   			listnull.add(null);
	   			if(coll!=null&&coll.size()>0){
	   				coll.removeAll(listnull);
	   			}
	   			if(coll!=null&&coll.size()>0){
	   				//充电中
	   				long CHARGING =0l;
	   				//离线
	   				long OFFLINE  =0l;
	   				//检修
	   				long REPAIR  =0l;
	   				//空闲
	   				long IDLE  =0l;
	   				//直流
	   				long type01 = 0l;
	   				//交流
	   				long type02 = 0l;
	   				for(Object item: coll){
	   				    RCharger rc = (RCharger)item;
	   					List<RConnector> listrc = rc.getConnectorList();
	   					if(listrc!=null&&listrc.size()>0){
	   						for(RConnector rConnector:listrc){
	   							//充电中
	   							if(ChargerStatus.CHARGING.equals(rConnector.getStatus())){
	   								CHARGING++;
	   							//离线
	   							}else if(ChargerStatus.OFFLINE.equals(rConnector.getStatus())){
	   								OFFLINE++;
	   							//检修	
	   							}else if(ChargerStatus.REPAIR.equals(rConnector.getStatus())){
	   								REPAIR++;
	   							//空闲
	   							}else if(ChargerStatus.IDLE.equals(rConnector.getStatus())){
	   								IDLE++;
	   							}
	   							// 接口类型(DC ： 直流 ， AC ： 交流)
	   							if("AC".equals(rConnector.getType())){
	   								type01++;
	   							}else if("DC".equals(rConnector.getType())){
	   								type02++;
	   							}
	   						}
	   					}
	   				}
	   				map.put("CHARGING", CHARGING);
	   				map.put("OFFLINE", OFFLINE);
	   				map.put("REPAIR", REPAIR);
	   				map.put("IDLE", IDLE);
	   				//交流
	   				map.put("type01", type01);
	   				//直流
	   				map.put("type02", type02);
	   			}
	   			
	   			/*if(coll!=null&&coll.size()>0){
	   				for(RCharger rc:coll){
	   					if(ChargerStatus.CHARGING.equals(rc.getChargerStatus())){
	   						List<RConnector> listrc = rc.getConnectorList();
		   					ObjectComparator mc = new ObjectComparator() ;  
		   				    Collections.sort(listrc, mc) ;
	   					}
	   				}
	   			}*/
	   			if(coll != null && coll.size() > 0) {
	   			    Collections.sort((List)coll, new Comparator<RCharger>(){
                        public int compare(RCharger o1, RCharger o2) {
                            if(o1 == null || o2 == null) {
                                return -1;
                            }
                            return o1.getCode().compareTo(o2.getCode());
                            //return Long.parseLong(o1.getCode())<Long.parseLong(o2.getCode()) ? -1 : 1;
                        }
	   			        
                    });
	   			}
	   			map.put("coll", coll);
	   		 }
	   	 }
	   	 return map;
    }
    
    
    /**
     * 统计用户数量总数，本月，今天
     */
    @RequestMapping("/countChargerDetailRedis")
    @ResponseBody  
    public Map countChargerDetailRedis(Model model,HttpServletRequest request,@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
    	Map map = new HashMap();
    	map.put("coll", null);
    	String stationId = request.getParameter("stationId");
	   	 Station station = stationService.findById(Long.valueOf(stationId));
	   	 if(station!=null){
	   		 List<Charger> list = chargerService.findByStation(station);
	   		 if(list!=null&&list.size()>0){
	   			 Collection<String> keys = new HashSet<>();
	   			 for(Charger ch:list){
	   				 keys.add(RedisUtil.PREFIX_CHARGER + ch.getCode()); 
	   			 }
	   			Collection<Object> coll = redisUtil.get(keys);
	   			if(coll != null && coll.size() > 0) {
                    Collections.sort((List)coll, new Comparator<Object>(){
                        public int compare(Object o1, Object o2) {
                            RCharger c1 = (RCharger)o1, c2 = (RCharger)o2;
                            return c1.getCode().compareTo(c2.getCode());
                        }
                        
                    });
                }
	   			map.put("coll", coll);
	   		 }
	   	 }
	   	 return map;
    }
    
    /**
     * 统计首页代办事项
     */
    @RequestMapping("/countNodo")
    @ResponseBody 
    public Map countNodo(Model model) {
    	Map map = new HashMap();
    	//告警处理
    	List eventcount = eventService.findByEventStatus();
    	map.put("eventcount", eventcount.size());
    	//用户处理反馈
    	List feedBackcount = feedBackService.findByStatus();
    	map.put("feedBackcount", feedBackcount.size());
    	//内容审核
    	List contentcount = contentService.findByStatus((byte) -1);
    	map.put("contentcount", contentcount.size());
    	//评价审核
    	List reviewcount = reviewService.findByStatus();
    	map.put("reviewcount", reviewcount.size());
    	//车主认证
    	List carcount = carService.findByCarIdentifyStatus();
    	map.put("carcount", carcount.size());
    	map.put("totalcount", eventcount.size()+carcount.size()
    	+feedBackcount.size()+contentcount.size()+reviewcount.size()
    	);
    	return map;
    }
}
