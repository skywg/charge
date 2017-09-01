package com.iycharge.server.admin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.domain.common.utils.VeDate;
import com.iycharge.server.domain.entity.IndexBean;
import com.iycharge.server.domain.entity.admin.Manager;
import com.iycharge.server.domain.entity.dict.CategoryConstant;
import com.iycharge.server.domain.entity.dict.DictData;
import com.iycharge.server.domain.entity.event.Event;
import com.iycharge.server.domain.service.DashboardService;
import com.iycharge.server.domain.service.EventService;

@Controller
@RequestMapping("/admin")
public class DashboardController {
	@Autowired
	private DashboardService dashboardService;
	@Autowired
	private EventService eventService;
    @RequestMapping("/")
    public String index(HttpSession session) {
        Manager manager = (Manager)session.getAttribute("user");
        /*if(manager.getLoginName().equalsIgnoreCase("admin")) {
            return "redirect:/admin/managers/";
        }*/
        return "admin/dashboard";
    }
    
    @RequestMapping("/countAllData")
    @ResponseBody  
    public Map countAllData(Model model,@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
    	Map map = new HashMap();
    	map.put("countUser", countUser(model));
    	map.put("countCharger", countCharger(model));
    	map.put("countElectricity", countUser(model));
    	map.put("countCharger", countElectricity(model));
    	map.put("countMoney", countMoney(model));
    	map.put("countChargerType", countChargerType(model));
    	map.put("countAllCharger", countAllCharger(model));
    	map.put("countAllOrderItem", countAllOrderItem(model));
    	map.put("countEvent", countEvent(model,pageable));
    	return map;
    	
    }
    
	@ModelAttribute("eventTypes")
    public List<DictData> eventTypes() {
    	List<DictData> lst = EntityUtil.getDictDatas(CategoryConstant.EVENT_TYPE);
        return lst != null?lst:(new ArrayList<DictData>());
    }
	@ModelAttribute("eventLevels")
    public List<DictData> eventLevels() {
    	List<DictData> lst = EntityUtil.getDictDatas(CategoryConstant.EVENT_LEVEL);
        return lst != null?lst:(new ArrayList<DictData>());
    }
	
    /**
     * 统计用户数量总数，本月，今天
     */
    @ModelAttribute("countUser")
    //@RequestMapping("/countUser")
    public Map<String,String> countUser(Model model) {
    	//model.addAttribute("charger", new Charger());
        return dashboardService.countUser();
    }
    
    /**
     * 统计充电次数总数，本月，今天
     */
    @ModelAttribute("countCharger")
    //@RequestMapping("/countCharger")
    public Map<String,String> countCharger(Model model) {
    	//model.addAttribute("charger", new Charger());
        return dashboardService.countCharger();
    }
    
    /**
     * 统计电量总数，本月，今天
     */
    @ModelAttribute("countElectricity")
    //@RequestMapping("/countElectricity")
    public Map<String,String> countElectricity(Model model) {
    	//model.addAttribute("charger", new Charger());
        return dashboardService.countElectricity();
    }
    
    /**
     * 统计金额总数，本月，今天
     */
    @ModelAttribute("countMoney")
   //@RequestMapping("/countMoney")
    public Map<String,String> countMoney(Model model) {
    	//model.addAttribute("charger", new Charger());
        return dashboardService.countMoney();
    }
    
    /**
     * 统计充电站
     */
    @RequestMapping("/countStationType")
    public String countStationType(Model model) {
    	//model.addAttribute("charger", new Charger());
        return "admin/charger/add";
    }
    
    /**
     * 统计充电桩，在用桩，离线桩，空闲桩，故障桩
     */
    @ModelAttribute("countChargerType")
    //@RequestMapping("/countChargerType")
    public Map<String,String> countChargerType(Model model) {
    	//model.addAttribute("charger", new Charger());
        return dashboardService.countChargerType();
    }
    
    /**
     * 获取地图充电桩数据
     */
    @ModelAttribute("countAllCharger")
    //@RequestMapping("/countAllCharger")
    public List<IndexBean> countAllCharger(Model model) {
        return dashboardService.countAllCharger();
    }
    
    /**
     * 获取图表时间段统计
     */
    @ModelAttribute("countAllOrderItem")
    //@RequestMapping("/countAllOrderItem")
    public Map countAllOrderItem(Model model) {
    	//model.addAttribute("charger", new Charger());
    	//t.startAt,t.degree,t.money
    	List<Object[]> list = dashboardService.findAllOrderItem();
    	//时间
    	Set set = new HashSet();
    	for(int i=0;i<24;i++){
    		set.add(i);
    	}
    	//电量
    	List setdatadl = new ArrayList();
    	for(int i=0;i<24;i++){
    		setdatadl.add(0);
    	}
    	//钱
    	List setdatamo = new ArrayList();
    	for(int i=0;i<24;i++){
    		setdatamo.add(0);
    	}
    	if(list!=null&&list.size()>0){
    		for(Object[] o:list){
        		Date date = VeDate.strToDateLong(o[0].toString());
        		int hours = date.getHours();
        		set.add(hours);
        		/*System.out.println(hours);
        		System.out.println(o[1].toString());
        		System.out.println(o[2].toString());*/
        	}
    	}
    	if(set!=null&&set.size()>0){
    		Iterator it = set.iterator();
    		while(it.hasNext()){
    			int hours = (int) it.next();
    			double dl =0d;
    			double mo =0d;
    			for(Object[] o:list){
            		Date date = VeDate.strToDateLong(o[0].toString());
            		int hour = date.getHours();
            		if(hours==hour){
            			dl+=Double.valueOf(o[1].toString());
            			mo+=Double.valueOf(o[2].toString());
            		}
            	}
    			setdatadl.set(hours, dl);
    			setdatamo.set(hours, mo);
    		}
    	}
    	Map map =new HashMap();
    	map.put("hour", set);
    	map.put("setdatadl", setdatadl);
    	map.put("setdatamo", setdatamo);
        return map;
    }
    
    /**
     * 统计用户数量总数，本月，今天
     */
    @ModelAttribute("countEvent")
    //@RequestMapping("/countUser")
    public Map<String,Event> countEvent(Model model,@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
    	Page<Event> page  = eventService.findAll(pageable);
    	List<Event> list = page.getContent();
    	Map<String,Event> map = new HashMap<String,Event>();
    	map.put("list0", null);
		map.put("list1", null);
    	if(list!=null&&list.size()>0){
    		map.put("list0", list.get(0));
    		map.put("list1", null);
    		if(list.size()>=2){
    			map.put("list1", list.get(1));
    		}
    	}
    	return map;
    }
   
}
