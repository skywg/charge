package com.iycharge.server.admin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
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
import com.iycharge.server.domain.common.utils.ReflectField;
import com.iycharge.server.domain.common.utils.VeDate;
import com.iycharge.server.domain.entity.account.Car;
import com.iycharge.server.domain.entity.account.CarAuditLog;
import com.iycharge.server.domain.entity.account.CarIdentifyStatus;
import com.iycharge.server.domain.entity.admin.Manager;
import com.iycharge.server.domain.entity.price.ParamTemplateQueryParam;
import com.iycharge.server.domain.service.CarAuditLogService;
import com.iycharge.server.domain.service.CarService;

@Controller
@RequestMapping("/admin/car")
public class CarIdentifyController {
	
	@Autowired
	private CarService carService;
	@Autowired
	private CarAuditLogService carAuditLogService;

	
	
	@RequestMapping("/")
    public String searchAll(HttpServletRequest request,Model model,@ModelAttribute Car car,@PageableDefault(sort = "updatedAt", direction = Sort.Direction.DESC, size = 15) Pageable pageable) {	    			
		 HttpSession session = request.getSession();
    	 session.removeAttribute("sessionCar");
		PageWrapper<Car> page = new PageWrapper<>(carService.findAll(pageable), "/admin/car/");
		model.addAttribute("page", page); 		
		model.addAttribute("car", car);
        return "admin/car/index";
    }
	
    @RequestMapping("/search")
    public String search(HttpServletRequest request,Model model, @ModelAttribute Car car,@PageableDefault(sort = "updatedAt", direction = Sort.Direction.DESC, size = 15) Pageable pageable) {
    	HttpSession session = request.getSession();
        if("1".equals(car.getFlag())){
        	session.setAttribute("sessionCar", car);
        }else{
        	Car ch = (Car)session.getAttribute("sessionCar");
       	 if(ch!=null){
       		car = ch;
       	 }
        }  
    	String fields[] = {"createdAt","realName","phone","carIdentifyStatus"};
    	 String key="";
         if(car.getAccount()!=null&&StringUtils.isNotEmpty(car.getAccount().getRealName())){
         	key+="account.realName="+car.getAccount().getRealName()+"&";
         }
         if(car.getAccount()!=null&&StringUtils.isNotEmpty(car.getAccount().getPhone())){
          	key+="account.phone="+car.getAccount().getPhone()+"&";
         }
         if(car.getStartAt()!=null){
        	car.setFormstartAt(VeDate.dateToStr(car.getStartAt()));
         	key+="startAt="+car.getFormstartAt()+"&";
         }
         if(car.getUpdatedAt()!=null){
        	 car.setFormupdateAt(VeDate.dateToStr(car.getUpdatedAt()));
          	key+="updatedAt="+car.getFormupdateAt()+"&";
          }
         if(car.getCarIdentifyStatus()!=null){
        	 key+="carIdentifyStatus="+car.getCarIdentifyStatus()+"&";
         }
         if(!StringUtils.isEmpty(key)){
     		key = key.substring(0,key.length()-1);
     	}
        PageWrapper<Car> page = new PageWrapper<>(carService.findAllSearch(fields,car,pageable), "/admin/car/search?"+key);
        model.addAttribute("page", page);
        model.addAttribute("car", car);
        return "admin/car/index";
    }
	
	/**
	 *查询认证信息
	 */
	@RequestMapping(value="/check/{id}")
	public String check(Model model,  @PathVariable("id") Long id) {
		Car car = carService.findById(id);
		if(car == null){
			car = new Car();
		}
		model.addAttribute("car",car);
		return "admin/car/check";
	}
	/**
	 * 认证信息处理
	 * @return
	 */
	@RequestMapping(value="/deal/{id}")
	public String deal(Model model,HttpSession session,@PathVariable("id") Long id){
		Car car = carService.findById(id);
		Manager manager = (Manager)session.getAttribute("user");
		model.addAttribute("date",new Date());
		model.addAttribute("manager",manager);
		model.addAttribute("car", car);
		return "admin/car/edit";
	}
	/**
	 *认证信息保存 
	 */
	@Transactional
	@RequestMapping(value="/save/{id}")
	public String save(Model model,HttpSession session,@PathVariable("id") Long id,String desc,String result){
		Car car = carService.findById(id);
		Manager manager = (Manager)session.getAttribute("user");
		//判定处理状态
		if(result.equals("true")){
			car.setCarIdentifyStatus(CarIdentifyStatus.PASSED);
		}else if(result.equals("false")){
			car.setCarIdentifyStatus(CarIdentifyStatus.FAILED);
		}
		//增加处理日志
		CarAuditLog ca = new CarAuditLog();
		ca.setAuditor(manager.getRealname());
		ca.setAuditTime(new Date());
		ca.setRemark(desc);
		ca.setResult(Boolean.valueOf(result));
		
		ca = carAuditLogService.save(ca);		
		List<CarAuditLog> carAuditLogList = car.getCarAuditLogList();
		if(carAuditLogList == null){
			carAuditLogList = new ArrayList<CarAuditLog>();
		}
		carAuditLogList.add(ca);
		carService.updateCarAndCarAuditLog(car, carAuditLogList);
		return "redirect:/admin/car/search";
	}
	
	
}
