package com.iycharge.server.domain.entity.utils;

import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.iycharge.server.api.common.WeixinPayConstants;
import com.iycharge.server.api.util.GetWxOrderno;
import com.iycharge.server.api.util.RequestHandler;
import com.iycharge.server.api.util.Sha1Util;
import com.iycharge.server.api.util.TenpayUtil;
import com.iycharge.server.ccu.util.Utility;
import com.iycharge.server.domain.entity.order.RechargeRecord;

/**
 *
 * @author zw
 */
public class AlipayWeiXinPaymentUtil {
	// 商户相关资料
	private static String appid = WeixinPayConstants.appid;
	private static String appsecret = WeixinPayConstants.appsecret;
	private static String partner = WeixinPayConstants.partner;
	private static String partnerkey = WeixinPayConstants.partnerkey;
	
    public static ResponseEntity<?> getRechargePayInfo(HttpServletRequest request,
			HttpServletResponse response,RechargeRecord rechargeRecord){
    	String json = null;
		JSONObject retMsgJson = new JSONObject();
		String userId = rechargeRecord.getAccount().getId().toString();
		// 金额转化为分为单位
		float sessionmoney = Float.parseFloat(rechargeRecord.getMoney().toString());
		String finalmoney = String.format("%.2f", sessionmoney);
		finalmoney = finalmoney.replace(".", "");
		String currTime = TenpayUtil.getCurrTime();
		// 8位日期
		String strTime = currTime.substring(8, currTime.length());
		// 四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		// 10位序列号,可以自行调整。
		String strReq = strTime + strRandom;
		// 商户号
		String mch_id = partner;
		// 子商户号 非必输
		// String sub_mch_id="";
		// 设备号 非必输
		String device_info = "";
		// 随机数
		String nonce_str = strReq;
		// 附加数据
		String attach = userId;
		// 商户订单号
		String out_trade_no = rechargeRecord.getTradeNo();// 订单编号
		int intMoney = Integer.parseInt(finalmoney);
		// 总金额以分为单位，不带小数点
		String total_fee = String.valueOf(intMoney);
		String body = "  ";
		// 订单生成的机器 IP
		String spbill_create_ip = request.getRemoteAddr();
		//spbill_create_ip = "127.0.0.1";
		String notify_url = getNotifyUrl();// 微信异步通知地址
		String trade_type = "APP";// app支付必须填写为APP
		// 对以下字段进行签名
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("attach", attach);
		packageParams.put("body", body);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("notify_url", notify_url);
		packageParams.put("out_trade_no", out_trade_no);
		packageParams.put("spbill_create_ip", spbill_create_ip);
		packageParams.put("total_fee", total_fee);
		packageParams.put("trade_type", trade_type);
		RequestHandler reqHandler = new RequestHandler(request, response);
		reqHandler.init(appid, appsecret, partnerkey);
		String sign = reqHandler.createSign(packageParams);// 获取签名
		String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<attach>" + attach + "</attach>" + "<body><![CDATA["
				+ body + "]]></body>" + "<mch_id>" + mch_id + "</mch_id>" + "<nonce_str>" + nonce_str + "</nonce_str>"
				+ "<notify_url>" + notify_url + "</notify_url>" + "<out_trade_no>" + out_trade_no + "</out_trade_no>"
				+ "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>" + "<total_fee>" + total_fee
				+ "</total_fee>" + "<trade_type>" + trade_type + "</trade_type>" + "<sign>" + sign + "</sign>"
				+ "</xml>";
		String createOrderURL = WeixinPayConstants.createOrderURL;
		String prepay_id = "";
		try {
			prepay_id = new GetWxOrderno().getPayNo(createOrderURL, xml);
			if (prepay_id.equals("")) {
				retMsgJson.put("msg", "error");
				json = retMsgJson.toString();
				return null;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// 获取到prepayid后对以下字段进行签名最终发送给app
		SortedMap<String, String> finalpackage = new TreeMap<String, String>();
		String timestamp = Sha1Util.getTimeStamp();
		finalpackage.put("appid", appid);
		finalpackage.put("timestamp", timestamp);
		finalpackage.put("noncestr", nonce_str);
		finalpackage.put("partnerid", WeixinPayConstants.partner);
		finalpackage.put("package", "Sign=WXPay");
		finalpackage.put("prepayid", prepay_id);
		String finalsign = reqHandler.createSign(finalpackage);
		retMsgJson.put("appid", appid);
		retMsgJson.put("timestamp", timestamp);
		retMsgJson.put("noncestr", nonce_str);
		retMsgJson.put("partnerid", WeixinPayConstants.partner);
		retMsgJson.put("prepayid", prepay_id);
		retMsgJson.put("package", "Sign=WXPay");
		retMsgJson.put("sign", finalsign);
		json = retMsgJson.toString();
		System.out.println("----------json-----------");
		System.out.println(json);
		System.out.println("----------json-----------");
		return new ResponseEntity<>(json, HttpStatus.OK);
    }
    
    public static String getNotifyUrl() {
    	System.out.println("----------WeixinPayConstants.notify_url----------");
		System.out.println(WeixinPayConstants.notify_url);
		System.out.println("---------WeixinPayConstants.notify_url-----------");
        return "http://" + Utility.getEnvVar("server.domain") + ":" + Utility.getEnvVar("server.port") + WeixinPayConstants.notify_url;
    }
    
}
