package com.iycharge.server.ccu.exec;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.ccu.msg.MsgAndExecAnnotation;
import com.iycharge.server.ccu.msg.MsgType;
import com.iycharge.server.ccu.msg.entity.ChargerEvent;
import com.iycharge.server.ccu.msg.request.ChargerEventReq;
import com.iycharge.server.ccu.msg.response.ChargerEventResp;
import com.iycharge.server.ccu.service.ChargerEventService;
import com.iycharge.server.ccu.util.JConverter;
import com.iycharge.server.domain.entity.event.Event;
import com.iycharge.server.domain.service.EventService;

/**
 * 告警事件处理
 * @author bwang
 */
@MsgAndExecAnnotation(msgType=MsgType._0X_66)
@Component("MSG_" + MsgType._0X_66)
public class ChargerEventExecutor extends RequestLogicExecutorBase {
    
    Logger logger = LoggerFactory.getLogger(ChargerEventExecutor.class);
    
    @Autowired
    private ChargerEventService service;
    
    @Resource
    private EventService eventService;
    
    @Override
    public void run() {
        ChargerEventReq eventReq = new ChargerEventReq(MsgType._0X_66, msgObject);
        if(eventReq.check()) {
            logger.info("ChargerEvent=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
            ChargerEvent event = eventReq.parse();
            
            ChargerEventResp response = new ChargerEventResp(MsgType._0X_67);
            Map<String, Object> respBody = new HashMap<String, Object>();
            respBody.put("chargerNo"  , event.getChargerNo());
            response.setResponseBody(respBody);
            response.format();
            
            channel.writeAndFlush(response.getMsgObject());
            
            //记录日志
            service.save(event);
            
            //
            Event eventObj = new Event();
            eventObj.setCharger(EntityUtil.getCharger(event.getChargerNo()));
            eventObj.setStation(eventObj.getCharger().getStation().getName());
            eventObj.setEventTime(event.getEventTime());
            eventObj.setEventCode(EntityUtil.getEventCode((int)event.getEventCode()));
            eventService.save(eventObj);
            
        } else {
            //非法请求
            logger.error("invalid request=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
        }
    }

}
