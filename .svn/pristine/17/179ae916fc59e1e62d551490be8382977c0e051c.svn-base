package com.iycharge.server.domain.exception;

/**
 * 系统业务异常
 * @author bwang
 */
public class BusinessException extends ChargerException {

    /**
     * 
     */
    private static final long serialVersionUID = 2003189924479524040L;
    
    /**
     * 错误代号
     */
    private String code;
    
    /**
     * 错误描述
     */
    private String descr;
    
    /**
     * 指明   错误代号和错误描述
     * @param code      异常代号
     * @param descr     异常描述
     */
    public BusinessException(String code, String descr) {  
        super("BusinessException{c[" + code + "], d[" + descr + "]}");
        this.code  = code;
        this.descr = descr;
    }
    
    /**
     * 指明   错误代号和错误描述
     * @param code      异常代号
     * @param descr     异常描述
     * @param cause     异常产生的原因
     */
    public BusinessException(String code, String descr, Throwable cause) {
        super("BusinessException{c[" + code + "], d[" + descr + "]}", cause);
        this.code  = code;
        this.descr = descr;
    }
    
    /**
     * 返回错误代号 
     * @return
     */
    public String getCode() {
        return code;
    }
    
    /**
     * 返回错误描述
     * @return
     */
    public String getDescr() {
        return descr;
    }
}
