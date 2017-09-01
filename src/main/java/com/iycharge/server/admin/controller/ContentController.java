package com.iycharge.server.admin.controller;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
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
import com.iycharge.server.domain.common.utils.ReflectField;
import com.iycharge.server.domain.entity.Image;
import com.iycharge.server.domain.entity.admin.Manager;
import com.iycharge.server.domain.entity.admin.ManagerLog;
import com.iycharge.server.domain.entity.content.Content;
import com.iycharge.server.domain.entity.dict.CategoryConstant;
import com.iycharge.server.domain.entity.dict.DictData;
import com.iycharge.server.domain.entity.station.Station;
import com.iycharge.server.domain.entity.utils.Utils;
import com.iycharge.server.domain.service.ContentService;
import com.iycharge.server.domain.service.ManagerLogService;
@Controller
@RequestMapping("/admin/contents")
public class ContentController {
	@Autowired
	private ContentService contentService;
	@Resource
	private ManagerLogService managerLogService;
	private static Content staticContent = null;
	@ModelAttribute("classifications")
	public List<DictData> types() {
	     return EntityUtil.getDictDatas(CategoryConstant.CONTENT_CLASSIFICATION);
	 }
	@ModelAttribute("contentTypes")
	public List<DictData> type() {
	     return EntityUtil.getDictDatas(CategoryConstant.CONTENT_TYPE);
	 }
	/**
	 * 分页
	 */
	@RequestMapping("/")
    public String index(Content content,@PageableDefault(sort = "id", direction = Sort.Direction.DESC, size=15) Pageable pageable, Model model,String flag) {
		PageWrapper<Content> page = null;
		if(flag!=null&&staticContent!=null){
			String field[] = {"releasedAt","title","classification","transientStatus"};
	    	String key="";
	    	for(String fieldName:field){
	    		if ("releasedAt".equals(fieldName)) {
					if (staticContent.getStartAt()!= null && staticContent.getReleasedAt() != null) {
						List<Date> date = new ArrayList<Date>();
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(staticContent.getReleasedAt());
						date.add(staticContent.getStartAt());
						date.add(calendar.getTime());
						SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
						String startAt =sdf.format(date.get(0));
						String releasedAt =sdf.format(date.get(1));
						key+="startAt"+"="+startAt+"&"+"releasedAt"+"="+releasedAt+"&";
					}
				}else {
					Object o = ReflectField.getFieldValueByName(fieldName, staticContent);
					if(o!=null&&!"".equals(o)){
						key+=fieldName+"="+o.toString()+"&";
					}
				}			
			}
	    	if(!StringUtils.isEmpty(key)){
	    		key = key.substring(0,key.length()-1);
	    	}
	        page = new PageWrapper<>(contentService.findAllSearch(field,staticContent,pageable), "/admin/contents/search?"+key);
	        model.addAttribute("page", page);
	        model.addAttribute("content", staticContent);
		}else{
			page = new PageWrapper<>(contentService.findAll(pageable),"/admin/contents/");
			model.addAttribute("page", page);
			model.addAttribute("content", content);
			staticContent = null;
		}
		return "admin/content/index";
	}
	/**
	 * 条件查询分页
	 */
	@RequestMapping("/search")
    public String search(Model model, @ModelAttribute Content content,
    		@PageableDefault(sort = "id", direction = Sort.Direction.DESC, size=15) Pageable pageable) {
    	String field[] = {"releasedAt","title","classification","transientStatus"};
    	String key="";
    	for(String fieldName:field){
    		if ("releasedAt".equals(fieldName)) {
				if (content.getStartAt()!= null && content.getReleasedAt() != null) {
					List<Date> date = new ArrayList<Date>();
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(content.getReleasedAt());
					date.add(content.getStartAt());
					date.add(calendar.getTime());
					SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
					String startAt =sdf.format(date.get(0));
					String releasedAt =sdf.format(date.get(1));
					key+="startAt"+"="+startAt+"&"+"releasedAt"+"="+releasedAt+"&";
				}
			}else {
				Object o = ReflectField.getFieldValueByName(fieldName, content);
				if(o!=null&&!"".equals(o)){
					key+=fieldName+"="+o.toString()+"&";
				}
			}			
		}
    	if(!StringUtils.isEmpty(key)){
    		key = key.substring(0,key.length()-1);
    	}
        PageWrapper<Content> page = new PageWrapper<>(contentService.findAllSearch(field,content,pageable), "/admin/contents/search?"+key);
        staticContent = content;
        model.addAttribute("page", page);
        model.addAttribute("content", content);
        return "admin/content/index";
    }
	/**
	 * 查看
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/check/{id}")
	public String check(Model model,  @PathVariable("id") Long id) {
		model.addAttribute("content", contentService.findById(id));
		model.addAttribute("image", new Image());
		return "admin/content/check";
	}
	/**
	 * 提交
	 */
	@RequestMapping(value="/submit/{id}")
	public String submit(Model model,  @PathVariable("id") Long id) {
		Content content = contentService.findById(id);
		content.setStatus(Byte.parseByte("0"));
		contentService.save(content);
		return "redirect:/admin/contents/?flag=true";
	}
	/**
	 * 审核
	 */
	@RequestMapping(value="/review/{id}")
	public String review(Model model,  @PathVariable("id") Long id,HttpServletRequest request) {
		Content content = contentService.findById(id);
		content.setStatus(Byte.parseByte("1"));
		contentService.save(content);
		ManagerLog log = Utils.setLog(request, "CONTENT", "AUDIT", "审核"+content.getTitle());
    	log.setStatus(true);
		managerLogService.save(log);
		return "redirect:/admin/contents/?flag=true";
	}
	/**
	 * 发布
	 */
	@RequestMapping(value="/release/{id}")
	public String release(Model model,  @PathVariable("id") Long id,HttpServletRequest request) {
		Content content = contentService.findById(id);
		content.setStatus(Byte.parseByte("2"));
		contentService.save(content);
		ManagerLog log = Utils.setLog(request, "CONTENT", "PUBLISH", "发布"+content.getTitle());
    	log.setStatus(true);
		managerLogService.save(log);
		return "redirect:/admin/contents/?flag=true";
	}
	/**
	 * 下架
	 */
	@RequestMapping(value="/drop/{id}")
	public String drop(Model model,  @PathVariable("id") Long id,HttpServletRequest request) {
		Content content = contentService.findById(id);
		content.setStatus(Byte.parseByte("-1"));
		contentService.save(content);
		ManagerLog log = Utils.setLog(request, "CONTENT", "OFFSHELVE", "下架"+content.getTitle());
    	log.setStatus(true);
		managerLogService.save(log);
		return "redirect:/admin/contents/?flag=true";
	}
	/**
	 * 编辑
	 */
	@RequestMapping(value="/edit/{id}")
	public String edit(Model model,  @PathVariable("id") Long id) {
		model.addAttribute("content", contentService.findById(id));
		model.addAttribute("image", new Image());
		return "admin/content/edit";
	}
	/**
	 * 跳转增加页面
	 */
	@RequestMapping(value="/add")
	public String add(Model model) {
	    model.addAttribute("content", new Content());
		model.addAttribute("image", new Image());
		return "admin/content/add";
	}
	/**
	 * 更新
	 */
	@Transactional
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable("id") Long id,@Valid Content form,HttpSession session,HttpServletRequest request) {
		Manager manager=(Manager) session.getAttribute("user");
		Content content = contentService.findById(id);
		content.setName(manager.getRealname());
    	setBean(form, content);
    	ManagerLog log = Utils.setLog(request, "CONTENT", "EDIT", "修改"+content.getTitle());
    	log.setStatus(true);
		managerLogService.save(log);
    	return "redirect:/admin/contents/?flag=true";        	        
    }
	
    /**
	 * 增加 
	 */
    @Transactional
    @RequestMapping(value="/addcontent", method=RequestMethod.POST) 
    public String addcontent(@ModelAttribute Content content,HttpSession session,HttpServletRequest request){
    	Manager manager = (Manager)session.getAttribute("user");
    	content.setName(manager.getRealname());
    	content.setStatus(Byte.parseByte("-1"));
    	contentService.save(content);
    	ManagerLog log = Utils.setLog(request, "CONTENT", "ADD", "添加"+content.getTitle());
    	log.setStatus(true);
		managerLogService.save(log);
    	return "redirect:/admin/contents/?flag=true";
    }
    
	
	private void setBean(Content from,Content content){
		content.setClassification(from.getClassification());
		content.setCreatedAt(new Date());
		content.setDescription(from.getDescription());
		content.setReleasedAt(from.getReleasedAt());
		content.setValidAt(from.getValidAt());
		content.setKeyword(from.getKeyword());
		content.setTitle(from.getTitle());
		content.setSlug(from.getSlug());
		content.setRemark(from.getRemark());
		content.setStatus(from.getStatus());
		content.setText(from.getText());
	}
}
