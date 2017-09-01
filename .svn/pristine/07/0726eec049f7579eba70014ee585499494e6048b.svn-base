package com.iycharge.server.admin.message.ios;

import com.iycharge.server.admin.message.IOSNotification;
public class IOSFilecast extends IOSNotification {
	public IOSFilecast(String appkey,String appMasterSecret,String display_type) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "filecast");	
			this.setPredefinedKeyValue("display_type", display_type);
	}
	
	public void setFileId(String fileId) throws Exception {
    	setPredefinedKeyValue("file_id", fileId);
    }
}
