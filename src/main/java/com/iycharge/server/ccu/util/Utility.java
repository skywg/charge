package com.iycharge.server.ccu.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.context.ApplicationContext;

/**
 * 常用工具类
 * @author bwang
 */
public class Utility {
    
    private static ApplicationContext applicationContext;
    
    private static ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>();
    
    public static void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }
       
    public static <T>T getBean(String name, Class<T> type) {
        if(applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(name, type);
    }
    
    public static <T>T getBean(Class<T> type) {
        if(applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(type);
    }
    
    public static String getEnvVar(String key) {
        return applicationContext.getEnvironment().getProperty(key);
    }
    
    public static SimpleDateFormat getSimpleDateFormat() {
        SimpleDateFormat sdf = threadLocal.get();
        if(sdf == null) {
            sdf = new SimpleDateFormat();
            threadLocal.set(sdf);
        }
        return sdf;
    }
    
    /**
     * 
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDate(Date date, String pattern) {
        if(date == null) {
            return null;
        }
        SimpleDateFormat sdf = getSimpleDateFormat();
        sdf.applyPattern(pattern);
        
        return sdf.format(date);
    }
    
    /**
     * 
     * @param date
     * @param pattern
     * @return
     */
    public static Date parseDate(String date, String pattern) {
        if(date == null || "".equals(date.trim())) {
            return null;
        }
        
        SimpleDateFormat sdf = getSimpleDateFormat();
        sdf.applyPattern(pattern);
        try {
            return sdf.parse(date);
        } catch (Exception e) {
            return null;
        }
    }
    
}
