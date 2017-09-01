package com.iycharge.server.admin.controller;

import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.admin.controller.helper.PageWrapper;
import com.iycharge.server.domain.common.utils.ReflectField;
import com.iycharge.server.domain.entity.Image;
import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.account.Card;
import com.iycharge.server.domain.entity.admin.Manager;
import com.iycharge.server.domain.entity.admin.ManagerLog;
import com.iycharge.server.domain.entity.charger.Charger;
import com.iycharge.server.domain.entity.dict.CategoryConstant;
import com.iycharge.server.domain.entity.dict.DictData;
import com.iycharge.server.domain.entity.operator.Operator;
import com.iycharge.server.domain.entity.review.Review;
import com.iycharge.server.domain.entity.station.OperatingStatus;
import com.iycharge.server.domain.entity.station.Station;
import com.iycharge.server.domain.entity.utils.Utils;
import com.iycharge.server.domain.entity.utils.outputExcel.ExcelUtil;
import com.iycharge.server.domain.service.ChargerService;
import com.iycharge.server.domain.service.ManagerLogService;
import com.iycharge.server.domain.service.ManagerService;
import com.iycharge.server.domain.service.OperatorService;
import com.iycharge.server.domain.service.ReviewService;
import com.iycharge.server.domain.service.StationService;

import org.elasticsearch.common.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/stations")
public class StationController {

    @Autowired
    private StationService stationService;
    @Autowired
    private OperatorService operatorService;
    @Autowired
    private ChargerService chargerService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ManagerLogService managerLogService;
    // Template file path
    private static final String TEMPLATE_INDEX_FILE = "admin/station/index";
    private static final String TEMPLATE_ADD_FILE = "admin/station/add";
    private static final String TEMPLATE_EDIT_FILE = "admin/station/edit";
    private static final String TEMPLATE_CHECK_FILE = "admin/station/check";
    
    @ModelAttribute("chargeTypes")
    public List<DictData> chargerTypes() {
        return EntityUtil.getDictDatas(CategoryConstant.CHAEGER_TYPE);
    }
    @ModelAttribute("types")
    public List<DictData> types() {
    	List<DictData> lst = EntityUtil.getDictDatas(CategoryConstant.EQUIPMENT_TYPE);
        return lst != null?lst:(new ArrayList<DictData>());

    }
    @ModelAttribute("chargeIfs")
    public List<DictData> chargerIfMethods() {
    	List<DictData> lst = EntityUtil.getDictDatas(CategoryConstant.CONNECTOR_TYPE);
        return lst != null?lst:(new ArrayList<DictData>());
    }
    @ModelAttribute("stationTypes")
    public List<DictData> stationTypes() {
        return EntityUtil.getDictDatas(CategoryConstant.STATION_TYPE);
    }

    @ModelAttribute("payments")
    public List<DictData> paymentMethods() {
    	return EntityUtil.getDictDatas(CategoryConstant.PAYMENT_TYPE);
    }

    @ModelAttribute("areas")
    public List<DictData> areas() {
        return EntityUtil.getDictDatas(CategoryConstant.AREA_TYPE);
    }
    @ModelAttribute("chargeModels")
    public List<DictData> chargerModelMethods() {
    	List<DictData> lst = EntityUtil.getDictDatas(CategoryConstant.CHARGER_MODEL);
        return lst != null?lst:(new ArrayList<DictData>());
    }
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
    
    @ModelAttribute("operatingStatus")
    public List<OperatingStatus> operatingStatus() {
        return OperatingStatus.asList();
    }
    
