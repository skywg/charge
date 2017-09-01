package com.iycharge.server.ccu.msg.request;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import com.iycharge.server.ccu.msg.RequestMsgBase;
import com.iycharge.server.ccu.msg.entity.BmsBaseData;
import com.iycharge.server.ccu.util.BCDUtil;
import com.iycharge.server.ccu.util.JConverter;
import com.iycharge.server.ccu.util.OffsetCalcUtil;
import com.iycharge.server.ccu.util.Utility;
import com.iycharge.server.ccu.util.OffsetCalcUtil.TotalVoltageCfg;

/**
 * BMS信息
 * @author bwang
 */
public class BmsBaseDataReq extends RequestMsgBase<BmsBaseData> {

    /**
     * @param msgType
     * @param msgObject
     */
    public BmsBaseDataReq(short msgType, byte[] msgObject) {
        super(msgType, msgObject);
    }

    @Override
    public BmsBaseData parse() {
        byte[] datas = Arrays.copyOfRange(msgObject, 4, msgObject.length - 2);
        
        BmsBaseData bmsBaseData =  new BmsBaseData();
        int index = 0;
        
        //充电桩编号
        bmsBaseData.setChargerNo(BCDUtil.bcd2Str(Arrays.copyOfRange(datas, index, index += 8)));        
        //充电接口 
        bmsBaseData.setIfName(Integer.toString(datas[index], 16));
        //BMS 协议版本号 
        bmsBaseData.setProtocalVersion(
                (datas[++index] & 0xFF)  + "." + (datas[++index] & 0xFF) + "." + (datas[++index] & 0xFF)
        );
        //电池类型
        bmsBaseData.setBetteryType(String.valueOf((byte)datas[++index]));        
        //整车动力力蓄电池系统额定容量 
        bmsBaseData.setMaxCapacity((datas[++index] & 0xFF) + (datas[++index] << 8 & 0xFF00));
        //整车动力蓄电池额定总电压 
        bmsBaseData.setMaxVoltage(
                OffsetCalcUtil.caculate(        
                    (datas[++index] & 0xFF) + (datas[++index] << 8 & 0xFF00),
                    TotalVoltageCfg.bitVal,
                    TotalVoltageCfg.offsetVal
                ).shortValue()
        );
        //电池生产厂商名称 
        try {
            bmsBaseData.setManufacturer(new String(Arrays.copyOfRange(datas, index, (index += 4)), "ascii"));
        } catch (UnsupportedEncodingException e) {
            bmsBaseData.setManufacturer(null);            
        }
        //电池组序号(预留由厂商自行定义)
        bmsBaseData.setSerialCode(JConverter.bytes2String(Arrays.copyOfRange(datas, index, (index += 4))));
        
        //电池组生产日期 
        String productionDate = ((datas[index] & 0xFF) + 1985) + ""+ (datas[++index] & 0xFF) + "" + (datas[++index] & 0xFF);
        bmsBaseData.setProductionDate(Utility.parseDate(productionDate, "yyyyMMdd"));        
        //电池组充电次数
        bmsBaseData.setChargingTimes(
            (datas[++index] & 0xFF) + (datas[++index] << 8 & 0xFF00) +
            (datas[++index] << 16 & 0xFF0000) + (datas[++index] << 24 & 0xFF000000)
        );
        //电池组产权标识 
        bmsBaseData.setProperty(String.valueOf(datas[++index] & 0xFF));
        //预留 
        bmsBaseData.setReservedField(JConverter.bytes2String(Arrays.copyOfRange(datas, ++index, ++index)));
        //车辆识别码(VIN) 
        bmsBaseData.setVinCode(new String(Arrays.copyOfRange(datas, index, (index += 17))));
        //BMS 软件版本号 
        bmsBaseData.setSoftwareVersion(new String(Arrays.copyOfRange(datas, index, (index += 8))));
        //原始报文信息
        bmsBaseData.setSource(JConverter.bytes2String(msgObject));
        return bmsBaseData;
    }
}
