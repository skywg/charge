package com.iycharge.server.domain.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.elasticsearch.common.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.iycharge.server.domain.common.utils.ReflectField;
import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.notification.Notification;
import com.iycharge.server.domain.entity.notification.Notification_Sender;
import com.iycharge.server.domain.repository.NotifiSenderRepository;
import com.iycharge.server.domain.repository.NotificationRepository;
import com.iycharge.server.domain.service.NotifiSenderService;
import com.iycharge.server.domain.service.NotificationService;

@Service
public class NotifiSenderServiceImpl implements NotifiSenderService{

	@Autowired
	private NotifiSenderRepository notifiSenderRepository;
	@Override
	public List<Notification_Sender> findAll() {
		return notifiSenderRepository.findAll();
	}
	@Override
	public Notification_Sender findById(Long id) {
		return notifiSenderRepository.findById(id);
	}
	@Override
	public Notification_Sender findByNotificationAndAccount(Long Notification_id, Long account_id) {
		// TODO Auto-generated method stub
		return notifiSenderRepository.findByNotificationAndAccount(Notification_id, account_id);
	}
	@Override
	public Notification_Sender save(Notification_Sender notification_Sender) {
		// TODO Auto-generated method stub
		return notifiSenderRepository.saveAndFlush(notification_Sender);
	}
	public List<Notification_Sender> findByAccount(Account account){
		return notifiSenderRepository.findByAccount(account);
	}
	@Override
	public List<Notification_Sender> saveAll(List<Notification_Sender> senders) {
		// TODO Auto-generated method stub
		senders = notifiSenderRepository.save(senders);
		notifiSenderRepository.flush();
		return senders;
	}
	@Override
	public List<Notification_Sender> findByNotification(Notification notification) {
		
		return notifiSenderRepository.findByNotification(notification);
	}
	@Override
	public Page<Notification_Sender> findByAccount(Account account, Pageable pageable) {
		// TODO Auto-generated method stub
		return notifiSenderRepository.findByAccount(account, pageable);
	}
}
