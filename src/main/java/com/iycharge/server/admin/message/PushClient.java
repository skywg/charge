package com.iycharge.server.admin.message;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import com.iycharge.server.admin.message.android.AndroidBroadcast;
import com.iycharge.server.admin.message.android.AndroidCustomizedcast;
import com.iycharge.server.admin.message.android.AndroidListcast;
import com.iycharge.server.admin.message.ios.IOSBroadcast;
import com.iycharge.server.admin.message.ios.IOSCustomizedcast;
import com.iycharge.server.admin.message.ios.IOSListcast;
import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.notification.Notification;


public class PushClient {
	// The user agent
	protected final String USER_AGENT = "Mozilla/5.0";
	// This object is used for sending the post request to Umeng
	protected HttpClient client = new DefaultHttpClient();
	// The host
	protected static final String host = "http://msg.umeng.com";
	// The upload path
	protected static final String uploadPath = "/upload";

	// The post path
	protected static final String postPath = "/api/send";
	
	private static final String android_appkey = "5791c36ee0f55a60d10009a6";
	private static final String android_appMasterSecret = "tzvua8lwugehyvqor0bqxnghmj3g46nh";
	
	private static final String ios_appkey = "5819dbeb717c1908880029a1";
	private static final String ios_appMasterSecret = "rop9ctdde5l74pguo4l87v0mqv8haksl";
	
	public Integer send(UmengNotification msg) throws Exception {
		String timestamp = Integer.toString((int)(System.currentTimeMillis() / 1000));
		msg.setPredefinedKeyValue("timestamp", timestamp);
        String url = host + postPath;
        String postBody = msg.getPostBody();
        String sign = DigestUtils.md5Hex(("POST" + url + postBody + msg.getAppMasterSecret()).getBytes("utf8"));
        url = url + "?sign=" + sign;
        HttpPost post = new HttpPost(url);
        post.setHeader("User-Agent", USER_AGENT);
        StringEntity se = new StringEntity(postBody, "UTF-8");
        post.setEntity(se);
        // Send the post request and get the response
        HttpResponse response = client.execute(post);
        int status = response.getStatusLine().getStatusCode();
        System.out.println("Response Code : " + status);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        System.out.println(result.toString());
        if (status == 200) {
            System.out.println("Notification sent successfully.");
        } else {
            System.out.println("Failed to send the notification!");
        }
        return status;
    }
	/**
	 * 
	 * @param notification 消息对象
	 * @param sendTarget 发送目标 ： unicast-单播 listcast-列播  broadcast-广播 groupcast-组播
	 * @param phoneType android/ios
	 * @return 状态码  200为发送成功
	 */
	public Integer send(Notification notification,String sendTarget,String phoneType,String display_type){
		UmengNotification um = genObj(notification,sendTarget,phoneType,display_type);
		initSendAction(notification , um,phoneType);
		Integer return_tag = 0;
		try {
			if(um != null){
				return_tag = send(um);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return return_tag;
	}
	
	private UmengNotification initSendAction(Notification notification , UmengNotification um,String phoneType) {
		if(phoneType!=null&&phoneType.toLowerCase().equals("ios")){
			return um;
		}
		String url = notification.getUrl();
		String action = notification.getAction();
		String voice = notification.getVoice();
		try{
			if(action!=null&&action.equals("openUrl")){
				um.setAfterOpen(action);
				um.setUrl(url);
			}
			if(voice!=null){
				switch(voice){
				case "voice": {um.setPlayVibrate(false);um.setPlaySound(true);break;}
				case "shake": {um.setPlaySound(false);um.setPlayVibrate(true);break;}
				case "voice,shake": {um.setPlayVibrate(true);um.setPlaySound(true);}
				default:break;
				
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return um;
	}
	private UmengNotification genObj(Notification notification,String sendTarget,String phoneType,String display_type){
		UmengNotification um = null;
		try{
			if(phoneType.equals("android")){
				switch(sendTarget){
				case "broadcast" : 
					AndroidBroadcast ab = new AndroidBroadcast(android_appkey,android_appMasterSecret,display_type);
					if(display_type.equals("notification")){
						ab.setText(notification.getContent());
						ab.setTicker(notification.getTicker());
						ab.setTitle(notification.getTitle());
					}else{
						ab.setTitle(notification.getTitle());
						ab.setCustomField(notification.getContent());
					}
					return ab;
				case "listcast" : 
					String tokens = setTokens(notification);
					AndroidListcast al =  new AndroidListcast(android_appkey, android_appMasterSecret,display_type);
					al.setDeviceToken(tokens);
					if(display_type.equals("notification")){
						al.setText(notification.getContent());
						al.setTicker(notification.getTicker());
						al.setTitle(notification.getTitle());
					}else{
						al.setTitle(notification.getTitle());
						al.setCustomField(notification.getContent());
					}
					return al;
				default : ;
 				}
			}else if(phoneType.equals("ios")){
				switch(sendTarget){
				case "broadcast" : 
					IOSBroadcast ab =new IOSBroadcast(ios_appkey,ios_appMasterSecret);
					if(display_type.equals("notification")){
						ab.setAlert(notification.getContent());
						ab.setSound("default");
					}
					return ab;
				case "listcast" : 
					String tokens = setTokens(notification);
					IOSListcast al = new IOSListcast(ios_appkey, ios_appMasterSecret);
					al.setDeviceToken(tokens);
					if(display_type.equals("notification")){
						al.setAlert(notification.getContent());
						al.setSound("default");
					}
					return al;
				default : ;
 				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return um;
	}
	private String setTokens(Notification notification){
		List<Account> senders = notification.getSenders();
		String tokens = "";
		for(Account account : senders){
			tokens+=account.getDeviceToken()+",";
		}
		if(tokens.length()>0){
			tokens = tokens.substring(0,tokens.lastIndexOf(","));
		}
		
		return tokens;
	}	
}
