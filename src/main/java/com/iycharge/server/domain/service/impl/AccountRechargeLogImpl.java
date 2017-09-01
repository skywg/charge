package com.iycharge.server.domain.service.impl;

import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.account.AccountRechargeLog;
import com.iycharge.server.domain.repository.AccountRechargeLogRepository;
import com.iycharge.server.domain.service.AccountRechargeLogService;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
@Service
public class AccountRechargeLogImpl implements AccountRechargeLogService{
	@Autowired
	AccountRechargeLogRepository accountRechargeLogRepository;

	@Override
	public Page<AccountRechargeLog> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AccountRechargeLog findById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AccountRechargeLog save(AccountRechargeLog accountRechargeLog) {
		return accountRechargeLogRepository.save(accountRechargeLog);
	}

	@Override
	public List<AccountRechargeLog> findListAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void del(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Page<AccountRechargeLog> findAllSearch(String[] fields, AccountRechargeLog accountRechargeLog,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AccountRechargeLog> findByIdIn(List<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<AccountRechargeLog> findByUpdatedAt(Date updatedAt) {
		return accountRechargeLogRepository.findByUpdatedAt(updatedAt);
	}

	@Override
	public List<AccountRechargeLog> findByCondition(Date startTime, Date endTime, Account account, boolean status) {
		// TODO Auto-generated method stub
		return accountRechargeLogRepository.findByCondition(startTime, endTime, account, status);
	}


}
