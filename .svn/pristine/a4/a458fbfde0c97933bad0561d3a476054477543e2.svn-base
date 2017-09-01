package com.iycharge.server.admin.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.admin.controller.helper.PageWrapper;
import com.iycharge.server.domain.entity.dict.DictCategory;
import com.iycharge.server.domain.entity.dict.DictData;
import com.iycharge.server.domain.service.DictCategoryService;
import com.iycharge.server.domain.service.DictDataService;


/**
 * 字典值列表
 * @author wgang 
 *
 */
@Controller
@RequestMapping("/admin/dictDatas")
public class DictDataController { 
     @Autowired
     private DictDataService dictDataService;
     
     @Autowired
     private DictCategoryService dictCategoryService;
     
     private static String staticDictCategory = null;
     
     @ModelAttribute("types")
     public List<DictCategory> getType(){
    	 return dictCategoryService.findAll();
     }
     
     @ModelAttribute("values")
     public List<DictData> getValue(){
    	 return dictDataService.findAll();
     }
     @RequestMapping("/")
     public String searchAll(Model model,@PageableDefault(sort = {"dictCategory.name","sortNo"}, direction = Sort.Direction.ASC, size = 15) Pageable pageable,String flag) {	    			
 		 PageWrapper<DictData> page = null;
 		 if(flag!=null&&staticDictCategory!=null&&staticDictCategory.trim().length()>0){
 			DictCategory dictCategory = dictCategoryService.findById(Long.parseLong(staticDictCategory));
 			page = new PageWrapper<>(dictDataService.findByDictCategory(pageable, dictCategory), "/admin/dictDatas/search"+"?dc="+staticDictCategory);
 			model.addAttribute("dc", staticDictCategory); 
 		 }else{
 			page = new PageWrapper<>(dictDataService.findAll(pageable), "/admin/dictDatas/");
 			staticDictCategory = null;
 		 }
 		 model.addAttribute("page", page); 		 
         return "admin/system/dict/upon/index";
     }

     @RequestMapping("/search")
     public String search(Model model,
    		 @PageableDefault(sort = {"dictCategory.name","sortNo"}, direction = Sort.Direction.ASC, size = 15) Pageable pageable,
    		 String dc) {
    	 PageWrapper<DictData> page=null;
    	 DictCategory	dictCategory = null;
    	 if(dc!=null&&dc.trim().length()>0){
    		 Long id = Long.parseLong(dc);
    		 dictCategory= dictCategoryService.findById(id); 
    	 }
    	   
    	 if(dictCategory==null){
    		 page = new PageWrapper<>(dictDataService.findAll(pageable), "/admin/dictDatas/");
    	 }else{
 		     page = new PageWrapper<>(dictDataService.findByDictCategory(pageable, dictCategory), "/admin/dictDatas/search"+"?dictCategory="+dictCategory.getId());
    	 }
    	 staticDictCategory = dc;
 		 model.addAttribute("page", page); 	
 		 model.addAttribute("dc", dc); 
         return "admin/system/dict/upon/index";
     }
     /**
      * 根据id删除类型
      * @param id
      * @return
      */

	@RequestMapping("/del/{id}")
	public String del(@PathVariable("id") long id ){
		DictData dictData=dictDataService.findById(id);
		dictDataService.delById(id);
		EntityUtil.removeDictData(dictData);
		return "redirect:/admin/dictDatas/?flag=true";
	}
	
