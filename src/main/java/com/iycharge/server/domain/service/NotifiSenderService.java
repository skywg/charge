package com.iycharge.server.domain.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.notification.Notification;
import com.iycharge.server.domain.entity.notification.Notification_Sender;

public interface NotifiSenderService {

	//public Page<Notification> findByRecipient(Account recipient, Pageable pageable);

	public List<Notification_Sender> findAll();
     
	public Notification_Sender findById(Long id);
    
//	public Page<Notification> findAll(Pageable  pageable);
//	public Page<Notification> findAllSearch(String  fields[],Notification notification,Pageable pageable);

	public Notification_Sender save(Notification_Sender notification_Sender);
	
	public Notification_Sender findByNotificationAndAccount(Long Notification_id , Long account_id);
	
	public List<Notification_Sender> findByAccount(Account account);
	
	public Page<Notification_Sender> findByAccount(Account account,Pageable pageable);
	
	public List<Notification_Sender> findByNotification(Notification notification);
	
	public List<Notification_Sender> saveAll(List<Notification_Sender> senders);
}
