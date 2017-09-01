package com.iycharge.server.domain.entity.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Cipher;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.iycharge.server.ccu.util.Utility;
import com.iycharge.server.domain.entity.order.RechargeRecord;

/**
 *
 * @author bwang
 */
public class AlipayPaymentUtil {
    
    private static final String PARTNER = "2088421497053595";
    private static final String SELLER = "qyjsyf@qq.com";
    private static final String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMYcnKm1lHa4EQ0uDeJvypGmbTAqp77MxiR5oH8RLz28a4E7WlkJ3GRsvDxFDN/Xzy+7W4z5/UOiTLzEDtTXnb5NWJlFpx6eMcK7ENqgEPA+dy2ICFik3VswpjBflXNlMz5KB47yp8Yfw/ERuvrLMOeqgfRMwnUie9aNwSbl2U2zAgMBAAECgYAIeejqCe7UNYPlQHcvnQDZwnkhenG65CAGJ34Kcb3HsmwXF2cCMD3RviU/nuSyHxWqQSXlnjyLzEHkQa8wxBjnnCt2/+t4vaZXwdHY2MOMyBhFp4v8nukDW95B6KzGz8eCpWv0PiL+cGOE8Wj3uq8+roLCvHjUDy5wr+jqfauNQQJBAPNMuhEkzWuSIBOS3JAWQduxoAigPsjuhmSTcZN+kaa8OfOg9r+kDPA4/vXeqJq2sioqy5JcM7tkXLabBYhMMrsCQQDQdAcROZVi6aQZpqaLA04B5kKmVRh/wVcXJ8OkZ0VkDOPAEbfHt7X0kWXKP4ym5Lhx911iywAIR1QC02CsPw1pAkAOw6Qy8CHLGlX5+hqKX99lPpUwkNDejqL3LX9h7PdXv+pgX2851/G1DjBiqT1gDiAnsOsjmZbGE2QQ7L1cNQuLAkEAh4rKhaLBxj1R3PQF2uJdONL2h51RftDCxmWuyxnzfuj6totOyuhjwhFA9M95r5ONc117NZ/192wZ7nRvqdCd8QJADx9JgtBI0eAy/iMv7iC2mP8As9BSkn8Qibhif7PIJChQr/fVbgfN7O5iOjpHf0/sxehg4nWAquIiG3sHeeyIsQ==";
    private static final String ALIPAY_VERIFY_URL = "https://mapi.alipay.com/gateway.do?service=notify_verify&";
    //TODO add host
    private static final String ORDER_NOTIFY_URL = "/hooks/order/payments/alipay";
    private static final String RECHARGE_RECORD_URL = "/hooks/recharge-record/payments/alipay";

    private static final String ALIPAY_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
    private static final String INPUT_CHARSET = "UTF-8";
    private static final String SIGN_TYPE = "RSA";
    
