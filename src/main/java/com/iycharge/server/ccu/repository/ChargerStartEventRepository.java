package com.iycharge.server.ccu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iycharge.server.ccu.msg.entity.ChargerStartEvent;

/**
 * 充电桩启动事件DAO接口
 * @author bwang
 */
@Repository
public interface ChargerStartEventRepository extends JpaRepository<ChargerStartEvent, Long> {

}
