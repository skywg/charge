package com.iycharge.server.admin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iycharge.server.admin.message.listener.CCUMessageListener;

import redis.clients.jedis.JedisPoolConfig;

/**
 * 缓存管理
 * @author bwang
 */
@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {
    @Value("${spring.redis.database}")
    private int database = 0;
    
    @Value("${spring.redis.host}")
    private String host = "localhost";
    
    @Value("${spring.redis.port}")
    private int port = 6379;
    
    @Value("${spring.redis.password}")
    private String password;
    
    @Value("${spring.redis.timeout}")
    private int timeout;
    
    @Value("${spring.redis.pool.min-idle}")
    private int minIdle;
    
    @Value("${spring.redis.pool.max-idle}")
    private int maxIdle;
    
    @Value("${spring.redis.pool.max-active}")
    private int maxTotal;
    
    @Value("${spring.redis.pool.max-wait}")
    private long maxWaitMillis;
    
    @Value("${spring.redis.testOnBorrow}")
    private boolean testOnBorrow = false;
    
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setDatabase(database);
        factory.setHostName(host);
        factory.setPassword(password);
        factory.setPort(port);
        factory.setTimeout(timeout); //设置连接超时时间
        
        
        //连接池配置
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMaxTotal(maxTotal);
        poolConfig.setMaxWaitMillis(maxWaitMillis);
        poolConfig.setTestOnBorrow(testOnBorrow);
        
        factory.setPoolConfig(poolConfig);
        
        return factory;
    }
    
    @Bean
    public CacheManager cacheManager(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
        return new RedisCacheManager(redisTemplate);
    }
    
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        //template.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        //设置序列化工具，这样Object不需要实现Serializable接口
        setSerializer(template);
        //template.setKeySerializer(new StringRedisSerializer());
        //template.setValueSerializer(new RedisObjectSerializer());
        //template.afterPropertiesSet();
        return template;
    }
    
    private void setSerializer(RedisTemplate<String, Object> template) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(
                new ObjectMapper()
                    .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
                    .enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL)
        );
        template.setValueSerializer(jackson2JsonRedisSerializer);
    }
    
    /**
     * 消息监听者容器
     * @param factory
     * @param listenerAdapter
     * @return
     */
    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory factory, 
                        ThreadPoolTaskScheduler taskExecutor,
                        CCUMessageListener      ccuMessageListener){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        
        //注册消息监听对象
        container.addMessageListener(ccuMessageListener, new PatternTopic("msg*"));
                
        container.setTaskExecutor(taskExecutor);
        
        return container;
    }
    
    @Bean
    public ThreadPoolTaskScheduler taskExecutor() {
        ThreadPoolTaskScheduler taskExecutor = new ThreadPoolTaskScheduler();
        taskExecutor.setPoolSize(4);
        return taskExecutor;
    }
}
