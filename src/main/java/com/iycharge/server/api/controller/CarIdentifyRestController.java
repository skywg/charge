package com.iycharge.server.api.controller;

import java.security.Principal;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.domain.entity.BaseEntity;
import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.account.Car;
import com.iycharge.server.domain.entity.account.CarIdentifyStatus;
import com.iycharge.server.domain.entity.utils.Status;
import com.iycharge.server.domain.service.AccountService;
import com.iycharge.server.domain.service.CarService;

/**
 * 车辆认证
 * @author uwayxs
 *
 */

@RestController
@RequestMapping("/api/carIdentify")
public class CarIdentifyRestController {
	
	@Autowired
	private AccountService accountService;
	@Autowired
	private CarService carService;
	
	/**
	 * 新增/更新车辆认证
	 * @return
	 */
	@Transactional
	@RequestMapping(value="/updateOrAdd",method=RequestMethod.POST)
	@JsonView(BaseEntity.Summary.class)
	public ResponseEntity<?> add(@RequestBody Car request ,Principal principal){
		Account account = accountService.findById(Long.valueOf(principal.getName()));
		Car car = null;
		if(null != request && null != request.getId()){
			car = account.getCar();
			setBean(request, car);
		}else{
			Car data_car =account.getCar();
			if(data_car == null){
				request.setCarIdentifyStatus(CarIdentifyStatus.CHECKING);
				request.setAccount(account);
				account = carService.addAndUpdate(account, request);
				return new ResponseEntity<>(account.getCar(), HttpStatus.OK);
			}else{
				setBean(request, data_car);
				account = carService.addAndUpdate(account, data_car);
				return new ResponseEntity<>(account.getCar(), HttpStatus.OK);	
			}	
		}
		account = carService.addAndUpdate(account, car);
		return new ResponseEntity<>(account.getCar(), HttpStatus.OK);	
	}
	
    /**
     * 查询
     */
    @RequestMapping(value="/car",method=RequestMethod.GET)
    @JsonView(BaseEntity.Summary.class)
    public ResponseEntity<?> getCarByAccountId(Principal principal) {
    	Account account = accountService.findById(Long.valueOf(principal.getName()));
    	JSONObject retMsgJson = new JSONObject();
    	Car car = null;
    	if(null != account){
    		car = account.getCar();
    	}else{
    		return new ResponseEntity<>(Status.SERVER_ERROR.asMap(), HttpStatus.OK);
    	}
    	if(car == null){
    		retMsgJson.put("msg", "未认证");
    		String json = retMsgJson.toString();
    		return new ResponseEntity<>(json, HttpStatus.OK);
    	}
    	return new ResponseEntity<>(car, HttpStatus.OK);
    }
    
    private void setBean(Car request , Car source){
    	source.setCarBrand(request.getCarBrand());
    	source.setCarType(request.getCarType());
    	source.setFrameNumber(request.getFrameNumber());
    	source.setEngineNumber(request.getEngineNumber());
    	source.setPlateNumber(request.getPlateNumber());
    	source.setVinNumber(request.getVinNumber());	
    	source.setDrivingLicensePhoto(request.getDrivingLicensePhoto());
    	source.setCarIdentifyStatus(CarIdentifyStatus.CHECKING);
    }
}
