package com.iycharge.server.ccu.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iycharge.server.ccu.exec.RequestLogicExecutorBase;
import com.iycharge.server.ccu.msg.AbstractMsg;
import com.iycharge.server.ccu.util.JConverter;
import com.iycharge.server.ccu.util.Utility;

import io.netty.channel.socket.SocketChannel;

/**
 * 电桩请求分发器, 根据消息类型将请求分发到对应的业务处理类
 * 
 * @author bwang
 */
public class ReqDispatcher {
    
    private static Logger logger = LoggerFactory.getLogger(ReqDispatcher.class);
    
    private static final int MAX_THREAD_NUM = 8;
    
    private static ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREAD_NUM);
    
    public static void submit(SocketChannel channel, byte[] msgObject) 
            throws InstantiationException, IllegalAccessException {
        
        int msgLength = msgObject.length;
        
        if(msgLength < AbstractMsg.MIN_MSG_LEN 
                || msgObject[0] != AbstractMsg.MSG_HEAD
                || msgObject[msgObject.length -1] != AbstractMsg.MSG_FOOTER) {
            logger.error("invalid request=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
            return;
        }
        String beanName = "MSG_" + (msgObject[3] & 0xFF);     
        RequestLogicExecutorBase executor = Utility.getBean(beanName, RequestLogicExecutorBase.class); 
        if(executor != null) {
            executor.setChannel(channel);
            executor.setMsgObject(msgObject);            
            executorService.submit(executor);
        } else {
            logger.error("invalid request=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
        }
    }
}
