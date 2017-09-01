package com.iycharge.server.admin.controller;

import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.admin.cache.RCharger;
import com.iycharge.server.admin.cache.RConnector;
import com.iycharge.server.admin.controller.helper.PageWrapper;
import com.iycharge.server.ccu.msg.entity.ChargerCommLog;
import com.iycharge.server.ccu.service.ChargerCommLogService;
import com.iycharge.server.domain.common.utils.ReflectField;
import com.iycharge.server.domain.common.utils.VeDate;
import com.iycharge.server.domain.entity.admin.LogModule;
import com.iycharge.server.domain.entity.admin.LogType;
import com.iycharge.server.domain.entity.admin.Manager;
import com.iycharge.server.domain.entity.admin.ManagerLog;
import com.iycharge.server.domain.entity.charger.Charger;
import com.iycharge.server.domain.entity.charger.ChargerGun;
import com.iycharge.server.domain.entity.charger.ChargerStatus;
import com.iycharge.server.domain.entity.dict.CategoryConstant;
import com.iycharge.server.domain.entity.dict.DictData;
import com.iycharge.server.domain.entity.operator.Operator;
import com.iycharge.server.domain.entity.order.Order;
import com.iycharge.server.domain.entity.price.ParamTemplate;
import com.iycharge.server.domain.entity.price.ParamTemplateAttr;
import com.iycharge.server.domain.entity.price.Price;
import com.iycharge.server.domain.entity.station.Station;
import com.iycharge.server.domain.entity.utils.RedisUtil;
import com.iycharge.server.domain.entity.utils.Utils;
import com.iycharge.server.domain.entity.utils.outputExcel.ExcelUtil;
import com.iycharge.server.domain.service.ChargerGunService;
import com.iycharge.server.domain.service.ChargerService;
import com.iycharge.server.domain.service.ManagerLogService;
import com.iycharge.server.domain.service.OperatorService;
import com.iycharge.server.domain.service.OrderService;
import com.iycharge.server.domain.service.ParamTemplateAttrService;
import com.iycharge.server.domain.service.ParamTemplateService;
import com.iycharge.server.domain.service.StationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/chargers/")
public class ChargerController {

    @Autowired
    private ChargerService chargerService;
    @Autowired
    private OperatorService operatorService;
    @Autowired
    private StationService stationService;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private ParamTemplateService paramTemplateService;
    
    @Autowired
	private ParamTemplateAttrService paramTemplateAttrService;
    @Resource
	private ManagerLogService managerLogService;
    @Autowired
    private ChargerGunService chargerGunService;
    @Resource
    private ChargerCommLogService chargerCommLogService;
    @Autowired
    private RedisUtil redisUtil;
    // Template file path
    private static final String TEMPLATE_INDEX_FILE = "admin/charger/index";
    private static final String TEMPLATE_ADD_FILE = "admin/charger/add";
    private static final String TEMPLATE_EDIT_FILE = "admin/charger/edit";
    private static final String TEMPLATE_CHECK_FILE = "admin/charger/check";
    
