package com.iycharge.server.admin.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.admin.controller.helper.PageWrapper;
import com.iycharge.server.domain.common.utils.VeDate;
import com.iycharge.server.domain.entity.account.Card;
import com.iycharge.server.domain.entity.admin.Manager;
import com.iycharge.server.domain.entity.admin.ManagerLog;
import com.iycharge.server.domain.entity.dict.CategoryConstant;
import com.iycharge.server.domain.entity.dict.DictData;
import com.iycharge.server.domain.entity.event.Event;
import com.iycharge.server.domain.entity.event.EventAuditLog;
import com.iycharge.server.domain.entity.event.EventCode;
import com.iycharge.server.domain.entity.event.EventCodeQueryParam;
import com.iycharge.server.domain.entity.event.EventQueryParam;
import com.iycharge.server.domain.entity.event.EventStatus;
import com.iycharge.server.domain.entity.order.Order;
import com.iycharge.server.domain.entity.order.OrderStatus;
import com.iycharge.server.domain.entity.utils.Utils;
import com.iycharge.server.domain.entity.utils.outputExcel.ExcelUtil;
import com.iycharge.server.domain.service.EventAuditLogService;
//import com.iycharge.server.domain.entity.order.Order;
import com.iycharge.server.domain.service.EventService;
import com.iycharge.server.domain.service.ManagerLogService;
import com.iycharge.server.domain.service.ManagerService;

/**
 * 告警事件
 * 
 * @author wgang
 */
@Controller
@RequestMapping("/admin/events/")
public class EventController {

	@Autowired
	private EventService eventService;
	
	@Autowired
	private ManagerService managerService;
	
	private static EventQueryParam staticqueryParam=null;
	@Autowired
	private EventAuditLogService eventAuditLogService;
	@Autowired
	private ManagerLogService managerLogService;
	@ModelAttribute("managers")
 	public List<Manager> types3() {
 	     return managerService.findAll();
 	}
	
	@InitBinder
    public void InitBinder(HttpServletRequest request,
            ServletRequestDataBinder binder) {
        // 不要删除下行注释!!! 将来"yyyy-MM-dd"将配置到properties文件中
        // SimpleDateFormat dateFormat = new
        // SimpleDateFormat(getText("date.format", request.getLocale()));
        /*SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(
                dateFormat, true));*/
    }
	
