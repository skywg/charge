package com.iycharge.server.domain.exception;

/**
 * 使用社交账号登录，当社交账号没有绑定手机号抛出此异常
 * @author bwang
 */
public class UnBindPhoneException extends BusinessException {


    /**
     * 
     */
    private static final long serialVersionUID = -6341118840145177418L;
    
    /**
     * 指明 错误代号和错误描述      
     * @param code      异常代号    
     * @param descr     异常描述    
     */
    public UnBindPhoneException(String code, String descr) {
        super(code, descr);
    }
    
    /**
     * 指明 错误代号和错误描述      
     * @param code      异常代号    
     * @param descr     异常描述    
     * @param cause     异常产生的原因      
     */
    public UnBindPhoneException(String code, String descr, Throwable cause) {
        super(code, descr, cause);
    }
}
