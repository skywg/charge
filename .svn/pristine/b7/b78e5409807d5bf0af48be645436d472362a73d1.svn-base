package com.iycharge.server.domain.service;

import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.account.AccountRechargeLog;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountRechargeLogService {

    public Page<AccountRechargeLog> findAll(Pageable pageable);

    public AccountRechargeLog findById(long id);

    public AccountRechargeLog save(AccountRechargeLog accountRechargeLog);

	List<AccountRechargeLog> findListAll();
	
	void del(Long id);

	Page<AccountRechargeLog> findAllSearch(String[] fields, AccountRechargeLog accountRechargeLog, Pageable pageable);

	List<AccountRechargeLog> findByIdIn(List<Long> ids);

	List<AccountRechargeLog> findByUpdatedAt(Date updatedAt);
	
	List<AccountRechargeLog> findByCondition(Date startTime,Date endTime,Account account , boolean status);

}
