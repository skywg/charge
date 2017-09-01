package com.iycharge.server.domain.service;

import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.order.OrderStatus;
import com.iycharge.server.domain.entity.order.RechargeRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface RechargeRecordService {

    public Page<RechargeRecord> findAll(Pageable pageable);

    public RechargeRecord findById(long id);
    
    public RechargeRecord findByTradeNo(String tradeNo);

    public Page<RechargeRecord> findByAccount(Account account, Pageable pageable);
    
    public Page<RechargeRecord> findByAccountAndStatus(Account account, OrderStatus status, Pageable pageable);

    public RechargeRecord save(RechargeRecord rechargeRecord);

    public RechargeRecord payWithAlipay(RechargeRecord rechargeRecord, Map<String, String> params);

    Page<RechargeRecord> findAll( String[] fields ,RechargeRecord rechargeRecord,Pageable pageable);

    Page<RechargeRecord> searchTime(Long num,Long id,Pageable pageable);

	public List<RechargeRecord> findByPeriod(Date startTime, Date endTime);
}
