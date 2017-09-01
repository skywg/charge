package com.iycharge.server.ccu.msg.request;

import java.util.Arrays;

import com.iycharge.server.ccu.msg.RequestMsgBase;
import com.iycharge.server.ccu.msg.entity.ChargingRecord;
import com.iycharge.server.ccu.util.BCDUtil;
import com.iycharge.server.ccu.util.JConverter;
import com.iycharge.server.ccu.util.OffsetCalcUtil;
import com.iycharge.server.ccu.util.OffsetCalcUtil.ChargingElectricCfg;
import com.iycharge.server.ccu.util.OffsetCalcUtil.MoneyCfg;
import com.iycharge.server.ccu.util.Utility;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 充电记录上传
 * @author bwang
 */
public class ChargingRecordReq extends RequestMsgBase<ChargingRecord> {

    /**
     * @param msgType
     * @param msgObject
     */
    public ChargingRecordReq(short msgType, byte[] msgObject) {
        super(msgType, msgObject);
    }

    @Override
    public ChargingRecord parse() {
        ChargingRecord record = new ChargingRecord();
        
        byte[] datas = Arrays.copyOfRange(msgObject, 4, msgObject.length - 2);
        
        int index = 0;
        //充电桩编号
        record.setChargerNo(BCDUtil.bcd2Str(Arrays.copyOfRange(datas, index, index+=8)));
        //充电接口名
        record.setIfName(Integer.toString(datas[index], 16));
        //充电接口类型
        record.setIfType(datas[++index]);
        //充电认证方式
        record.setAuthType(datas[++index]);
        //卡号/流水号
        record.setOrderId(BCDUtil.bcd2Str(Arrays.copyOfRange(datas, ++index, index += 8)));
        //结算标记
        record.setPayFlag((datas[index]) > 0);
        //开始充电时间
        record.setStartAt(
            Utility.parseDate(BCDUtil.bcd2Str(Arrays.copyOfRange(datas, ++index, index += 7)), "yyyyMMddHHmmss")
        );
        //结束充电时间
        record.setEndAt(
            Utility.parseDate(BCDUtil.bcd2Str(Arrays.copyOfRange(datas, index, index += 7)), "yyyyMMddHHmmss")
        );
        //开始充电 SOC
        record.setStartSoc(datas[index]);
        //结束充电 SOC
        record.setEndSoc(datas[++index]);
        //本次充电电量
        record.setDegree(
            OffsetCalcUtil.caculate(
                (datas[++index] & 0xFF) + (datas[++index] << 8 & 0xFF00), 
                ChargingElectricCfg.bitVal, 
                ChargingElectricCfg.offsetVal
            )
        );
        //本次充电时长 
        record.setChargeTime((datas[++index] & 0xFF) + (datas[++index] << 8 & 0xFF00));
        //时段数
        record.setItemCount((byte)(datas[++index] & 0xFF));
        
        JSONArray items = new JSONArray();
        for(int i=0; i<record.getItemCount(); i++) {  
            JSONObject item = new JSONObject();
            item.put("startAt", BCDUtil.bcd2Str(Arrays.copyOfRange(datas, ++index, index += 7)));
            item.put("endAt"  , BCDUtil.bcd2Str(Arrays.copyOfRange(datas, index  , index += 7)));
            item.put("degree" , 
                    OffsetCalcUtil.caculate(
                        (datas[index] & 0xFF) + (datas[++index] << 8 & 0xFF00), 
                        ChargingElectricCfg.bitVal, 
                        ChargingElectricCfg.offsetVal
                    ));
            item.put("price"  , 
                    OffsetCalcUtil.caculate(
                        (datas[++index] & 0xFF) + (datas[++index] << 8 & 0xFF00), 
                        MoneyCfg.bitVal, 
                        MoneyCfg.offsetVal
                    ));
            item.put("fee"    , 
                    OffsetCalcUtil.caculate(
                        (datas[++index] & 0xFF) + (datas[++index] << 8 & 0xFF00), 
                        MoneyCfg.bitVal, 
                        MoneyCfg.offsetVal
                    ));
            item.put("money"  , 
                    OffsetCalcUtil.caculate(
                        (datas[++index] & 0xFF) + (datas[++index] << 8 & 0xFF00) + (datas[++index] << 16 & 0xFF0000) + (datas[++index] << 24 & 0xFF000000), 
                        MoneyCfg.bitVal, 
                        MoneyCfg.offsetVal
                    ));
            
            items.add(item);
        }
        //消费明细
        record.setItemDetail(items.toString());
        //原始报文
        record.setSource(JConverter.bytes2String(msgObject));
        return record;
    }

}
