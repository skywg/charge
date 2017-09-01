package com.iycharge.server.ccu.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * ccu(中央控制单元)，接受电桩发起TCP连接
 * 
 * @author bwang
 */
public class CCUServer {
    
    private Logger logger = LoggerFactory.getLogger(CCUServer.class);
    
    /**
     * 服务监听默认端口
     */
    private int port = 6373;
    
    /**
     * 服务器端允许的最大客户端连接数
     */
    private int clientMaxSize = 128;
    
    /** 
     * 用于分配处理业务线程的线程组个数 
     */
    private int bossGroupSize = Runtime.getRuntime().availableProcessors() * 2;
    
    /**
     *  工作线程数量 
     */
    private int workGroupSize = 5;
    
    /**
     * 用于接收发来的连接请求
     */
    private EventLoopGroup bossGroup;
    
    /**
     * 用于处理boss接受并且注册给worker的连接中的信息
     */
    private EventLoopGroup workerGroup;
    
    /**
     * server 启动程序
     */
    private ServerBootstrap bootstrap;
    
    public CCUServer() {
        
    }
    
    public CCUServer(int port, int clientMaxSize, int bossGroupSize, int workGroupSize) {
        this.port = port;
        this.clientMaxSize = clientMaxSize;
        this.bossGroupSize = bossGroupSize;
        this.workGroupSize = workGroupSize;
    }
    
    /**
     * 启动服务
     * @throws InterruptedException
     */
    public void start() throws Exception {
        try {
            
            bossGroup   = new NioEventLoopGroup(bossGroupSize);
            workerGroup = new NioEventLoopGroup(workGroupSize);
            
            bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                     .channel(NioServerSocketChannel.class)
                     .option(ChannelOption.SO_BACKLOG, clientMaxSize)    //最大客户端连接数为128  
                     //通过NoDelay禁用Nagle,使消息立即发出去，不用等待到一定的数据量才发出去
                     .option(ChannelOption.TCP_NODELAY, true)
                     .option(ChannelOption.SO_KEEPALIVE, true)
                     .option(ChannelOption.SO_RCVBUF, 1024 * 1024 * 64) //收包换成区   
                     .option(ChannelOption.SO_SNDBUF, 1024 * 1024) //发包缓冲区
                     //保持长连接状态
                     .childOption(ChannelOption.SO_KEEPALIVE, true)
                     //允许半关闭socket
                     //.childOption(ChannelOption.ALLOW_HALF_CLOSURE, true)
                     .childHandler(new MsgChannelInitializer());
            
            // 服务器绑定端口监听
            ChannelFuture future = bootstrap.bind(port).sync();
          
            if(future.isSuccess()) {
                logger.info("CCU Server has started success!");
                // 监听服务器关闭监听，此方法会阻塞 
                future.channel().closeFuture().sync();               
            }
        } finally {
            //优雅退出，释放线程池资源 
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();  
        }
    }
    
    /**
     * 停止服务
     */
    public void stop() {
        //关闭EventLoopGroup，释放掉全部资源
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();  
        ClientChannelMap.clear();
        logger.info("CCU Servr has stoped!");
    }
    
    /**
     * 重启服务
     * @throws Exception
     */
    public void restart() throws Exception {
        stop();
        start();
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getClientMaxSize() {
        return clientMaxSize;
    }

    public void setClientMaxSize(int clientMaxSize) {
        this.clientMaxSize = clientMaxSize;
    }

    public int getBossGroupSize() {
        return bossGroupSize;
    }

    public void setBossGroupSize(int bossGroupSize) {
        this.bossGroupSize = bossGroupSize;
    }

    public int getWorkGroupSize() {
        return workGroupSize;
    }

    public void setWorkGroupSize(int workGroupSize) {
        this.workGroupSize = workGroupSize;
    }  
}