	 /**
     * 用户进入添加界面
     * @param model
     * @return
     */
    @RequestMapping("/add")
    public String add(Model model) {
    	model.addAttribute("dict", new DictData());
        return "admin/system/dict/upon/add";
    }
    
   
    @Transactional
    @RequestMapping(value = "/dictAdd", method = RequestMethod.POST)
    public String save(Model model ,@ModelAttribute DictData dict, RedirectAttributes redirectAttributes) {
    	
    	DictData test = dictDataService.findByDictKey(dict.getDictKey());
    	if(test!=null){
    		DictData dt = new DictData();
    		dt.setDescr(dict.getDescr());
    		dt.setDictKey(dict.getDictKey());
    		dt.setDictValue(dict.getDictValue());
    		dt.setSortNo(dict.getSortNo());
    		dt.setRemark(dict.getRemark());
    		dt.setDictCategory(dict.getDictCategory());
    		dt.setParent(dict.getParent());
    		model.addAttribute("dict",dt);
    	    model.addAttribute("failed", "代码key已经存在，请修改key的值");
            return "admin/system/dict/upon/add";
    	}
    	dict.setCreatedAt(new Date());
        DictData dictData = dictDataService.save(dict);
        EntityUtil.addDictData(dictData);
		return "redirect:/admin/dictDatas/?flag=true";
    }
    
    @RequestMapping("/getDictData")
    @ResponseBody
    public Map<String,String> getDictData(String id){
    	if(id==null||id.trim().equals("")){
    		return null;
    	}
    	DictCategory sc = dictCategoryService.findById(Long.parseLong(id));
    	List<DictData> dds = sc.getDictDataList();
    	Map<String,String> map = new HashMap<String,String>();
    	for(int i = 0; i < dds.size() ; i++){
    		DictData dd = dds.get(i);
    		map.put(dd.getId().toString(), dd.getDescr());
    	}
    	return map;
    }
    
    /**
     * 进入查看页面
     * @param id
     * @param model
     * @return
     */
   @RequestMapping("/check/{id}")
   public String check(@PathVariable("id")long id,Model model ){
	  DictData dict=dictDataService.findById(id);
	  model.addAttribute("dict",dict);
	   return "admin/system/dict/upon/check";
   }
	
   
   @RequestMapping("/edit/{id}")
   public String edit(@PathVariable("id")Long id,Model model ){
	  DictData dict=dictDataService.findById(id);
	  DictCategory dc = dict.getDictCategory();
	  List<DictData> dds = dc.getDictDataList();
	  model.addAttribute("dict",dict);
	  model.addAttribute("parentKey",dds);
	   return "admin/system/dict/upon/edit";
   }
   
   
   @Transactional
   @RequestMapping(value = "/editSave/{id}", method = RequestMethod.POST)
   public String editSave(@Valid DictData dict, 
		                  @PathVariable("id")long id) {
	        DictData form=dictDataService.findById(id);
	        form.setDescr(dict.getDescr());
	        form.setDictCategory(dict.getDictCategory());
	        form.setParent(dict.getParent());
	        form.setAllowedDel(dict.isAllowedDel());
	        form.setAllowedEdit(dict.isAllowedEdit());
	        form.setDictKey(dict.getDictKey());
	        form.setDictValue(dict.getDictValue());
	        form.setSortNo(dict.getSortNo());
	        form.setUpdatedAt(new Date());
	        form.setRemark(dict.getRemark());
	        DictData dictData = dictDataService.save(form);
	        EntityUtil.updateDictData(dictData);
	      
          return "redirect:/admin/dictDatas/?flag=true";
   }
   
   @RequestMapping(value = "/test")
	public  String cate(@RequestParam("dictCategory")DictCategory dictCategory){
		List<DictCategory> dicts=dictCategoryService.findByIds(dictCategory.getId());
		System.out.println(dictCategory.getId());
		if(dicts!=null){
			for(int i=0;i<dicts.size();i++){
				List<DictData> datas=dicts.get(i).getDictDataList();
				if(datas!=null){
	 				for(int j=0;j<datas.size();i++){
	 					System.out.println(datas.get(j).getDescr()+"\n");
					}
				}
				// cate(dicts.get(i));
			}
			
		}
		return  "admin/system/dict/upon/index2";	
		}
   
   @RequestMapping(value = "/tests")
	public  String cates(){
		
		return  "admin/system/dict/upon/index2";	
		}
}