    /**
     * form表单提交 Date类型数据绑定
     */
    @InitBinder  
    public void initBinder(WebDataBinder binder) {  
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
            dateFormat.setLenient(false);  
            binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));  
    }  
    
    @ModelAttribute("types")
    public List<DictData> types() {
    	List<DictData> lst = EntityUtil.getDictDatas(CategoryConstant.EQUIPMENT_TYPE);
        return lst != null?lst:(new ArrayList<DictData>());

    }
    
    @ModelAttribute("payments")
    public List<DictData> payments() {
    	List<DictData> lst = EntityUtil.getDictDatas(CategoryConstant.PAYMENT_TYPE);
        return lst != null?lst:(new ArrayList<DictData>());
    }

    @ModelAttribute("areas")
    public List<DictData> areas() {
    	List<DictData> lst = EntityUtil.getDictDatas(CategoryConstant.AREA_TYPE);
        return lst != null?lst:(new ArrayList<DictData>());
    }
    @ModelAttribute("chargeIfs")
    public List<DictData> chargerIfMethods() {
    	List<DictData> lst = EntityUtil.getDictDatas(CategoryConstant.CONNECTOR_TYPE);
        return lst != null?lst:(new ArrayList<DictData>());
    }
    
    @ModelAttribute("chargeModels")
    public List<DictData> chargerModelMethods() {
    	List<DictData> lst = EntityUtil.getDictDatas(CategoryConstant.CHARGER_MODEL);
        return lst != null?lst:(new ArrayList<DictData>());
    }
    @ModelAttribute("netingWorkModels")
    public List<DictData> netingWorkModelMethods() {
    	List<DictData> lst = EntityUtil.getDictDatas(CategoryConstant.NTWORKING_MODE);
        return lst != null?lst:(new ArrayList<DictData>());
    }
    @ModelAttribute("manufacturers")
    public List<DictData> manufacturers() {
    	List<DictData> lst = EntityUtil.getDictDatas(CategoryConstant.MANUFACTURER);
        return lst != null?lst:(new ArrayList<DictData>());
    }
    @ModelAttribute("carssupports")
    public List<DictData> carssupports() {
    	List<DictData> lst = EntityUtil.getDictDatas(CategoryConstant.CARS_SUPPORTS);
        return lst != null?lst:(new ArrayList<DictData>());
    }
    
    @ModelAttribute("paidFroms")
    public List<DictData> paidFroms() {
        List<DictData> lst = EntityUtil.getDictDatas(CategoryConstant.PAID_FROM);
        return lst != null?lst:(new ArrayList<DictData>());      
    }
    /**
     * 所属运营商
     * @return
     */
    @ModelAttribute("operators")
    public List<Operator> findOperatorListAll() {
    	List<Operator> list = operatorService.findListAll();
    	if(list!=null&&list.size()>0){
    		for(Operator operator:list){
    			String name = operator.getName();
    			String code = operator.getCode();
    			String codeAndName = name+"("+code+")";
    			operator.setCodeAndName(codeAndName);
    		}
    	}
        return list;
    }
    /**
     * 所属充电站
     * @return
     */
    @ModelAttribute("stations")
    public List<Station> findStationListAll() {
    	List<Station> list = stationService.findListAll();
    	if(list!=null&&list.size()>0){
    		for(Station station:list){
    			String name = station.getName();
    			String code = station.getCode();
    			String codeAndName = name+"("+code+")";
    			station.setCodeAndName(codeAndName);
    		}
    	}
        return list;
    }
    
    @RequestMapping("/")
    public String index(HttpServletRequest request,Model model, @ModelAttribute Charger charger,@PageableDefault(sort = "id", direction = Sort.Direction.DESC, size=15) Pageable pageable) {
    	 HttpSession session = request.getSession();
    	 session.removeAttribute("sessionCharger");
    	PageWrapper<Charger> page = new PageWrapper<>(chargerService.findAll(pageable), "/admin/chargers/");
    	model.addAttribute("page", page);
    	model.addAttribute("charger", charger);
        return TEMPLATE_INDEX_FILE;
    }
    /**
     * 返回station页面
     */
    @RequestMapping("/ret/{stationId}")
    public String ret(Model model,@PathVariable("stationId") Long stationId) {
    	if(stationId != null){
    		return "redirect:/admin/stations/edit/chargers/"+stationId;
    	}
        return "admin/charger/deal";
    }
    
