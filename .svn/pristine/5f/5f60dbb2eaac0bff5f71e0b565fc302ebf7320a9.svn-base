package com.iycharge.server.admin.controller;

import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.admin.controller.helper.PageWrapper;
import com.iycharge.server.domain.entity.admin.LogModule;
import com.iycharge.server.domain.entity.admin.LogType;
import com.iycharge.server.domain.entity.admin.Manager;
import com.iycharge.server.domain.entity.admin.ManagerLog;
import com.iycharge.server.domain.entity.charger.Charger;
import com.iycharge.server.domain.entity.dict.CategoryConstant;
import com.iycharge.server.domain.entity.dict.DictData;
import com.iycharge.server.domain.entity.operator.Operator;
import com.iycharge.server.domain.entity.price.ParamTemplateQueryParam;
import com.iycharge.server.domain.entity.station.Station;
import com.iycharge.server.domain.service.ChargerService;
import com.iycharge.server.domain.service.ManagerLogService;
import com.iycharge.server.domain.service.OperatorService;
import com.iycharge.server.domain.service.StationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/operators/")
public class OperatorController {

    @Autowired
    private OperatorService operatorService;
    @Autowired
    private ChargerService chargerService;
    @Autowired
    private StationService stationService;
	@Resource
	private ManagerLogService managerLogService;
    // Template file path
    private static final String TEMPLATE_INDEX_FILE = "admin/operator/index";
    private static final String TEMPLATE_ADD_FILE = "admin/operator/add";
    private static final String TEMPLATE_EDIT_FILE = "admin/operator/edit";
    private static final String TEMPLATE_CHECK_FILE = "admin/operator/check";
    private static final String TEMPLATE_EDIT_LIST_CHARGERS_FILE = "admin/operator/chargers";
    private static final String TEMPLATE_EDIT_LIST_STATIONS_FILE = "admin/operator/stations";

    @ModelAttribute("types")
    public List<DictData> types() {
    	List<DictData> lst = EntityUtil.getDictDatas(CategoryConstant.OPERATOR_TYPE);
        return lst != null?lst:(new ArrayList<DictData>());
    }

    @ModelAttribute("statuses")
    public List<DictData> statuses() {
    	List<DictData> lst = EntityUtil.getDictDatas(CategoryConstant.OPERATOR_STATUE);
        return lst != null?lst:(new ArrayList<DictData>());
    }

