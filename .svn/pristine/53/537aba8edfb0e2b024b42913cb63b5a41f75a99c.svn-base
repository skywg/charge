package com.iycharge.server.api.payment;

import com.iycharge.server.api.common.WeixinPayConstants;
import com.iycharge.server.api.util.GetWxOrderno;
import com.iycharge.server.api.util.RequestHandler;
import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.order.Order;
import com.iycharge.server.domain.entity.order.OrderStatus;
import com.iycharge.server.domain.entity.order.RechargeRecord;
import com.iycharge.server.domain.entity.utils.AlipayPaymentUtil;
import com.iycharge.server.domain.service.AccountService;
import com.iycharge.server.domain.service.OrderService;
import com.iycharge.server.domain.service.RechargeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import java.util.*;

@RestController
public class AlipayPaymentController {
	// 商户相关资料
	private static String appid = WeixinPayConstants.appid;
	private static String appsecret = WeixinPayConstants.appsecret;
	private static String partner = WeixinPayConstants.partner;
	private static String partnerkey = WeixinPayConstants.partnerkey;
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private RechargeRecordService rechargeRecordService;

    @RequestMapping(value = "/hooks/order/payments/alipay", method = RequestMethod.POST)
    public ResponseEntity<?> handleOrderAlipayPayment(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();

        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            String value = "";
            try {
                value = new String(StringUtils.arrayToCommaDelimitedString(entry.getValue()).getBytes("UTF-8"), "UTF-8");
            }
            catch (UnsupportedEncodingException e) {
                return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
            }
            params.put(entry.getKey(), value);
        }

        if (!AlipayPaymentUtil.verify(params)) {
            return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
        }

        Order order = orderService.findById(Long.valueOf(params.get("out_trade_no")));
        if (order == null) {
            return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
        }

        if (params.get("trade_status").equals("TRADE_SUCCESS")) {
            orderService.payWithAlipay(order, params);
        }

