package com.iycharge.server.ccu.core;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iycharge.server.admin.cache.RCharger;
import com.iycharge.server.admin.cache.RConnector;
import com.iycharge.server.admin.message.listener.MessageChannel;
import com.iycharge.server.ccu.msg.entity.ChargerCommLog;
import com.iycharge.server.ccu.msg.entity.LogType;
import com.iycharge.server.ccu.util.JConverter;
import com.iycharge.server.ccu.util.Utility;
import com.iycharge.server.domain.entity.charger.ChargerStatus;
import com.iycharge.server.domain.entity.utils.RedisUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 用于处理从客户端接收到的消息
 * @author bwang
 */
public class MsgInBoundHandler extends SimpleChannelInboundHandler<byte[]> {
    
    private static final Logger logger = LoggerFactory.getLogger(MsgInBoundHandler.class);

    /**
     * 计数器，连续3次检测都超时，关闭客户端连接
     */
    private int unconnect_count = 0;
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, byte[] msgObject) throws Exception {
        //接收到的客户端消息
        logger.info("C(" + (ctx.channel().remoteAddress()) + " ==>>S:" + JConverter.bytes2String(msgObject));
        
        ReqDispatcher.submit((SocketChannel)ctx.channel(), msgObject);
        
        unconnect_count = 0;
    }
    
    /*
     * 在channel被启用的时候触发 (在建立连接的时候)
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {  
        logger.info("Client : " + ctx.channel().remoteAddress() + " active !");
    }
    
    /*
     * 在channel断开的时候触发 
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {      
        logger.info("Client : " + ctx.channel().remoteAddress() + " inactive !");
        
        //连接关闭后，清理系统资源
        String chargerNo = ClientChannelMap.getChargerNo(ctx.channel().id().asLongText());  
        if(chargerNo != null && !"".equals(chargerNo.trim())) {
            ClientChannelMap.remove(chargerNo);       
            
            RedisUtil redisUtil = Utility.getBean(RedisUtil.class);
            //更新电桩状态为离线状态                    
            if(chargerNo != null && !"".equals(chargerNo)) {              
                RCharger rcharger = (RCharger)redisUtil.get(RedisUtil.PREFIX_CHARGER + chargerNo);
                rcharger.setChargerStatus(ChargerStatus.OFFLINE);
                
                List<RConnector> rconnList = rcharger.getConnectorList();
                if(rconnList != null && rconnList.size() > 0) {
                    for(RConnector rconn : rconnList) {
                        rconn.setStatus(ChargerStatus.OFFLINE);
                    }
                }
                
                redisUtil.set(RedisUtil.PREFIX_CHARGER + chargerNo, rcharger);
            }     
            
            //存储离线日志
            ChargerCommLog log = new ChargerCommLog();
            log.setChargerCode(chargerNo);
            log.setLogType(LogType.OFFLINE);
            log.setLogTime(new Date());
            log.setParams("client:" + ctx.channel().remoteAddress());
            redisUtil.sendMessage(MessageChannel.MSG_CHANNAL_CONN, log);
        }
    }
    
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;  
            if(event.state() == IdleState.READER_IDLE) {
                //连续3次没有收到客户端消息，则断开与客户端之间的连接
                if(++unconnect_count >= 3) {          
                    //断开连接         
                    ctx.close(); 
                    
                    //计数器归零
                    unconnect_count = 0;
                    logger.error("Client : " + ctx.channel().remoteAddress() + " connect timeout!");
                }
            }
        } else { 
            super.userEventTriggered(ctx, evt);
        }
    }

    /*
     * 异常处理
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("channel is exception over.(SocketChannel)ctx.channel()=" + (SocketChannel)ctx.channel(), cause);
    } 
}
