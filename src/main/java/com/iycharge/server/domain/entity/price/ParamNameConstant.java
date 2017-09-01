package com.iycharge.server.domain.entity.price;

/**
 * 模板参数名称定义
 * @author bwang
 */
public interface ParamNameConstant {
    
    /*********************电价模板*********************/
    //时段数, 在实际执行中，电价参数名(时段开始时间/时段结束时间/电价/服务费/备注)后会跟上相应时段数
    public static final String RICE_PERIOD_NUM = "periodNum"; 
    
    //时段开始时间
    public static final String PRICE_START_TIME = "startAt";
    
    //时段结束时间
    public static final String PRICE_END_TIME = "endAt";
    
    //电价
    public static final String PRICE_PRICE = "price"; 
    
    //服务费
    public static final String PRICE_FEE = "fee";
    
    //备注
    public static final String PRICE_REMARK = "remark";
    
    /*********************远程参数*********************/
    //目标IP
    public static final String REMOTE_IP   = "ip";
    
    //端口号
    public static final String REMOTE_PORT = "port";
    
    //报文上传周期
    public static final String INTERVAL = "interval";
    
    
}
