package com.iycharge.server.report.schedule.repository;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.event.Event;

import com.iycharge.server.report.entity.UserBillData;
import com.iycharge.server.report.entity.UserData;

/**
 *
 * @author bwang
 */
@Repository
public interface UserBillDataRepository extends JpaRepository<UserBillData, Long> {

	public Page<UserBillData> findAll(Pageable pageable);
	
	Page findAll(Specification spec, Pageable pageable);
	
	UserBillData findById(Long id);
	
	public List<UserBillData> findAll(Specification<UserBillData> spec);
	
	UserBillData findByAccountAndMonth(Account account,Date month);
    
	List<UserBillData> findByAccountOrderByIdDesc(Account account);

}
