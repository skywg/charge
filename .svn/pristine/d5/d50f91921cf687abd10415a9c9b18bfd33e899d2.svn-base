package com.iycharge.server.domain.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理类
 * @author bwang
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public Map<String, Object> businessExceptionHandler(
            HttpServletRequest req, HttpServletResponse resp, BusinessException e) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", e.getCode());
        result.put("descr", e.getDescr());
        if(e.getAdditionalInformation() != null) {
            result.putAll(e.getAdditionalInformation());
        }
        return result;
    }
}
