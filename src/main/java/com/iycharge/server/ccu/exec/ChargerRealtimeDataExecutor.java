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
import com.iycharge.server.ccu.msg.entity.ChargerRealtimeData;
import com.iycharge.server.ccu.msg.request.ChargerRealtimeDataReq;
import com.iycharge.server.ccu.msg.response.ChargerRealtimeDataResp;
import com.iycharge.server.ccu.service.ChargerRealtimeDataService;
import com.iycharge.server.ccu.util.JConverter;
import com.iycharge.server.domain.entity.charger.ChargerStatus;
import com.iycharge.server.domain.entity.utils.RedisUtil;

/**
 * 充电桩实时数据上传处理
 * @author bwang
 */
@MsgAndExecAnnotation(msgType=MsgType._0X_40)
@Component("MSG_" + MsgType._0X_40)
public class ChargerRealtimeDataExecutor extends RequestLogicExecutorBase {

    Logger logger = LoggerFactory.getLogger(ChargerRealtimeDataExecutor.class);
    
    @Autowired
    private ChargerRealtimeDataService service;
    
    @Autowired
    private RedisUtil redisUtil;
    
    /*
     * 计数器，当电桩在充电时，每隔5次存储一次
     */
    int count = 0;
    @Override
    public void run() {
        ChargerRealtimeDataReq request = new ChargerRealtimeDataReq(MsgType._0X_40, msgObject);
        
        if(request.check()) {
            logger.info("ChargerRealtimeData=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
            try{
                ChargerRealtimeData data = request.parse();
                
                ChargerRealtimeDataResp response = new ChargerRealtimeDataResp(MsgType._0X_41);
                Map<String, Object> respBody = new HashMap<String, Object>();
                respBody.put("chargerNo"  , data.getChargerNo());
                response.setResponseBody(respBody);
                response.format();
                
                channel.writeAndFlush(response.getMsgObject());
                
                //记录电桩状态           
                RCharger    rcharger   = (RCharger)redisUtil.get(RedisUtil.PREFIX_CHARGER + data.getChargerNo());
                if(rcharger == null) {
                    return;
                }
                RConnector  rconnector = rcharger.getRConnectorByCode(data.getIfName());
                if(rconnector == null) {
                    rconnector = new RConnector();   
                    rconnector.setCode(data.getIfName());
                    rconnector.setName(getIfName(data.getIfName()));
                    rconnector.setType(data.getIfType()); 
                    rcharger.addRConnector(rconnector);
                }     
                //rconnector.setName(getIfName(data.getIfName()));
                rconnector.setStatus(convert(data.getIfWorkStatus()));
                rconnector.setOutputVoltage(data.getOutputVoltage());
                rconnector.setOutputCurrent(data.getOutputCurrent());  
                rconnector.setCarConnStatus(data.getCarConnStatus() > 0);
                
                rcharger.setChargerStatus(rconnector.getStatus());
                redisUtil.set(RedisUtil.PREFIX_CHARGER + rcharger.getCode(), rcharger);
                
                if(rconnector.getStatus() == ChargerStatus.CHARGING) {
                    //当电桩正在充电时才记录电桩上报的实时数据
                    if(++count % 5  == 0) {
                        service.save(data); 
                        count = 0;
                    }
                } 
            }catch (Exception e) {
                e.printStackTrace();
            }            
        } else {
            //非法请求
            logger.error("invalid request=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
        }
    }
    
    private ChargerStatus convert(String status) {
        //默认离线
        ChargerStatus chargerStatus = ChargerStatus.OFFLINE;
        
        if("10".equals(status)) {
            //空闲
            chargerStatus = ChargerStatus.IDLE;
        } else if("20".equals(status)) {
            //充电中
            chargerStatus = ChargerStatus.CHARGING;
        } else if("30".equals(status)) {
            //检修
            chargerStatus = ChargerStatus.REPAIR;
        } else if("40".equals(status)) {
            //占用
            chargerStatus = ChargerStatus.OCCUPIED;
        }
        
        return chargerStatus;
    }
    
    private String getIfName(String code) {
        if("10".equals(code)) {
            return "A枪";
        } else if("20".equals(code)) {
            return "B枪";
        }
        return null;
    }
}
