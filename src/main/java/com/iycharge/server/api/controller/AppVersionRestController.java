package com.iycharge.server.api.controller;
import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.domain.entity.BaseEntity;
import com.iycharge.server.domain.entity.admin.AppType;
import com.iycharge.server.domain.entity.admin.AppVersion;
import com.iycharge.server.domain.service.AppVersionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/app")
public class AppVersionRestController {

	@Autowired
    private AppVersionService appVersionService;
	
	@Autowired
	private Environment env;
    
    @RequestMapping(value = "/update")
    @JsonView(BaseEntity.Summary.class)
    public Map<String, String>  checkUpdate(Integer appType){
    	Map<String,String> map = new LinkedHashMap<>();
    	if(appType != 0 && appType != 1){
    		map.put("ErrorMessage:", appType+"请输入正确的手机类型：1表示android，0表示ios");
    		return map;
    	}
    	AppType at = (appType==1)?AppType.ANDROID:AppType.IOS;
    	List<AppVersion> lst = new ArrayList<>();
    	
    	lst = appVersionService.findByAppType(at);
    	if(lst == null || lst.size() == 0){
    		map.put("Message:", "暂时还没有版本");
    		return map;
    	}
    	AppVersion appVersion = findMax(lst);
    	String url = "http://"+env.getProperty("server.domain")+":"+
    	env.getProperty("server.port")+"/app/files/"+appVersion.getAppType().getTitle().toLowerCase()+"/"+appVersion.getAppFile();
    	map.put("appType", appVersion.getAppType().getTitle());
    	map.put("version", appVersion.getVersion());
    	map.put("updateDescr" , appVersion.getUpdateDescr());
    	map.put("forceUpdate", String.valueOf(appVersion.isForceUpdate()));
    	map.put("appLink", url);
    	return map;
    }
    
    private AppVersion findMax(List<AppVersion> lst){
    	AppVersion appVersion = new AppVersion();
    	appVersion = lst.get(0);
    	for(AppVersion av : lst){
    		try {
				if(compareVersion(appVersion.getVersion(),av.getVersion()) < 0){
					appVersion = av;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    				
    	}
    	return appVersion;
    }
    @RequestMapping(value="/downLoad")
    public ResponseEntity<InputStreamResource> downLoad(Integer appType){
    	AppType at = (appType==1)?AppType.ANDROID:AppType.IOS;
    	List<AppVersion> lst = new ArrayList<>();
    	lst = appVersionService.findByAppType(at);
    	AppVersion appVersion = findMax(lst);
    	String name = appVersion.getAppFile();
    	//Resource resource = new ClassPathResource("static/files/"+appVersion.getAppType().name().toLowerCase()+"/"+name);
    	ClassPathResource outfile = null;
    	try {
			outfile = new ClassPathResource("static/app/files/"+appVersion.getAppType().name().toLowerCase()+"/"+name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	HttpHeaders headers = new HttpHeaders();  
    	if(outfile!=null){
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");  
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", outfile.getFilename()));  
            headers.add("Pragma", "no-cache");  
            headers.add("Expires", "0");
	        try {
	 		return ResponseEntity  
	 		           .ok()  
	 		           .headers(headers)  
	 		           .contentLength(outfile.contentLength())  
	 		           .contentType(MediaType.parseMediaType("application/vnd.android.package-archive"))  
	 		           .body(new InputStreamResource(outfile.getInputStream()));
	 		} catch (IOException e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		}
    	}
    	return null;
    }
    
    
    /** 
     * 比较版本号的大小,前者大则返回一个正数,后者大返回一个负数,相等则返回0 
     * @param version1 
     * @param version2 
     * @return 
     */  
    private int compareVersion(String version1, String version2) throws Exception {  
        if (version1 == null || version2 == null) {  
            throw new Exception("compareVersion error:illegal params.");  
        }  
        String[] versionArray1 = version1.split("\\.");
        String[] versionArray2 = version2.split("\\.");  
        int idx = 0;  
        int minLength = Math.min(versionArray1.length, versionArray2.length);//取最小长度值  
        int diff = 0;  
        while (idx < minLength  
                && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0//先比较长度  
                && (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {//再比较字符  
            ++idx;  
        }  
        //如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；  
        diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;  
        return diff;  
    }  
    
}
