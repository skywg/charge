package com.iycharge.server.ccu.util;

/**
 * 和校验工具
 * 
 * @author bwang
 */
public final class CheckSumUtil {
    
    /**
     * 生成16进制累加和校验码
     *
     * @param data 除去校验位的数据
     * @return
     */
    public static int makeChecksum(byte[] datas) {
        if(datas == null || datas.length == 0) {
            return -1;
        }
        int total = 0;
        for(byte data : datas) {
            total += data;
        }  
        total = total & 0xff;      
        return total;
    }
    
    /**
     * 16进制累加和校验
     *
     * @param data 除去校验位的数据
     * @param sign 校验位的数据
     * @return
     */
    public static boolean check(byte[] datas, int sign) {
        if(datas == null || datas.length == 0 || sign < 0) {
            return false;
        }
        
        int checksum = makeChecksum(datas);
        
        if (checksum == sign) {
            return true;
        } else {
            return false;
        }
    }
}
