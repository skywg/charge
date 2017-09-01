package com.iycharge.server.ccu.exec;

import io.netty.channel.socket.SocketChannel;

/**
 * 处理电桩请求的业务逻辑基类
 * @author bwang
 */
public class RequestLogicExecutorBase implements Runnable {
    
    protected SocketChannel channel;
    
    protected byte[] msgObject;
    
    public SocketChannel getChannel() {
        return channel;
    }
    
    public void setChannel(SocketChannel channel) {
        this.channel = channel;
    }

    public byte[] getMsgObject() {
        return msgObject;
    }

    public void setMsgObject(byte[] msgObject) {
        this.msgObject = msgObject;
    }

    @Override
    public void run() {
        
    }
}
