package com.iycharge.server.ccu.msg;

/**
 * 相应消息基类
 * @author bwang
 */
public abstract class ResponseMsgBase<T> extends AbstractMsg {
    
    protected T responseBody;
    
    public ResponseMsgBase(short msgType) {
        super(msgType);
    }
    
    public abstract void format();

    public T getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(T responseBody) {
        this.responseBody = responseBody;
    }
}
