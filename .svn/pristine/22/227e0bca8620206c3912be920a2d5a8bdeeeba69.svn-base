package com.iycharge.server.ccu.exec;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iycharge.server.admin.cache.RCharger;
import com.iycharge.server.admin.cache.RConnector;
import com.iycharge.server.ccu.msg.MsgAndExecAnnotation;
import com.iycharge.server.ccu.msg.MsgType;
import com.iycharge.server.ccu.msg.entity.ChargerStartEvent;
import com.iycharge.server.ccu.msg.request.ChargerStartEventReq;
import com.iycharge.server.ccu.msg.response.ChargerStartEventResp;
import com.iycharge.server.ccu.service.ChargerStartEventService;
import com.iycharge.server.ccu.util.JConverter;
import com.iycharge.server.domain.entity.account.Card;
import com.iycharge.server.domain.entity.account.CardStatus;
import com.iycharge.server.domain.entity.utils.RedisUtil;
import com.iycharge.server.domain.service.CardService;

/**
 * 电桩启动事件处理
 * @author bwang
 */
@MsgAndExecAnnotation(msgType=MsgType._0X_60)
@Component("MSG_" + MsgType._0X_60)
public class ChargerStartEventExecutor extends RequestLogicExecutorBase {
    
    Logger logger = LoggerFactory.getLogger(ChargerStartEventExecutor.class);
    
    @Autowired
    private ChargerStartEventService service;
    
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private CardService cardService;
    @Override
    public void run() {
        ChargerStartEventReq request = new ChargerStartEventReq(MsgType._0X_60, msgObject);
        if(request.check()) {   
            logger.info("ChargerStartEvent=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
            try {
                ChargerStartEvent startEvent = request.parse();
                
                ChargerStartEventResp response = new ChargerStartEventResp(MsgType._0X_61);
                Map<String, Object> respBody = new HashMap<String, Object>();
                respBody.put("chargerNo"  , startEvent.getChargerNo());
                response.setResponseBody(respBody);
                response.format();
                channel.writeAndFlush(response.getMsgObject());
                
                //===============修改卡状态====================
                /*if(startEvent.getAuthType() == 1) {
                    String cardNo = startEvent.getOrderId();                
                    Card card = cardService.findByCardNo(cardNo);
                    if(card!=null) {
                    	//设置为充电中
                    	card.setStatus(CardStatus.CHARGERING_CARD);
                    	cardService.save(card);
                    }                    
                }*/
                //===============修改卡状态====================
                               
                service.save(startEvent);
                
                //记录充电启动时间
                RCharger rcharger = (RCharger)redisUtil.get(RedisUtil.PREFIX_CHARGER + startEvent.getChargerNo());
                if(rcharger == null) {
                    return;
                }
                
                RConnector  rconnector = rcharger.getRConnectorByName(getIfName(startEvent.getIfName()));
                if(rconnector == null) {
                    return;
                }
                
                rconnector.setChargingStartTime(startEvent.getStartTime());
                
                redisUtil.set(RedisUtil.PREFIX_CHARGER + startEvent.getChargerNo(), rcharger);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //非法请求
            logger.error("invalid request=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
        }
    }
    
    private String getIfName(String code) {
        if("10".equals(code)) {
            return "A枪";
        } else if("20".equals(code)) {
            return "B枪";
        } else if("30".equals(code)) {
            return "C枪";
        }  else if("40".equals(code)) {
            return "D枪";
        }  
        return null;
    }
    
}
