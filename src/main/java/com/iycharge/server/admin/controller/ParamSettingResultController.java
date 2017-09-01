package com.iycharge.server.admin.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iycharge.server.admin.controller.helper.PageWrapper;
import com.iycharge.server.ccu.service.ParamSettingService;
import com.iycharge.server.domain.entity.admin.Manager;
import com.iycharge.server.domain.entity.charger.Charger;
import com.iycharge.server.domain.entity.price.ParamSetting;
import com.iycharge.server.domain.entity.price.ParamSettingResult;
import com.iycharge.server.domain.entity.price.ParamTemplate;
import com.iycharge.server.domain.service.ChargerService;
import com.iycharge.server.domain.service.ParamSettingResultService;
import com.iycharge.server.domain.service.ParamSettingSService;
import com.iycharge.server.domain.service.ParamTemplateService;

@Controller
@RequestMapping("/admin/paramSettingResults/")
public class ParamSettingResultController {

    @Autowired
    private ParamSettingResultService paramSettingResultService;
    @Autowired
    private ParamSettingSService paramSettingService;
    private static final String TEMPLATE_INDEX_FILE = "admin/paramSetting/result";
    @Autowired
    private ChargerService chargerService;
    @Autowired
    private ParamSettingService paramSettingServices;
    @Autowired
    private ParamTemplateService paramTemplateService;
    
    @ModelAttribute("chargers")
    public List<Charger> findChargerListAll() {
    	return chargerService.findAll();
    }
    
    @RequestMapping("/")
    public String index(@PageableDefault(sort = "id", direction = Sort.Direction.DESC, size=15) Pageable pageable, Model model) {
    	PageWrapper<ParamSettingResult> page = new PageWrapper<>(paramSettingResultService.findAll(pageable), "/admin/paramSettingResults/");
    	List<ParamSettingResult> list = page.getContent();
    	if(list!=null){
    		for(ParamSettingResult ps:list){
    			if(ps.isResult()){
    				ps.setResultName("下发成功");
    			}else{
    				ps.setResultName("下发失败");
    			}
    		}
    	}
    	model.addAttribute("page", page);
    	return TEMPLATE_INDEX_FILE;
    }
    
    @RequestMapping("/search")
    public String search(Model model, @ModelAttribute ParamSettingResult paramSettingResult,@PageableDefault(sort = "id", direction = Sort.Direction.DESC, size=15) Pageable pageable) {
        String field[] = {"charger"};
        PageWrapper<ParamSettingResult> page = new PageWrapper<>(paramSettingResultService.findAllSearch(field,paramSettingResult,pageable), "/admin/paramSettingResults/");
        List<ParamSettingResult> list = page.getContent();
    	if(list!=null){
    		for(ParamSettingResult ps:list){
    			if(ps.isResult()){
    				ps.setResultName("下发成功");
    			}else{
    				ps.setResultName("下发失败");
    			}
    		}
    	}
        model.addAttribute("page", page);
        return TEMPLATE_INDEX_FILE;
    }
    @RequestMapping("/send/{id}/{pid}")
    public String send(HttpSession session,@PathVariable("id") Long id, @PathVariable("pid") Long pid,Model model) {
    	ParamSettingResult paramSettingResult = paramSettingResultService.findById(id);
    	if(null!=paramSettingResult){
    		ParamSetting p = paramSettingService.findById(pid);
    		if(null!=p){
    			ParamTemplate paramTemplate = paramTemplateService.findById(p.getParamTemplate().getId());
    			if(null!=paramTemplate){
    				Set<String> set = new HashSet<String>();
    				long chargerid = paramSettingResult.getCharger().getId();
		    		set.add(String.valueOf(chargerid));
    				try {
						paramSettingServices.setCharegerParam(new Date(), paramTemplate, set);
					} catch (Exception e) {
						e.printStackTrace();
					}
    			}
    	    	Manager user = (Manager)session.getAttribute("user");
    	    	p.setSender(user.getRealname());
    	    	p.setSendTime(new Date());
    	    	paramSettingService.save(p);
    		}
    	}
    	paramSettingResult.setCreatedAt(new Date());
    	paramSettingResult.setResult(false);
    	paramSettingResultService.save(paramSettingResult);
    	return "redirect:/admin/paramSettingResults/";
    }
    
}
