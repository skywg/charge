package com.iycharge.server.admin.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.admin.controller.helper.PageWrapper;
import com.iycharge.server.api.util.IDCreator;
import com.iycharge.server.domain.common.utils.ReflectField;
import com.iycharge.server.domain.common.utils.VeDate;
import com.iycharge.server.domain.entity.Image;
import com.iycharge.server.domain.entity.admin.Manager;
import com.iycharge.server.domain.entity.admin.ManagerLog;
import com.iycharge.server.domain.entity.content.Content;
import com.iycharge.server.domain.entity.dict.CategoryConstant;
import com.iycharge.server.domain.entity.dict.DictData;
import com.iycharge.server.domain.entity.feedback.FeedBack;
import com.iycharge.server.domain.entity.feedback.FeedBackAuditLog;
import com.iycharge.server.domain.entity.feedback.FeedBackStatus;
import com.iycharge.server.domain.entity.utils.Utils;
import com.iycharge.server.domain.service.FeedBackAuditLogService;
import com.iycharge.server.domain.service.FeedBackService;
import com.iycharge.server.domain.service.ManagerService;

@Controller
@RequestMapping("/admin/feedBacks")
public class FeedBackController {
    @Autowired
    private FeedBackService feedBackService;
    @Autowired
    private ManagerService managerService;
    @Autowired
    private FeedBackAuditLogService feedBackAuditLogService;
    private static FeedBack staticFeedBack=null;
    
    @ModelAttribute("channels")
 	public List<DictData> types() {
 	     return EntityUtil.getDictDatas(CategoryConstant.PROBLEM_CHANNEL);
 	}
    @ModelAttribute("categorys")
 	public List<DictData> types1() {
 	     return EntityUtil.getDictDatas(CategoryConstant.PROBLEM_TYPE);
 	}
    @ModelAttribute("statuss")
 	public List<FeedBackStatus> types2() {
 	     return FeedBackStatus.asList();
 	}
    @ModelAttribute("managers")
 	public List<Manager> types3() {
 	     return managerService.findAll();
 	}
    /**
	 * 跳转增加页面
	 */
	@RequestMapping(value="/add")
	public String add(Model model) {
	    model.addAttribute("feedBack", new FeedBack());
	    String seqNo = IDCreator.generator(IDCreator.BUS_FEED_BACK);
	    Date date =new Date();
	    SimpleDateFormat simpleDateFormat  =new SimpleDateFormat("yyyy-MM-dd");
	    String update =simpleDateFormat.format(date);
	    model.addAttribute("seqNo", seqNo);
	    model.addAttribute("update", update);
		return "admin/feedBack/add";
	}
	 @Transactional
	 @RequestMapping(value="/addfeedBack", method=RequestMethod.POST) 
	 public String addcontent(@ModelAttribute FeedBack feedBack,HttpServletRequest request) throws ParseException{
	 
	    	feedBack.setStatus(FeedBackStatus.SUSPENDING);
	    	feedBack.setValid(true);
	    	feedBackService.save(feedBack);
	    	
	    	return "redirect:/admin/feedBacks/?flag=true";
	    }
    
