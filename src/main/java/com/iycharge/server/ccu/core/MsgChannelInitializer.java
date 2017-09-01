package com.iycharge.server.ccu.core;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * 定义消息管道初始化流程
 * @author bwang
 */
public class MsgChannelInitializer extends ChannelInitializer<SocketChannel>{
    
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {       
        //自定义解码器，处理TCP粘包/拆包的问题
        ch.pipeline().addLast("bytesDecoder", new MsgByteArrayDecoder());       
        ch.pipeline().addLast("bytesEncoder", new ByteArrayEncoder());   
        //使用读超时为心跳检测机制，检测客户端连接是否断开
        ch.pipeline().addLast(new IdleStateHandler(10, 0, 0));
        ch.pipeline().addLast(new MsgInBoundHandler());
        ch.pipeline().addLast(new MsgOutBoundHandler());
    }
}
