package com.iycharge.server.admin.controller;

import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.admin.controller.helper.PageWrapper;
import com.iycharge.server.domain.common.utils.VeDate;
import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.account.Card;
import com.iycharge.server.domain.entity.admin.ManagerLog;
import com.iycharge.server.domain.entity.dict.CategoryConstant;
import com.iycharge.server.domain.entity.dict.DictData;
import com.iycharge.server.domain.entity.order.Order;
import com.iycharge.server.domain.entity.order.OrderStatus;
import com.iycharge.server.domain.entity.station.Station;
import com.iycharge.server.domain.entity.station.StationBean;
import com.iycharge.server.domain.entity.utils.Utils;
import com.iycharge.server.domain.entity.utils.outputExcel.ExcelUtil;
import com.iycharge.server.domain.service.ManagerLogService;
import com.iycharge.server.domain.service.OrderService;
import com.iycharge.server.domain.service.StationService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/orders/")
public class OrderController {
	@Autowired
	private OrderService orderService;

	@Autowired
	private ManagerLogService managerLogService;
	
	@Autowired
	private StationService stationService;
	
	private Order staticOrder = null;
	/**
	 * 查询所有
	 * 
	 * @param model
	 * @param order
	 * @param pageable
	 * @return
	 */
	@RequestMapping("/")
	public String searchAll(Model model,Order order,
			@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC, size=15) Pageable pageable,String flag) {
		PageWrapper<Order> page = null;
		Collection<Station> allStations =  EntityUtil.getAllStations();
		List<Map<String,String>> allStationInfos = new ArrayList<>();
		for(Station stat : allStations){
			Map<String,String> map = new HashMap<>();
			map.put("id", stat.getId().toString());
			map.put("name", stat.getName());
			map.put("code", stat.getCode());
			map.put("province", stat.getProvince());
			map.put("city", stat.getCity());
			map.put("district", stat.getDistrict());
			map.put("address", stat.getAddress());
			map.put("oper", stat.getOperator()!=null?stat.getOperator().getName():"");
			allStationInfos.add(map);
		}
		if(flag!=null&&staticOrder!=null){
			String[] fields=new String[]{"orderId","status","account","charger","createdAt","checkboxstationname","cardNoOrPhoneNum"};
		    String key="";
	         if(staticOrder.getAccount()!=null&&StringUtils.isNotEmpty(staticOrder.getAccount().getRealName())){
	         	key+="account.realName="+staticOrder.getAccount().getRealName()+"&";
	         }
	         if(staticOrder.getStatus() != null){
	          	key+="status="+staticOrder.getStatus().name()+"&";
	         }
	         if(staticOrder.getCharger()!=null&&StringUtils.isNotEmpty(staticOrder.getCharger().getName())){
		          	key+="charger.name="+staticOrder.getCharger().getName()+"&";
		         }
	         if(staticOrder.getStartAt()!=null){
	        	 staticOrder.setFormstartAt(VeDate.dateToStr(staticOrder.getStartAt()));
	         	key+="startAt="+staticOrder.getFormstartAt()+"&";
	         }
	         if(staticOrder.getEndAt()!=null){
	        	 staticOrder.setFormendAt(VeDate.dateToStr(staticOrder.getEndAt()));
	          	key+="endAt="+staticOrder.getFormendAt()+"&";
	          }
	         if(StringUtils.isNotEmpty(staticOrder.getOrderId())){
	          	key+="orderId="+staticOrder.getOrderId()+"&";
	          }
	         if(StringUtils.isNotEmpty(staticOrder.getCardNoOrPhoneNum())){
	        	 key+="cardNoOrPhoneNum="+staticOrder.getCardNoOrPhoneNum()+"&";
	         }
	         if(StringUtils.isNotEmpty(order.getCheckboxstationname())){
	        	 key+="checkboxstationname="+order.getCheckboxstationname()+"&";
	         }
	         if(!StringUtils.isEmpty(key)){
	     		key = key.substring(0,key.length()-1);
	     	  }	
			page = new PageWrapper<>(orderService.findAllSearch(fields,staticOrder,pageable), "/admin/orders/search?"+key);
			model.addAttribute("order", staticOrder);
			model.addAttribute("allStationInfos",allStationInfos);
		}else{
			page = new PageWrapper<>(orderService.findAll(pageable), "/admin/orders/");
			order.setStatus(null);
			model.addAttribute("order", order);
			model.addAttribute("allStationInfos",allStationInfos);
			model.addAttribute("checkboxstationname","");
			staticOrder = null;
		}
		model.addAttribute("page", page);
		return "admin/order/index";
	}

	 
	@RequestMapping(value="/search" )
    public String search(Model model,@ModelAttribute Order order,
    		@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC, size=15) Pageable pageable) {
		    String[] fields=new String[]{"orderId","status","account","charger","createdAt","checkboxstationname","cardNoOrPhoneNum"};
		    String key="";
		    Collection<Station> allStations =  EntityUtil.getAllStations();
			List<Map<String,String>> allStationInfos = new ArrayList<>();
			for(Station stat : allStations){
				Map<String,String> map = new HashMap<>();
				map.put("id", stat.getId().toString());
				map.put("name", stat.getName());
				map.put("code", stat.getCode());
				map.put("province", stat.getProvince());
				map.put("city", stat.getCity());
				map.put("district", stat.getDistrict());
				map.put("address", stat.getAddress());
				map.put("oper", stat.getOperator()!=null?stat.getOperator().getName():"");
				allStationInfos.add(map);
			} 
		    if(order.getAccount()!=null&&StringUtils.isNotEmpty(order.getAccount().getRealName())){
	         	key+="account.realName="+order.getAccount().getRealName()+"&";
	         }
	         if(order.getStatus() != null){
	          	key+="status="+order.getStatus().name()+"&";
	         }
	         if(order.getCharger()!=null&&StringUtils.isNotEmpty(order.getCharger().getName())){
		          	key+="charger.name="+order.getCharger().getName()+"&";
		         }
	         if(order.getStartAt()!=null){
	        	 order.setFormstartAt(VeDate.dateToStr(order.getStartAt()));
	         	key+="startAt="+order.getFormstartAt()+"&";
	         }
	         if(order.getEndAt()!=null){
	        	 order.setFormendAt(VeDate.dateToStr(order.getEndAt()));
	          	key+="endAt="+order.getFormendAt()+"&";
	          }
	         if(StringUtils.isNotEmpty(order.getOrderId())){
	          	key+="orderId="+order.getOrderId()+"&";
	          }
	         if(StringUtils.isNotEmpty(order.getCardNoOrPhoneNum())){
	        	 key+="cardNoOrPhoneNum="+order.getCardNoOrPhoneNum()+"&";
	         }
	         if(StringUtils.isNotEmpty(order.getCheckboxstationname())){
	        	 key+="checkboxstationname="+order.getCheckboxstationname()+"&";
	         }
	         if(!StringUtils.isEmpty(key)){
	     		key = key.substring(0,key.length()-1);
	     	  }	
		    staticOrder = order;
		    PageWrapper<Order> page = new PageWrapper<>(orderService.findAllSearch(fields,order,pageable), "/admin/orders/search?"+key);
	    		    model.addAttribute("page", page);
	    		    model.addAttribute("order", order);
	    		    model.addAttribute("allStationInfos",allStationInfos);
	    		    return "admin/order/index";
    }
	
	
	@RequestMapping("/findListAllStation")
	@ResponseBody
	public List<Map<String,String>>  findListAllStation(@ModelAttribute Station station) {
		/*String []fields = {"city","district","province","delStatus","code","name"};*/
		String []fields = {"city","district","province","delStatus"};
		/*if("1".equals(station.getStationType())){
			station.setName(station.getCodeAndName());
		}else if("2".equals(station.getStationType())){
			station.setCode(station.getCodeAndName());
		}*/
		station.setName(station.getCodeAndName());
		station.setCode(station.getCodeAndName());
		List<Station> list = stationService.findSearch(fields, station);
		List<Map<String,String>> allStationInfos = new ArrayList<>();
		if(list.size()>0&&list!=null){
			for(Station stat :list){
				Map<String,String> map = new HashMap<>();
				map.put("id", stat.getId().toString());
				map.put("name", stat.getName());
				map.put("code", stat.getCode());
				map.put("province", stat.getProvince());
				map.put("city", stat.getCity());
				map.put("district", stat.getDistrict());
				map.put("address", stat.getAddress());
				map.put("oper", stat.getOperator()!=null?stat.getOperator().getName():"");
				allStationInfos.add(map);
			}
		}
		return allStationInfos;
	}
	
	
	 @ModelAttribute("status")
	public List<DictData> types() {
	    List<DictData> lst = EntityUtil.getDictDatas(CategoryConstant.ORDER_STATUS);
	    return lst != null?lst:(new ArrayList<DictData>());		 
	}
	 
	 @ModelAttribute("paidFroms")
	public List<DictData> paidFroms() {
	    List<DictData> lst = EntityUtil.getDictDatas(CategoryConstant.PAID_FROM);
	    return lst != null?lst:(new ArrayList<DictData>());		 
	}

	/**
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/{orderId}")
	public String getDetil(@PathVariable("orderId") long id, Model model, Pageable pageable) {
		model.addAttribute("order", orderService.findById(id));
		return "admin/order/edit";
	}
	
	//excel导出
    @RequestMapping(value="/excle_output")
    public ResponseEntity<InputStreamResource> output(String inputOrderId,String inputChargerName,
    		String inputCusName,String status,String start,String end,String checkboxinput,
    		String inputCardOrPhoneNumber,String checkboxstationname,
    		HttpServletRequest request){
 	   Map<String,String> conditionMap = new HashMap<>();
 	   conditionMap.put("inputOrderId", inputOrderId);
 	   conditionMap.put("inputChargerName", inputChargerName);
 	   conditionMap.put("inputCusName", inputCusName);
 	   conditionMap.put("status", status);
 	   conditionMap.put("start", start);
 	   conditionMap.put("end", end);
 	   conditionMap.put("checkboxinput", checkboxinput);
 	   conditionMap.put("inputCardOrPhoneNumber", inputCardOrPhoneNumber);
 	   conditionMap.put("checkboxstationname", checkboxstationname);
 	   Order order = new Order();
 	   switch (status) {
		case "PAID":order.setStatus(OrderStatus.PAID);break;
		case "UNPAID":order.setStatus(OrderStatus.UNPAID);break;
		case "DONE":order.setStatus(OrderStatus.DONE);break;
		default:
			break;
		}
 	   	List<Order> cards = orderService.findByCondition(conditionMap, new Order());
        ExcelUtil eu = ExcelUtil.getInstance();
        String files = System.getProperty("user.dir");
        File fileChild = new File(files);
        File parentFile = fileChild.getParentFile();
        String path = parentFile.getPath()+File.separator+"excel"+File.separator+"orders"+Utils.dateChangeToNumber(new Date())+".xls";
        File file = new File(path);
        ManagerLog log = Utils.setLog(request, "ORDER", "EXPORT", "订单列表导出");
		log.setStatus(true);
		managerLogService.save(log);
        if(!file.exists()){
     	   if(!file.getParentFile().exists()){
     		   file.getParentFile().mkdirs();
     	   }
     	   try {
 			file.createNewFile();
 		   } catch (IOException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 				log.setStatus(false);
 				managerLogService.save(log);
 		   }
        }
        Map<String, String> map = new HashMap<>();
        map.put("title", "订单列表");
        map.put("total", cards.size()+" 条");
        map.put("date", Utils.getDate());
        OutputStream os=null;
        try {
 			os = new FileOutputStream(file);
 		} catch (FileNotFoundException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 			log.setStatus(false);
 			managerLogService.save(log);
 		} catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 			log.setStatus(false);
 			managerLogService.save(log);
 		}
        eu.exportObj2ExcelByTemplate(map, "/order-info-template.xls", os, cards, Order.class, true);
        FileSystemResource outfile = new FileSystemResource(path);  
        HttpHeaders headers = new HttpHeaders();  
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");  
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", outfile.getFilename()));  
        headers.add("Pragma", "no-cache");  
        headers.add("Expires", "0");  
        try {
 		return ResponseEntity  
 		           .ok()  
 		           .headers(headers)  
 		           .contentLength(outfile.contentLength())  
 		           .contentType(MediaType.parseMediaType("application/vnd-ms-excel"))  
 		           .body(new InputStreamResource(outfile.getInputStream()));
 		} catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 			log.setStatus(false);
 			managerLogService.save(log);
 		} 
        return null;
    }
	 
	
}
