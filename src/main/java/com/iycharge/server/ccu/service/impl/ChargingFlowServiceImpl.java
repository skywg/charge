package com.iycharge.server.ccu.service.impl;

import java.util.Arrays;
import java.util.concurrent.Future;

import org.springframework.stereotype.Service;

import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.ccu.core.ClientChannelMap;
import com.iycharge.server.ccu.core.ReqDispatcher;
import com.iycharge.server.ccu.core.RespDispatcher;
import com.iycharge.server.ccu.msg.AbstractMsg;
import com.iycharge.server.ccu.msg.MsgType;
import com.iycharge.server.ccu.service.ChargingFlowService;
import com.iycharge.server.ccu.util.BCDUtil;
import com.iycharge.server.ccu.util.CheckSumUtil;
import com.iycharge.server.ccu.util.Utility;
import com.iycharge.server.domain.entity.charger.Charger;

import io.netty.channel.socket.SocketChannel;
import net.sf.json.JSONObject;

/**
 *
 * @author bwang
 */
@Service
public class ChargingFlowServiceImpl implements ChargingFlowService {
    
    @Override
    public Future<Object> sendAuthMsg(JSONObject params) {
        SocketChannel channel = getSocketChannel(params.getString("chargerCode"));
        if(channel != null) {
            if(Utility.getEnvVar("spring.profiles.active").equals("dev")) {
                respAuthMsg(channel, params);
            }            
            return RespDispatcher.submit(MsgType._0X_50, channel, params);
        } 
        return null;
    }


    @Override
    public Future<Object> sendStarChargerMsg(JSONObject params) {
        SocketChannel channel = getSocketChannel(params.getString("chargerCode"));
        if(channel != null) {
            if(Utility.getEnvVar("spring.profiles.active").equals("dev")) {
                respStartMsg(channel, params);
                sendChargerRealTimeMsg(channel, params, (byte)0x20);
                sendChargingRealTimeMsg(channel, params);
            }           
            return RespDispatcher.submit(MsgType._0X_54, channel, params);
        } 
        return null;
    }


    @Override
    public Future<Object> sendStopChargerMsg(JSONObject params) {
        SocketChannel channel = getSocketChannel(params.getString("chargerCode"));
        if(channel != null) {
            if(Utility.getEnvVar("spring.profiles.active").equals("dev")) {
                respStopMsg(channel, params);
                sendChargerRealTimeMsg(channel, params, (byte)0x10);
                sendChargingRecordMsg(channel, params);
            }             
            return RespDispatcher.submit(MsgType._0X_56, channel, params);
        } 
        return null;
    }
    
    private SocketChannel getSocketChannel(String chargerCode) {
        Charger charger = EntityUtil.getCharger(chargerCode);
        if(charger != null) {
            return ClientChannelMap.getSockeChannel(chargerCode);
        }
        return null;
    }
    