    @RequestMapping("/")
    public String index(HttpServletRequest request,Station station,Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size=15) Pageable pageable,String delFlag) {
    	 HttpSession session = request.getSession();
    	 session.removeAttribute("sessionStation");
    	PageWrapper<Station> page = new PageWrapper<>(stationService.findAll(pageable), "/admin/stations/");
        model.addAttribute("page", page);
        model.addAttribute("station", station);
        if(delFlag!=null&&delFlag.equals("false")){
        	model.addAttribute("failed", "该充电站下存在充电桩，无法删除");
        }
        return TEMPLATE_INDEX_FILE;
    }

    @RequestMapping("/add")
    public String add(Model model) {
        model.addAttribute("station", new Station());
        
        return TEMPLATE_ADD_FILE;
    }
    
    @RequestMapping("/adds/{stationId}")
    public String addWithStation(Model model, @PathVariable("stationId") Long stationId) {
    	model.addAttribute("stationId", stationId);
        return "redirect:/admin/chargers/add?stationId="+stationId; 
    }
    
    @RequestMapping("/add/{operatorId}")
    public String addWithOperator(Model model, @PathVariable("operatorId") Long operatorId, Station station) {
        if (operatorId != null) {
            station.setOperator(operatorService.findById(operatorId));
        }
        model.addAttribute("station", station);

        return TEMPLATE_ADD_FILE;
    }
    /**
     * 添加充电站
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String save(Model model,HttpServletRequest request,@ModelAttribute Station station, BindingResult result, RedirectAttributes redirectAttributes,String action) {
        //记录添加充电站日志
    	
    	//log.set
    	if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("failed", "添加新电站时时发生错误，请检查后重新尝试。");
            return TEMPLATE_ADD_FILE;
        }
        List<Station> list = stationService.findByCode(station.getCode());
        if(list.size()>=1){
        	model.addAttribute("station", station);
        	model.addAttribute("failed", "添加新充电站时充电站编码重复，请检查后重新尝试。");
        	 return "admin/station/add";
        }else{
        	ManagerLog log  = Utils.setLog(request, "STATION", "ADD", "添加充电站:"+station.getName());
        	station = stationService.save(station);
            EntityUtil.addStation(
            		station
            );
            log.setStatus(true);
            managerLogService.save(log);
        }
//        if (station.getOperator() != null) {
//            return "redirect:/admin/operators//" + station.getOperator().getId() + "/stations/";
//        }
        redirectAttributes.addFlashAttribute("success", MessageFormat.format("站点 {0} 已经成功添加。", station.getName()));
        
        return "redirect:/admin/stations/search";
    }

    @RequestMapping("/edit/{stationId}")
    public String edit(@PathVariable("stationId") Long stationId, Model model) {
        model.addAttribute("station", stationService.findById(stationId));
        return TEMPLATE_EDIT_FILE;
    }
    
    @RequestMapping("/check/{stationId}")
    public String check(@PathVariable("stationId") Long stationId, Model model) {
        model.addAttribute("station", stationService.findById(stationId));
        return TEMPLATE_CHECK_FILE;
    }

    @RequestMapping(value = "/{stationId}", method = RequestMethod.POST)
    public String update(Model model,@PathVariable("stationId") Long stationId,
                         @Valid Station form, BindingResult result, RedirectAttributes redirectAttributes,HttpServletRequest request) {
    	//记录更新充电站日志
    	
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("failed", "更新电站时发生错误，请检查后重新尝试。");
            return TEMPLATE_EDIT_FILE;
        }

        Station station = stationService.findById(stationId);
        
        List<Station> list = stationService.findByCode(form.getCode());
        if(list.size()==1){
        	if(list.get(0).getId()==station.getId()){
        		 tranBean(station,form);
        		 EntityUtil.addStation(
	                 stationService.save(station)
	             );
        		 ManagerLog log = Utils.setLog(request, "STATION", "EDIT", "更新充电站:"+form.getName());
                 log.setStatus(true);
                 managerLogService.save(log);
        	}else{
        		model.addAttribute("failed", "添加充电站时充电站编码重复，请检查后重新尝试。");
        		model.addAttribute("station", station);
        		return TEMPLATE_EDIT_FILE;
        	}
        }else if(list.size()==0){
        	 tranBean(station,form);
             EntityUtil.addStation(
                 stationService.save(station)
             );
             ManagerLog log = Utils.setLog(request, "STATION", "EDIT", "更新充电站:"+form.getName());
             log.setStatus(true);
             managerLogService.save(log);
        }else{
        	model.addAttribute("station", station);
        	redirectAttributes.addFlashAttribute("failed", "添加充电站时充电站编码重复，请检查后重新尝试。");
       	 	return "redirect:/admin/stations/add";
        }

        redirectAttributes.addFlashAttribute("success", MessageFormat.format("电站 {0} 更新成功。", station.getName()));
        return "redirect:/admin/stations/search";
    }

    public void tranBean(Station station,Station form){
    	station.setName(form.getName());
        station.setDescription(form.getDescription());
        station.setCode(form.getCode());
        station.setOperator(form.getOperator());
        station.setChargerType(form.getChargerType());
        station.setProvince(form.getProvince());
        station.setCity(form.getCity());
        station.setDistrict(form.getDistrict());
        station.setAddress(form.getAddress());
        station.setPrice(form.getPrice());
        station.setFee(form.getFee());
        station.setOpenTime(form.getOpenTime());
        station.setStationType(form.getStationType());
        station.setPaymentMethod(form.getPaymentMethod());
        station.setArea(form.getArea());

        station.setLongitude(form.getLongitude());
        station.setLatitude(form.getLatitude());
        station.setOperatingStatus(form.getOperatingStatus());
        
    }
    
    @RequestMapping("/check/chargers/{stationId}")
    public String checkChargers(Model model, @PathVariable("stationId") Long stationId, @ModelAttribute Charger charger,@PageableDefault(sort = "id", direction = Sort.Direction.DESC, size=15) Pageable pageable) {
        String field[] = {"station","delStatus"};
        if(stationId>0){
        	Station station = new Station();
        	station.setId(stationId);
        	charger.setStation(station);
        	PageWrapper<Charger> page = new PageWrapper<>(chargerService.findAllSearch(field,charger,pageable), "/admin/stations/check/chargers/"+stationId);
            model.addAttribute("page", page);
            model.addAttribute("station", stationService.findById(stationId));
        }
        return "admin/station/checkindex";
    }
    
    @RequestMapping("/check/images/{stationId}")
    public String checkImages(@PathVariable("stationId") Long stationId, Model model) {
        model.addAttribute("station", stationService.findById(stationId));
        model.addAttribute("image", new Image());
        return "admin/station/checkimages";
    }
    @RequestMapping("/check/reviews/{stationId}")
    public String checkReviews(Model model, @PathVariable("stationId") Long stationId, @ModelAttribute Review review,@PageableDefault(sort = "id", direction = Sort.Direction.DESC, size=15) Pageable pageable) {
    	String fields[] = {"updatedAt","name","realName"};
    	Station station = stationService.findById(stationId);
    	review.setStation(station);
    	PageWrapper<Review> page = new PageWrapper<>(reviewService.findAllSearch(fields,review,pageable), "/admin/stations/check/reviews/"+stationId);
    	model.addAttribute("station", stationService.findById(stationId));
    	model.addAttribute("page", page);
    	return "admin/station/checkreviews";
    }
    
    @RequestMapping("/edit/chargers/{stationId}")
    public String editChargers(Model model, @PathVariable("stationId") Long stationId, @ModelAttribute Charger charger,@PageableDefault(sort = "id", direction = Sort.Direction.DESC, size=15) Pageable pageable) {
        String field[] = {"station","delStatus"};
        if(stationId>0){	
        	Station station = new Station();
        	station.setId(stationId);
        	charger.setStation(station);
        	PageWrapper<Charger> page = new PageWrapper<>(chargerService.findAllSearch(field,charger,pageable), "/admin/stations/edit/chargers/"+stationId);
            model.addAttribute("page", page);
            model.addAttribute("station", stationService.findById(stationId));
        }
        return "admin/station/editindex";
    }
    
    @RequestMapping("/edit/images/{stationId}")
    public String editImages(@PathVariable("stationId") Long stationId, Model model) {
        model.addAttribute("station", stationService.findById(stationId));
        model.addAttribute("image", new Image());
        return "admin/station/editimages";
    }
    
    @RequestMapping("/images/{stationId}")
    public String images(@PathVariable("stationId") Long stationId, Model model) {
        model.addAttribute("station", stationService.findById(stationId));
        model.addAttribute("image", new Image());
        return "redirect:/admin/stations/";
    }
    
    @RequestMapping("/images")
    public String images(Model model) {
        model.addAttribute("image", new Image());
        return "admin/station/images";
    }

    @RequestMapping(value = "/images/{stationId}", method = RequestMethod.POST)
    public String saveImages(@PathVariable("stationId") Long stationId, Model model, Image image,HttpServletRequest request) {
        if (image.getSrc() != null && !image.getSrc().equals("")) {
            Station station = stationService.findById(stationId);
            List<Image> images = station.getImages();
            String imageSrc[] = image.getSrc().split(",");
            for(int i = 0 ; i < imageSrc.length ; i++){
            	Image img = new Image();
            	img.setSrc(imageSrc[i]);
            	images.add(img);
            }  
            station.setImages(images);
            ManagerLog log = Utils.setLog(request, "STATION", "EDIT", "充电站图片更新");
            log.setStatus(true);
            stationService.save(station);
            managerLogService.save(log);
            model.addAttribute("station", station);
        }
        return "redirect:/admin/stations/images/" + stationId;
    }
    
    @RequestMapping("/del/{stationId}")
    public String del(@PathVariable("stationId") Long stationId, Model model,HttpServletRequest request) {
    	Station entity = stationService.findById(stationId);
    	List<Charger> ll = chargerService.findByStation(entity);
    	if(ll.size()>0){
    		ManagerLog log = Utils.setLog(request, "STATION", "DELETE", "充电站删除-充电站名称："+entity.getName());
    		log.setStatus(false);
    		managerLogService.save(log);
    		return "redirect:/admin/stations/?delFlag=false";
    	}
    	if(null!=entity){
    		
    		ManagerLog log = Utils.setLog(request, "STATION", "DELETE", "充电站删除-充电站名称："+entity.getName());
    		entity.setDelStatus("del");
    		stationService.delStation(entity);
    		log.setStatus(true);
    		managerLogService.save(log);
			if(ll!=null&&ll.size()>0){
				for(Charger charger:ll){
					ManagerLog tmp = Utils.setLog(request, "CHARGER", "DELETE", "充电站删除后删除对应站下的充电桩:"+charger.getName());
					tmp.setStatus(true);
					charger.setDelStatus("del");
					chargerService.delCharger(charger);
					managerLogService.save(tmp);
				}
			}
    	}else{
    		
    	}
    	return "redirect:/admin/stations/search";
    }
    
    @RequestMapping("/search")
    public String search(HttpServletRequest request,Model model, @ModelAttribute Station station,@PageableDefault(sort = "id", direction = Sort.Direction.DESC, size=15) Pageable pageable) {
    	HttpSession session = request.getSession();
        if("1".equals(station.getFlag())){
        	session.setAttribute("sessionStation", station);
        }else{
        	Station ch = (Station)session.getAttribute("sessionStation");
       	 if(ch!=null){
       		station = ch;
       	 }
        }
    	String []fields = {"stationType","name","province","delStatus"};
    	String key="";
        for(String fieldName:fields){
        	Object o = ReflectField.getFieldValueByName(fieldName, station);
			if(o!=null&&!"".equals(o)){
				key+=fieldName+"="+o.toString()+"&";
			}
        }
        if(!StringUtils.isEmpty(key)){
    		key = key.substring(0,key.length()-1);
    	}
    	PageWrapper<Station> page = new PageWrapper<>(stationService.findAllSearch(fields,station,pageable), "/admin/stations/search?"+key);
    	model.addAttribute("station", station);
    	model.addAttribute("page", page);
        return "admin/station/index";
    }
    //导出excel
    @RequestMapping(value="/excle_output")
    public ResponseEntity<InputStreamResource> output(String name,String provice,
    		String stype,HttpServletRequest request){
 	   Map<String,String> conditionMap = new HashMap<>();
 	   conditionMap.put("name",name);
 	   conditionMap.put("stype",stype);
 	   conditionMap.put("provice",provice);
 	   List<Station> cards = stationService.findByCondition(conditionMap,new Station());
        ExcelUtil eu = ExcelUtil.getInstance();
        String files = System.getProperty("user.dir");
        File fileChild = new File(files);
        File parentFile = fileChild.getParentFile();
        String path = parentFile.getPath()+File.separator+"excel"+File.separator+"stations"+Utils.dateChangeToNumber(new Date())+".xls";
        File file = new File(path);
        ManagerLog log = Utils.setLog(request, "STATION", "EXPORT", "充电站列表导出");
		log.setStatus(true);
		managerLogService.save(log);
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
        map.put("title", "充电站列表");
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
 		} catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
			log.setStatus(false);
			managerLogService.save(log);
 		}
        eu.exportObj2ExcelByTemplate(map, "/station-info-template.xls", os, cards, Station.class, true);
        FileSystemResource outfile = new FileSystemResource(path);  
        HttpHeaders headers = new HttpHeaders();  
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");  
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", outfile.getFilename()));  
        headers.add("Pragma", "no-cache");  
        headers.add("Expires", "0");  
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
}
