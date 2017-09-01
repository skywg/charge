package com.iycharge.server.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iycharge.server.domain.entity.account.CarAuditLog;
import com.iycharge.server.domain.repository.CarAuditLogRepository;
import com.iycharge.server.domain.repository.CarRepository;
import com.iycharge.server.domain.service.CarAuditLogService;
@Service
public class CarAuditLogServiceImpl implements CarAuditLogService{
	
	@Autowired
	private CarAuditLogRepository carAuditLogRepository;
	@Override
	public CarAuditLog save(CarAuditLog carAuditLog) {
		// TODO Auto-generated method stub
		return carAuditLogRepository.save(carAuditLog);
	}
	
}