	@RequestMapping("/")
    public String searchAll(EventQueryParam queryParam,Model model,@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC, size=15) Pageable pageable,String flag) {	    			
		 PageWrapper<Event> page = null;
		 if(flag!=null&&staticqueryParam!=null){
			 page = new PageWrapper<>(eventService.find(staticqueryParam, pageable), "/admin/events/query");
			 model.addAttribute("queryParam", staticqueryParam);
		 }else{
			 page = new PageWrapper<>(eventService.findAll(pageable), "/admin/events/");
			 model.addAttribute("queryParam", new EventQueryParam());
			 staticqueryParam = null;
		 }
		 model.addAttribute("page", page);
        return "admin/event/index";
    }
	
	@RequestMapping(value="/query")
    public String query(EventQueryParam queryParam, Model model, @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC, size=15) Pageable pageable) {
	        PageWrapper<Event> page = new PageWrapper<>(eventService.find(queryParam, pageable), "/admin/events/query");
	        model.addAttribute("page", page);
	        model.addAttribute("queryParam", queryParam);
	        staticqueryParam = queryParam;
	        return  "admin/event/index";
	}

	
	@ModelAttribute("eventTypes")
	public List<DictData> asList(){
    	List<DictData> lst = EntityUtil.getDictDatas(CategoryConstant.EVENT_TYPE);
        return lst != null?lst:(new ArrayList<DictData>());
	} 
	
	@ModelAttribute("eventLevels")
	public List<DictData> asList1(){
    	List<DictData> lst = EntityUtil.getDictDatas(CategoryConstant.EVENT_LEVEL);
        return lst != null?lst:(new ArrayList<DictData>());
	}
	
	@RequestMapping("/{id}")
	public String find(Model model, @PathVariable long id) {
			Event event=eventService.findById(id);
			model.addAttribute("event", event);			
		return "admin/event/index";
	}
	/**
	 * 查看
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/check/{id}")
	public String check(Model model,  @PathVariable("id") Long id) {
		model.addAttribute("event", eventService.findById(id));
		return "admin/event/check";
	}
	/**
	 * 处理/反馈
	 */
	@RequestMapping(value="/handle/{id}")
	public String handle(Model model,  @PathVariable("id") Long id,HttpSession session) {
		Event event = eventService.findById(id);
		Manager manager = null;
		if(null!=event.getManager()){
			manager = event.getManager();
		}else{
			manager = (Manager)session.getAttribute("user");
		}
		String createAt =VeDate.getStringDate();
		model.addAttribute("eventAuditLog",new EventAuditLog());
		model.addAttribute("manager",manager);
		model.addAttribute("id",id);
		model.addAttribute("createAt",createAt);
		return "admin/event/handle";
	}
	/**
	 * 转发处理
	 */
	@Transactional
    @RequestMapping(value="/addeventAuditLog", method=RequestMethod.POST) 
	public String addeventAuditLog(@ModelAttribute  EventAuditLog eventAuditLog,
									@RequestParam("id")String id,
									@RequestParam("createAt")String createAt,
									@RequestParam("loginName1")String loginName1,
									@RequestParam("statu")String statu,
									@RequestParam("contents") String contents,
									@RequestParam("loginName") String loginName){
		Event event = eventService.findById(Long.parseLong(id));
		eventAuditLog.setContent(contents);
		Manager manager = managerService.findByLoginName(loginName1);
		eventAuditLog.setManager(manager);
		eventAuditLog.setStatus(EventStatus.valueOf(statu));
		eventAuditLog.setEvent(event);
		event.setEventStatus(EventStatus.valueOf(statu));
		List<EventAuditLog> eventAuditLogs = event.getEventAuditLogs();
		eventAuditLogs.add(eventAuditLog);
    	if (!"".equals(loginName)) {
    		Manager manager1 = managerService.findByLoginName(loginName);
    		event.setManager(manager1);
		}else {
			event.setManager(manager);
		}
    	eventAuditLogService.save(eventAuditLog);
    	eventService.save(event);
    	return "redirect:/admin/events/?flag=true";
    }
	//excel导出
    @RequestMapping(value="/excle_output")
    public ResponseEntity<InputStreamResource> output(String inputCusName,String inputChargerName,
    		String eventLevel,String eventType,String start,String end,HttpServletRequest request){
 	   Map<String,String> conditionMap = new HashMap<>();
 	   conditionMap.put("inputCusName", inputCusName);
 	   conditionMap.put("inputChargerName", inputChargerName);
 	   conditionMap.put("eventLevel", eventLevel);
 	   conditionMap.put("eventType", eventType);
 	   conditionMap.put("start", start);
 	   conditionMap.put("end", end);
 	   List<Event> cards = eventService.findByCondition(conditionMap, new Event());
        ExcelUtil eu = ExcelUtil.getInstance();
        String files = System.getProperty("user.dir");
        File fileChild = new File(files);
        File parentFile = fileChild.getParentFile();
        String path = parentFile.getPath()+File.separator+"excel"+File.separator+"events"+Utils.dateChangeToNumber(new Date())+".xls";
        ManagerLog log = Utils.setLog(request, "EVENT", "EXPORT", "告警列表导出");
		log.setStatus(true);
		managerLogService.save(log);
        File file = new File(path);
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
        map.put("title", "告警信息列表");
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
        eu.exportObj2ExcelByTemplate(map, "/event-info-template.xls", os, cards, Event.class, true);
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
