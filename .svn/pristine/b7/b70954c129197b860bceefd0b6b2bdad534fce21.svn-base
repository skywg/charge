package com.iycharge.server.admin.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.admin.controller.helper.PageWrapper;
import com.iycharge.server.domain.entity.admin.Manager;
import com.iycharge.server.domain.entity.admin.ManagerLog;
import com.iycharge.server.domain.entity.dict.CategoryConstant;
import com.iycharge.server.domain.entity.dict.DictData;
import com.iycharge.server.domain.entity.price.ParamSetting;
import com.iycharge.server.domain.entity.price.ParamTemplate;
import com.iycharge.server.domain.entity.price.ParamTemplateAttr;
import com.iycharge.server.domain.entity.price.ParamTemplateQueryParam;
import com.iycharge.server.domain.entity.price.Price;
import com.iycharge.server.domain.service.ManagerLogService;
import com.iycharge.server.domain.service.ParamTemplateAttrService;
import com.iycharge.server.domain.service.ParamTemplateService;

@Controller
@RequestMapping("/admin/paramTemplates")
public class ParamTemplateController {
	@Autowired
	private ParamTemplateService paramTemplateService;
	@Autowired
	private ParamTemplateAttrService paramTemplateAttrService;
	@Resource
	private ManagerLogService managerLogService;
	
