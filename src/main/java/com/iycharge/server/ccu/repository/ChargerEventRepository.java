package com.iycharge.server.ccu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iycharge.server.ccu.msg.entity.ChargerEvent;

/**
 * 告警事件数据DAO接口
 * @author bwang
 */
@Repository
public interface ChargerEventRepository extends JpaRepository<ChargerEvent, Long> {

}
