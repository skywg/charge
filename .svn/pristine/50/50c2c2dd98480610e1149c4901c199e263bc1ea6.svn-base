package com.iycharge.server.api.controller;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iycharge.server.api.common.WeixinPayConstants;
import com.iycharge.server.api.util.GetWxOrderno;
import com.iycharge.server.api.util.RequestHandler;
import com.iycharge.server.api.util.Sha1Util;
import com.iycharge.server.api.util.TenpayUtil;
import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.service.AccountService;

/**
 * @author zw
 * @version ：2016-9-26
 */
@Controller()
@RequestMapping("/admin/api/weixin")
public class WeixinPayController {
	// 商户相关资料
	private static String appid = WeixinPayConstants.appid;
	private static String appsecret = WeixinPayConstants.appsecret;
	private static String partner = WeixinPayConstants.partner;
	private static String partnerkey = WeixinPayConstants.partnerkey;
	@Autowired
    private AccountService accountService;
	@RequestMapping(value = "/topay")
	@ResponseBody
	public void topay(HttpServletRequest request, HttpServletResponse response, Principal principal) throws Exception {
		/*request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String orderNo = request.getParameter("Orderid");
		String name = principal.getName();
		PrintWriter out = response.getWriter();
		String json = null;
		JSONObject retMsgJson = new JSONObject();
		if (orderNo == null || name == null) {
			retMsgJson.put("msg", "error");
			retMsgJson.put("body", "数据请求异常");
			json = retMsgJson.toString();
			out.write(json);
			out.close();
			return;
		}
		Account account = accountService.findById(Long.valueOf(principal.getName()));// 获取用户数据
		String userId = account.getId().toString();
		// 金额转化为分为单位
		float sessionmoney = Float.parseFloat(money);
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
		String out_trade_no = UUID.randomUUID().toString();// 订单编号
		int intMoney = Integer.parseInt(finalmoney);
		// 总金额以分为单位，不带小数点
		String total_fee = String.valueOf(intMoney);
		// 订单生成的机器 IP
		String spbill_create_ip = request.getRemoteAddr();
		String notify_url = WeixinPayConstants.notify_url;// 微信异步通知地址
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
		String allParameters = "";
		try {
			allParameters = reqHandler.genPackage(packageParams);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String createOrderURL = WeixinPayConstants.createOrderURL;
		String prepay_id = "";
		try {
			prepay_id = new GetWxOrderno().getPayNo(createOrderURL, xml);
			if (prepay_id.equals("")) {
				retMsgJson.put("msg", "error");
				json = retMsgJson.toString();
				out.write(json);
				out.close();
				return;
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
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
		out.write(json);
		out.close();*/
	}

