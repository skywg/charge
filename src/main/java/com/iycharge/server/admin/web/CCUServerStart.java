package com.iycharge.server.admin.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.iycharge.server.ccu.core.CCUServer;

/**
 * 启动电桩控制器
 * @author bwang
 */
@Component
public class CCUServerStart{
    
    private Logger logger = LoggerFactory.getLogger(CCUServerStart.class);
    
    /**
     * 端口号
     */
    @Value("${ccu.server.port}")
    private int port;
    
    /**
     * boss工作组线程数
     */
    @Value("${ccu.server.thread.bossgroup}")
    private int bossGroupSize;
    
    /**
     * work工作组线程数
     */
    @Value("${ccu.server.thread.workgroup}")
    private int workGroupSize;
    
    /**
     * 允许的客户端最大连接数
     */
    @Value("${ccu.server.client.max-size}")
    private int clientMaxSize;
    
    public void start() throws Exception {
        try {
            CCUServer server = new CCUServer(port, clientMaxSize, bossGroupSize, workGroupSize);
            server.start();
        } catch (Exception e) {
            logger.error("CCU Server start failed!", e);
        }
    } 
}
