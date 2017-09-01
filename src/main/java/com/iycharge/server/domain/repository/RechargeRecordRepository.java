package com.iycharge.server.domain.repository;

import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.order.OrderStatus;
import com.iycharge.server.domain.entity.order.RechargeRecord;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RechargeRecordRepository extends JpaRepository<RechargeRecord, Long> {

    public Page<RechargeRecord> findByAccount(Account account, Pageable pageable);
    
    public RechargeRecord findByTradeNo(String tradeNo);
    
    public Page<RechargeRecord> findByAccountAndStatus(Account account, OrderStatus status, Pageable pageable);
    
    Page<RechargeRecord> findAll(Specification<RechargeRecord> spec,Pageable pageable);
   
	@Query("select r from RechargeRecord r where to_days(now())-to_days(r.updatedAt)<:num and r.account.id=:id")
    Page<RechargeRecord> searchTime(@Param("num")Long num,@Param("id")Long id,Pageable pageable);
	
	/**
	 * 查询某时段内的有效充值数
	 * @param startTime        查询起始时间
	 * @param endTime          查询结束时间
	 * @return
	 */
	@Query("select rechargeRecord from RechargeRecord rechargeRecord where :startTime <= createdAt and createdAt < :endTime and status=:status")
	List<RechargeRecord> findByPeriod(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("status") OrderStatus status);
}