//    @RequestMapping("/searchStation/{stationId}")
//    public String searchStation(Model model, @PathVariable("stationId") Long stationId, @ModelAttribute Charger charger,@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
//        String field[] = {"station","delStatus"};
//        if(stationId>0){
//        	Station station = new Station();
//        	station.setId(stationId);
//        	charger.setStation(station);
//        	PageWrapper<Charger> page = new PageWrapper<>(chargerService.findAllSearch(field,charger,pageable), "/admin/chargers/");
//            model.addAttribute("page", page);
//        }
//        return "admin/station/checkindex";
//    }
    
    @RequestMapping("/search")
    public String search(HttpServletRequest request,Model model, @ModelAttribute Charger charger,@PageableDefault(sort = "id", direction = Sort.Direction.DESC, size=15) Pageable pageable) {
    	 HttpSession session = request.getSession();
         if("1".equals(charger.getFlag())){
         	session.setAttribute("sessionCharger", charger);
         }else{
        	 Charger ch = (Charger)session.getAttribute("sessionCharger");
        	 if(ch!=null){
        		 charger = ch;
        	 }
         }
    	String field[] = {"name","type","station","chargeIf","delStatus"};
        String key="";
        for(String fieldName:field){
			Object o = ReflectField.getFieldValueByName(fieldName, charger);
			if(o!=null&&!"".equals(o)){
				if("station".equals(fieldName)){
					Station s = (Station)o;
					if(s!=null&&s.getId()!=null){
						key+=fieldName+"="+s.getId()+"&";
					}
				}else{
					key+=fieldName+"="+o.toString()+"&";
				}
			}
		}
        if(!StringUtils.isEmpty(key)){
    		key = key.substring(0,key.length()-1);
    	}
        PageWrapper<Charger> page = new PageWrapper<>(chargerService.findAllSearch(field,charger,pageable), "/admin/chargers/search?"+key);
        model.addAttribute("charger", charger);
        model.addAttribute("page", page);
        return TEMPLATE_INDEX_FILE;
    }

    @RequestMapping("/add")
    public String add(Model model,Long stationId) {
    	Charger charger = new Charger();
    	ChargerGun cg = new ChargerGun();
    	cg.setGunNo("10");
    	cg.setGunName("A枪");
    	charger.getGuns().add(cg);
    	Station station = null;
    	if(stationId != null){
    		station  = stationService.findById(stationId);
    		model.addAttribute("stationId", stationId); 
        	charger.setStation(station);
        }
        model.addAttribute("charger", charger);     
        return TEMPLATE_ADD_FILE;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String save(HttpServletRequest request,
			HttpSession session,Model model,@ModelAttribute Charger charger,String stationId, BindingResult result, RedirectAttributes redirectAttributes) {
    	String[] gunNos = request.getParameterValues("gunNos");
		String[] gunNames = request.getParameterValues("gunNames");
		String[] chargeIfs = request.getParameterValues("chargeIfs");
		String[] voltages = request.getParameterValues("voltages");
		String[] powers = request.getParameterValues("powers");
		String[] electricitys = request.getParameterValues("electricitys");
		String[] baks = request.getParameterValues("baks");
		List<ChargerGun> cgs = new ArrayList<>();
		for(int i = 0 ; i < gunNos.length ; i++){
			ChargerGun cg = new ChargerGun();
			cg.setGunNo(gunNos[i]);
			cg.setGunName(gunNames[i]);
			cg.setChargeIf(chargeIfs[i]);
			cg.setVoltage(voltages[i]);
			cg.setPower(powers[i]);
			cg.setElectricity(electricitys[i]);
			cg.setBak(baks[i]);
			cg.setStatus(ChargerStatus.OFFLINE);
			cgs.add(cg);
		}
    	if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("failed", "添加新电桩时时发生错误，请检查后重新尝试。");
            return TEMPLATE_ADD_FILE;
        }
        List<Charger> list = chargerService.findByCodeList(charger.getCode());
        List<Charger> list1 = chargerService.findByQrcodeList(charger.getQrcode());
        if(list.size()>=1&&list1.size()==0){
        	charger.setGuns(cgs);
        	model.addAttribute("charger", charger);
        	model.addAttribute("failed", "添加新充电桩时充电桩编码重复，请检查后重新尝试。");
        	 return "admin/charger/add";
        }else if (list1.size()>=1&&list.size()==0) {
        	charger.setGuns(cgs);
        	model.addAttribute("charger", charger);
        	model.addAttribute("failed", "添加新充电桩时充电桩二维码重复，请检查后重新尝试。");
        	 return "admin/charger/add";
		}else if (list1.size()>=1&&list.size()>=1) {
			charger.setGuns(cgs);
			model.addAttribute("charger", charger);
			model.addAttribute("failed", "添加新充电桩时充电桩二维码和编码重复，请检查后重新尝试。");
        	return "admin/charger/add";
		}else{
        	if(stationId != null&&stationId.trim().length()>0){
        		Station station = stationService.findById(Long.valueOf(stationId));
        		charger.setOperator(station.getOperator());
        		charger.setStation(station);
        	}
        	charger.setStatus(ChargerStatus.OFFLINE);
        	charger = chargerService.save(charger);
        	for(int i = 0 ; i < cgs.size() ; i++){
        		cgs.get(i).setCharger(charger);
        	}
        	charger.setGuns(cgs);
        	charger = chargerService.save(charger);
            EntityUtil.addCharger(
            		charger
            );
        }
        redirectAttributes.addFlashAttribute("success", MessageFormat.format("电桩 {0} 已经成功添加。", charger.getName()));
        if(stationId != null&&stationId.trim().length()>0){
        	return "redirect:/admin/stations/edit/chargers/"+stationId;
        }
        Manager manager = (Manager)session.getAttribute("user");
		ManagerLog log = new ManagerLog();
	 	log.setLoginName(manager.getLoginName());
	 	log.setRealName(manager.getRealname());
	 	log.setIp(request.getRemoteAddr());
	 	log.setLogTime(new Date());
	 	log.setLogModule(LogModule.CHARGER.name());
	 	log.setLogType(LogType.ADD.name());
	 	log.setParams("添加充电桩"+charger.getName());
	 	log.setStatus(true);
	    managerLogService.save(log);
        return "redirect:/admin/chargers/search";
    }

    @RequestMapping("/edit/{chargerId}")
    public String edit(@PathVariable("chargerId") Long chargerId, Model model) {
        model.addAttribute("charger", chargerService.findById(chargerId));
        return TEMPLATE_EDIT_FILE;
    }
    
    @RequestMapping("/check/{chargerId}")
    public String check(@PathVariable("chargerId") Long chargerId, Model model) {
    	Charger charger = chargerService.findById(chargerId);
    	String chargerCode = charger.getCode();
    	RCharger rcharger = (RCharger)redisUtil.get(RedisUtil.PREFIX_CHARGER + chargerCode);
    	if(rcharger != null) {
        	//=======设置枪状态========//
            List<RConnector> rConnectors = rcharger.getConnectorList();
            List<ChargerGun> cgs = charger.getGuns();
            if(null!=rConnectors&&rConnectors.size()>0){
            	for(int i = 0 ; i < rConnectors.size() ; i++){
            		for(int j = 0 ; j < cgs.size() ; j++){
            			if(cgs.get(j)==null||!cgs.get(j).getGunName().equals(rConnectors.get(i).getName())){
            				continue;
            			}
            			cgs.get(j).setStatus(rConnectors.get(i).getStatus());
            		}
            	}
            	charger.setGuns(cgs);
            }
            //=======设置枪状态========//
        }
        model.addAttribute("charger",charger );
        return TEMPLATE_CHECK_FILE;
    }
    @RequestMapping("/check1/{chargerId}/{stationId}")
    public String check1(@PathVariable("chargerId") Long chargerId,@PathVariable("stationId") Long stationId, Model model) {
        Charger charger = chargerService.findById(chargerId);
        String chargerCode = charger.getCode();
    	RCharger rcharger = (RCharger)redisUtil.get(RedisUtil.PREFIX_CHARGER + chargerCode);
    	if(rcharger != null) {
        	//=======设置枪状态========//
            List<RConnector> rConnectors = rcharger.getConnectorList();
            List<ChargerGun> cgs = charger.getGuns();
            if(null!=rConnectors&&rConnectors.size()>0){
            	for(int i = 0 ; i < rConnectors.size() ; i++){
            		for(int j = 0 ; j < cgs.size() ; j++){
            			if(cgs.get(j)==null||!cgs.get(j).getGunName().equals(rConnectors.get(i).getName())){
            				continue;
            			}
            			cgs.get(j).setStatus(rConnectors.get(i).getStatus());
            		}
            	}
            	charger.setGuns(cgs);
            }
            //=======设置枪状态========//
        }
    	model.addAttribute("charger", charger);
        model.addAttribute("stationId", stationId);
        return "admin/station/checkcharge";
    }

    @RequestMapping("/checkDeal/{id}")
    public String checkOrders(Model model,@PathVariable("id") Long id,
    		@PageableDefault(sort = "updatedAt", direction = Sort.Direction.DESC, size=15) Pageable pageable) {
    	PageWrapper<Order> page =
    			new PageWrapper<>(orderService.findByCharger(chargerService.findById(id), pageable), "/admin/chargers/checkDeal/"+id);
    	System.out.println(page.getContent());
		model.addAttribute("page", page);
		model.addAttribute("charger", chargerService.findById(id));
		model.addAttribute("order", new Order());
    	return "admin/charger/deal";
    }
    
    @RequestMapping(value="/searchDeal/{id}", method = RequestMethod.GET)
    public String search(Model model,HttpServletRequest req,
    		@ModelAttribute Order order,@PathVariable("id") Long id,
    		@PageableDefault(sort = "updatedAt", direction = Sort.Direction.DESC,size=15)Pageable pageable) {
		    	Charger	charger=chargerService.findById(id);
		    	if(order.getStartAt()!=null){
	    			order.setFormstartAt(VeDate.dateToStr(order.getStartAt()));
	    		}
	    		if(order.getEndAt()!=null){
	    			order.setFormendAt(VeDate.dateToStr(order.getEndAt()));
	    		}
	    		String key ="startAt="+order.getFormstartAt()+"&endAt="+order.getFormendAt();
	    		PageWrapper<Order> page = new PageWrapper<>(orderService.findAll(order, pageable),"/admin/chargers/searchDeal/"+id+"?"+key);
	    		model.addAttribute("page", page);
	    		model.addAttribute("charger", charger);
	    		model.addAttribute("order", order);
		    	return "admin/charger/deal";
    }
    /**
     * 交易记录固定时间的查询
     * @param model
     * @param req
     * @param pageable
     * @return
     */
    
    @RequestMapping(value="/searchTime/{id}/{days}", method = RequestMethod.GET)
    public String searchTime(Model model,
    		@PathVariable("id") Long id,
    		@PathVariable("days") Long days,
    		@PageableDefault(sort = "id", direction = Sort.Direction.DESC, size=15)Pageable pageable
    		) {
    	model.addAttribute("charger", chargerService.findById(id));
		PageWrapper<Order> page = 
				new PageWrapper<>(orderService.searchTime(days, id, pageable),"/admin/chargers/searchTime/"+id+"/"+days);
		model.addAttribute("page", page);
		model.addAttribute("order", new Order());
    	return "admin/charger/deal";
    	
    }
	