    /**
	 * 分页
	 */
	@RequestMapping("/")
    public String index(FeedBack feedBack,@PageableDefault(sort = "updatedAt", direction = Sort.Direction.DESC, size=15) Pageable pageable, Model model,String flag) {
		PageWrapper<FeedBack> page = null;
		if(flag!=null&&staticFeedBack!=null){
			String fields[] = {"updatedAt","channel","category","accountName","accountPhone","status"};
	    	String key="";
	    	for(String fieldName:fields){
	    		if ("updatedAt".equals(fieldName)) {
					if (staticFeedBack.getStartAt()!= null && staticFeedBack.getUpdatedAt() != null) {
						List<Date> date = new ArrayList<Date>();
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(staticFeedBack.getUpdatedAt());
						date.add(staticFeedBack.getStartAt());
						date.add(calendar.getTime());
						SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
						String startAt =sdf.format(date.get(0));
						String updatedAt =sdf.format(date.get(1));
						key+="startAt"+"="+startAt+"&"+"updatedAt"+"="+updatedAt+"&";
					}
				}else if("accountPhone".equals(fieldName)){
					Object o = ReflectField.getFieldValueByName(fieldName,staticFeedBack);
					if (o != null && !"".equals(o)) {
						key+="accountPhone"+"="+o.toString()+"&";
					}
				}else if("accountName".equals(fieldName)){
					Object o = ReflectField.getFieldValueByName(fieldName,staticFeedBack);
					if (o != null && !"".equals(o)) {
						key+="accountName"+"="+o.toString()+"&";
					}
				}else if("channel".equals(fieldName)){
					Object o = ReflectField.getFieldValueByName(fieldName,staticFeedBack);
					if (o!=null&&!"".equals(o)) {
						key+="channel"+"="+o.toString()+"&";
					}
				}else if("category".equals(fieldName)){
					Object o = ReflectField.getFieldValueByName(fieldName,staticFeedBack);
					if (o!=null&&!"".equals(o)) {
						key+="category"+"="+o.toString()+"&";
					}
				}else if("status".equals(fieldName)){
					Object o = ReflectField.getFieldValueByName(fieldName,staticFeedBack);
					if (o!=null&&!"".equals(o)) {
						key+="status"+"="+o.toString()+"&";
					}
				}
	    		
			}
	    	if(!StringUtils.isEmpty(key)){
	    		key = key.substring(0,key.length()-1);
	    	}
	    	page = new PageWrapper<>(feedBackService.findAllSearch(fields,staticFeedBack,pageable), "/admin/feedBacks/research?"+key);
	    	model.addAttribute("feedBack", staticFeedBack);
		}else{
			page = new PageWrapper<>(feedBackService.findAll(pageable),"/admin/feedBacks/");
			model.addAttribute("feedBack", feedBack);
			staticFeedBack = null;
		}
		model.addAttribute("page", page);
		
		return "admin/feedBack/index";
	}
	/**
	 * 多条件查询
	 * @param model
	 * @param feedBack
	 * @param pageable
	 * @return
	 */
	@RequestMapping("/research")
	public String search(Model model, @ModelAttribute FeedBack feedBack,@PageableDefault(sort = "updatedAt", direction = Sort.Direction.DESC, size=15) Pageable pageable) {
	    	String fields[] = {"updatedAt","channel","category","accountName","accountPhone","status"};
	    	String key="";
	    	for(String fieldName:fields){
	    		if ("updatedAt".equals(fieldName)) {
					if (feedBack.getStartAt()!= null && feedBack.getUpdatedAt() != null) {
						List<Date> date = new ArrayList<Date>();
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(feedBack.getUpdatedAt());
						date.add(feedBack.getStartAt());
						date.add(calendar.getTime());
						SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
						String startAt =sdf.format(date.get(0));
						String updatedAt =sdf.format(date.get(1));
						key+="startAt"+"="+startAt+"&"+"updatedAt"+"="+updatedAt+"&";
					}
				}else if("accountPhone".equals(fieldName)){
					Object o = ReflectField.getFieldValueByName(fieldName,feedBack);
					if (o != null && !"".equals(o)) {
						key+="accountPhone"+"="+o.toString()+"&";
					}
				}else if("accountName".equals(fieldName)){
					Object o = ReflectField.getFieldValueByName(fieldName,feedBack);
					if (o != null && !"".equals(o)) {
						key+="accountName"+"="+o.toString()+"&";
					}
				}else if("channel".equals(fieldName)){
					Object o = ReflectField.getFieldValueByName(fieldName,feedBack);
					if (o!=null&&!"".equals(o)) {
						key+="channel"+"="+o.toString()+"&";
					}
				}else if("category".equals(fieldName)){
					Object o = ReflectField.getFieldValueByName(fieldName,feedBack);
					if (o!=null&&!"".equals(o)) {
						key+="category"+"="+o.toString()+"&";
					}
				}else if("status".equals(fieldName)){
					Object o = ReflectField.getFieldValueByName(fieldName,feedBack);
					if (o!=null&&!"".equals(o)) {
						key+="status"+"="+o.toString()+"&";
					}
				}
	    		
			}
	    	if(!StringUtils.isEmpty(key)){
	    		key = key.substring(0,key.length()-1);
	    	}
	    	PageWrapper<FeedBack> page = new PageWrapper<>(feedBackService.findAllSearch(fields,feedBack,pageable), "/admin/feedBacks/research?"+key);
	    	staticFeedBack = feedBack;
	    	model.addAttribute("page", page);
	    	model.addAttribute("feedBack", feedBack);
	        return "admin/feedBack/index";
	  }
	/**
	 * 查看
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/check/{id}")
	public String check(Model model,  @PathVariable("id") Long id) {
		model.addAttribute("feedBack", feedBackService.findById(id));
		return "admin/feedBack/check";
	}
	/**
	 * 编辑
	 */
	@RequestMapping(value="/edit/{id}")
	public String edit(Model model,  @PathVariable("id") Long id) {
		model.addAttribute("feedBack", feedBackService.findById(id));
		return "admin/feedBack/edit";
	}
	/**
	 * 更新
	 */
	@Transactional
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable("id") Long id,@Valid FeedBack form,@RequestParam("Valid") String isValid) {
		FeedBack feedBack = feedBackService.findById(id);
    	setBean(form, feedBack,isValid);
    	return "redirect:/admin/feedBacks/?flag=true";        	        
    }
	private void setBean(FeedBack from,FeedBack feedBack,String isValid){
		feedBack.setCategory(from.getCategory());
		if (isValid.equals("1")) {
			feedBack.setValid(true);
		}else {
			feedBack.setStatus(FeedBackStatus.CLOSED);;
			feedBack.setValid(false);
		}
		
		feedBackService.save(feedBack);
	}
	/**
	 * 处理/反馈
	 */
	@RequestMapping(value="/handle/{id}")
	public String handle(Model model,  @PathVariable("id") Long id,HttpSession session) {
		//List<FeedBackAuditLog> feedBackAuditLog =feedBackService.findById(id).getAuditLogList();
		Manager manager = (Manager)session.getAttribute("user");
		String createAt =VeDate.getStringDate();
		model.addAttribute("feedBackAuditLog",new FeedBackAuditLog());
		model.addAttribute("manager",manager);
		model.addAttribute("id",id);
		model.addAttribute("createAt",createAt);
		return "admin/feedBack/handle";
	}
	/**
	 * 转发处理
	 */
	@Transactional
    @RequestMapping(value="/addFeedBackAuditLog", method=RequestMethod.POST) 
	public String addFeedBackAuditLog(@ModelAttribute FeedBackAuditLog feedBackAuditLog,
									@RequestParam("id")String id,
									@RequestParam("createAt")String createAt,
									@RequestParam("loginName1")String loginName1,
									@RequestParam("statu")String statu,
									@RequestParam("contents") String contents,
									@RequestParam("loginName") String loginName){
		FeedBack feedBack = feedBackService.findById(Long.parseLong(id));
		feedBackAuditLog.setContent(contents);
		Manager manager = managerService.findByLoginName(loginName1);
		feedBackAuditLog.setManager(manager);
		feedBackAuditLog.setStatus(FeedBackStatus.valueOf(statu));
		feedBackAuditLog.setFeedback(feedBack);
		feedBack.setStatus(FeedBackStatus.valueOf(statu));
		List<FeedBackAuditLog> feedBackAuditLogs = feedBack.getAuditLogList();
		feedBackAuditLogs.add(feedBackAuditLog);
    	if (!"".equals(loginName)) {
    		Manager manager1 = managerService.findByLoginName(loginName);
    		feedBack.setManager(manager1);
		}else {
			feedBack.setManager(manager);
		}
    	feedBackAuditLogService.save(feedBackAuditLog);
    	feedBackService.save(feedBack);

    	return "redirect:/admin/feedBacks/?flag=true";
    }
}