	// 微信异步通知
	@RequestMapping(value = "/notify")
	@ResponseBody
	public void notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		InputStream in = request.getInputStream();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = in.read(buffer)) != -1) {
			out.write(buffer, 0, len);
		}
		out.close();
		in.close();
		String msgxml = new String(out.toByteArray(), "utf-8");// xml数据
		System.out.println(msgxml);
		Map map = new GetWxOrderno().doXMLParse(msgxml);
		String result_code = (String) map.get("result_code");
		String out_trade_no = (String) map.get("out_trade_no");
		String total_fee = (String) map.get("total_fee");
		String sign = (String) map.get("sign");
		Double amount = new Double(total_fee) / 100;// 获取订单金额
		String attach = (String) map.get("attach");
		String sn = out_trade_no.split("\\|")[0];// 获取订单编号
		Long memberid = Long.valueOf(attach);
		Member member = memberService.find(memberid);
		Order order = orderService.findBySn(sn);
		if (result_code.equals("SUCCESS") && member != null && order != null) {
			// 验证签名
			float sessionmoney = Float.parseFloat(order.getAmount().toString());
			String finalmoney = String.format("%.2f", sessionmoney);
			finalmoney = finalmoney.replace(".", "");
			int intMoney = Integer.parseInt(finalmoney);
			// 总金额以分为单位，不带小数点
			String order_total_fee = String.valueOf(intMoney);
			String fee_type = (String) map.get("fee_type");
			String bank_type = (String) map.get("bank_type");
			String cash_fee = (String) map.get("cash_fee");
			String is_subscribe = (String) map.get("is_subscribe");
			String nonce_str = (String) map.get("nonce_str");
			String openid = (String) map.get("openid");
			String return_code = (String) map.get("return_code");
			String sub_mch_id = (String) map.get("sub_mch_id");
			String time_end = (String) map.get("time_end");
			String trade_type = (String) map.get("trade_type");
			String transaction_id = (String) map.get("transaction_id");
			// 需要对以下字段进行签名
			SortedMap<String, String> packageParams = new TreeMap<String, String>();
			packageParams.put("appid", appid);
			packageParams.put("attach", order.getMember().getId().toString()); // 用自己系统的数据：会员id
			packageParams.put("bank_type", bank_type);
			packageParams.put("cash_fee", cash_fee);
			packageParams.put("fee_type", fee_type);
			packageParams.put("is_subscribe", is_subscribe);
			packageParams.put("mch_id", partner);
			packageParams.put("nonce_str", nonce_str);
			packageParams.put("openid", openid);
			packageParams.put("out_trade_no", out_trade_no);
			packageParams.put("result_code", result_code);
			packageParams.put("return_code", return_code);
			packageParams.put("sub_mch_id", sub_mch_id);
			packageParams.put("time_end", time_end);
			packageParams.put("total_fee", order_total_fee); // 用自己系统的数据：订单金额
			packageParams.put("trade_type", trade_type);
			packageParams.put("transaction_id", transaction_id);
			RequestHandler reqHandler = new RequestHandler(request, response);
			reqHandler.init(appid, appsecret, partnerkey);
			String endsign = reqHandler.createSign(packageParams);
			if (sign.equals(endsign)) {// 验证签名是否正确
										// 官方签名工具地址：https://pay.weixin.qq.com/wiki/tools/signverify/
				if ("订单没有支付") {
					try {
						// 处理自己的业务逻辑
						response.getWriter().write(setXml("SUCCESS", "OK")); // 告诉微信已经收到通知了
					} catch (Exception e) {
						System.out.println("微信支付异步通知异常");
					}
				} else if ("订单支付了") {
					response.getWriter().write(setXml("SUCCESS", "OK")); // 告诉微信已经收到通知了
				}

			}
		}*/
	}

	public static String setXml(String return_code, String return_msg) {
		return "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA[" + return_msg
				+ "]]></return_msg></xml>";
	}
	@RequestMapping(value = "/topay123")
	@ResponseBody
	public ResponseEntity<?> test(){
		JSONObject retMsgJson = new JSONObject();
		retMsgJson.put("appid", appid);
		retMsgJson.put("timestamp", 12312);
		retMsgJson.put("noncestr", 123);
		retMsgJson.put("partnerid", WeixinPayConstants.partner);
		retMsgJson.put("prepayid", 42142);
		retMsgJson.put("package", "Sign=WXPay");
		retMsgJson.put("sign", 12);
		String json = retMsgJson.toString();
		System.out.println(json);
		return new ResponseEntity<>(json, HttpStatus.OK);
	}
	
	/*public static void main(String[] args) {
		JSONObject retMsgJson = new JSONObject();
		retMsgJson.put("appid", appid);
		retMsgJson.put("timestamp", 12312);
		retMsgJson.put("noncestr", 123);
		retMsgJson.put("partnerid", WeixinPayConstants.partner);
		retMsgJson.put("prepayid", 42142);
		retMsgJson.put("package", "Sign=WXPay");
		retMsgJson.put("sign", 12);
		String json = retMsgJson.toString();
		System.out.println(json);
	}*/
}