    @RequestMapping("/")
    public String index(HttpServletRequest request,Operator operator,Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size=15) Pageable pageable) {
    	 HttpSession session = request.getSession();
    	 session.removeAttribute("sessionOperator");
    	PageWrapper<Operator> page = new PageWrapper<>(operatorService.findAll(pageable), "/admin/operators/");
        model.addAttribute("page", page);
        model.addAttribute("operator", operator);
        return TEMPLATE_INDEX_FILE;
    }

    @RequestMapping("/add")
    public String add(Model model) {
        model.addAttribute("operator", new Operator());
        return TEMPLATE_ADD_FILE;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String save(Model model ,HttpServletRequest request, HttpSession session,@ModelAttribute Operator operator, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("failed", "添加新运营商时数据发生错误，请检查后重新尝试。");
            return "redirect:/admin/operators/add";
        }
//        //检验name不重复
//        List<Operator> listName = operatorService.findByName(operator.getName());
//        if(listName.size()>=1){
//        	redirectAttributes.addFlashAttribute("failed", "添加新运营商时运营商编码名称重复，请检查后重新尝试。");
//        	 return "redirect:/admin/operators/add";
//        }else{
//        	
//        }
      //校验code编码不重复
        List<Operator> list = operatorService.findByCode(operator.getCode());
        if(list.size()>=1){
        	model.addAttribute("operator", operator);
        	model.addAttribute("failed", "添加新运营商时运营商编码重复，请检查后重新尝试。");
        	 return "admin/operator/add";
        }else{
        	//upFile(operator);
            operatorService.save(operator);
            EntityUtil.addOperator(
                operatorService.save(operator)
            );
        }
        Manager manager = (Manager)session.getAttribute("user");
   		ManagerLog log = new ManagerLog();
    	log.setLoginName(manager.getLoginName());
    	log.setRealName(manager.getRealname());
    	log.setIp(request.getRemoteAddr());
    	log.setLogTime(new Date());
    	log.setLogModule(LogModule.OPERATION.name());
    	log.setLogType(LogType.ADD.name());
    	log.setParams("添加运营商"+operator.getName());
    	log.setStatus(true);
	    managerLogService.save(log);
        redirectAttributes.addFlashAttribute("success", MessageFormat.format("运营商 {0} 已经成功添加。", operator.getName()));
        return "redirect:/admin/operators/search";
    }

  /*  public String upFile(Operator operator){
    	if(StringUtils.isNotEmpty(operator.getFile().getName())){
    	String fileName = File.separator+"images"+File.separator+"operator";
    	if (!operator.getFile().isEmpty()) {  
            try {  
                  byte[] bytes = operator.getFile().getBytes(); 
                  String name = operator.getFile().getOriginalFilename();
                  String realPath = MyPath.class.getClassLoader().getResource("").getFile();
                  realPath = realPath+File.separator+"static"+fileName;
                  java.io.File file = new java.io.File(realPath);
                  if(!file.exists()){
                  	file.mkdirs();
                  }
                  realPath = realPath+File.separator+name;
                  fileName =fileName +File.separator+name;
                  java.io.File realfile = new java.io.File(realPath);
                  realPath = realfile.getPath();
                  System.out.println(realPath);
                  try {
                      realPath = java.net.URLDecoder.decode (realPath, "utf-8");
                   // String path = MyPath.getProjectPath()+File.separator+name;
                      BufferedOutputStream stream =  
                              new BufferedOutputStream(new FileOutputStream(realfile));  
                      stream.write(bytes);  
                      stream.close();  
                  } catch (Exception e) {
                      e.printStackTrace();
                  }
                  
            } catch (Exception e) {  
            }  
            return fileName;
        } else {  
        	return "";
        }  
    	}
    	return "";
    	
    }*/
    @RequestMapping("/edit/{operatorId}")
    public String edit(@PathVariable("operatorId") Long operatorId, Model model) {
    	model.addAttribute("operator", operatorService.findById(operatorId));
        return TEMPLATE_EDIT_FILE;
    }
    @RequestMapping("/check/{operatorId}")
    public String check(@PathVariable("operatorId") Long operatorId, Model model) {
        model.addAttribute("operator", operatorService.findById(operatorId));
        return TEMPLATE_CHECK_FILE;
    }
    
    @RequestMapping(value = "/{operatorId}", method = RequestMethod.POST)
    public String update(HttpServletRequest request,
				HttpSession session,Model model,@PathVariable("operatorId") Long operatorId,
                         @Valid Operator form, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("failed", "更新运营商时发生错误，请检查后重新尝试。");
            return TEMPLATE_EDIT_FILE;
        }

        Operator operator = operatorService.findById(operatorId);