        return new ResponseEntity<>("success", HttpStatus.OK);
    }
    
    @Transactional
    @RequestMapping(value = "/hooks/recharge-record/payments/alipay", method = RequestMethod.POST)
    public ResponseEntity<?> handleRechargeRecordAlipayPayment(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();

        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            String value = "";
            try {
                value = new String(StringUtils.arrayToCommaDelimitedString(entry.getValue()).getBytes("UTF-8"), "UTF-8");
            }
            catch (UnsupportedEncodingException e) {
                return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
            }
            params.put(entry.getKey(), value);
        }
        if (!AlipayPaymentUtil.verify(params)) {
            return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
        }

        RechargeRecord order = rechargeRecordService.findByTradeNo(params.get("out_trade_no"));
        if (order == null) {
            return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
        }
        
        if (params.get("trade_status").equals("TRADE_SUCCESS")) {
            rechargeRecordService.payWithAlipay(order, params);
            
            Account account = accountService.findById(order.getAccount().getId());
            account.setMoney(account.getMoney().add(order.getMoney()));
            
            accountService.save(account);
        }
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
    
    // 微信异步通知
 	@RequestMapping(value = "/hooks/recharge-record/payments/weixinAlipay")
 	@ResponseBody
 	public ResponseEntity<?> notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
 		request.setCharacterEncoding("UTF-8");
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
/*String msgxml="<xml><appid><![CDATA[wx2acd55b37b3ac821]]></appid>"+
 		 		"<attach><![CDATA[1]]></attach>"+
 		 		"<bank_type><![CDATA[CCB_DEBIT]]></bank_type>"+
 		 		"<cash_fee><![CDATA[1]]></cash_fee>"+
 		 		"<fee_type><![CDATA[CNY]]></fee_type>"+
 		 		"<is_subscribe><![CDATA[N]]></is_subscribe>"+
 		 		"<mch_id><![CDATA[1374971502]]></mch_id>"+
 		 		"<nonce_str><![CDATA[1550446758]]></nonce_str>"+
 		 		"<openid><![CDATA[oEm8fv4rmlMpXOKhj79byQurAk-0]]></openid>"+
 		 		"<out_trade_no><![CDATA[1475049044547]]></out_trade_no>"+
 		 		"<result_code><![CDATA[SUCCESS]]></result_code>"+
 		 		"<return_code><![CDATA[SUCCESS]]></return_code>"+
 		 		"<sign><![CDATA[628785E24106E979E6684C9C1A107DC0]]></sign>"+
 		 		"<time_end><![CDATA[20160928155111]]></time_end>"+
 		 		"<total_fee>1</total_fee>"+
 		 		"<trade_type><![CDATA[APP]]></trade_type>"+
 		 		"<transaction_id><![CDATA[4000382001201609285166498373]]></transaction_id>"+
 		 		"</xml>";*/
 		System.out.println(msgxml);
		Map map = new GetWxOrderno().doXMLParse(msgxml);
 		String appid = (String) map.get("appid");
 		String attach = (String) map.get("attach");
 		String bank_type = (String) map.get("bank_type");
 		String cash_fee = (String) map.get("cash_fee");
 		String fee_type = (String) map.get("fee_type");
 		String is_subscribe = (String) map.get("is_subscribe");
		String mch_id = (String) map.get("mch_id");
		String nonce_str = (String) map.get("nonce_str");
		String openid = (String) map.get("openid");
		String out_trade_no = (String) map.get("out_trade_no");
		String result_code = (String) map.get("result_code");
		String return_code = (String) map.get("return_code");
		String sign = (String) map.get("sign");
		String time_end = (String) map.get("time_end");
		String total_fee = (String) map.get("total_fee");
		String trade_type = (String) map.get("trade_type");
		String transaction_id = (String) map.get("transaction_id");
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("attach", attach); // 用自己系统的数据：会员id
		packageParams.put("bank_type", bank_type);
		packageParams.put("cash_fee", cash_fee);
		packageParams.put("fee_type", fee_type);
		packageParams.put("is_subscribe", is_subscribe);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("openid", openid);
		packageParams.put("out_trade_no", out_trade_no);
		packageParams.put("result_code", result_code);
		packageParams.put("return_code", return_code);
		//packageParams.put("sign", sign);
		packageParams.put("time_end", time_end);
		packageParams.put("total_fee", total_fee);
		packageParams.put("trade_type", trade_type);
		packageParams.put("transaction_id", transaction_id);
 		Account account = accountService.findById(Long.valueOf(attach));
 		RechargeRecord order = rechargeRecordService.findByTradeNo(out_trade_no);
 		if (result_code.equals("SUCCESS") && account != null && order != null) {
 			// 需要对以下字段进行签名
 			RequestHandler reqHandler = new RequestHandler(request, response);
 			reqHandler.init(appid, appsecret, partnerkey);
 			String endsign = reqHandler.createSign(packageParams);
 			if (sign.equals(endsign)) {// 验证签名是否正确
 										// 官方签名工具地址：https://pay.weixin.qq.com/wiki/tools/signverify/
 				if(order.getStatus()==OrderStatus.PAID){
 					return new ResponseEntity<>("已经支付！", HttpStatus.OK);
 				}else if(order.getStatus()==OrderStatus.UNPAID){
 					account.setMoney(account.getMoney().add(order.getMoney()));
 					accountService.save(account);
 					order.setStatus(OrderStatus.PAID);
 					order.setTransactionId(transaction_id);
 					order.setWeixinalipayPaymentTime(new Date());
 					rechargeRecordService.save(order);
 					return new ResponseEntity<>("success", HttpStatus.OK);
 				}
 			}else{
 				System.out.println("sign验证不正确");
 				return new ResponseEntity<>("sign验证不正确", HttpStatus.BAD_REQUEST);
 			}
 		}else{
 			System.out.println("订单失败fail");
 	       return new ResponseEntity<>("订单失败fail", HttpStatus.BAD_REQUEST);
 		}
 		return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
 	}
 	

 	public static String setXml(String return_code, String return_msg) {
 		return "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA[" + return_msg
 				+ "]]></return_msg></xml>";
 	}
}
