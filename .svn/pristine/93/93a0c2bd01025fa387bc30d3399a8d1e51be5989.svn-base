package com.iycharge.server.domain.repository;

import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.account.AccountRechargeLog;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRechargeLogRepository extends JpaRepository<AccountRechargeLog, Long> {
	
	public  List<AccountRechargeLog> findByUpdatedAt(Date updatedAt);
	@Query("select ar from AccountRechargeLog ar where :startTime <=  createdAt and createdAt < :endTime and status=:status and account=:account")
	List<AccountRechargeLog> findByCondition(@Param("startTime") Date startTime, @Param("endTime") Date endTime,
			@Param("account") Account account, @Param("status") boolean status);
}
