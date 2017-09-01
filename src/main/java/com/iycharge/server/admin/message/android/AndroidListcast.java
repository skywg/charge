package com.iycharge.server.admin.message.android;

import com.iycharge.server.admin.message.AndroidNotification;

public class AndroidListcast extends AndroidNotification {
	public AndroidListcast(String appkey,String appMasterSecre,String display_type) throws Exception {
		setAppMasterSecret(appMasterSecre);
		setPredefinedKeyValue("appkey", appkey);
		this.setPredefinedKeyValue("type", "listcast");	
		this.setPredefinedKeyValue("display_type", display_type);
}

	public void setDeviceToken(String token) throws Exception {
    	setPredefinedKeyValue("device_tokens", token);
    }
}
