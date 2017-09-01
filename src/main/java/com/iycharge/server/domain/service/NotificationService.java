package com.iycharge.server.domain.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.notification.Notification;
import com.iycharge.server.domain.entity.notification.NotificationQueryParam;

public interface NotificationService {

	//public Page<Notification> findByRecipient(Account recipient, Pageable pageable);

	public List<Notification> findAll();
     
	public Notification findById(Long id);
    
	public Page<Notification> findAll(Pageable  pageable);
	public Page<Notification> findAllSearch(NotificationQueryParam queryParam,Pageable pageable);

	public Notification save(Notification notification);
}
