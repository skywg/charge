package com.iycharge.server.ccu.service;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.iycharge.server.ccu.msg.entity.ChargerCommLog;

/**
 *
 * @author bwang
 */
public interface ChargerCommLogService {
    
    ChargerCommLog save(ChargerCommLog log);
    
    /**
     * 查询某电桩的通信日志
     * @param chargerCode       电桩编号
     * @param startTime         开始时间    
     * @param endTime           结束时间
     * @param pageable          分页条件
     * @return
     */
    Page<ChargerCommLog> search(String chargerCode, Date startTime, Date endTime, Pageable pageable);
}
