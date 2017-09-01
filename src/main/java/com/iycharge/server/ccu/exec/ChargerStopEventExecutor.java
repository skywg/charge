package com.iycharge.server.ccu.exec;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iycharge.server.ccu.msg.MsgAndExecAnnotation;
import com.iycharge.server.ccu.msg.MsgType;
import com.iycharge.server.ccu.msg.entity.ChargerStopEvent;
import com.iycharge.server.ccu.msg.request.ChargerStopEventReq;
import com.iycharge.server.ccu.msg.response.ChargerStopEventResp;
import com.iycharge.server.ccu.service.ChargerStopEventService;
import com.iycharge.server.ccu.util.JConverter;
import com.iycharge.server.domain.entity.account.Card;
import com.iycharge.server.domain.entity.account.CardStatus;
import com.iycharge.server.domain.entity.utils.RedisUtil;
import com.iycharge.server.domain.service.CardService;

import net.sf.json.JSONObject;

/**
 * 充电桩停机事件处理
 * @author bwang
 */
@MsgAndExecAnnotation(msgType=MsgType._0X_62)
@Component("MSG_" + MsgType._0X_62)
public class ChargerStopEventExecutor extends RequestLogicExecutorBase {
    
    Logger logger = LoggerFactory.getLogger(ChargerStopEventExecutor.class);
    
    @Autowired
    private ChargerStopEventService service;
    
    @Resource
    private RedisUtil redisUtil;
    @Autowired
    private CardService cardService;
    @Override
    public void run() {
        ChargerStopEventReq request = new ChargerStopEventReq(MsgType._0X_62, msgObject);
        if(request.check()) {
            logger.info("ChargerStopEvent=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
            ChargerStopEvent stopEvent = request.parse();
            
            ChargerStopEventResp response = new ChargerStopEventResp(MsgType._0X_63);
            Map<String, Object> respBody = new HashMap<String, Object>();
            respBody.put("chargerNo"  , stopEvent.getChargerNo());
            response.setResponseBody(respBody);
            response.format();
            
            channel.writeAndFlush(response.getMsgObject());
            //===============修改卡状态====================
            /*String cardNo = stopEvent.getOrderId();
            Card card = cardService.findByCardNo(cardNo);
            
            if(card!=null){
            	//如果状态是“充电中”,则设置为正常,其他状态不变
            	if(card.getStatus()!=null&&card.getStatus().getTitle().equals("充电中")){
            		card.setStatus(CardStatus.NORMAL);
            		cardService.save(card);
            	}
            }*/
            
            //===============修改卡状态====================
            if(stopEvent.getAuthType() == 2) {            
                JSONObject obj = new JSONObject();
                obj.put("chargerCode", stopEvent.getChargerNo());
                obj.put("chargerConn", stopEvent.getIfName());
                obj.put("orderId"    , stopEvent.getOrderId());
                obj.put("result"     , 1);
                obj.put("cause", stopEvent.getStopCause());
                redisUtil.set(RedisUtil.PREFIX_STOP + stopEvent.getOrderId(), obj.toString(), 24 * 3600L);
            }
            
            service.save(stopEvent);
        } else {
            //非法请求
            logger.error("invalid request=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
        }
    }
    
}
