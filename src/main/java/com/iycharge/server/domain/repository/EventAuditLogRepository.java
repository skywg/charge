package com.iycharge.server.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.ResponseBody;
import com.iycharge.server.domain.entity.event.EventAuditLog;
@ResponseBody
public interface EventAuditLogRepository extends JpaRepository<EventAuditLog, Long>{
	public EventAuditLog save(EventAuditLog eventAuditLog);
}
