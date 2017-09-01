package com.iycharge.server.admin.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.elasticsearch.common.lang3.StringUtils;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iycharge.server.admin.controller.helper.PageWrapper;
import com.iycharge.server.domain.entity.dict.DictCategory;
import com.iycharge.server.domain.service.DictCategoryService;


/**
 * 字典类型列表
 * @author wgang 
 *
 */
@Controller
@RequestMapping("/admin/dictCategorys")
public class DictCategoryController {
     @Autowired
     private DictCategoryService dictCategoryService;
     
     private static String dictCategorydescr = null;
     @ModelAttribute("types")
     public List<DictCategory> getType(){
    	 return dictCategoryService.findAll();
     }
     
    
     @RequestMapping("/")
     public String searchAll(DictCategory dictCategory,Model model,@PageableDefault(sort = "name", direction = Sort.Direction.ASC, size = 15) Pageable pageable,String flag) {	    			
    	 PageWrapper<DictCategory> page = null;
 		 if(flag!=null&&dictCategorydescr!=null){
 			model.addAttribute("descrForSearch", dictCategorydescr);
 			page = new PageWrapper<>(dictCategoryService.findAllSearch(dictCategorydescr,dictCategory,pageable), "/admin/dictCategorys/search?descr="+dictCategorydescr);
 		 }else{
 			page = new PageWrapper<>(dictCategoryService.findAll(pageable), "/admin/dictCategorys/");
 			model.addAttribute("descrForSearch", "");
 			dictCategorydescr = null;
 		 }
 		 model.addAttribute("page", page);
		 model.addAttribute("dictCategory", dictCategory);
         return "admin/system/dict/mold/index";
     }
     
     @RequestMapping("/search")
     public String searchByName(Model model,String descrForSearch,@PageableDefault(sort = "name", direction = Sort.Direction.ASC, size = 15) Pageable pageable) {	    			
    	 PageWrapper<DictCategory> page = null;
    	 DictCategory dictCategory = new DictCategory();
    	 if(descrForSearch!=null && StringUtils.isNotBlank(descrForSearch)){
    		 page = new PageWrapper<>(dictCategoryService.findAllSearch(descrForSearch,dictCategory,pageable), "/admin/dictCategorys/search?descr="+descrForSearch);
    	 }else{
    		 page = new PageWrapper<>(dictCategoryService.findAll(pageable), "/admin/dictCategorys/");
    	 }
    	 dictCategorydescr = descrForSearch;
 		 model.addAttribute("page", page);
 		 model.addAttribute("dictCategory", dictCategory);
 		 model.addAttribute("descrForSearch",descrForSearch);
 		 
         return "admin/system/dict/mold/index";
     }
     
     /**
      * 根据id删除类型
      * @param id
      * @return
      */
	@RequestMapping("/del/{id}")
	public String del(@PathVariable("id") Long id ) {
		DictCategory dictCategory=dictCategoryService.findById(id);
		if(null!=dictCategory){
			dictCategoryService.delById(id);
	    }
		return "redirect:/admin/dictCategorys/?flag=true";
	}
	
	 /**
     * 用户进入添加界面
     * @param model
     * @return
     */
    @RequestMapping("/add")
    public String add(Model model) {
    	model.addAttribute("dict", new DictCategory());
        return "admin/system/dict/mold/add";
    }
    
    /**
     * 新增字典
     * @param dict
     * @param redirectAttributes
     * @return
     */
    @Transactional
    @RequestMapping(value = "/dictAdd", method = RequestMethod.POST)
    public String save(Model model , @ModelAttribute DictCategory dict , RedirectAttributes redirectAttributes
    		) {
    	DictCategory test = dictCategoryService.findByName(dict.getName());
    	 if(test!=null){
    		DictCategory dt = new DictCategory();
    		dt.setDescr(dict.getDescr());
    		dt.setName(dict.getName());
    		dt.setDictCategoryList(dict.getDictCategoryList());
    		dt.setDictDataList(dict.getDictDataList());
    		dt.setParent(dict.getParent());
    		model.addAttribute("dict",dt);
        	model.addAttribute("failed", "代码类型已经存在，不可重复添加");
            return "admin/system/dict/mold/add";
    	 }
    	 dict.setCreatedAt(new Date());
         dictCategoryService.save(dict);
		return "redirect:/admin/dictCategorys/?flag=true";
    }
    
    /**
     * 进入查看页面
     * @param id
     * @param model
     * @return
     */
   @RequestMapping("/check/{id}")
   public String check(@PathVariable("id") long id,Model model ){
	  DictCategory dict=dictCategoryService.findById(id);
	  model.addAttribute("dict",dict);
	   return "admin/system/dict/mold/check";
   }
	
   
   @RequestMapping("/edit/{id}")
   public String edit(@PathVariable("id")long id,Model model){
	  DictCategory dict=dictCategoryService.findById(id);
	  model.addAttribute("dict",dict);
	  return "admin/system/dict/mold/edit";
   }
   
   
   @Transactional
   @RequestMapping(value = "/editSave/{id}", method = RequestMethod.POST)
   public String editSave(@Valid DictCategory dict,
		                  @PathVariable("id")long id,
		                  RedirectAttributes redirectAttributes
		                 ) {
	   DictCategory form=dictCategoryService.findById(id);
		   	form.setAllowedDel(dict.isAllowedDel());
		   	form.setAllowedEdit(dict.isAllowedEdit());
		   	form.setName(dict.getName());
		   	form.setRemark(dict.getRemark());
		   	form.setDescr(dict.getDescr());
		   	form.setParent(dict.getParent());
		   	form.setUpdatedAt(new Date());
		    dictCategoryService.save(form);
          return "redirect:/admin/dictCategorys/?flag=true";
   }
}