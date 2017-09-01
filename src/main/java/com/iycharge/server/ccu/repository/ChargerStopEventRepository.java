package com.iycharge.server.ccu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iycharge.server.ccu.msg.entity.ChargerStopEvent;

/**
 * 充电桩停机事件DAO接口
 * @author bwang
 */
@Repository
public interface ChargerStopEventRepository extends JpaRepository<ChargerStopEvent, Long> {

}
