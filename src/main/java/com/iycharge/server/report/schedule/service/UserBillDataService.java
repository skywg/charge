package com.iycharge.server.report.schedule.service;


import java.util.Date;
import java.util.List;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.event.Event;

import com.iycharge.server.report.entity.UserBillData;
import com.iycharge.server.report.entity.UserData;

/**
 * 企业会员消费数据业务查询接口
 * @author bwang
 */
public interface UserBillDataService {
	public Page<UserBillData> findAll(Pageable pageable);
    
    /**
     * 
     * @param queryParam        查询条件
     * @param pagealbe      分页条件
     * @return
     */
    Page<UserBillData> find(Map<String, String> queryParam, Pageable pagealbe);
    
    /**
     * 保存一条企业会员消费汇总数据
     * @param userBillData
     * @return
     */
    UserBillData save(UserBillData userBillData);

     
    UserBillData findById(Long id);
   
    public List<UserBillData> findByCondition(Map<String,String> map , UserBillData userBillData);
 
    
    List<UserBillData> findByAccountOrderByIdDesc(Account account);
    
    UserBillData findByAccountAndMonth(Account account,Date month);

}
