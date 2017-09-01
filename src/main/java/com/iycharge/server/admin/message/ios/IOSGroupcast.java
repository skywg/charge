package com.iycharge.server.admin.message.ios;

import com.iycharge.server.admin.message.IOSNotification;

import net.sf.json.JSONObject;
public class IOSGroupcast extends IOSNotification {
	public IOSGroupcast(String appkey,String appMasterSecret,String display_type) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "groupcast");
			this.setPredefinedKeyValue("display_type", display_type);
	}
	
	public void setFilter(JSONObject filter) throws Exception {
    	setPredefinedKeyValue("filter", filter);
    }
}