//        //检验name不重复
//        List<Operator> listName = operatorService.findByName(form.getName());
//        if(listName.size()==1){
//        	if(listName.get(0).getId()==operator.getId()){
//        	}else{
//        		model.addAttribute("failed", "添加新运营商时运营商名称重复，请检查后重新尝试。");
//        		model.addAttribute("operator", operator);
//        		return TEMPLATE_EDIT_FILE;
//        	}
//        }else if(listName.size()==0){
//        }else{
//        	redirectAttributes.addFlashAttribute("failed", "添加新运营商时运营商名称重复，请检查后重新尝试。");
//       	 	return "redirect:/admin/operators/add";
//        }
        //检验code不重复
        List<Operator> list = operatorService.findByCode(form.getCode());
        if(list.size()==1){
        	if(list.get(0).getId()==operator.getId()){
        		tranBean(operator,form);
//    			String fileName = upFile(form);
//        		if(StringUtils.isNotEmpty(fileName)){
//        			operator.setFileName(fileName);
//        		}
        		operatorService.save(operator);
        		EntityUtil.addOperator(
        		    operatorService.save(operator)
        		);
        	}else{
        		model.addAttribute("failed", "添加新运营商时运营商编码重复，请检查后重新尝试。");
        		model.addAttribute("operator", operator);
        		return TEMPLATE_EDIT_FILE;
        	}
        }else if(list.size()==0){
        	tranBean(operator,form);
//        	upFile(form);
        	operatorService.save(operator);
        	EntityUtil.addOperator(
                operatorService.save(operator)
            );
        }else{
        	redirectAttributes.addFlashAttribute("failed", "添加新运营商时运营商编码重复，请检查后重新尝试。");
       	 	return "redirect:/admin/operators/add";
        }
       // operatorService.save(operator);
        Manager manager = (Manager)session.getAttribute("user");
   		ManagerLog log = new ManagerLog();
    	log.setLoginName(manager.getLoginName());
    	log.setRealName(manager.getRealname());
    	log.setIp(request.getRemoteAddr());
    	log.setLogTime(new Date());
    	log.setLogModule(LogModule.OPERATION.name());
    	log.setLogType(LogType.EDIT.name());
    	log.setParams("修改运营商"+operator.getName());
    	log.setStatus(true);
	    managerLogService.save(log);
        redirectAttributes.addFlashAttribute("success", MessageFormat.format("运营商 {0} 更新成功。", operator.getName()));
        return "redirect:/admin/operators/search";
    }
    public void tranBean(Operator operator,Operator form){
    	operator.setName(form.getName());
        operator.setCode(form.getCode());
        operator.setContact(form.getContact());
        operator.setAddress(form.getAddress());

        operator.setType(form.getType());
        operator.setStatus(form.getStatus());

        operator.setTelephone(form.getTelephone());
        operator.setDescription(form.getDescription());
        
    }

    //    Operator to Charger

    @RequestMapping("/{operatorId}/chargers/")
    public String listChargers(@PathVariable("operatorId") Long operatorId, Model model,
                               @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size=15) Pageable pageable) {
        Operator operator = operatorService.findById(operatorId);
        PageWrapper<Charger> page = new PageWrapper<>(chargerService.findByOperator(operator, pageable), operatorId + "/chargers/");
        model.addAttribute("page", page);
        model.addAttribute("operator", operator);
        return TEMPLATE_EDIT_LIST_CHARGERS_FILE;

    }    //    Operator to Station

    @RequestMapping("/{operatorId}/stations/")
    public String listStations(@PathVariable("operatorId") Long operatorId, Model model,
                               @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size=15) Pageable pageable) {
        Operator operator = operatorService.findById(operatorId);
        PageWrapper<Station> page = new PageWrapper<>(stationService.findByOperator(operator, pageable), operatorId + "/stations/");
        model.addAttribute("page", page);
        model.addAttribute("operator", operator);
        return TEMPLATE_EDIT_LIST_STATIONS_FILE;
    }

    @RequestMapping("/del/{operatorId}")
    public String del(HttpServletRequest request,
			HttpSession session,@PathVariable("operatorId") Long operatorId, Model model) {
    	Operator entity = operatorService.findById(operatorId);
    	if(null!=entity){
    		entity.setDelStatus("del");
    		operatorService.save(entity);
    		List<Station> list = stationService.findByOperator(entity);
    		if(list!=null&&list.size()>0){
    			for(Station station:list){
    				station.setDelStatus("del");
    				stationService.delStation(station);
    				List<Charger> ll = chargerService.findByStation(station);
    				if(ll!=null&&ll.size()>0){
    					for(Charger charger:ll){
    						charger.setDelStatus("del");
    						chargerService.delCharger(charger);
    					}
    				}
    			}
    		}
    		List<Charger> llop = chargerService.findByOperator(entity);
    		if(llop!=null&&llop.size()>0){
    			for(Charger charger:llop){
					charger.setDelStatus("del");
					chargerService.delCharger(charger);
				}
    		}
    	}else{
    		
    	}
    	 Manager manager = (Manager)session.getAttribute("user");
    		ManagerLog log = new ManagerLog();
     	log.setLoginName(manager.getLoginName());
     	log.setRealName(manager.getRealname());
     	log.setIp(request.getRemoteAddr());
     	log.setLogTime(new Date());
     	log.setLogModule(LogModule.OPERATION.name());
     	log.setLogType(LogType.DELETE.name());
     	log.setParams("删除运营商"+entity.getName());
     	log.setStatus(true);
 	    managerLogService.save(log);
    	return "redirect:/admin/operators/search";
    }
    
    @RequestMapping("/search")
    public String search(HttpServletRequest request,Model model,@ModelAttribute Operator operator,@PageableDefault(sort = "id", direction = Sort.Direction.DESC, size=15) Pageable pageable) {
    	HttpSession session = request.getSession();
        if("1".equals(operator.getFlag())){
        	session.setAttribute("sessionOperator", operator);
        }else{
        	Operator ch = (Operator)session.getAttribute("sessionOperator");
       	 if(ch!=null){
       		operator = ch;
       	 }
        }   
    	PageWrapper<Operator> page = new PageWrapper<>(operatorService.findSearch(operator.getName(),pageable), "/admin/operators/search?name="+operator.getName());
        model.addAttribute("page", page);
        model.addAttribute("operator", operator);
        return "admin/operator/index";
    }
    
    
  /*  @RequestMapping(value="/upload", method=RequestMethod.POST)  
    public  String handleFileUpload(@ModelAttribute Operator operator){  
        if (!operator.getFile().isEmpty()) {  
            try {  
                byte[] bytes = operator.getFile().getBytes();  
                BufferedOutputStream stream =  
                        new BufferedOutputStream(new FileOutputStream(new File(operator.getName() + "-uploaded")));  
                stream.write(bytes);  
                stream.close();  
            } catch (Exception e) {  
            }  
        } else {  
        }  
        return "redirect:/admin/operators/";
    } */ 
}
