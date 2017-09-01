package com.iycharge.server.domain.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.iycharge.server.domain.entity.account.Account;

@Repository
public interface DashboardRepository extends JpaRepository<Account, Long> {
	
	/**
	 * 用户总数
	 */
	@Query("select count(1) as num from Account ")
		//	+ "from Account p where p.station.id =:stationId and p.delStatus='normal' group by p.type")
	public String countUser();
	
	/**
	 * 用户本月总数
	 * 
	select * from accounts s where (DATE_FORMAT(NOW(),'%y/%m')) = DATE_FORMAT(updated_at,'%y/%m')
	 */
	@Query("select count(1) from Account s where (DATE_FORMAT(NOW(),'%y/%m')) = DATE_FORMAT(createdAt,'%y/%m') ")
		//	+ "from Account p where p.station.id =:stationId and p.delStatus='normal' group by p.type")
	public String countUserMonth();
	
	/**
	 * 用户每天总数
	 * select   count(1)   from   accounts 
		where   datediff(DATE(updated_at),DATE(now()))=0  
	 */
	@Query("select count(1) from Account where datediff(DATE(createdAt),DATE(now()))=0")
	public String countUserDay();

}