    /**
     * 
     * @param channel
     * @param params
     */
    public void respAuthMsg(SocketChannel channel, JSONObject params) {       
        final int LENGTH = 23;
        byte[] msgObject = new byte[LENGTH];
        int index = 0;       
        msgObject[index++] = AbstractMsg.MSG_HEAD;       
        msgObject[index++] = (byte)((LENGTH - 3) & 0xff); 
        msgObject[index++] = (byte)((LENGTH - 3) >>8 & 0xff);
        msgObject[index++] = (byte) MsgType._0X_51;
        
        //充电桩编号
        byte[] chargerNoBytes = BCDUtil.str2Bcd(params.get("chargerCode").toString());
        for(int i=0; i<8 - chargerNoBytes.length; i++) {
            msgObject[index++] = AbstractMsg.FILLED_VAL;
        }
        
        for(byte b : chargerNoBytes) {
            msgObject[index++] = b;
        }
        
        //流水号
        byte[] orderIdBytes = BCDUtil.str2Bcd(params.get("orderId").toString());
        for(int i=0; i<8 - orderIdBytes.length; i++) {
            msgObject[index++] = AbstractMsg.FILLED_VAL;
        }
        
        for(byte b : orderIdBytes) {
            msgObject[index++] = b;
        }
        
        msgObject[index++] = 0x01;
        
        msgObject[index++] = (byte)CheckSumUtil.makeChecksum(Arrays.copyOfRange(msgObject, 3, LENGTH-2));
        
        msgObject[index++] = AbstractMsg.MSG_FOOTER;
        try {
            ReqDispatcher.submit(channel, msgObject);
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void respStartMsg(SocketChannel channel, JSONObject params) {
        final int LENGTH = 24;
        byte[] msgObject = new byte[LENGTH];
        int index = 0;       
        msgObject[index++] = AbstractMsg.MSG_HEAD;       
        msgObject[index++] = (byte)((LENGTH - 3) & 0xff); 
        msgObject[index++] = (byte)((LENGTH - 3) >>8 & 0xff);
        msgObject[index++] = (byte) MsgType._0X_55;
        
        //充电桩编号
        byte[] chargerNoBytes = BCDUtil.str2Bcd(params.get("chargerCode").toString());
        for(int i=0; i<8 - chargerNoBytes.length; i++) {
            msgObject[index++] = AbstractMsg.FILLED_VAL;
        }
        
        for(byte b : chargerNoBytes) {
            msgObject[index++] = b;
        }
        
        msgObject[index++] = 0x10;
        
        //流水号
        byte[] orderBytes = BCDUtil.str2Bcd(params.get("orderId").toString());
        
        for(int i=0; i<8 - orderBytes.length; i++) {
            msgObject[index++] = AbstractMsg.FILLED_VAL;
        }
        
        for(byte b : orderBytes) {
            msgObject[index++] = b;
        }
        
        msgObject[index++] = 0x01;
        
        msgObject[index++] = (byte)CheckSumUtil.makeChecksum(Arrays.copyOfRange(msgObject, 3, LENGTH-2));
        
        msgObject[index++] = AbstractMsg.MSG_FOOTER;
        try {
            ReqDispatcher.submit(channel, msgObject);
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void respStopMsg(SocketChannel channel, JSONObject params) {
        final int LENGTH = 24;
        byte[] msgObject = new byte[LENGTH];
        int index = 0;       
        msgObject[index++] = AbstractMsg.MSG_HEAD;       
        msgObject[index++] = (byte)((LENGTH - 3) & 0xff); 
        msgObject[index++] = (byte)((LENGTH - 3) >>8 & 0xff);
        msgObject[index++] = (byte) MsgType._0X_57;
        
        //充电桩编号
        byte[] chargerNoBytes = BCDUtil.str2Bcd(params.get("chargerCode").toString());
        for(int i=0; i<8 - chargerNoBytes.length; i++) {
            msgObject[index++] = AbstractMsg.FILLED_VAL;
        }
        
        for(byte b : chargerNoBytes) {
            msgObject[index++] = b;
        }
        
        msgObject[index++] = 0x10;
        
        //流水号
        byte[] orderBytes = BCDUtil.str2Bcd(params.get("orderId").toString());
        
        for(int i=0; i<8 - orderBytes.length; i++) {
            msgObject[index++] = AbstractMsg.FILLED_VAL;
        }
        
        for(byte b : orderBytes) {
            msgObject[index++] = b;
        }
        
        msgObject[index++] = 0x01;
        
        msgObject[index++] = (byte)CheckSumUtil.makeChecksum(Arrays.copyOfRange(msgObject, 3, LENGTH-2));
        
        msgObject[index++] = AbstractMsg.MSG_FOOTER;
        
        try {
            ReqDispatcher.submit(channel, msgObject);
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void sendChargerRealTimeMsg(SocketChannel channel, JSONObject params, byte status) {
        final int LENGTH = 33;
        byte[] msgObject = new byte[LENGTH];
        int index = 0;       
        msgObject[index++] = AbstractMsg.MSG_HEAD;       
        msgObject[index++] = (byte)((LENGTH - 3) & 0xff); 
        msgObject[index++] = (byte)((LENGTH - 3) >>8 & 0xff);
        msgObject[index++] = (byte) MsgType._0X_40;
        
        //充电桩编号
        byte[] chargerNoBytes = BCDUtil.str2Bcd(params.get("chargerCode").toString());
        for(int i=0; i<8 - chargerNoBytes.length; i++) {
            msgObject[index++] = AbstractMsg.FILLED_VAL;
        }
        
        for(byte b : chargerNoBytes) {
            msgObject[index++] = b;
        }
        
        //充电接口
        msgObject[index++] = 0x10;
        
        //充电类型
        msgObject[index++] = 0x01;
        
        //充电接口工作状态
        
        msgObject[index++] = status;
        
        //车辆连接状态
        msgObject[index++] = (byte)(status > 0 ? 0x01 : 0x00);
        
        //输入 A相电压
        msgObject[index++] = 0x00;
        msgObject[index++] = 0x00;
        //输入 B相电压
        msgObject[index++] = 0x00;
        msgObject[index++] = 0x00;
        // 输入 C 相电压
        msgObject[index++] = 0x00;
        msgObject[index++] = 0x00;
        //输出电压 
        msgObject[index++] = 0x00;
        msgObject[index++] = 0x00;
        //输出电流 
        msgObject[index++] = 0x00;
        msgObject[index++] = 0x00;
        //电表表底 
        msgObject[index++] = 0x00;
        msgObject[index++] = 0x00;
        msgObject[index++] = 0x00;
        msgObject[index++] = 0x00;
        //生命周期
        msgObject[index++] = 0x00;

        msgObject[index++] = (byte)CheckSumUtil.makeChecksum(Arrays.copyOfRange(msgObject, 3, LENGTH-2));
        
        msgObject[index++] = AbstractMsg.MSG_FOOTER;
        
        try {
            ReqDispatcher.submit(channel, msgObject);
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void sendChargingRealTimeMsg(SocketChannel channel, JSONObject params) {
        final int LENGTH = 40;
        byte[] msgObject = new byte[LENGTH];
        int index = 0;       
        msgObject[index++] = AbstractMsg.MSG_HEAD;       
        msgObject[index++] = (byte)((LENGTH - 3) & 0xff); 
        msgObject[index++] = (byte)((LENGTH - 3) >>8 & 0xff);
        msgObject[index++] = (byte) MsgType._0X_42;
        
        //充电桩编号
        byte[] chargerNoBytes = BCDUtil.str2Bcd(params.get("chargerCode").toString());
        for(int i=0; i<8 - chargerNoBytes.length; i++) {
            msgObject[index++] = AbstractMsg.FILLED_VAL;
        }
        
        for(byte b : chargerNoBytes) {
            msgObject[index++] = b;
        }
        
        //充电接口
        msgObject[index++] = 0x10;
               
        //电压需求 
        msgObject[index++] = 0x48;
        msgObject[index++] = 0x18;
        //电流需求 
        msgObject[index++] = 0x68;
        msgObject[index++] = 0x08;
        
        //充电模式 
        msgObject[index++] = 0x01;
        
        //电池侧充电电压测量值
        msgObject[index++] = 0x00;
        msgObject[index++] = 0x00;
        //电池侧充电电流测量值 
        msgObject[index++] = 0x00;
        msgObject[index++] = 0x00;
        //最高单体动力蓄电池电压及其组号
        msgObject[index++] = 0x00;
        msgObject[index++] = 0x00;
        //当前电荷状态 SOC% 
        msgObject[index++] = 0x17;
        //估算剩余充电时间
        msgObject[index++] = 0x37;
        msgObject[index++] = 0x00;
        //充电电量 
        msgObject[index++] = 0x23;
        msgObject[index++] = 0x00;
        //充电时长
        msgObject[index++] = 0x23;
        msgObject[index++] = 0x10;
        
        //最高单体动力蓄电池电压所在编号
        msgObject[index++] = 0x00;
        
        //最高动力电池温度
        msgObject[index++] = 0x00;
        //最高温度检测点编号
        msgObject[index++] = 0x00;
        //最低动力蓄电池温度 
        msgObject[index++] = 0x00;
        //最低动力蓄电池温度 检测点编号 
        msgObject[index++] = 0x00;
        //
        msgObject[index++] = 0x00;
        msgObject[index++] = 0x00;
        
        msgObject[index++] = (byte)CheckSumUtil.makeChecksum(Arrays.copyOfRange(msgObject, 3, LENGTH-2));
        
        msgObject[index++] = AbstractMsg.MSG_FOOTER;
        
        try {
            ReqDispatcher.submit(channel, msgObject);
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void sendChargingRecordMsg(SocketChannel channel, JSONObject params) {
        final int LENGTH = 143;
        byte[] msgObject = new byte[LENGTH];
        int index = 0;       
        msgObject[index++] = AbstractMsg.MSG_HEAD;       
        msgObject[index++] = (byte)((LENGTH - 3) & 0xff); 
        msgObject[index++] = (byte)((LENGTH - 3) >>8 & 0xff);
        msgObject[index++] = (byte) MsgType._0X_64;
        
        //充电桩编号
        byte[] chargerNoBytes = BCDUtil.str2Bcd(params.get("chargerCode").toString());
        for(int i=0; i<8 - chargerNoBytes.length; i++) {
            msgObject[index++] = AbstractMsg.FILLED_VAL;
        }
        
        for(byte b : chargerNoBytes) {
            msgObject[index++] = b;
        }
        
        //充电接口 
        msgObject[index++] = 0x10;
        
        //充电接口类型
        msgObject[index++] = 0x02;
        
        //充电认证方式 
        msgObject[index++] = 0x02;
        
        //卡号/流水号 
        byte[] orderIdBytes = BCDUtil.str2Bcd(params.get("orderId").toString());
        for(int i=0; i<8 - orderIdBytes.length; i++) {
            msgObject[index++] = AbstractMsg.FILLED_VAL;
        }
        
        for(byte b : orderIdBytes) {
            msgObject[index++] = b;
        }
        
        //结算标记
        msgObject[index++] = 0x01;
        
        //开始充电时间 
        byte[] startTimeBytes = BCDUtil.str2Bcd("20161119105132");
        for(byte b : startTimeBytes) {
            msgObject[index++] = b;
        }
        
        //结束充电时间
        byte[] stopTimeBytes = BCDUtil.str2Bcd("20161119113132");
        for(byte b : stopTimeBytes) {
            msgObject[index++] = b;
        }
        
        //开始充电 SOC 
        msgObject[index++] = 0x09;
        //结束充电 SOC
        msgObject[index++] = 0x51;
        
        //本次充电电量 
        msgObject[index++] = (byte)0x80;
        msgObject[index++] = 0x57;
        
        //本次充电时长 
        msgObject[index++] = 0x60;
        msgObject[index++] = (byte)0x09;
        
        //时段数
        msgObject[index++] = 0x04;
        
        //时段1
        
        //时段 1开始时间
        byte[] startTime1Bytes = BCDUtil.str2Bcd("20161119000000");
        for(byte b : startTime1Bytes) {
            msgObject[index++] = b;
        }
        
        //时段 1结束时间 
        byte[] endTime1Bytes = BCDUtil.str2Bcd("20161119060000");
        for(byte b : endTime1Bytes) {
            msgObject[index++] = b;
        }
        
        //时段 1充电电量
        msgObject[index++] = 0x00;
        msgObject[index++] = 0x00;
        
        //时段 1电价 
        msgObject[index++] = 0x32;
        msgObject[index++] = 0x00;
        //时段 1服务费单价
        msgObject[index++] = 0x32;
        msgObject[index++] = 0x00;
        //时段 1消费
        msgObject[index++] = 0x00;
        msgObject[index++] = 0x00;
        msgObject[index++] = 0x00;
        msgObject[index++] = 0x00;
        
        //时段2
        //时段 2开始时间
        byte[] startTime2Bytes = BCDUtil.str2Bcd("20161119060000");
        for(byte b : startTime2Bytes) {
            msgObject[index++] = b;
        }
        
        //时段 2结束时间 
        byte[] endTime2Bytes = BCDUtil.str2Bcd("20161119120000");
        for(byte b : endTime2Bytes) {
            msgObject[index++] = b;
        }
        
        //时段2充电电量
        msgObject[index++] = (byte)0x80;
        msgObject[index++] = 0x57;
        
        //时段 2电价 
        msgObject[index++] = 0x64;
        msgObject[index++] = 0x00;
        //时段2服务费单价
        msgObject[index++] = 0x64;
        msgObject[index++] = 0x00;
        //时段 1消费
        msgObject[index++] = 0x00;
        msgObject[index++] = (byte)0xaf;
        msgObject[index++] = 0x00;
        msgObject[index++] = 0x00;
        
        //时段3
        //时段 3开始时间
        byte[] startTime3Bytes = BCDUtil.str2Bcd("20161119120000");
        for(byte b : startTime3Bytes) {
            msgObject[index++] = b;
        }
        
        //时段 3结束时间 
        byte[] endTime3Bytes = BCDUtil.str2Bcd("20161119180000");
        for(byte b : endTime3Bytes) {
            msgObject[index++] = b;
        }
        
        //时段3充电电量
        msgObject[index++] = 0x00;
        msgObject[index++] = 0x00;
        
        //时段 3电价 
        msgObject[index++] = (byte)0x96;
        msgObject[index++] = 0x00;
        //时段 3服务费单价
        msgObject[index++] = (byte)0x96;
        msgObject[index++] = 0x00;
        //时段 3消费
        msgObject[index++] = 0x00;
        msgObject[index++] = 0x00;
        msgObject[index++] = 0x00;
        msgObject[index++] = 0x00;
        
        //时段4
        //时段 4开始时间
        byte[] startTime4Bytes = BCDUtil.str2Bcd("20161119180000");
        for(byte b : startTime4Bytes) {
            msgObject[index++] = b;
        }
        
        //时段 4结束时间 
        byte[] endTime4Bytes = BCDUtil.str2Bcd("20161119240000");
        for(byte b : endTime4Bytes) {
            msgObject[index++] = b;
        }
        
        //时段4充电电量
        msgObject[index++] = 0x00;
        msgObject[index++] = 0x00;
        
        //时段 4电价 
        msgObject[index++] = (byte)0xc8;
        msgObject[index++] = 0x00;
        //时段 4服务费单价
        msgObject[index++] = (byte)0xc8;
        msgObject[index++] = 0x00;
        //时段 4消费
        msgObject[index++] = 0x00;
        msgObject[index++] = 0x00;
        msgObject[index++] = 0x00;
        msgObject[index++] = 0x00;
        
        msgObject[index++] = (byte)CheckSumUtil.makeChecksum(Arrays.copyOfRange(msgObject, 3, LENGTH-2));
        
        msgObject[index++] = AbstractMsg.MSG_FOOTER;
        try {
            ReqDispatcher.submit(channel, msgObject);
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
