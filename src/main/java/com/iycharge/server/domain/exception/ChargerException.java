package com.iycharge.server.domain.exception;

import java.util.Map;
import java.util.TreeMap;

/**
 *  charger项目所有内部异常类的基类
 * @author bwang
 */
public class ChargerException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 
     */
    private Map<String, String> additionalInformation = null;
    
    public ChargerException() {
        super();
    }
    
    /**
     * 
     * @param message
     */
    public ChargerException(String message) {
        super(message);
    }
    
    /**
     * 
     * @param message
     * @param e
     */
    public ChargerException(String message, Throwable e) {
        super(message, e);
    }

    /**
     * 
     * @return
     */
    public Map<String, String> getAdditionalInformation() {
        return this.additionalInformation;
    }
    
    /**
     * 
     * @param key
     * @param value
     */
    public void addAdditionalInformation(String key, String value) {
        if (this.additionalInformation == null) {
            this.additionalInformation = new TreeMap<String, String>();
        }

        this.additionalInformation.put(key, value);
    }
}
