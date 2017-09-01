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
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.elasticsearch.common.lang3.StringUtils;
import org.elasticsearch.search.rescore.QueryRescorer.QueryRescoreContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iycharge.server.domain.common.utils.ReflectField;
import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.notification.Notification;
import com.iycharge.server.domain.entity.notification.NotificationQueryParam;
import com.iycharge.server.domain.entity.notification.NotificationStatus;
import com.iycharge.server.domain.entity.notification.Notification_Sender;
import com.iycharge.server.domain.entity.notification.SendType;
import com.iycharge.server.domain.repository.NotificationRepository;
import com.iycharge.server.domain.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService{

	@Autowired
	private NotificationRepository notificationRepository;
/*	public Page<Notification> findByRecipient(Account recipient, Pageable pageable) {
//		return notificationRepository.findByRecipient(recipient, pageable);
	}*/
	
	@Override
	public List<Notification> findAll() {
		return notificationRepository.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Notification findById(Long id) {
		Notification notification = notificationRepository.findById(id);
		System.out.println(notification.getSenders());
		return notificationRepository.findById(id);
	}

	@Override
	public Page<Notification> findAll(Pageable pageable) {
		return notificationRepository.findAll(pageable);
	}

	@Override
	public Page<Notification> findAllSearch(NotificationQueryParam queryParam, Pageable pageable) {
		Specification<Notification> spec = new Specification<Notification>(){
			@Override
			public Predicate toPredicate(Root<Notification> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				List<Expression<Boolean>> expressions = predicate.getExpressions();
				if(queryParam.getEndAt()!=null&&queryParam.getEndAt()!=null){
					expressions.add(cb.between(root.<Date>get("updatedAt"), queryParam.getStartAt(), queryParam.getEndAt()));
				}
				if(queryParam.getSendType()!=null&&!"".equals(queryParam.getSendType())){
					Path<String> nick = root.<String>get("sendType");
					expressions.add(cb.equal(nick, queryParam.getSendType()));
				}
				if(queryParam.getNstatus()!=null&&!"".equals(queryParam.getNstatus())){
					Path<String> nick = root.<String>get("notificationStatus");
					expressions.add(cb.equal(nick, queryParam.getNstatus()));
				}
				if(queryParam.getPhone()!=null&&!"".equals(queryParam.getPhone())){
					
					Join<Notification, Account> join = root.join(root.getModel().getList("senders", Account.class), JoinType.LEFT);
					Path<String> nick = join.get("phone");
					//Path<String> nick = root.join("senders").get("phone");
					expressions.add(cb.like(nick, "%"+queryParam.getPhone()+"%"));
				}
				query.distinct(true);
				//query.groupBy(root.get("id"));
				return predicate;
			}
		}; 
		return notificationRepository.findAll(spec, pageable);
	}

	@Override
	public Notification save(Notification notification) {
		// TODO Auto-generated method stub
		return notificationRepository.saveAndFlush(notification);
	}
	
}
