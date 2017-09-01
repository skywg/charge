package com.iycharge.server.api.controller;

import com.iycharge.server.api.security.OneTimePasswordAuthenticator;
import com.iycharge.server.api.security.SmsApi_Send;
import com.iycharge.server.domain.entity.Authorization;
import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.account.AccountType;
import com.iycharge.server.domain.service.AccountService;
import com.iycharge.server.domain.service.MessageService;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.iycharge.server.domain.entity.utils.Status;

@RestController
public class AuthRestController {
    /*
     * 用户注册请求标记
     */
    private static final int  REGISTER = 1;
    /*
     * 用户找回密码请求标记
     */
    private static final int FORGET    = 2;
    /*
     * 用户绑定请求标记
     */
    private static final int BIND   = 3;
    
    private OneTimePasswordAuthenticator oneTimePasswordAuthenticator =new OneTimePasswordAuthenticator();

    @Autowired
    MessageService messageService;
    @Autowired
    private AccountService accountService;
    
    /**
     * 请求短信验证码
     * @param phone     手机号
     * @param type      用户请求类型（1：注册   2：找回密码 3:绑定）
     * @return
     */
    @RequestMapping(value = "/oauth/send-otp-code", method = RequestMethod.POST)
    public ResponseEntity<?> sendOtpCode(@RequestParam("phone") String phone ,@RequestParam("type") int type) {
        System.out.println(phone + ":" + type);
        //参数校验
        if(phone == null || !StringUtils.hasText(phone)) {
            return new ResponseEntity<>(Status.MISSING_PHONE.asMap(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        
        HttpHeaders httpHeaders = new HttpHeaders();

        try {
            Account account=accountService.findByPhone(phone);
            
        	//注册请求
        	if(type == REGISTER){       		
        	    if(account!=null){
        	        return  new ResponseEntity<>(Status.ALREADY_EXISTS.asMap(),HttpStatus.UNPROCESSABLE_ENTITY);	
        	    }
        	    httpHeaders.add("Code", SmsApi_Send.sendSMS(phone));         	
        	}
        	
        	//找回密码请求
        	if(type == FORGET){
        	    if(account == null) {
        	        return new ResponseEntity<>(Status.NOT_EXISTS.asMap(),HttpStatus.UNPROCESSABLE_ENTITY);
        	    }
        		 httpHeaders.add("Code", SmsApi_Send.sendSMS(phone));
        	} 
        	//绑定手机号请求
        	if (type==BIND) {
         		 httpHeaders.add("Code", SmsApi_Send.sendSMS(phone));
			}
        }
        catch (Exception e){       	
            return new ResponseEntity<>(Status.SERVER_ERROR.asMap(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return new ResponseEntity<>(Status.OK.asMap(), httpHeaders, HttpStatus.OK);
    }
    
    /**
     * 新用户注册/找回密码
     * @param phone         手机号
     * @param password      密码
     * @param code          系统下发的短信验证码
     */
   
    @RequestMapping(value = {"/api/user/register", "/api/user/forget"}, method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestParam("phone") String phone , 
            @RequestParam("password") String password ,
            @RequestParam("code") String code,
            @RequestParam("type") int type){
        //参数合法性检查：
        // 1 phone , password， 不能为空
        // 2 验证phone是否为有效的手机号码
    	if (phone == null || !StringUtils.hasText(phone)) {
    		
    	    return new ResponseEntity<>(Status.MISSING_PHONE.asMap(), HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (password == null || !StringUtils.hasText(password)) {
			
		    return new ResponseEntity<>(Status.MISSING_PASS.asMap(), HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		if (code == null || !StringUtils.hasText(code)) {
			
		    return new ResponseEntity<>(Status.CODE_FIELD.asMap(), HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
			//检查传入验证码是否为系统发出的验证码以及是否
		try {
			if (!oneTimePasswordAuthenticator.verifyCode(phone, Integer.parseInt(code), 2)) {
			    return new ResponseEntity<>(Status.INVALID_CODE.asMap(), HttpStatus.UNPROCESSABLE_ENTITY);
			}
			createOrUpdateAccountWithPhone(phone, password,type);	
		} catch (InvalidRequestException e) {
		    return new ResponseEntity<>(Status.BAD_REQUEST.asMap(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
		    return new ResponseEntity<>(Status.SERVER_ERROR.asMap(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
		return new ResponseEntity<>(Status.OK.asMap(), HttpStatus.OK);
		
	} 
  	private Account createOrUpdateAccountWithPhone(String phone, String password,int type) throws InvalidRequestException{
	
  		//密码进行加密
  		String passwd = DigestUtils.md5DigestAsHex(password.getBytes());
  		Account account = accountService.findByPhone(phone);
  		
  		if(REGISTER == type) {        
            //处理注册请求，添加用户
  		   account=new Account();
      	   account.setPhone(phone);
      	   account.setPassword(passwd);
      	   account.setAccountType(AccountType.PERSON.name());
      	   //account.setNickname(phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
      	   account.setAccountType("PERSON");
        } else if (FORGET == type){
            //处理找回密码的请求，修改用户密码
        	 account.setPassword(passwd);
        } else {
            //非法请求
        	throw new InvalidRequestException("非法用户请求");
        }
                  
  		return accountService.save(account);
  		
  	}
  	/**
  	 * 注销
  	 * @param principal
  	 * @return
  	 */
  	@RequestMapping(value="/api/user/logout")
  	public ResponseEntity<?> logout(Principal principal) {
  	  return new ResponseEntity<>(Status.OK.asMap(), HttpStatus.OK);
  	}
}
