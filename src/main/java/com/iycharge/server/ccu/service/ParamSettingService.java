package com.iycharge.server.ccu.service;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import com.iycharge.server.domain.entity.price.ParamTemplate;
import com.iycharge.server.domain.entity.price.PriceTemplate;

/**
 * 电桩相关参数设置service接口   
 * @author bwang
 */
public interface ParamSettingService {
    
    /**
     * 电桩费率设置
     * @param effectiveTime    生效时间
     * @param template         电价模板
     * @param chargerNoSet     电桩编号集合
     * @throws Exception
     */
    @Deprecated
    void setChargerPrice(Date effectiveTime, PriceTemplate template, Set<String> chargerNoSet) throws Exception;
    
    /**
     * 电桩参数设置
     * @param effectiveTime     生效时间
     * @param template          参数模板
     * @param chargerIds        电桩Id集合
     * @throws Exception
     */
    void setCharegerParam(Date effectiveTime, ParamTemplate template, Set<String> chargerIds) throws Exception;
    
    
    /**
     * 更新参数设置结果
     * @param chargerNo     电桩编号
     * @param result        参数设置结果，成功(true)或失败(false)
     */
    void updateSettingResult(String chargerNo, boolean result);
    
    /**
     * 充电认证
     * @param authObj
     */
    void chargingAuth(Map<String,Object> authObj) throws Exception;
}
