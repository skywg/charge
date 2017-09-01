package com.iycharge.server.api.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.dict.DictData;
import com.iycharge.server.domain.entity.notification.Notification;
import com.iycharge.server.domain.entity.notification.Notification_Sender;
import com.iycharge.server.domain.service.AccountService;
import com.iycharge.server.domain.service.NotifiSenderService;
import com.iycharge.server.domain.service.NotificationService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RestController
@RequestMapping("/api/getnotify")
public class NotificationRestController {
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private NotifiSenderService notifiSenderService;
	
	@Autowired
	private AccountService accountService;
	
	@RequestMapping("/get")
	public JSONObject getNotify(Principal principal){
		Account account = accountService.findById(Long.valueOf(principal.getName()));
		//Account account = accountService.findById(id);
		List<Notification_Sender> nss = notifiSenderService.findByAccount(account);
		boolean flag = true;
		List<Map<String,String>> nst = new ArrayList<>();
		for(Notification_Sender ns : nss){
			Notification notification = ns.getNotification();
			if(notification.getSendType().name().equals("APPMSG")
					&&notification.getNotificationStatus().name().equals("SENDED")){
				boolean ifRead = ns.isIfRead();
				if(ifRead==false){
					flag = false;
				}
			}
		}
		JSONObject obj = new JSONObject();
		obj.put("flag", flag);
		return obj;
	}
	
	@RequestMapping("/read")
	public ResponseEntity<?> changeStatus(String page,String size,Principal principal){
		Account account = accountService.findById(Long.valueOf(principal.getName()));
		//Account account = accountService.findById(id);
		List<Notification_Sender> nss = notifiSenderService.findByAccount(account);
		nss = sort(nss);
		List<Map<String,String>> nst = new ArrayList<>();
		for(Notification_Sender ns : nss){
			Notification notification = ns.getNotification();
			if(notification.getSendType().name().equals("APPMSG")
					&&notification.getNotificationStatus().name().equals("SENDED")){
				Map<String,String> map = new HashMap<>();
				String time = notification.getSendAt().toString();
				time = time.substring(0, time.lastIndexOf("."));
				map.put("time", time==null?"":time);
				map.put("title", notification.getTitle()==null?"":notification.getTitle());
				map.put("content", notification.getContent()==null?"":notification.getContent());
				nst.add(map);
				if(ns.isIfRead()==false){
					ns.setIfRead(true);;
					notifiSenderService.save(ns);
				}
			}
		}
		List<Map<String,String>> returns = new ArrayList<>();
		if(page==null){
			page = "0";
		}
		if(size==null){
			size="20";
		}
		for(int i = Integer.valueOf(page)*Integer.valueOf(size) ; i < nst.size() ; i++){
			if(i >= nst.size()){
				break;
			}
			returns.add(nst.get(i));
		}
		JSONArray jsonArray = JSONArray.fromObject(returns);
		JSONObject obj = new JSONObject();
		obj.put("totalSize", nst.size());
		obj.put("currentPage", page);
		obj.put("totalPage", nst.size()/Integer.valueOf(size)+1);
		obj.put("items", jsonArray);
		return new ResponseEntity<>(obj,HttpStatus.OK);
	}
	
	private List<Notification_Sender> sort(List<Notification_Sender> nss){
		if(nss==null){
			return nss;
		}
		Collections.sort(nss, new Comparator<Notification_Sender>(){  
			@Override
			public int compare(Notification_Sender o1, Notification_Sender o2) {
				// TODO Auto-generated method stub
				if(o1.getId() > o2.getId()){  
                    return -1;  
                }  
                if(o1.getId() == o2.getId()){  
                    return 0;  
                }  
                return 1;
			}  
        });   
		
		return nss;
	}
}
