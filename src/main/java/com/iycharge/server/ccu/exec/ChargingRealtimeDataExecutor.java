package com.iycharge.server.ccu.exec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iycharge.server.admin.cache.RCharger;
import com.iycharge.server.admin.cache.RConnector;
import com.iycharge.server.ccu.msg.MsgAndExecAnnotation;
import com.iycharge.server.ccu.msg.MsgType;
import com.iycharge.server.ccu.msg.entity.ChargingRealtimeData;
import com.iycharge.server.ccu.msg.request.ChargingRealtimeDataReq;
import com.iycharge.server.ccu.service.ChargingRealtimeDataService;
import com.iycharge.server.ccu.util.JConverter;
import com.iycharge.server.domain.entity.utils.RedisUtil;

/**
 * 充电桩充电实时数据上传处理
 * @author bwang
 */
@MsgAndExecAnnotation(msgType=MsgType._0X_42)
@Component("MSG_" + MsgType._0X_42)
public class ChargingRealtimeDataExecutor extends RequestLogicExecutorBase {
    
    Logger logger = LoggerFactory.getLogger(ChargingRealtimeDataExecutor.class);
    
    @Autowired
    private ChargingRealtimeDataService service;
    
    @Autowired
    private RedisUtil redisUtil;
    
    @Override
    public void run() {
        ChargingRealtimeDataReq request = new ChargingRealtimeDataReq(MsgType._0X_42, msgObject);
        if(request.check()) {
            logger.info("ChargingRealtimeData=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
            try {
                ChargingRealtimeData data = request.parse();
                //更新充电接口的数据
                RCharger rcharger = (RCharger)redisUtil.get(RedisUtil.PREFIX_CHARGER + data.getChargeNo()); 
                if(rcharger == null) {
                    return;
                }
                
                RConnector  rconnector = rcharger.getRConnectorByCode(data.getIfName());
                if(rconnector == null) {
                    return;
                }                           
                rconnector.setSocVal(data.getRealtimeSocStatus()); 
                rconnector.setChargingTime(data.getChargingTime());
                rconnector.setElectricity(data.getChargingNum().doubleValue());
                rconnector.setRemainedChargingTime(data.getRemainedChargingTime());
                
                redisUtil.set(RedisUtil.PREFIX_CHARGER + rcharger.getCode(), rcharger);
                
                //记录日志
                service.save(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //非法请求
            logger.error("invalid request=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
        }
    }
}
