package com.iycharge.server.domain.entity.utils;

import java.util.HashMap;
import java.util.Map;

public enum Status {
    
	INVALID(422,"phone","无效手机号"),
	ALREADY_EXISTS(422,"phone","手机号已被注册"),
	NOT_EXISTS(422, "phone", "手机号未注册"),
	CODE_FIELD(422,"phone","验证码没找到"),
	OK(200,"phone","请求成功"),
	MISSING_PHONE(422,"phone","手机号为空"),
	MISSING_PASS(422, "password", "密码为空"),
	INVALID_CODE(422, "code", "验证码无效"),
	BAD_REQUEST(400, "request", "非法请求"),
	CHECK_PASS(422, "password", "原密码输入错误"),
	CONFIRM_PASS(422, "password", "两次输入密码不一致"),
	SERVER_ERROR(500, null, "请求失败"),
    USER_NOT_EXIST(404, "user", "用户不存在"),
    USER_INVALID(423, "user", "用户被锁定"),
    LOGIN_FAILD(422, "user", "用户名和密码输入错误");
   
	//http错误状态码
	private int httpErroCode;
	
	//错误码
	private String errorCode;
	
	//错误原因描述
    private String errorDescr;
	
	
    private Status(int httpErrorCode, String errorCode, String errorDescr){
    	this.httpErroCode = httpErrorCode;
    	this.errorCode    = errorCode;
    	this.errorDescr   = errorDescr;
    }

    public int getHttpErroCode() {
        return httpErroCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorDescr() {
        return errorDescr;
    }
    
    public Map<String, Object> asMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("httpErrorCode", this.getHttpErroCode());
        map.put("errorCode", this.getErrorCode());
        map.put("errorDescr", this.getErrorDescr());
        return map;
    }
}
