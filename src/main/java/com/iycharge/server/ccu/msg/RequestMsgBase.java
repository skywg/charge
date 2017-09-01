package com.iycharge.server.ccu.msg;

import java.util.Arrays;

import com.iycharge.server.ccu.util.CheckSumUtil;

/**
 * 请求消息基类
 * @author bwang
 */
public abstract class RequestMsgBase<T> extends AbstractMsg {

    public RequestMsgBase(short msgType, byte[] msgObject) {
        super(msgType, msgObject);
    }
    
    /**
     * 将二进制消息解析成制定类型的对象
     * @param msgObject  
     * @return
     */
    public abstract T parse();
    
   /**
    * 数据校验,验证请求数据的合法性
    * @return
    */
    public boolean check() {
        //
        if(msgObject == null || msgObject.length < 5) {
            return false;
        }
        
        //消息必须以68开头，16结尾
        if(msgObject[0] != MSG_HEAD || msgObject[msgObject.length -1] != MSG_FOOTER) {
            return false;
        }
        
        //校验ASDU长度  
        int asduLen = (msgObject[1] & 0xFF) + ((msgObject[2] << 8) & 0xFF00);
        if(asduLen > MAX_ASDU_LEN || asduLen != (msgObject.length - 3)) {
            return false;
        }
        
        //和校验
        byte[] msgBody = Arrays.copyOfRange(msgObject, 3, msgObject.length - 2);
        int    sign    = msgObject[msgObject.length - 2] & 0xff;
        
        return CheckSumUtil.check(msgBody, sign);
    }
}
