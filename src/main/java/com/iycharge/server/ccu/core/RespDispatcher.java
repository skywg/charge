package com.iycharge.server.ccu.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.iycharge.server.ccu.exec.ResponseLogicExecutorBase;
import com.iycharge.server.ccu.util.Utility;

import io.netty.channel.socket.SocketChannel;

/**
 * 消息下发请求分发器
 * 
 * @author bwang
 */
public class RespDispatcher {
    
    private static final int MAX_THREAD_NUM = 8;
    
    private static ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREAD_NUM);
    
    public static Future<Object> submit(short msgType, SocketChannel channel, Object msgObject) {
        
        if(msgObject == null) {
            return null;
        }
        
        String beanName = "MSG_" + msgType;

        ResponseLogicExecutorBase executor = Utility.getBean(beanName, ResponseLogicExecutorBase.class);
        executor.setMsgType(msgType);
        executor.setChannel(channel);
        executor.setMsgObject(msgObject);
        
        return executorService.submit(executor);
    }
}
