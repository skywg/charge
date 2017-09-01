package com.iycharge.server.ccu.exec;

import java.util.concurrent.Callable;

import io.netty.channel.socket.SocketChannel;

/**
 * 处理服务器下发请求的业务逻辑基类
 * @author bwang
 */
public abstract class ResponseLogicExecutorBase implements Callable<Object> {

    protected short msgType;
    
    protected SocketChannel channel;
    
    protected Object msgObject;
    
    public ResponseLogicExecutorBase() {
        
    }

    public short getMsgType() {
        return msgType;
    }

    public void setMsgType(short msgType) {
        this.msgType = msgType;
    }

    public SocketChannel getChannel() {
        return channel;
    }

    public void setChannel(SocketChannel channel) {
        this.channel = channel;
    }

    public Object getMsgObject() {
        return msgObject;
    }

    public void setMsgObject(Object msgObject) {
        this.msgObject = msgObject;
    }
    
    public abstract Object call() throws Exception;
     
}
