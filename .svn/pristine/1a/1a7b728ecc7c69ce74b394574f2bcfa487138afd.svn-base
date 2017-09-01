package com.iycharge.server.ccu.exec;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iycharge.server.admin.message.listener.MessageChannel;
import com.iycharge.server.ccu.msg.MsgAndExecAnnotation;
import com.iycharge.server.ccu.msg.MsgType;
import com.iycharge.server.ccu.msg.entity.ChargingRecord;
import com.iycharge.server.ccu.msg.request.ChargingRecordReq;
import com.iycharge.server.ccu.msg.response.ChargingRecordResp;
import com.iycharge.server.ccu.util.JConverter;
import com.iycharge.server.domain.entity.utils.RedisUtil;

import net.sf.json.JSONObject;

/**
 * 充电记录上传处理
 * @author bwang
 */
@MsgAndExecAnnotation(msgType=MsgType._0X_64)
@Component("MSG_" + MsgType._0X_64)
public class ChargingRecordExecutor extends RequestLogicExecutorBase {
    
    Logger logger = LoggerFactory.getLogger(ChargingRecordExecutor.class);
    
    @Autowired
    private RedisUtil redisUtil;
    
    @Override
    public void run() {
        ChargingRecordReq request = new ChargingRecordReq(MsgType._0X_64, msgObject);
        if(request.check()) {
            logger.info("ChargingRecord=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
            try {
                ChargingRecord record = request.parse();
                
                JSONObject obj =  new JSONObject();
                obj.put("chargerCode", record.getChargerNo());
                obj.put("startTime"  , record.getStartAt().getTime());
                obj.put("endTime"    , record.getEndAt().getTime());
                obj.put("chargeTime" , record.getChargeTime());
                obj.put("electric"   , record.getDegree());
                obj.put("money"      , record.getPayment());
                
                redisUtil.set(RedisUtil.PREFIX_ORDER + record.getOrderId(), obj.toString(), 24 * 3600L);
                
                redisUtil.sendMessage(MessageChannel.MSG_CHANNAL_RECORD, record);
                       
                ChargingRecordResp response = new ChargingRecordResp(MsgType._0X_65);
                Map<String, Object> respBody = new HashMap<String, Object>();
                respBody.put("chargerNo"  , record.getChargerNo());
                response.setResponseBody(respBody);
                response.format();
                
                channel.writeAndFlush(response.getMsgObject());

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //非法请求
            logger.error("invalid request=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
        }
    } 

}
