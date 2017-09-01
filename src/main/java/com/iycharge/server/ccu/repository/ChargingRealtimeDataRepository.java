package com.iycharge.server.ccu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iycharge.server.ccu.msg.entity.ChargingRealtimeData;

/**
 * 充电桩充电实时数据DAO接口
 * @author bwang
 */
@Repository
public interface ChargingRealtimeDataRepository extends JpaRepository<ChargingRealtimeData, Long> {
    
}
