package com.iycharge.server.ccu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iycharge.server.ccu.msg.entity.BatteryParamData;

/**
 * 电池参数信息DAO接口
 * @author bwang
 */
@Repository
public interface BatteryParamDataRepository extends JpaRepository<BatteryParamData, Long> {

}
