package com.iycharge.server.admin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.admin.controller.helper.PageWrapper;
import com.iycharge.server.domain.entity.dict.CategoryConstant;
import com.iycharge.server.domain.entity.dict.DictData;
import com.iycharge.server.domain.entity.event.EventCode;
import com.iycharge.server.domain.entity.event.EventCodeQueryParam;
import com.iycharge.server.domain.service.EventCodeService;

@Controller
@RequestMapping("/admin/eventCodes")
public class EventCodeController {
		@Autowired
		private EventCodeService eventCodeService;
		
		private static EventCodeQueryParam staticQueryParam = null;
		
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
		/**
		 * 列表页面
		 * @param model
		 * @param pageable
		 * @return
		 */
		@RequestMapping("/")
	    public String searchAll(Model model,@PageableDefault(sort = "code", direction = Sort.Direction.ASC, size = 15) Pageable pageable,String flag) {	    			
			 PageWrapper<EventCode> page = null;
			 
			 if(flag!=null&&staticQueryParam!=null){
				 page = new PageWrapper<>(eventCodeService.find(staticQueryParam, pageable), "/admin/eventCodes/query");
				 model.addAttribute("queryParam", staticQueryParam); 
			 }else{
				 page = new PageWrapper<>(eventCodeService.findAll(pageable), "/admin/eventCodes/");
				 model.addAttribute("queryParam", new EventCodeQueryParam()); 
				 staticQueryParam = null;
			 }
			 model.addAttribute("page", page); 	
	        return "admin/event/eventCode/index";
	    }
		@RequestMapping(value="/query", method=RequestMethod.POST)
	    public String query( EventCodeQueryParam queryParam, Model model, @PageableDefault(sort = "code", direction = Sort.Direction.ASC, size = 15) Pageable pageable) {
		        PageWrapper<EventCode> page = new PageWrapper<>(eventCodeService.find(queryParam, pageable), "/admin/eventCodes/query");
		        model.addAttribute("page", page);
		        model.addAttribute("queryParam", queryParam);
		        staticQueryParam = queryParam;
		        return "admin/event/eventCode/index";
		}
		/**
		 * 查看页面
		 * @param model
		 * @param id
		 * @return
		 */
		@RequestMapping(value="/check/{code}")
		public String check(Model model,  @PathVariable("code") int code) {
			model.addAttribute("eventCode", eventCodeService.findByEventCode(code));
			return "admin/event/eventCode/check";
		}
		/**
		 * 编辑
		 */
		@RequestMapping(value="/edit/{code}")
		public String edit(Model model,  @PathVariable("code") int code) {
			model.addAttribute("eventCode", eventCodeService.findByEventCode(code));
			return "admin/event/eventCode/edit";
		}
		/**
		 * 更新
		 */
		@Transactional
	    @RequestMapping(value = "/update/{code}", method = RequestMethod.POST)
	    public String update(Model model,@PathVariable("code") String code,@Valid EventCode form,BindingResult result, RedirectAttributes redirectAttributes
	    		,@RequestParam("isActive") String isActive) {
			EventCode eventCode = eventCodeService.findByEventCode(Integer.parseInt(code));
			
			
			if (result.hasErrors()) {
	            redirectAttributes.addFlashAttribute("failed", "编辑警告码时发生错误，请检查后重新尝试。");
	            return "admin/eventCodes/edit";
	        }
	    	setBean(form, eventCode,isActive);
	    	return "redirect:/admin/eventCodes/?flag=true";        	        
	    }
		private void setBean(EventCode from,EventCode eventCode,String isActive){
			if (isActive.equals("1")) {
				eventCode.setActive(true);
			}else {
				eventCode.setActive(false);
			}
			eventCode.setDecription(from.getDecription());
			eventCode.setEventLevel(from.getEventLevel());
			eventCode.setEventType(from.getEventType());
			eventCode.setRemark(from.getRemark());
			eventCodeService.save(eventCode);
		}
		 /**
		 * 增加 
		 */
	    @Transactional
	    @RequestMapping(value="/addEventCode", method=RequestMethod.POST) 
	    public String addcontent(Model model,@ModelAttribute EventCode eventCode,BindingResult result, RedirectAttributes redirectAttributes
	    		,@RequestParam("isActive") String isActive){
	    	EventCode eventCode2 =eventCodeService.findByEventCode(eventCode.getCode());
	    	if (result.hasErrors()) {
	            redirectAttributes.addFlashAttribute("failed", "添加警告码时发生错误，请检查后重新尝试。");
	            return "admin/event/eventCode/add";
	        }
	    	if (eventCode2!=null) {
	    		model.addAttribute("failed", "故障编码重复！");
	    		model.addAttribute("eventCode", eventCode);
	    		return "admin/event/eventCode/add";
			}
	    	if (isActive.equals("1")) {
				eventCode.setActive(true);
			}else {
				eventCode.setActive(false);
			}
	    	eventCodeService.save(eventCode);
	    	return "redirect:/admin/eventCodes/?flag=true";
	    }
	    /**
		 * 跳转增加页面
		 */
		@RequestMapping(value="/add")
		public String add(Model model) {
		    model.addAttribute("eventCode", new EventCode());
			return "admin/event/eventCode/add";
		}
}
