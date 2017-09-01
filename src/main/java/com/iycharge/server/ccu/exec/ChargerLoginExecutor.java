package com.iycharge.server.ccu.exec;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iycharge.server.admin.cache.RCharger;
import com.iycharge.server.admin.message.listener.MessageChannel;
import com.iycharge.server.ccu.core.ClientChannelMap;
import com.iycharge.server.ccu.msg.MsgAndExecAnnotation;
import com.iycharge.server.ccu.msg.MsgType;
import com.iycharge.server.ccu.msg.entity.ChargerCommLog;
import com.iycharge.server.ccu.msg.entity.ChargerLoginMsg;
import com.iycharge.server.ccu.msg.entity.LogType;
import com.iycharge.server.ccu.msg.request.ChargerLoginReq;
import com.iycharge.server.ccu.msg.response.TimeSyncSetting;
import com.iycharge.server.ccu.util.JConverter;
import com.iycharge.server.ccu.util.Utility;
import com.iycharge.server.domain.entity.charger.ChargerStatus;
import com.iycharge.server.domain.entity.utils.RedisUtil;

import io.netty.channel.socket.SocketChannel;
import net.sf.json.JSONObject;

/**
 * 充电桩登录报文识别处理
 * @author bwang
 */
@MsgAndExecAnnotation(msgType=MsgType._0X_01)
@Component("MSG_" + MsgType._0X_01)
public class ChargerLoginExecutor extends RequestLogicExecutorBase {
    
    Logger logger = LoggerFactory.getLogger(ChargerLoginExecutor.class);
    
    @Autowired
    private RedisUtil redisUtil;
    
    @Override
    public void run() {
        ChargerLoginReq request = new ChargerLoginReq(MsgType._0X_01, msgObject);
        if(request.check()) {
            logger.info("ChargerLoginMsg=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
            ChargerLoginMsg chargerLoginMsg = request.parse();                
            
            //服务器向电桩响应时间同步报文
            TimeSyncSetting serverTimeSyncResp = new TimeSyncSetting(MsgType._0X_02);
            Map<String, Object> respBody = new HashMap<String, Object>();
            respBody.put("chargerNo"  , chargerLoginMsg.getChargerNo());
            respBody.put("currentTime", Utility.formatDate(new Date(), "yyyyMMddHHmmss"));

            serverTimeSyncResp.setResponseBody(respBody);
            serverTimeSyncResp.format();         
            channel.writeAndFlush(serverTimeSyncResp.getMsgObject());
            
            //认证通过，保存电桩跟socketChannel的关系
            SocketChannel hisChannel = ClientChannelMap.getSockeChannel(chargerLoginMsg.getChargerNo());        
            if(hisChannel == null || hisChannel != channel) {
                ClientChannelMap.add(chargerLoginMsg.getChargerNo(), channel);
            }
            //保存或更新电桩的状态
            updateRCharger(chargerLoginMsg.getChargerNo());
            
            //存储上线日志
            ChargerCommLog log = new ChargerCommLog();
            log.setChargerCode(chargerLoginMsg.getChargerNo());
            log.setLogType(LogType.ONLINE);
            log.setLogTime(new Date());
            log.setParams("client:" + channel.remoteAddress());
            redisUtil.sendMessage(MessageChannel.MSG_CHANNAL_CONN, log);
        } else {
            //非法请求
            logger.error("invalid request=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
        }
    }
    
    /**
     * 更新电桩的状态
     * @param chargerNo
     */
    private void updateRCharger(String chargerNo) {
        try {
            RCharger rcharger = (RCharger)redisUtil.get(RedisUtil.PREFIX_CHARGER + chargerNo);
            if(rcharger == null) {
                rcharger = new RCharger();
                rcharger.setCode(chargerNo);
            }
            rcharger.setChargerStatus(ChargerStatus.IDLE);
            
            redisUtil.set(RedisUtil.PREFIX_CHARGER + chargerNo, rcharger);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
