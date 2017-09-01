package com.iycharge.server.api.controller;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.dict.DictData;
import com.iycharge.server.domain.entity.notification.Notification_Sender;
import com.iycharge.server.domain.entity.notification.SendCloudReturn;
import com.iycharge.server.domain.entity.utils.EmailAddress;
import com.iycharge.server.domain.service.AccountService;
import com.iycharge.server.domain.service.NotifiSenderService;


/**
 * sendcloud url响应
 * @author bwang
 */
@RestController
@RequestMapping(value="/api/scloudrt")
public class SendCloudReturnController {
	@Autowired
	private NotifiSenderService notifiSenderService;
	@Autowired
	private AccountService accountService;
	//邮件webhook 响应
    @RequestMapping(value="/request",method=RequestMethod.POST)
    public ResponseEntity<?> mail(String recipient) {
    	//EmailAddress email = new EmailAddress(recipient);
    	if(recipient == null){
    		return new ResponseEntity<>(HttpStatus.OK);
    	}
    	Account account = accountService.findByEmail(recipient);
    	if(account == null){
    		return new ResponseEntity<>(HttpStatus.OK);
    	}
    	List<Notification_Sender> senders = notifiSenderService.findByAccount(account);
    	if(senders!=null&&senders.size()>0){
    		senders = sort(senders);
    		Notification_Sender ms = senders.get(0);
    		ms.setStatus(false);
    		notifiSenderService.save(ms);
    	}
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //短信webhook响应
    @RequestMapping(value="/note_response",method=RequestMethod.POST)
    public ResponseEntity<?> chargingAuth(@RequestParam(required = false, value = "phone") String phone) {
    	List<Notification_Sender> needChanges = new ArrayList<>();
    	if(phone==null){
    		return new ResponseEntity<>(HttpStatus.OK);
    	}
		Account account = accountService.findByPhone(phone);
		if(account == null){
			return new ResponseEntity<>(HttpStatus.OK);
		}
		List<Notification_Sender> senders = notifiSenderService.findByAccount(account);
		Notification_Sender need = sort(senders).get(0);
		need.setStatus(false);
		needChanges.add(need);
    	
    	notifiSenderService.saveAll(needChanges);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    
    //排序，找出插入的记录
    private List<Notification_Sender> sort(List<Notification_Sender> sender){
    	if(sender==null){
			return sender;
		}
		Collections.sort(sender, new Comparator<Notification_Sender>(){  
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
		return sender;
    }
}
