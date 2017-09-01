package com.iycharge.server.ccu.util;

/**
 * 格式转换公共类 
 * @author bwang
 */
public final class JConverter {
    
    private static final char [] CH_ARRAY = 
                    {
                        '0', '1', '2', '3', 
                        '4', '5', '6', '7', 
                        '8', '9', 'A', 'B', 
                        'C', 'D', 'E', 'F' 
                    };
    
    /**
     * 将 byte 数组转换为 16 进制的字符串       
     * 比如 new byte [] {0x23, 0x45, 0x5}           转换为 "234505"         
     * 
     * @param buf       指定的待转换的字节数组      
     *         
     * @return 转换后的字符串       
     */

    public static String bytes2String(byte [] buf) {
        if (buf == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            sb.append(CH_ARRAY[buf[i] >>> 4 & 0x0F]);
            sb.append(CH_ARRAY[buf[i] & 0x0F]);
        }
        return sb.toString();
    }

    /**
     * 将指定的十六进制字符串转化为其字面值的字节数组       
     * 比如 "4567f" 转换为 new byte [] {0x04, 0x56, 0x7F}       
     * 
     * @param str       指定的十六进制字符串    
     *                      
     * @return 输入字符串对映的字面值的字节数组     
     * 
     */

    public static byte [] string2Bytes(String str) {
        if (str == null) {
            return null;
        }
        if ((str.length() % 2) != 0) {
            str = "0" + str;
        }
        byte [] buf = new byte[str.length() / 2];
        for (int i = 0; i < buf.length; i++) {
            buf[i] = (byte) (Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16) & 0xFF);
        }
        return buf;
    }
    
    
    
    /**
     * 将二进制字符串转换回字节
     * 
     * @param bString
     * @return
     */
    public static byte bit2byte(String bString) {
        byte result = 0;

        for (int i = bString.length() - 1, j = 0; i >= 0; i--, j++) {
            result += (Byte.parseByte(bString.charAt(i) + "") * Math.pow(2, j));
        }
        return result;
    }
}
