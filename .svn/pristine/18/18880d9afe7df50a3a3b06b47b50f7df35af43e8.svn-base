package com.iycharge.server.admin.message.ios;

import com.iycharge.server.admin.message.IOSNotification;

public class IOSCustomizedcast extends IOSNotification {
	public IOSCustomizedcast(String appkey,String appMasterSecret,String display_type) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "customizedcast");	
			this.setPredefinedKeyValue("display_type", display_type);
	}
	
	public void setAlias(String alias,String aliasType) throws Exception {
    	setPredefinedKeyValue("alias", alias);
    	setPredefinedKeyValue("alias_type", aliasType);
    }
		
	public void setFileId(String fileId, String aliasType) throws Exception {
		setPredefinedKeyValue("file_id", fileId);
		setPredefinedKeyValue("alias_type", aliasType);
	}

}
