package com.iycharge.server.ccu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iycharge.server.ccu.msg.entity.BatteryTempData;

/**
 * 电池温度数据DAO接口
 * @author bwang
 */
@Repository
public interface BatteryTempDataRepository extends JpaRepository<BatteryTempData, Long> {

}
