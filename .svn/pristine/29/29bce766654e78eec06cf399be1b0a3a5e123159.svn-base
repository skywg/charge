package com.iycharge.server.ccu.exec;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.iycharge.server.ccu.msg.MsgAndExecAnnotation;
import com.iycharge.server.ccu.msg.MsgType;
import com.iycharge.server.ccu.msg.request.RespTimeSynSettingReq;
import com.iycharge.server.ccu.msg.response.PriceSetting;
import com.iycharge.server.ccu.util.JConverter;
import com.iycharge.server.ccu.util.Utility;
import com.iycharge.server.domain.entity.price.ParamNameConstant;


/**
 * 充电桩应答时间同步报文处理逻辑
 * @author bwang
 */
@MsgAndExecAnnotation(msgType=MsgType._0X_03)
@Component("MSG_" + MsgType._0X_03)
public class RespTimeSynSettingExecutor extends RequestLogicExecutorBase {
    
    Logger logger = LoggerFactory.getLogger(RespTimeSynSettingExecutor.class);
    
    @Override
    public void run() {       
        RespTimeSynSettingReq request = new RespTimeSynSettingReq(MsgType._0X_03, msgObject);
        if(request.check()) {     
            logger.info("TimeSynSettingResp=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
            try{
/*                //下发费率设置
                PriceSetting priceSetting = new PriceSetting(MsgType._0X_10);
                
                Map<String, Object> respbody = new HashMap<>();
    
                Map<String, String> params = new HashMap<>();           
                for(int i=0; i<4; i++) {
                    params.put(ParamNameConstant.PRICE_START_TIME + i, (i * 6 < 10 ? ("0" + i * 6) : i *6)  + ":00");
                    params.put(ParamNameConstant.PRICE_END_TIME + i  , ((i + 1) * 6 < 10 ? ("0" + (i + 1) * 6) : (i + 1) *6)  + ":00");
                    //params.put(ParamNameConstant.PRICE_PRICE + i     , String.valueOf(0.5 * (i + 1)));
                    //params.put(ParamNameConstant.PRICE_FEE + i           , String.valueOf(0.5 * (i + 1)));
                    params.put(ParamNameConstant.PRICE_PRICE + i         , "0.66" );
                    params.put(ParamNameConstant.PRICE_FEE + i           , "0.55");
                    params.put(ParamNameConstant.PRICE_REMARK + i        , null);
                }
                   
                respbody.put("params", params);
                respbody.put("effectiveTime", Utility.parseDate("2016-10-26 00:00:00", "yyyy-MM-dd HH:mm:ss"));
                
                priceSetting.setResponseBody(respbody);
                priceSetting.format();
                
                channel.writeAndFlush(priceSetting.getMsgObject());*/
            }catch(Exception e) {
                e.printStackTrace();
            }
        } else {
            //非法请求
            logger.error("invalid request=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
        }
    } 
}
