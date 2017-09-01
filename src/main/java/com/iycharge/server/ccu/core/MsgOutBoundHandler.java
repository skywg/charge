package com.iycharge.server.ccu.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iycharge.server.ccu.util.JConverter;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * 处理向客户端发送消息的业务逻辑
 * @author bwang
 */
public class MsgOutBoundHandler extends ChannelOutboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(MsgOutBoundHandler.class);
    
    @Override
    public void write(final ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {       
        if (msg instanceof byte[]) {
            try {
                ctx.write(msg,promise).addListener(new ChannelFutureListener(){
        
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        // TODO Auto-generated method stub
                        logger.info("S==>>C(" + ctx.channel().remoteAddress() + "):" + JConverter.bytes2String((byte[])msg));
                    }
                    
                });
            } catch (Exception e) {
                logger.error("MsgOutBoundHandler Exception", e);
                throw e;
            }
        }
    }

}
