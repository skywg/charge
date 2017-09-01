package com.iycharge.server.ccu.util;

import com.iycharge.server.ccu.msg.AbstractMsg;

/**
 * BCD与十进制的转换工具类
 * 
 * @author bwang
 */
public final class BCDUtil {
    
    /**
     * BCD码转为10进制串(阿拉伯数据)
     * @param bytes BCD码
     * @return 10进制串
     */
    public static String bcd2Str(byte[] bytes) {
        StringBuffer temp = new StringBuffer(bytes.length * 2);  
        boolean flag = true;
        for (int i = 0; i < bytes.length; i++) {  
            if(flag){
                if(bytes[i] == AbstractMsg.FILLED_VAL) {
                    continue;
                } else {
                    flag = false;
                }
            }
            temp.append((byte) ((bytes[i] & 0xf0) >>> 4));  
            temp.append((byte) (bytes[i] & 0x0f));  
        }  
        //return temp.charAt(0) == '0' ? temp.deleteCharAt(0).toString() : temp.toString();
        return temp.toString();
    }

    /**
     * 10进制串转为BCD码
     * @param asc 10进制串
     * @return BCD码
     */
    public static byte[] str2Bcd(String asc) {
        if(asc == null || "".equals(asc.trim())) {
            return null;
        }
        
        if((asc.length() % 2) != 0) {
            asc = "0" + asc;
        }
        
        byte[] abt = asc.getBytes();
        
        byte[] bbt = new byte[asc.length() / 2];
        
        int j, k;
        for (int p = 0; p < bbt.length; p++) {
            if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
                j = abt[2 * p] - '0';
            } else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
                j = abt[2 * p] - 'a' + 0x0a;
            } else {
                j = abt[2 * p] - 'A' + 0x0a;
            }
            if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
                k = abt[2 * p + 1] - '0';
            } else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
                k = abt[2 * p + 1] - 'a' + 0x0a;
            } else {
                k = abt[2 * p + 1] - 'A' + 0x0a;
            }
            
            bbt[p] = (byte)((j << 4) + k);
        }
        
        return bbt;
    }
}
