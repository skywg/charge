package com.iycharge.server.domain.service;

import com.iycharge.server.domain.entity.Authorization;
import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.utils.EmailAddress;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountService {

    public Page<Account> findAll(Pageable pageable);

    public Account findById(long id);

    public Account findByEmail(EmailAddress email);

    public Account findByEmail(String email);
    
    public Account findByPhone(String phone);

    public Account findByNickname(String nickname);

    public Page<Account> findByNicknameContaining(String nickname, Pageable pageable);

    public Page<Account> findByNicknameContainingOrPhoneContaining(String nickname, String phone, Pageable pageable);

    public Page<Account> findByBlockedTrue(Pageable pageable);

    public Authorization findAuthorization(Authorization.Provider provider, String uid);

    public Authorization addAuthorization(final Account account, Authorization authorization);

    public Authorization updateAuthorization(final Account account, Authorization authorization);
    public void delete(Authorization authorization);

    public Account save(Account account);

	void del(Long id);

	Page<Account> findAllSearch(String[] fields, Account account, Pageable pageable);
	
	public List<Account> findByConditionMap(Map<String,String> map , Account account);

	
	public List<Account> findAll();
	
	public List<Account> findByAccountType(String accountType);
	
	/**
	 * 统计某个时段内，企业会员和个人会员的新增用户数
	 * @param startTime        统计起始时间
	 * @param endTime          统计结束时间
	 * @return   返回数组，数组长度为2 ， [0] : 新增企业会员数， [1] : 新增个人会员数
	 */
	int[] statisticByAccountType(Date startTime, Date endTime);
	
	/**
	 * 统计到截止时间，企业会员和个人会员的总数
	 * @param time         统计截止时间
	 * @return    返回数组，数组长度为2 ， [0] : 企业会员数， [1] : 个人会员数            
	 */
	int[] statisticByAccountType(Date time);
	
	public List<Account> findByCondition(Map<String,String> map , Account account);
}
