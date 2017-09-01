package com.iycharge.server.ccu.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.socket.SocketChannel;

/**
 * 管理客户端连接
 * @author bwang
 */
public class ClientChannelMap {
    
    /**
     * key   : 电桩编号
     * value ：电桩与电桩控制之间的连接通道
     */
    private static Map<String, SocketChannel> chargerTChannelMap = new ConcurrentHashMap<String, SocketChannel>(); 
    
    /**
     * key   : SocketChannel的引用地址
     * value : 电桩编号
     */
    private static Map<String, String> channelTChargerMap = new ConcurrentHashMap<String, String>();
    
    /**
     * 获取所有的连接
     * @return
     */
    public static Map<String, SocketChannel> getAll() {
        return chargerTChannelMap;
    }
    
    /**
     * 保存电桩编号和对应的连接
     * @param chargerNo         电桩编号
     * @param socketChannel     客户端连接
     */
    public static void add(String chargerNo, SocketChannel socketChannel){  
        chargerTChannelMap.put(chargerNo, socketChannel);  
        channelTChargerMap.put(socketChannel.id().asLongText(), chargerNo);
    }
    
    /**
     * 根据电桩编号，获得对应的连接
     * @param chargerNo         电桩编号
     * @return
     */
    public static SocketChannel getSockeChannel(String chargerNo){  
        return chargerTChannelMap.get(chargerNo);  
    }  
    
    /**
     * 根据channelId获取电桩编号
     * @param channelId     SocketChannel全局唯一标识
     * @return
     */
    public static String getChargerNo(String channelId) {
        return channelTChargerMap.get(channelId);
    } 
    
    /**
     * 移除电桩编号对应的客户端连接
     * @param chargerNo     电桩编号
     */
    public static void remove(String chargerNo) {
        if(chargerTChannelMap.containsKey(chargerNo)) {
            SocketChannel socketChannel = chargerTChannelMap.remove(chargerNo);
            channelTChargerMap.remove(socketChannel.id().asLongText());
        }
    }
    
    /**
     * 当电桩控制器服务停止时，需清除有的连接
     */
    public static void clear() {
        chargerTChannelMap.clear();
        channelTChargerMap.clear();
    }
}
