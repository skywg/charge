package com.iycharge.server.ccu.util;

import java.math.BigDecimal;

/**
 * 偏移计算工具类
 * @author bwang
 */
public class OffsetCalcUtil {
    
    //总电压
    public interface TotalVoltageCfg {
        //分辨率
        public static final BigDecimal bitVal = new BigDecimal("0.1");
        
        //偏移量
        public static final int offsetVal = 0;
    }
    
    //总电流 
    public interface CurrentCfg {
        //分辨率
        public static final BigDecimal bitVal = new BigDecimal("0.1");
        
        //偏移量
        public static final int offsetVal = 4000;
    }
    
    //单体电压 
    public interface SigleBatteryVoltageCfg {
        //分辨率
        public static final BigDecimal bitVal = new BigDecimal("0.01");
        
        //偏移量
        public static final int offsetVal = 0;
    }
    
    //充电电量 
    public interface ChargingElectricCfg {
        //分辨率
        public static final BigDecimal bitVal = new BigDecimal("0.01");
        
        //偏移量
        public static final int offsetVal = 0;
    }
    
    //充电功率 
    public interface ChargingPowerCfg {
        //分辨率
        public static final BigDecimal bitVal = new BigDecimal("0.1");
        
        //偏移量
        public static final int offsetVal = 0;
    }
    
    //温度
    public interface TemperatureCfg {
        //分辨率
        public static final BigDecimal bitVal = new BigDecimal("1");
        
        //偏移量
        public static final int offsetVal = 50;
    }
    
    //输入电压
    public interface InputVoltageCfg {
        //分辨率
        public static final BigDecimal bitVal = new BigDecimal("0.1");
        
        //偏移量
        public static final int offsetVal = 0;
    }
    
    //输入电压
    public interface ConnConfirmVoltageCfg {
        //分辨率
        public static final BigDecimal bitVal = new BigDecimal("0.1");
        
        //偏移量
        public static final int offsetVal = 0;
    } 
    
    //钱
    public interface MoneyCfg {
      //分辨率
        public static final BigDecimal bitVal = new BigDecimal("0.01");
        
        //偏移量
        public static final int offsetVal = 0;
    }
    /**
     * 返回经过偏移运算后的值
     * @param value     需要进行偏移运算的数
     * @param bitVal    分辨 
     * @param offsetVal 偏移量
     * @return
     */
    public static BigDecimal caculate(int value, BigDecimal bitVal, int offsetVal) {
        return new BigDecimal(value - offsetVal).multiply(bitVal);
    }
    
}