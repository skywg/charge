package com.iycharge.server.report.schedule.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iycharge.server.report.entity.ChargingData;

/**
 * 充电统计数据查询DAO接口
 * @author bwang
 */
@Repository
public interface ChargingDataRepository extends JpaRepository<ChargingData, Long> {
	public List<ChargingData> findByDayBetween(Date startdate,Date enddate);

	public List<ChargingData> findAll(Specification<ChargingData> spec);
}
