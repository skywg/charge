package com.iycharge.server.ccu.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.ccu.core.ClientChannelMap;
import com.iycharge.server.ccu.core.RespDispatcher;
import com.iycharge.server.ccu.msg.MsgType;
import com.iycharge.server.ccu.service.ParamSettingService;
import com.iycharge.server.domain.entity.charger.Charger;
import com.iycharge.server.domain.entity.price.ParamTemplate;
import com.iycharge.server.domain.entity.price.ParamTemplateAttr;
import com.iycharge.server.domain.entity.price.ParamType;
import com.iycharge.server.domain.entity.price.PriceTemplate;

import io.netty.channel.socket.SocketChannel;

/**
 * 电桩相关参数设置service接口实现类   
 * @author bwang
 */
@Service("MSG_ParamSettingServiceImpl")
public class ParamSettingServiceImpl implements ParamSettingService {

    @Override
    public void setChargerPrice(Date effectiveTime, PriceTemplate template, Set<String> chargerNoSet) throws Exception {
        if(chargerNoSet == null || chargerNoSet.size() == 0) {
            return;
        }
        
        Map<String, Object> msgObject = new HashMap<>();
        for(String chargerNo : chargerNoSet) {
            msgObject.clear();
            Charger charger  = EntityUtil.getCharger(Long.parseLong(chargerNo));
            
            if(charger != null) {
                if(ClientChannelMap.getSockeChannel(charger.getCode()) == null) {
                    continue;
                }
                msgObject.put("effectiveTime", effectiveTime);
                msgObject.put("template"     , template);
                
                RespDispatcher.submit(MsgType._0X_10, ClientChannelMap.getSockeChannel(charger.getCode()), msgObject);
            }            
        }
    }
       
    @Override
    public void setCharegerParam(Date effectiveTime, ParamTemplate template, Set<String> chargerIds)
            throws Exception {
        // TODO Auto-generated method stub
        if(chargerIds == null || chargerIds.isEmpty()) {
            return;
        }
        
        Map<String, Object> msgObject = new HashMap<>();
        for(String chargerId : chargerIds) {
            msgObject.clear();
            
            Charger charger  = EntityUtil.getCharger(Long.parseLong(chargerId));
            if(charger != null) {
                if(ClientChannelMap.getSockeChannel(charger.getCode()) == null) {
                    continue;
                }
                short msgType = -1;
                
                if(template.getType().equals(ParamType.PRICE.name())) {
                    //费率设置
                    msgType = MsgType._0X_10;                   
                    msgObject.put("template"     , template);
                } else if (template.getType().equals(ParamType.PARAM.name())) {
                    //远程参数设置
                    msgType = MsgType._0X_12;  
                    
                } else if(template.getType().equals(ParamType.INTERVAL.name())) {
                    //报文上报周期设置
                    msgType = MsgType._0X_14;
                    
                }else if (template.getType().equals(ParamType.UPDATE.name())) {
                    //升级设置，保留
                }
                
                msgObject.put("chargerCode"  , charger.getCode());
                msgObject.put("effectiveTime", effectiveTime);
                
                List<ParamTemplateAttr> paramTemplateAttrList = template.getParamList();
                Map<String, String> params = new HashMap<>();
                for(ParamTemplateAttr paramTemplateAttr : paramTemplateAttrList) {
                    params.put(paramTemplateAttr.getAttrName(), paramTemplateAttr.getAttrVal());
                }
                msgObject.put("params", params);
                
                if(msgType > 0) {
                    RespDispatcher.submit(msgType, ClientChannelMap.getSockeChannel(charger.getCode()), msgObject);
                }
            }
        }
        
    }

    @Override
    public void updateSettingResult(String chargerNo, boolean result) {
        
    }

    @Override
    public void chargingAuth(Map<String, Object> authObj) throws Exception {
        SocketChannel channel = ClientChannelMap.getSockeChannel(authObj.get("chargerNo").toString());
        if(channel != null) {
            RespDispatcher.submit(MsgType._0X_50, channel, authObj);
        }      
    }
}
