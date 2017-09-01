package com.iycharge.server.admin.message.android;

import com.iycharge.server.admin.message.AndroidNotification;

public class AndroidBroadcast extends AndroidNotification {
	public AndroidBroadcast(String appkey,String appMasterSecret,String display_type) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "broadcast");	
			this.setPredefinedKeyValue("display_type", display_type);
	}
}
