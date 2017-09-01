package com.iycharge.server.ccu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iycharge.server.ccu.msg.entity.BatteryVoltageData;

/**
 * 电池电压数据DAO接口
 * @author bwang
 */
@Repository
public interface BatteryVoltageDataRepository extends JpaRepository<BatteryVoltageData, Long> {

}
