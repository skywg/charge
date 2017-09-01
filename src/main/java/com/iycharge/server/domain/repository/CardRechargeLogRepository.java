package com.iycharge.server.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iycharge.server.domain.entity.account.CardRechargeLog;

@Repository
public interface CardRechargeLogRepository extends JpaRepository<CardRechargeLog, Long> {
	
	
}
