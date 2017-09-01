package com.iycharge.server.admin.message.ios;

import com.iycharge.server.admin.message.IOSNotification;

public class IOSListcast extends IOSNotification {
	public IOSListcast(String appkey,String appMasterSecret) throws Exception{
		setAppMasterSecret(appMasterSecret);
		setPredefinedKeyValue("appkey", appkey);
		this.setPredefinedKeyValue("type", "listcast");	
	}
	@Override
	public void setDeviceToken(String token) throws Exception {
		String all_tokens = "";
		String tokens[] = token.split(",");
		for(int i = 0 ; i < tokens.length ; i++){
			if(i == tokens.length - 1){
				all_tokens+=tokens[i];
				break;
			}
			all_tokens += tokens[i] + ",";
		}
		setPredefinedKeyValue("device_tokens", all_tokens);
	}
}
