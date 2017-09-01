package com.iycharge.server.domain.entity.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.client.RestTemplate;

import com.iycharge.server.api.security.OneTimePasswordAuthenticator;
import com.iycharge.server.api.security.SmsApi_Send;
import com.iycharge.server.domain.entity.admin.Manager;
import com.iycharge.server.domain.entity.admin.ManagerLog;

import main.java.com.UpYun;

public class Utils {

    // 运行前先设置好以下三个参数
    private static final String BUCKET_NAME = "itemspot-assets";
    private static final String USER_NAME = "itemspotassets";
    private static final String USER_PWD = "itemspotassets";
    private static RestTemplate restTemplate;
    private static OneTimePasswordAuthenticator oneTimePasswordAuthenticator = new OneTimePasswordAuthenticator();
    private static SmsApi_Send smsApi_Send=new SmsApi_Send();
   /* public static String sendOneTimePassword(MessageService messageService, String phone)
            throws InvalidKeyException, NoSuchAlgorithmException {

        long timeIndex = System.currentTimeMillis() / 1000 / 30;
        long code = oneTimePasswordAuthenticator.getCode(phone.getBytes(), timeIndex);
        String token = String.valueOf(code);

        messageService.sendSMS(phone, token);

        return token;
    }*/

    public static String sendOneTimePassword(String phone)
            throws InvalidKeyException, NoSuchAlgorithmException {

        long timeIndex = System.currentTimeMillis() / 1000 / 30;
        long code = oneTimePasswordAuthenticator.getCode(phone.getBytes(), timeIndex);
        String token = String.valueOf(code);

        //messageService.sendSMS(phone, token);
        SmsApi_Send.sendSMS(phone);
        return token;
    }
    
    public static String genFileName(String directory) {
        String filename = UUID.randomUUID().toString();
        return String.format("/%s/%s/%s/%s/%s", directory,
                filename.substring(0, 2), filename.substring(2, 4),
                filename.substring(4, 6), filename);
    }

    public static boolean save(String filePath, byte[] data) {
        UpYun upyun = new UpYun(BUCKET_NAME, USER_NAME, USER_PWD);
        upyun.setDebug(true);
        return upyun.writeFile(filePath, data, true);
    }

    public static String fetchAndStoreAvatar(String url) {
        String imageData = "";
        String path = genFileName("avatar");

        try {
            imageData = restTemplate.getForObject(url, String.class);
            save(path, imageData.getBytes());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return path;
    }
    
    public static String dateChangeToNumber(Date date){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    	return sdf.format(date);
    }
    
    public static String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return sdf.format(new Date());
    }
    public static Date stringToDate(String s) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
			return sdf.parse(s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new Date();
    }
    
    public static ManagerLog setLog(HttpServletRequest request,String logModule,String logType,String params){
    	HttpSession session = request.getSession();
    	Manager manager = (Manager) session.getAttribute("user");
    	ManagerLog log = new ManagerLog();
    	log.setLoginName(manager.getLoginName());
    	log.setRealName(manager.getRealname());
    	log.setLogTime(new Date());
    	log.setIp(request.getRemoteAddr());
    	log.setLogModule(logModule);
    	log.setLogType(logType);
    	log.setParams(params);
    	return log;
    }
    
}