//    @RequestMapping("/del/{chargerId}")
//    public String del(@PathVariable("chargerId") Long chargerId, Model model) {
//    	Charger entity = chargerService.findById(chargerId);
//    	if(null!=entity){
//    		chargerService.del(entity);
//    	}
//    	return "redirect:/admin/chargers/";
//    }
    
    @RequestMapping("/del/{chargerId}")
    public String del(HttpServletRequest request, HttpSession session,@PathVariable("chargerId") Long chargerId, Model model) {
    	Charger entity = chargerService.findById(chargerId);
    	if(null!=entity){
    		entity.setDelStatus("del");
    		chargerService.delCharger(entity);
    	}
    	Manager manager = (Manager)session.getAttribute("user");
		ManagerLog log = new ManagerLog();
	 	log.setLoginName(manager.getLoginName());
	 	log.setRealName(manager.getRealname());
	 	log.setIp(request.getRemoteAddr());
	 	log.setLogTime(new Date());
	 	log.setLogModule(LogModule.CHARGER.name());
	 	log.setLogType(LogType.DELETE.name());
	 	log.setParams("删除充电桩"+entity.getName());
	 	log.setStatus(true);
	    managerLogService.save(log);
    	return "redirect:/admin/chargers/search";
    }
    /**
     * 
     * @param stationId  用于返回到station 页面
     * @param chargerId  删除charger
     * @param model
     * @return
     */
    @RequestMapping("/dels/{stationId}/{chargerId}")
    public String dels(HttpServletRequest request,
			HttpSession session,@PathVariable("stationId") Long stationId,@PathVariable("chargerId") Long chargerId, Model model) {
    	Charger entity = chargerService.findById(chargerId);
    	if(null!=entity){
    		entity.setDelStatus("del");
    		chargerService.delCharger(entity);
    	}
    	Manager manager = (Manager)session.getAttribute("user");
		ManagerLog log = new ManagerLog();
	 	log.setLoginName(manager.getLoginName());
	 	log.setRealName(manager.getRealname());
	 	log.setIp(request.getRemoteAddr());
	 	log.setLogTime(new Date());
	 	log.setLogModule(LogModule.CHARGER.name());
	 	log.setLogType(LogType.DELETE.name());
	 	log.setParams("删除充电桩"+entity.getName());
	 	log.setStatus(true);
	    managerLogService.save(log);
    	return "redirect:/admin/stations/edit/chargers/"+stationId;
    }
    
    @Transactional
    @RequestMapping(value = "/{chargerId}", method = RequestMethod.POST)
    public String update(HttpServletRequest request,
			HttpSession session,Model model,@PathVariable("chargerId") Long chargerId,
                         @Valid Charger form, BindingResult result, RedirectAttributes redirectAttributes) {
    
    	String[] gunNos = request.getParameterValues("gunNos");
		String[] gunNames = request.getParameterValues("gunNames");
		String[] chargeIfs = request.getParameterValues("chargeIfs");
		String[] voltages = request.getParameterValues("voltages");
		String[] powers = request.getParameterValues("powers");
		String[] electricitys = request.getParameterValues("electricitys");
		String[] baks = request.getParameterValues("baks");
		List<ChargerGun> cgs = new ArrayList<>();
		for(int i = 0 ; i < gunNos.length ; i++){
			ChargerGun cg = new ChargerGun();
			cg.setGunNo(gunNos[i]);
			cg.setGunName(gunNames[i]);
			cg.setChargeIf(chargeIfs[i]);
			cg.setVoltage(voltages[i]);
			cg.setPower(powers[i]);
			cg.setElectricity(electricitys[i]);
			cg.setBak(baks[i]);
			cg.setStatus(ChargerStatus.OFFLINE);
			cgs.add(cg);
		}
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("failed", "更新电桩时发生错误，请检查后重新尝试。");
            return TEMPLATE_EDIT_FILE;
        }

        Charger charger = chargerService.findById(chargerId);
        List<Charger> list1 =chargerService.findByQrcodeList(form.getQrcode());
        List<Charger> list = chargerService.findByCodeList(form.getCode());
        if(list.size()==1&&list1.size()==0){
        	if(list.get(0).getId()==charger.getId()){
        		 if(charger.getGuns()!=null&&charger.getGuns().size()>0){
        			 chargerGunService.deleteByCharger(charger);
        		 }
        		 tranBean(charger,form);
        		 charger.setGuns(cgs);
        		 for(int i = 0 ; i < cgs.size() ; i++){
            		 cgs.get(i).setCharger(charger);
            	 }
        		 EntityUtil.addCharger(
	                 chargerService.save(charger)
	             );
        	}else{
        		model.addAttribute("failed", "添加充电桩时充电桩编码重复，请检查后重新尝试。");
        		model.addAttribute("charger", charger);
        		return TEMPLATE_EDIT_FILE;
        	}
        }else if(list.size()==1&&list1.size()==1){
        	 if(list1.get(0).getId()==charger.getId()&&list.get(0).getId()==charger.getId()){
       		 	if(charger.getGuns()!=null&&charger.getGuns().size()>0){
       			 chargerGunService.deleteByCharger(charger);
       		 }
       		 tranBean(charger,form);
       		 charger.setGuns(cgs);
       		 for(int i = 0 ; i < cgs.size() ; i++){
           		 cgs.get(i).setCharger(charger);
           	 }
       		 EntityUtil.addCharger(
	                 chargerService.save(charger)
	             );
        	}else if (list1.get(0).getId()!=charger.getId()&&list.get(0).getId()==charger.getId()) {
        		model.addAttribute("failed", "添加充电桩时充电桩二维码重复，请检查后重新尝试。");
        		model.addAttribute("charger", charger);
        		return TEMPLATE_EDIT_FILE;
			}else if (list1.get(0).getId()==charger.getId()&&list.get(0).getId()!=charger.getId()) {
        		model.addAttribute("failed", "添加充电桩时充电桩编码重复，请检查后重新尝试。");
        		model.addAttribute("charger", charger);
        		return TEMPLATE_EDIT_FILE;
			}else {
        		model.addAttribute("failed", "添加充电桩时充电桩二维码和编码重复，请检查后重新尝试。");
        		model.addAttribute("charger", charger);
        		return TEMPLATE_EDIT_FILE;
        	}
        }else if(list.size()==0&&list1.size()==1){
        	if(list1.get(0).getId()==charger.getId()){
       		 	if(charger.getGuns()!=null&&charger.getGuns().size()>0){
       			 chargerGunService.deleteByCharger(charger);
       		 	}
       		 	tranBean(charger,form);
       		 	charger.setGuns(cgs);
       		 	for(int i = 0 ; i < cgs.size() ; i++){
       		 		cgs.get(i).setCharger(charger);
       		 	}
       		 	EntityUtil.addCharger(
	                 chargerService.save(charger)
	            );
        	}else{
        		model.addAttribute("failed", "添加充电桩时充电桩二维码重复，请检查后重新尝试。");
        		model.addAttribute("charger", charger);
        		return TEMPLATE_EDIT_FILE;
        	}
        }else if(list.size()==0&&list1.size()==0){
        	if(charger.getGuns()!=null&&charger.getGuns().size()>0){
   			 chargerGunService.deleteByCharger(charger);
   		 	 }
        	 tranBean(charger,form);
        	 charger.setGuns(cgs);
        	 for(int i = 0 ; i < cgs.size() ; i++){
        		 cgs.get(i).setCharger(charger);
        	 }
        	 EntityUtil.addCharger(
                 chargerService.save(charger)
             );
        }
        
        
        Manager manager = (Manager)session.getAttribute("user");
		ManagerLog log = new ManagerLog();
	 	log.setLoginName(manager.getLoginName());
	 	log.setRealName(manager.getRealname());
	 	log.setIp(request.getRemoteAddr());
	 	log.setLogTime(new Date());
	 	log.setLogModule(LogModule.CHARGER.name());
	 	log.setLogType(LogType.EDIT.name());
	 	log.setParams("修改充电桩"+charger.getName());
	 	log.setStatus(true);
	    managerLogService.save(log);
        redirectAttributes.addFlashAttribute("success", MessageFormat.format("电桩 {0} 更新成功。", charger.getName()));
        return "redirect:/admin/chargers/search";
    }
    
    
    
    public void tranBean(Charger charger,Charger form){
    	charger.setName(form.getName());
        charger.setCode(form.getCode());
        charger.setParkNo(form.getParkNo());
        charger.setManufacturer(form.getManufacturer());
        charger.setType(form.getType());
        charger.setChargeIf(form.getChargeIf());
        charger.setVoltage(form.getVoltage());
        charger.setElectricity(form.getElectricity());
        charger.setPower(form.getPower());
        charger.setSupportCars(form.getSupportCars());
        charger.setCertified(form.getCertified());
        charger.setOnlined(form.getOnlined());
        charger.setDescription(form.getDescription());
        charger.setStation(form.getStation());
        charger.setOnorder(form.getOnorder());
        charger.setOperator(form.getOperator());
        charger.setChargerModel(form.getChargerModel());
        charger.setNetingWorkModel(form.getNetingWorkModel());
        charger.setQrcode(form.getQrcode());;
    }
    @RequestMapping("applications/")
    public String applications() {
        return "admin/charger/applications";
    }

    @RequestMapping("reports/")
    public String reports() {
        return "admin/charger/reports";
    }

    @RequestMapping("map")
    public String map() {
        return "admin/charger/map";
    }
    
    @RequestMapping("/param/{chargerId}")
    public String params(@PathVariable("chargerId") Long chargerId, Model model) {
        ParamTemplate paramTemplate = paramTemplateService.findChargerPrice(chargerId);
        ParamTemplate paramTemplate1 = paramTemplateService.findChargerParam(chargerId);
        List<ParamTemplateAttr> attr = paramTemplateAttrService.findByTemplate(paramTemplate);
        List<ParamTemplateAttr> attrs = paramTemplateAttrService.findByTemplate(paramTemplate1);
        List<Price> prices =new ArrayList<>();
		setPrice(attr,prices);
        model.addAttribute("id", chargerId);
        model.addAttribute("prices", prices);
        model.addAttribute("attrs", attrs);
        return "admin/charger/param";
    }
    //excel导出
    @RequestMapping(value="/excle_output")
    public ResponseEntity<InputStreamResource> output(String name,String stationId,String ctype,
    		String cif,HttpServletRequest request){
 	   Map<String,String> conditionMap = new HashMap<>();
 	   conditionMap.put("name", name);
 	   conditionMap.put("stationId", stationId);
 	   conditionMap.put("ctype", ctype);
 	   conditionMap.put("cif", cif);
 	   List<Charger> cards = chargerService.findByCondition(conditionMap, new Charger());
        ExcelUtil eu = ExcelUtil.getInstance();
        String files = System.getProperty("user.dir");
        File fileChild = new File(files);
        File parentFile = fileChild.getParentFile();
        String path = parentFile.getPath()+File.separator+"excel"+File.separator+"chargers"+Utils.dateChangeToNumber(new Date())+".xls";
        File file = new File(path);
        ManagerLog log = Utils.setLog(request, "CHARGER", "EXPORT", "充电桩列表导出");
        if(!file.exists()){
     	   if(!file.getParentFile().exists()){
     		   file.getParentFile().mkdirs();
     	   }
     	   try {
 			file.createNewFile();
 		   } catch (IOException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 				log.setStatus(false);
 				managerLogService.save(log);
 		   }
        }
        Map<String, String> map = new HashMap<>();
        map.put("title", "充电桩列表");
        map.put("total", cards.size()+" 条");
        map.put("date", Utils.getDate());
        OutputStream os=null;
        try {
 			os = new FileOutputStream(file);
 		} catch (FileNotFoundException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 			log.setStatus(false);
			managerLogService.save(log);
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 			log.setStatus(false);
			managerLogService.save(log);
 		}
        eu.exportObj2ExcelByTemplate(map, "/charger-info-template.xls", os, cards, Charger.class, true);
        FileSystemResource outfile = new FileSystemResource(path);  
        HttpHeaders headers = new HttpHeaders();  
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");  
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", outfile.getFilename()));  
        headers.add("Pragma", "no-cache");  
        headers.add("Expires", "0"); 
        log.setStatus(true);
		managerLogService.save(log);
        try {
 		return ResponseEntity  
 		           .ok()  
 		           .headers(headers)  
 		           .contentLength(outfile.contentLength())  
 		           .contentType(MediaType.parseMediaType("application/vnd-ms-excel"))  
 		           .body(new InputStreamResource(outfile.getInputStream()));
 		} catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 			log.setStatus(false);
 			managerLogService.save(log);
 		} 
        return null;
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
	 
	 /**
	  * 电桩通信日志
	  * @param start   查询条件: 开始时间
	  * @param end     查询条件：结束时间
	  * @param model
	  * @return
	  */
	 @RequestMapping(value="commLog/{chargerId}")
	 public String searchChargerCommLogs(
	         @PathVariable Long chargerId,
	         @RequestParam(required=false) Date startTime, 
	         @RequestParam(required=false) Date endTime, 
	         Model model, @PageableDefault(sort="logTime", direction=Direction.DESC, size=15)Pageable pageable) {
	     Charger charger = EntityUtil.getCharger(chargerId);
	     if(charger != null) {
    	     PageWrapper<ChargerCommLog> page = new PageWrapper<>(chargerCommLogService.search(charger.getCode(), startTime, endTime, pageable), "/admin/chargers/commLog");
    	     model.addAttribute("page"       , page);
    	     model.addAttribute("chargerId"  , chargerId);
    	     model.addAttribute("startTime"  , startTime);
    	     model.addAttribute("endTime"    , endTime);
	     }
	     return "admin/charger/commLog";
	 } 
}
