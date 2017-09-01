package com.iycharge.server.ccu.msg;

/**
 * 抽象消息类
 * @author bwang
 */
public class AbstractMsg {

    /**
     * 填充值,
     */
    public static final byte FILLED_VAL = 0x00;
    
    /**
     * 用作无数据或保留项无特殊情况下的赋值
     */
    public static final byte RESERVED_VAL = (byte)0xFF;
    
    /**
     * 消息头部
     */
    public static final byte MSG_HEAD = 0x68;
    
    /**
     * 消息尾部
     */
    public static final byte MSG_FOOTER  = 0x16; 
    
    /**
     * ASDU允许最大长度
     */
    public static final short MAX_ASDU_LEN  = 2047;
    
    /**
     * 消息体最小长度
     */
    public static final short MIN_MSG_LEN = 14;
    
    /**
     * 消息类型， 由com.iycharge.server.ccu.msg.MsgType定义
     */
    protected short msgType;
    
    /**
     * 电桩与电桩控制器之间进行通信的数据
     */
    protected byte[] msgObject;
    
    public AbstractMsg() {
        
    }
    
    public AbstractMsg(short msgType) {
        this.msgType = msgType;
    }
    
    public AbstractMsg(short msType, byte[] msgObject) {
        this.msgType   = msType;
        this.msgObject = msgObject;
    }

    public short getMsgType() {
        return msgType;
    }

    public void setMsgType(short msgType) {
        this.msgType = msgType;
    }

    public byte[] getMsgObject() {
        return msgObject;
    }

    public void setMsgObject(byte[] msgObject) {
        this.msgObject = msgObject;
    }
}
