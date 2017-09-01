package com.iycharge.server.api.common;

public class WeixinPayConstants {  
    public static final String appid = "wx2acd55b37b3ac821";//在微信开发平台登记的app应用  
    public static final String appsecret = "ca19520173f41b618234b3e184fc2288";  
    public static final String partner = "1374971502";//商户号  
    public static final String partnerkey ="3f6307e991474dad813e38b3de649b8a";//不是商户登录密码，是商户在微信平台设置的32位长度的api秘钥，  
    public static final String createOrderURL="https://api.mch.weixin.qq.com/pay/unifiedorder";  
    //public static final String backUri="http://localhost:8101/admin/api/weixin/topay123";//微信支付下单地址  
    public static final String notify_url="/hooks/recharge-record/payments/weixinAlipay";//异步通知地址  
} 
