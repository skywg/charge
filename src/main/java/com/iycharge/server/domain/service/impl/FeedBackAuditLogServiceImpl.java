package com.iycharge.server.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iycharge.server.domain.entity.feedback.FeedBackAuditLog;
import com.iycharge.server.domain.repository.FeedBackAuditLogRepository;
import com.iycharge.server.domain.service.FeedBackAuditLogService;
@Service
public class FeedBackAuditLogServiceImpl implements FeedBackAuditLogService{
	@Autowired
	private FeedBackAuditLogRepository  feedBackAuditLogRepository;
	@Override
	public FeedBackAuditLog save(FeedBackAuditLog feedBackAuditLog) {
		
		return  feedBackAuditLogRepository.save(feedBackAuditLog);
	}

}
