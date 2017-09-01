package com.iycharge.server.admin.message.android;

import org.json.JSONObject;

import com.iycharge.server.admin.message.AndroidNotification;


public class AndroidGroupcast extends AndroidNotification {
	public AndroidGroupcast(String appkey,String appMasterSecret,String display_type) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "groupcast");	
			this.setPredefinedKeyValue("display_type", display_type);
	}
	
	public void setFilter(JSONObject filter) throws Exception {
    	setPredefinedKeyValue("filter", filter);
    }
}
