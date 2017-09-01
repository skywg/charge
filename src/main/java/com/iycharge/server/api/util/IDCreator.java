package com.iycharge.server.api.util;

import java.util.Date;

import com.iycharge.server.ccu.util.Utility;

/**
 * 流水号生成器
 * 可根据不同的业务生成对应的流水号
 * @author bwang
 */
public class IDCreator {
    
    /**
     * 业务类型： APP充值业务
     */
    public static final String BUS_RECHARGE = "401";
    
    /**
     * 业务类型: 充电业务
     */
    public static final String BUS_CHARGING = "402"; 
    
    /**
     * 业务类型: 企业用户充值
     */
    public static final String BUS_COMPANY_RECHARGE = "501";
    
    /**
     * 业务类型: 充电卡充值
     */
    public static final String BUS_CARD_RECHARGE = "502";
    
    /**
     *  业务类型：客户反馈意见
     */
    public static final String BUS_FEED_BACK = "601"; 
    
    /**
     * 流水号长度为16位
     * 生成规则：业务号(3位) + 年月日(6) + 流水号(7位）
     * @param busType   业务类型
     * @return
     */
    public static String generator(String busType) {        
        StringBuilder id = new StringBuilder();
        id.append(busType)
          .append(Utility.formatDate(new Date(), "yyMMdd"))
          .append((int)((Math.random()*9+1)*1000000));
        return id.toString();
    }
}
