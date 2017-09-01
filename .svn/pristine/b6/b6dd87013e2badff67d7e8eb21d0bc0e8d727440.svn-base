package com.iycharge.server.domain.repository;

import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.utils.EmailAddress;

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
public interface AccountRepository extends JpaRepository<Account, Long> {
	
    public Account findByEmail(EmailAddress email);
    
    public Account findByEmail(String email);
    
    public Account findByPhone(String phone);

    public Account findByNickname(String nickname);
    
    public Page<Account> findByDelstatus(String delstatus, Pageable pageable);
    
    public Page<Account> findByNicknameContaining(String nickname, Pageable pageable);

    public Page<Account> findByNicknameContainingOrPhoneContaining(String nickname, String phone, Pageable pageable);

    public Page<Account> findByBlockedTrue(Pageable pageable);

	public Page<Account> findByRealNameAndPhone(String realName, String phone, Pageable pageable);

	public Page<Account> findAll(Specification<Account> spec, Pageable pageable);
	
	public List<Account> findByAccountType(String accountType);
	
	public List<Account> findAll(Specification<Account> spec);
	
	/**
	 * 统计某个时段内，企业会员和个人会员的新增用户数
	 * @param startTime        统计起始时间
	 * @param endTime          统计结束时间
	 * @return
	 */
	@Query(value="select accountType, count(phone) from Account where :startTime <= createdAt and createdAt < :endTime group by accountType")
	List<Object[]> statisticByAccountType(@Param("startTime")Date startTime, @Param("endTime")Date endTime);
	
	/**
	 * 统计到截止时间，企业会员和个人会员的总数
	 * @param time     统计截止时间
	 * @return
	 */
	@Query(value="select accountType, count(phone) from Account where createdAt < :time group by accountType")
	List<Object[]> statisticByAccountType(@Param("time")Date time);
}
