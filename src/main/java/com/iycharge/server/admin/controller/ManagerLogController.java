package com.iycharge.server.admin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.admin.controller.helper.PageWrapper;
import com.iycharge.server.domain.entity.admin.LogQueryParam;
import com.iycharge.server.domain.entity.admin.ManagerLog;
import com.iycharge.server.domain.entity.dict.CategoryConstant;
import com.iycharge.server.domain.entity.dict.DictData;
import com.iycharge.server.domain.service.ManagerLogService;

/**
 * 系统用户操作日志Controller 
 * @author bwang
 */
@Controller
@RequestMapping("/admin/system/logs")
public class ManagerLogController {
    
    private static final String INDEX = "admin/system/log/index";
    
    @Resource
    private ManagerLogService managerLogService;
    
    @ModelAttribute("logModules")
    public List<DictData> initLogModules() {
    	List<DictData> lst = EntityUtil.getDictDatas(CategoryConstant.LOG_MODULE);
        return lst != null?lst:(new ArrayList<DictData>());
    }
    
    @ModelAttribute("logTypes")
    public List<DictData> initLogTypes() {
    	List<DictData> lst = EntityUtil.getDictDatas(CategoryConstant.LOG_TYPE);
        return lst != null?lst:(new ArrayList<DictData>());
    }
    
    /**
     * form表单提交 Date类型数据绑定
     */
    @InitBinder  
    public void initBinder(WebDataBinder binder) {  
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
            dateFormat.setLenient(false);  
            binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));  
    }  

    @RequestMapping(value="/")
    public String index(Model model, @PageableDefault(sort = "logTime", direction = Sort.Direction.DESC, size = 15) Pageable pageable) {
        PageWrapper<ManagerLog> page = new PageWrapper<>(managerLogService.findAll(pageable), "/admin/system/logs/");
        model.addAttribute("page", page);
        return INDEX;
    }
    
    @RequestMapping(value="/query", method=RequestMethod.POST)
    public String query(
                LogQueryParam queryParam, Model model, 
                @PageableDefault(sort = "logTime", direction = Sort.Direction.DESC, size=15) Pageable pageable) {
        PageWrapper<ManagerLog> page = new PageWrapper<>(managerLogService.find(queryParam, pageable), "/admin/system/logs/query");
        model.addAttribute("page", page);
        model.addAttribute("queryParam", queryParam);
        return INDEX;
    }
}
