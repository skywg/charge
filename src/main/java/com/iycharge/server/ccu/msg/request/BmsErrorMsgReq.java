package com.iycharge.server.ccu.msg.request;

import java.util.Arrays;

import com.iycharge.server.ccu.msg.RequestMsgBase;
import com.iycharge.server.ccu.msg.entity.BmsErrorMsg;
import com.iycharge.server.ccu.util.BCDUtil;
import com.iycharge.server.ccu.util.JConverter;

/**
 * BMS 错误报文
 * @author bwang
 */
public class BmsErrorMsgReq extends RequestMsgBase<BmsErrorMsg> {

    /**
     * @param msgType
     * @param msgObject
     */
    public BmsErrorMsgReq(short msgType, byte[] msgObject) {
        super(msgType, msgObject);
    }

    @Override
    public BmsErrorMsg parse() {
        BmsErrorMsg bmsErrorMsg = new BmsErrorMsg();
        
        byte[] datas = Arrays.copyOfRange(msgObject, 4, msgObject.length - 2);        
        int index = 0;
        
        //充电桩编号
        bmsErrorMsg.setChargerNo(BCDUtil.bcd2Str(Arrays.copyOfRange(datas, index, index += 8)));
        //充电接口
        bmsErrorMsg.setIfName(Integer.toString(datas[index], 16));
        
        String byteStr = Integer.toBinaryString(datas[++index] & 0xFF);
        while(byteStr.length() < 8) {
            byteStr = "0" + byteStr;
        }
        //接收充电桩SPN2560=0x00 辨识报文超时
        bmsErrorMsg.setSpn1TimeoutFlag(JConverter.bit2byte(byteStr.substring(0, 4)));
        //接收充电桩SPN2560=0xaa 辨识报文超时
        bmsErrorMsg.setSpn1TimeoutFlag(JConverter.bit2byte(byteStr.substring(4, 8)));
        
        byteStr = Integer.toBinaryString(datas[++index] & 0xFF);
        while(byteStr.length() < 8) {
            byteStr = "0" + byteStr;
        }
        //接收充电桩的时间同步报文和充电桩最大输出能力报文超时 
        bmsErrorMsg.setSynTimeoutFlag(JConverter.bit2byte(byteStr.substring(0, 4)));
        //接收充电桩完成充电准备报文超时
        bmsErrorMsg.setFinishedTimeoutFlag(JConverter.bit2byte(byteStr.substring(4, 8)));
        
        byteStr = Integer.toBinaryString(datas[++index] & 0xFF);
        while(byteStr.length() < 8) {
            byteStr = "0" + byteStr;
        }
        //接收充电桩充电状态报文超时          
        bmsErrorMsg.setWorkingTimeoutFlag(JConverter.bit2byte(byteStr.substring(0, 4)));
        //接收充电桩中止充电报文超时 
        bmsErrorMsg.setStopTimeoutFlag(JConverter.bit2byte(byteStr.substring(4, 8)));
        
        byteStr = Integer.toBinaryString(datas[++index] & 0xFF);
        while(byteStr.length() < 8) {
            byteStr = "0" + byteStr;
        }
        //接收充电桩统计报文超时
        bmsErrorMsg.setStatTimeoutFlag(JConverter.bit2byte(byteStr.substring(0, 2)));
        
        //其他
        bmsErrorMsg.setOther(String.valueOf(JConverter.bit2byte(byteStr.substring(2, 8))));
        
        //原始报文信息
        bmsErrorMsg.setSource(JConverter.bytes2String(msgObject));
        
        return bmsErrorMsg;
    }

}
