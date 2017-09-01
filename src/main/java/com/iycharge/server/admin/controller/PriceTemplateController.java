/*package com.iycharge.server.admin.controller;

import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.admin.controller.helper.PageWrapper;
import com.iycharge.server.domain.entity.admin.Manager;
import com.iycharge.server.domain.entity.dict.CategoryConstant;
import com.iycharge.server.domain.entity.dict.DictData;
import com.iycharge.server.domain.entity.operator.Operator;
import com.iycharge.server.domain.entity.price.ParamTemplate;
import com.iycharge.server.domain.entity.price.ParamTemplateAttr;
import com.iycharge.server.domain.entity.price.Price;
import com.iycharge.server.domain.entity.price.PriceTemplate;
import com.iycharge.server.domain.entity.price.PriceTemplateQueryParam;
import com.iycharge.server.domain.entity.price.TemplateStatus;
import com.iycharge.server.domain.service.ParamTemplateAttrService;
import com.iycharge.server.domain.service.PeriodPriceService;
import com.iycharge.server.domain.service.PriceTemplateService;

import org.eclipse.jetty.util.statistic.SampleStatistic;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin/priceTemplates")
public class PriceTemplateController {
	@Autowired
	private PriceTemplateService priceTemplateService;

	
	@Autowired
	private ParamTemplateAttrService paramTemplateAttrService;
	
	
	 * @Autowired
	   private OperatorService operatorService;
	

	private static final String TEMPLATE_INDEX_FILE = "admin/price/index";

	private static List<Price> prices;
	@ModelAttribute("types")
	public List<DictData> types() {
	    List<DictData> lst = EntityUtil.getDictDatas(CategoryConstant.TEMPLATE_STATUS);
	    return lst != null?lst:(new ArrayList<DictData>());	
	}

	@ModelAttribute("operators")
	public List<Operator> operators() {
		return operatorService.findListAll();
	}

	@RequestMapping("/")
	public String index(Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		PageWrapper<PriceTemplate> page = new PageWrapper<>(priceTemplateService.findAll(pageable),
				"/admin/priceTemplates/");
		model.addAttribute("page", page);

		return TEMPLATE_INDEX_FILE;
	}

	@RequestMapping(value="/query", method=RequestMethod.POST)
    public String query( PriceTemplateQueryParam queryParam, Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
	        PageWrapper<PriceTemplate> page = new PageWrapper<>(priceTemplateService.find(queryParam, pageable), "/admin/priceTemplates/query");
	        model.addAttribute("page", page);
	        model.addAttribute("queryParam", queryParam);
	        return "admin/price/index";
	}

	*//**
	 * 删除用户
	 * 
	 * @param loginName
	 * @param model
	 * @return
	 *//*
	@RequestMapping("/del/{id}")
	public String del(@PathVariable("id") long id) {
		PriceTemplate template = priceTemplateService.findById(id);
		List<ParamTemplateAttr> attrs = paramTemplateAttrService.findByPriceTemplate(template);
		for(ParamTemplateAttr attr:attrs){
	       	  paramTemplateAttrService.delete(attr);
	         }
		priceTemplateService.del(id);
		return "redirect:/admin/priceTemplates/";
	}

	*//**
	 * 进入查看页面
	 * 
	 * @param id
	 * @param model
	 * @return
	 *//*
	@RequestMapping("/check/{id}")
	public String check(@PathVariable("id") long id, Model model) {
		PriceTemplate priceTemplate = priceTemplateService.findById(id);
		//List<PeriodPrice> prices = periodPriceService.findByTemplate(template);
		List<ParamTemplateAttr> attrs = paramTemplateAttrService.findByPriceTemplate(priceTemplate);
		List<Price> prices =new ArrayList<>();
		setPrice(attrs,prices);
		model.addAttribute("priceTemplate", priceTemplate);
		model.addAttribute("prices", prices);
		return "admin/price/check";
	}

	*//**
	 * 退出查看页面
	 * 
	 * @param id
	 * @param model
	 * @return
	 *//*
	@RequestMapping("/exit")
	public String exit() {
		return "redirect:admin/priceTemplates/";
	}

	*//**
	 * 进入编辑页面
	 * 
	 * @param id
	 * @param model
	 * @return
	 *//*
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable("id") long id, Model model) {
		PriceTemplate priceTemplate = priceTemplateService.findById(id);
		List<ParamTemplateAttr> attrs = paramTemplateAttrService.findByPriceTemplate(priceTemplate);
		List<Price> prices =new ArrayList<>();
		setPrice(attrs,prices);
		model.addAttribute("priceTemplate", priceTemplate);
		model.addAttribute("prices", prices);
		return "admin/price/edit";
	}

	
	*//**
	 * 保存编辑页面
	 * 
	 * @param id
	 * @param model
	 * @param form
	 * @return
	 * @throws ParseException 
	 *//*
	@Transactional
	@RequestMapping("/save/{id}")
	public String save(@PathVariable("id") long id, Model model, 
			           @Valid PriceTemplate form ,
			           HttpServletRequest request
			           ) throws ParseException {
		PriceTemplate priceTemplate = priceTemplateService.findById(id);
		Date createdAt = priceTemplate.getCreatedAt();
		priceTemplate.setCreatedAt(createdAt);
		priceTemplate.setUpdatedAt(new Date());
		priceTemplate.setName(form.getName());
		priceTemplate.setDescription(form.getDescription());
		priceTemplate.setStatus(form.getStatus());
		List<ParamTemplateAttr> attrs = paramTemplateAttrService.findByPriceTemplate(priceTemplate);
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
        			Date startDate=sdf.parse(start[i]);
        			Date endDate=sdf.parse(endAt[i]);
        			Date start1=null;
        			long cha=endDate.getTime()-startDate.getTime();
        			if(len>1){
        				if(i<len-1){
        					start1=sdf.parse(start[i+1]);
        					if(!start1.equals(endDate)){
        						model.addAttribute("message","第二阶段模板开始时间必须等于第一阶段结束时间。");
        						model.addAttribute("priceTemplate", priceTemplate);
                				model.addAttribute("prices", prices);
                				return "admin/price/edit";
        					}
        				}
        				
        			}	 	
			 }
				List<ParamTemplateAttr> list =new ArrayList<>();
    			priceTemplateService.save(priceTemplate);
    			add(priceTemplate, "startAt", start,list);
    			add(priceTemplate, "endAt", endAt,list);
    			add(priceTemplate, "fee", fee,list);
    			add(priceTemplate, "remark", remark,list);
    			add(priceTemplate, "price", period,list);
    			priceTemplate.setParamList(list);
    			priceTemplateService.save(priceTemplate);
         
		return "redirect:/admin/priceTemplates/";
	}

	 public static String returnEdit( Model model,PriceTemplate template,List<Price> pp){
		 model.addAttribute("template", template);
			model.addAttribute("prices", pp);
		 return "admin/price/edit";
	 }
	*//**
	 * 添加模板
	 * 
	 * @param model
	 * @return
	 *//*
	@RequestMapping("/add")
	public String add(Model model) {
		model.addAttribute("template", new PriceTemplate());
		model.addAttribute("attrs", new ParamTemplateAttr());
		return "admin/price/add";
	}
	
	


	*//**
	 * 保存新建模板
	 * 
	 * @param redirectAttributes
	 * @param template
	 * @return
	 * @throws ParseException 
	 *//*
	@Transactional
	@RequestMapping(value = "/addTemplate", method = RequestMethod.POST)
	public String save(RedirectAttributes redirectAttributes,
						@ModelAttribute PriceTemplate template,
						HttpSession session, Model model,
						HttpServletRequest request) throws ParseException {
		Manager manager = (Manager) session.getAttribute("user");
		template.setCreator(manager.getRealname());
		// 保存电价信息
		String[] start = request.getParameterValues("startAt");
		String[] endAt = request.getParameterValues("endAt");
		String[] fee = request.getParameterValues("fee");
		String[] remark = request.getParameterValues("remark");
		String[] period = request.getParameterValues("price");
		List<ParamTemplateAttr> list =new ArrayList<>();
		priceTemplateService.save(template);
		add(template, "startAt", start,list);
		add(template, "endAt", endAt,list);
		add(template, "fee", fee,list);
		add(template, "remark", remark,list);
		add(template, "price", period,list);
		template.setParamList(list);
		priceTemplateService.save(template);
		return "redirect:/admin/priceTemplates/";
	}
	private void add(PriceTemplate  template,String names,String[] keys,List<ParamTemplateAttr> list){
		 for (int i = 0; i < keys.length; i++) {
			  String key = keys[i];
			  String name =names+i;
			  ParamTemplateAttr attr = new ParamTemplateAttr(); 
			  attr.setAttrName(name);
			  attr.setAttrVal(key);
		      attr.setPriceTemplate(template);
		      list.add(attr);
		}
	 }
	 private static String getAttr(String name, List<ParamTemplateAttr> attrs){
		 for(ParamTemplateAttr attr : attrs){
			 if(name.equals(attr.getAttrName())){
				 return attr.getAttrVal();
			 }
		 }
		 return "";
	 }
	
	
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
}
*/