	@ModelAttribute("statuss")
	public List<DictData> types() {
	    List<DictData> lst = EntityUtil.getDictDatas(CategoryConstant.TEMPLATE_STATUS);
	    return lst != null?lst:(new ArrayList<DictData>());	
	}
	@ModelAttribute("types")
	public List<DictData> types1() {
	    List<DictData> lst = EntityUtil.getDictDatas(CategoryConstant.PARAM_TYPE);
	    return lst != null?lst:(new ArrayList<DictData>());	
	}
	/**
	 * 列表展示
	 * @param model
	 * @param pageable
	 * @return
	 */
	@RequestMapping("/")
	public String index(HttpServletRequest request,Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 15) Pageable pageable) {
		 HttpSession session = request.getSession();
    	 session.removeAttribute("sessionParamTemplate");
		PageWrapper<ParamTemplate> page = new PageWrapper<>(paramTemplateService.findByDelStatus("normal", pageable),
				"/admin/paramTemplates/");
		model.addAttribute("page", page);

		return "admin/paramTemplate/index";
	}
	/**
	 * 分页多条件查询
	 * @param queryParam
	 * @param model
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value="/query")
    public String query(HttpServletRequest request, ParamTemplateQueryParam queryParam, Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size=15) Pageable pageable) {
		HttpSession session = request.getSession();
        if("1".equals(queryParam.getFlag())){
        	session.setAttribute("sessionParamTemplate", queryParam);
        }else{
        	ParamTemplateQueryParam ch = (ParamTemplateQueryParam)session.getAttribute("sessionParamTemplate");
       	 if(ch!=null){
       		queryParam = ch;
       	 }
        }    
		PageWrapper<ParamTemplate> page = new PageWrapper<>(paramTemplateService.find(queryParam, pageable), "/admin/paramTemplates/query");
	        model.addAttribute("page", page);
	        model.addAttribute("queryParam", queryParam);
	        return "admin/paramTemplate/index";
	}
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/del/{id}")
	public String del(@PathVariable("id") long id,HttpSession session,HttpServletRequest request) {
		ParamTemplate paramTemplate = paramTemplateService.findById(id);
		Manager manager = (Manager) session.getAttribute("user");
		ManagerLog log = new ManagerLog();
        log.setLoginName(manager.getLoginName());
        log.setRealName(manager.getRealname());
        log.setIp(request.getRemoteAddr());
        log.setLogTime(new Date());
        log.setLogModule("PARAMTEMPLATE");
        log.setLogType("DELETE");
        log.setParams("删除"+paramTemplate.getName());
		paramTemplate.setDelStatus("del");
		paramTemplate.setStatus("INVALID");
		paramTemplateService.save(paramTemplate);
		
        log.setStatus(true);
		managerLogService.save(log);
		return "redirect:/admin/paramTemplates/query";
	}
	/**
	 * 进入查看页面
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/check/{id}")
	public String check(@PathVariable("id") long id, Model model) {
		ParamTemplate paramTemplate = paramTemplateService.findById(id);
		List<ParamTemplateAttr> attrs = paramTemplateAttrService.findByTemplate(paramTemplate);
		String type =paramTemplate.getType();
		if (type.equals("PARAM")) {
			model.addAttribute("paramTemplate", paramTemplate);
			model.addAttribute("attrs", attrs);
		}else if (type.equals("PRICE")) {
			List<Price> prices =new ArrayList<>();
			setPrice(attrs,prices);
			model.addAttribute("paramTemplate", paramTemplate);
			model.addAttribute("prices", prices);
		}else if (type.equals("UPDATE")) {
			model.addAttribute("paramTemplate", paramTemplate);
		}
		return "admin/paramTemplate/check";
	}
	/**
	 * 进入编辑页面
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable("id") long id, Model model) {
		ParamTemplate paramTemplate = paramTemplateService.findById(id);
		List<ParamTemplateAttr> attrs=paramTemplateAttrService.findByTemplate(paramTemplate);
		String type =paramTemplate.getType();
		if (type.equals("PARAM")) {
			model.addAttribute("paramTemplate", paramTemplate);
			model.addAttribute("attrs", attrs);
		}else if (type.equals("PRICE")) {
			List<Price> prices =new ArrayList<>();
			setPrice(attrs,prices);
			model.addAttribute("paramTemplate", paramTemplate);
			model.addAttribute("prices", prices);
		}else if (type.equals("UPDATE")) {
			model.addAttribute("paramTemplate", paramTemplate);
		}
		return "admin/paramTemplate/edit";
	}
	/**
	 * 保存编辑页面
	 * 
	 * @param id
	 * @param model
	 * @param form
	 * @return
	 * @throws ParseException 
	 */
	@Transactional
	@RequestMapping("/save/{id}")
	public String save(@PathVariable("id") long id, Model model, 
			           @Valid ParamTemplate form ,
			           HttpSession session,
			           HttpServletRequest request,
			           @RequestParam("type")String type) throws ParseException {
		ParamTemplate paramTemplate = paramTemplateService.findById(id);
		paramTemplate.setName(form.getName());
		paramTemplate.setDescription(form.getDescription());
		paramTemplate.setStatus(form.getStatus());
		
		Manager manager = (Manager) session.getAttribute("user");
		ManagerLog log = new ManagerLog();
        log.setLoginName(manager.getLoginName());
        log.setRealName(manager.getRealname());
        log.setIp(request.getRemoteAddr());
        log.setLogTime(new Date());
        log.setLogModule("PARAMTEMPLATE");
        log.setLogType("EDIT");
        log.setParams("修改"+paramTemplate.getName());	
        
		if (type.equals("PARAM")) {
			List<ParamTemplateAttr> pp=paramTemplateAttrService.findByTemplate(paramTemplate);
			String ip = request.getParameter("ip");
			String port = request.getParameter("port");
			String interval = request.getParameter("interval");
			for(ParamTemplateAttr p:pp){
				if(p.getAttrName().equals("ip")){
					p.setAttrVal(ip);
				}else if(p.getAttrName().equals("port")){
					p.setAttrVal(port);
				}else if(p.getAttrName().equals("interval")){
					p.setAttrVal(interval);
				}
					paramTemplateAttrService.save(p);
      		    }
				paramTemplateService.save(paramTemplate);
				
				log.setStatus(true);
				managerLogService.save(log);
				
		}else if (type.equals("PRICE")) {
			List<ParamTemplateAttr> attrs=paramTemplateAttrService.findByTemplate(paramTemplate);
			List<Price> prices =new ArrayList<>();
			setPrice(attrs,prices);
			for(ParamTemplateAttr attr:attrs){
		       	  paramTemplateAttrService.delete(attr);
		         }
			// 保存电价信息
					String[] start = request.getParameterValues("startAt");
					String[] endAt = request.getParameterValues("endAt");
					String[] fee = request.getParameterValues("fee");
					String[] remark = request.getParameterValues("remark");
					String[] period = request.getParameterValues("price");
					int len=start.length;
			for (int i = 0; i < len; i++) {
	                	SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
	        			Date endDate=sdf.parse(endAt[i]);
	        			Date start1=null;
	        			if(len>1){
	        				if(i<len-1){
	        					start1=sdf.parse(start[i+1]);
	        					if(!start1.equals(endDate)){
	        						model.addAttribute("message","第二阶段模板开始时间必须等于第一阶段结束时间。");
	        						model.addAttribute("paramTemplate", paramTemplate);
	                				model.addAttribute("prices", prices);
	                				log.setStatus(false);
	                				managerLogService.save(log);
	                				return "admin/paramTemplate/edit";
	        					}
	        				}
	        				
	        			}	 	
				 }
					List<ParamTemplateAttr> list =new ArrayList<>();
	    			add(paramTemplate, "startAt", start,list);
	    			add(paramTemplate, "endAt", endAt,list);
	    			add(paramTemplate, "fee", fee,list);
	    			add(paramTemplate, "remark", remark,list);
	    			add(paramTemplate, "price", period,list);
	    			paramTemplate.setParamList(list);
	    			paramTemplateService.save(paramTemplate);
	    			
	    			log.setStatus(true);
    				managerLogService.save(log);
		}else if (type.equals("UPDATE")) {
			paramTemplateService.save(paramTemplate);
			
			log.setStatus(true);
			managerLogService.save(log);
		}
		 
         
		return "redirect:/admin/paramTemplates/query";
	}
	/**
	 * 添加模板
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/add")
	public String add(Model model) {
		model.addAttribute("paramTemplate", new ParamTemplate());
		model.addAttribute("attrs", new ParamTemplateAttr());
		return "admin/paramTemplate/add";
	}
	/**
	 * 保存新建模板
	 * 
	 * @param redirectAttributes
	 * @param template
	 * @return
	 * @throws ParseException 
	 */
	@Transactional
	@RequestMapping(value = "/addTemplate", method = RequestMethod.POST)
	public String save(RedirectAttributes redirectAttributes,
						@ModelAttribute  ParamTemplate  template,
						HttpSession session, Model model,
						HttpServletRequest request,
						@RequestParam("type")String type) throws ParseException {
		Manager manager = (Manager) session.getAttribute("user");
		ManagerLog log = new ManagerLog();
        log.setLoginName(manager.getLoginName());
        log.setRealName(manager.getRealname());
        log.setIp(request.getRemoteAddr());
        log.setLogTime(new Date());
        log.setLogModule("PARAMTEMPLATE");
        log.setLogType("ADD");
        log.setParams("添加"+template.getName());
		template.setCreator(manager.getRealname());
		template.setDelStatus("normal");
		if (type.equals("PARAM")) {
			String ip = request.getParameter("ip");
			String port = request.getParameter("port");
			String interval = request.getParameter("interval");
			paramTemplateService.save(template);
			List<ParamTemplateAttr> list =new ArrayList<>();
			add(template, "ip", ip,list);
			add(template, "port", port,list);
			add(template, "interval", interval,list);
			template.setParamList(list);
			paramTemplateService.save(template);
			log.setStatus(true);
			managerLogService.save(log);
		}else if (type.equals("PRICE")) {
			String[] start = request.getParameterValues("startAt");
			String[] endAt = request.getParameterValues("endAt");
			String[] fee = request.getParameterValues("fee");
			String[] remark = request.getParameterValues("remark");
			String[] period = request.getParameterValues("price");
			List<ParamTemplateAttr> list =new ArrayList<>();
			paramTemplateService.save(template);
			add(template, "startAt", start,list);
			add(template, "endAt", endAt,list);
			add(template, "fee", fee,list);
			add(template, "remark", remark,list);
			add(template, "price", period,list);
			template.setParamList(list);
			paramTemplateService.save(template);
			log.setStatus(true);
			managerLogService.save(log);
		}else if (type.equals("UPDATE")) {
			paramTemplateService.save(template);
			log.setStatus(true);
			managerLogService.save(log);
		}
		
        
		
		return "redirect:/admin/paramTemplates/query";
		
	}
	/*
	 * 参数信息添加
	 */
	 private void add(ParamTemplate  template,String name,String key,List<ParamTemplateAttr> list){
		 ParamTemplateAttr attr = new ParamTemplateAttr(); 
		 attr.setAttrName(name);
		 attr.setAttrVal(key);
		 attr.setTemplate(template);
		 paramTemplateAttrService.save(attr);
		 list.add(attr);
		 
	 }
	 /*
	  * 电价参数添加
	  */
	 private void add(ParamTemplate  template,String names,String[] keys,List<ParamTemplateAttr> list){
		 for (int i = 0; i < keys.length; i++) {
			  String key = keys[i];
			  String name =names+i;
			  ParamTemplateAttr attr = new ParamTemplateAttr(); 
			  attr.setAttrName(name);
			  attr.setAttrVal(key);
		      attr.setTemplate(template);
		      list.add(attr);
		}
	 }
	 /*
	  * 查看电价参数
	  */
	 private  static  void  setPrice(List<ParamTemplateAttr> attrs,List<Price> prices){
		 	int size = attrs.size()/5;
			for(int i = 0 ; i < size ; i++){
				Price periodPrice =new Price();
				periodPrice.setStartAt(getAttr("startAt"+i, attrs));
				periodPrice.setEndAt(getAttr("endAt"+i, attrs));
				periodPrice.setFee(getAttr("fee"+i, attrs));
				periodPrice.setPrice(getAttr("price"+i, attrs));
				periodPrice.setRemark(getAttr("remark"+i, attrs));
				prices.add(periodPrice);
			}
	 }
	 /*
	  * 设置查看电价参数
	  */
	 private static String getAttr(String name, List<ParamTemplateAttr> attrs){
		 for(ParamTemplateAttr attr : attrs){
			 if(name.equals(attr.getAttrName())){
				 return attr.getAttrVal();
			 }
		 }
		 return "";
	 }
	
}
