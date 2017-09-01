package com.iycharge.server.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iycharge.server.domain.entity.event.EventAuditLog;
import com.iycharge.server.domain.repository.EventAuditLogRepository;
import com.iycharge.server.domain.service.EventAuditLogService;
@Service
public class EventAuditLogServiceImpl implements EventAuditLogService{
	@Autowired
	private EventAuditLogRepository eventAuditLogRepository;
	@Override
	public EventAuditLog save(EventAuditLog eventAuditLog) {

		return eventAuditLogRepository.save(eventAuditLog);
	}

}
