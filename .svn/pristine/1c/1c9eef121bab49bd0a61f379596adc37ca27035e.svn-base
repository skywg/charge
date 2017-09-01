package com.iycharge.server.domain.entity.utils;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * redicache 工具类
 * @author bwang
 */
@Component
public class RedisUtil {
    
    /**
     * 电桩key前缀
     */
    public static final String PREFIX_CHARGER  = "charger";
    
    /**
     * 电站key前缀
     */
    public static final String PREFIX_STATION  = "station";
    
   /**
    * app扫码认证
    */
    public static final String PREFIX_AUTH = "auth";
    
    /**
     * 电桩启动
     */
    public static final String PREFIX_START = "start";
    
    /**
     * 电桩停机
     */
    public static final String PREFIX_STOP  = "stop";
    
    /**
     * 充电记录
     */
    public static final String PREFIX_ORDER = "order";
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    /**
     * 读取缓存
     * 
     * @param key       单个key
     * @return
     */
    public Object get(String key) { 
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }
    
    /**
     * 读取缓存
     * @param keys      多个key
     * @return
     */
    public Collection<Object> get(Collection<String> keys) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        return operations.multiGet(keys);
    }
    
    /**
     * 根据模式读取缓存
     * @param pattern       模式字符串
     * @return
     */
    public Collection<Object> getByPattern(String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        if(keys != null && keys.size() > 0) {
            ValueOperations<String, Object> operations = redisTemplate.opsForValue();
            return operations.multiGet(keys);
        }
        return null;
    }
    
    /**
     * 写入缓存
     * @param key       多个key
     * @param value
     * @return
     */
    public boolean set(String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<String, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 写入缓存
     * 
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<String, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 删除缓存
     * @param key       单个key
     * @return
     */
    public boolean delete(String key) {
        boolean result = false;
        try {
            if(redisTemplate.hasKey(key)) {
                redisTemplate.delete(key);
            }
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 删除缓存
     * @param keys  多个key
     * @return
     */
    public boolean delete(Collection<String> keys) {
        boolean result = false;
        try {
           redisTemplate.delete(keys);
           result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
        
    /**
     * 是都存在键key
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }
    
    /**
     * 向频道发布消息
     * @param channel       频道名称
     * @param message       消息体
     */
    public void sendMessage(String channel, Object message) {  
         redisTemplate.convertAndSend(channel, message);  
    }  
}
