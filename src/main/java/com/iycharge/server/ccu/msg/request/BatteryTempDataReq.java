package com.iycharge.server.ccu.msg.request;

import java.util.Arrays;

import com.iycharge.server.ccu.msg.RequestMsgBase;
import com.iycharge.server.ccu.msg.entity.BatteryTempData;
import com.iycharge.server.ccu.util.BCDUtil;
import com.iycharge.server.ccu.util.JConverter;
import com.iycharge.server.ccu.util.OffsetCalcUtil;
import com.iycharge.server.ccu.util.OffsetCalcUtil.TemperatureCfg;

/**
 * 电池温度数据解析类
 * @author bwang
 */
public class BatteryTempDataReq extends RequestMsgBase<BatteryTempData>{
    
    /**
     * @param msgType
     * @param msgObject
     */
    public BatteryTempDataReq(short msgType, byte[] msgObject) {
        super(msgType, msgObject);
    }
    
    @Override
    public BatteryTempData parse() {
        BatteryTempData batteryTempData = new BatteryTempData();
        try {
        byte[] datas = Arrays.copyOfRange(msgObject, 4, msgObject.length - 2);        
        int index = 0;
        
        //充电桩编号
        batteryTempData.setChargerNo(BCDUtil.bcd2Str(Arrays.copyOfRange(datas, index, index += 8)));
        //充电接口 
        batteryTempData.setIfName(Integer.toString(datas[index], 16));
        StringBuilder sb = new StringBuilder();
        int temprature = 0;
        while(index < datas.length -1) {
            temprature = (datas[++index] & 0xFF) +  (datas[++index] << 8 & 0xFF00);
            sb.append(OffsetCalcUtil.caculate(temprature, TemperatureCfg.bitVal, TemperatureCfg.offsetVal).intValue()).append(",");
        }
        //
        batteryTempData.setTempItems(sb.toString());       
        //原始报文信息
        batteryTempData.setSource(JConverter.bytes2String(msgObject));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return batteryTempData;
    }
}
