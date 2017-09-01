package com.iycharge.server.report.schedule.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iycharge.server.report.entity.UserData;

/**
 * 用户统计数据查询DAO接口
 * @author bwang
 */
@Repository
public interface UserDataRepository extends JpaRepository<UserData, Long> {
	public List<UserData> findByDayBetween(Date startdate,Date enddate);
}