    public static ResponseEntity<?> getRechargePayInfo(RechargeRecord order) {
        
        Map<String, String> info = new HashMap<>();
        
        //签约合作者身份ID  
        info.put("partner", PARTNER);
        //签约卖家支付宝账号  
        info.put("seller_id", SELLER);
        // 商户网站唯一订单号  
        info.put("out_trade_no", order.getTradeNo());
        // 商品名称  
        //info.put("subject", order.getId().toString());
        info.put("subject", "充电大亨");
        //商品详情  
        info.put("body", order.getMoney().toString());
        // 商品金额
        info.put("total_fee", order.getMoney().toString());
        // 服务器异步通知页面路径 
        info.put("notify_url", getRechargeNotifyURL());      
        // 服务接口名称
        info.put("service", "mobile.securitypay.pay");
        // 支付类型
        info.put("payment_type", "1");   
        // 设置未付款交易的超时时间  
        info.put("it_b_pay", "24h");
        // 参数编码
        info.put("_input_charset", INPUT_CHARSET);
        //生成签名
        String sign = RSA.sign(buildSignString(prefilter(info), true), PRIVATE_KEY, INPUT_CHARSET);
        info.put("sign_type", SIGN_TYPE);

        try {
            info.put("sign", URLEncoder.encode(sign, INPUT_CHARSET));
        }
        catch (UnsupportedEncodingException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        System.out.println(info);
        
        return new ResponseEntity<>(buildSignString(info, true), HttpStatus.OK);
    }
    
    public static String getRechargeNotifyURL() {
    	System.out.println("------RECHARGE_RECORD_URL----------");
    	System.out.println(RECHARGE_RECORD_URL);
    	System.out.println("-------RECHARGE_RECORD_URL---------");
        return "http://" + Utility.getEnvVar("server.domain") + ":" + Utility.getEnvVar("server.port") + RECHARGE_RECORD_URL;
    }
    
    public  static boolean verify(Map<String, String> params) {
        String response = verifyResponse(params.get("notify_id"));
        boolean signed = verifySign(params, params.get("sign"));
        return signed && response.equals("true");
    }

    //验证是否来自支付宝的通知
    private static String verifyResponse(String notifyId) {
        String response = "";

        String url = ALIPAY_VERIFY_URL + "partner=" + PARTNER + "&notify_id=" + notifyId;
        RestTemplate restTemplate = new RestTemplate();

        try {
            response = restTemplate.getForObject(url, String.class);
        }
        catch (RestClientException e) {
            e.printStackTrace();
        }

        return response;
    }

    private static boolean verifySign(Map<String, String> params, String sign) {
        String preSignString = buildSignString(prefilter(params), false);
        return RSA.verify(preSignString, sign, ALIPAY_PUBLIC_KEY, INPUT_CHARSET);
    }

    public static Map<String, String> prefilter(Map<String, String> params) {
        Map<String, String> result = new HashMap<>();

        if (params == null || params.size() <= 0) return result;

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (StringUtils.isEmpty(entry.getValue()) ||
                    entry.getKey().equalsIgnoreCase("sign") ||
                    entry.getKey().equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    public static String buildSignString(Map<String, String> params, boolean quote) {
        Map<String, String> sortedMap = new TreeMap<>(new Comparator<String>() {
            @Override
			public int compare(String arg0, String arg1) {
                return arg0.compareToIgnoreCase(arg1);
            }
        });
        sortedMap.putAll(params);

        LinkedList<String> pairs = new LinkedList<>();
        for (Map.Entry<String, String> entry : sortedMap.entrySet()) {
            if (quote)
                pairs.add(String.format("%s=\"%s\"", entry.getKey(), entry.getValue()));
            else
                pairs.add(String.format("%s=%s", entry.getKey(), entry.getValue()));
        }

        return StringUtils.collectionToDelimitedString(pairs, "&");
    }
    
    public static class RSA {

        public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

        /**
         * RSA签名
         *
         * @param content    待签名数据
         * @param privateKey 商户私钥
         * @param charset    编码格式
         * @return 签名值
         */
        public static String sign(String content, String privateKey, String charset) {
            try {
                PrivateKey priKey = getPrivateKey(privateKey);

                Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
                signature.initSign(priKey);
                signature.update(content.getBytes(charset));

                return new String(Base64.encode(signature.sign()));
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * RSA验签名检查
         *
         * @param content   待签名数据
         * @param sign      签名值
         * @param publicKey 支付宝公钥
         * @param charset   编码格式
         * @return 布尔值
         */
        public static boolean verify(String content, String sign, String publicKey, String charset) {
            try {
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                byte[] encodedKey = Base64.decode(publicKey.getBytes());
                PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));


                Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
                signature.initVerify(pubKey);
                signature.update(content.getBytes(charset));

                return signature.verify(Base64.decode(sign.getBytes()));
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }

        /**
         * 解密
         *
         * @param content    密文
         * @param privateKey 商户私钥
         * @param charset    编码格式
         * @return 解密后的字符串
         */
        public static String decrypt(String content, String privateKey, String charset) throws Exception {
            PrivateKey prikey = getPrivateKey(privateKey);

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, prikey);

            InputStream ins = new ByteArrayInputStream(Base64.decode(content.getBytes()));
            ByteArrayOutputStream writer = new ByteArrayOutputStream();

            //rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
            byte[] buf = new byte[128];
            int bufl;

            while ((bufl = ins.read(buf)) != -1) {
                byte[] block = null;

                if (buf.length == bufl) {
                    block = buf;
                } else {
                    block = new byte[bufl];
                    System.arraycopy(buf, 0, block, 0, bufl);
                }

                writer.write(cipher.doFinal(block));
            }

            return new String(writer.toByteArray(), charset);
        }


        /**
         * 得到私钥
         *
         * @param key 密钥字符串（经过base64编码）
         * @throws Exception
         */
        public static PrivateKey getPrivateKey(String key) throws Exception {

            byte[] keyBytes = Base64.decode(key.getBytes());

            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            return keyFactory.generatePrivate(keySpec);
        }
    }
}
