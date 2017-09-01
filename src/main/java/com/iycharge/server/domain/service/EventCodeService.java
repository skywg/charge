
package com.iycharge.server.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.iycharge.server.domain.entity.admin.LogQueryParam;
import com.iycharge.server.domain.entity.admin.ManagerLog;
import com.iycharge.server.domain.entity.event.EventCode;
import com.iycharge.server.domain.entity.event.EventCodeQueryParam;

import java.util.List;



/**
 * 故障码相关业务操作接口定义类
 * @author bwang
 */
public interface EventCodeService {
    
    /**
     * 根据故障码查询故障定义
     * @param eventCode
     * @return
     */
    EventCode findByEventCode(int eventCode);
    
    /**
     * 保存或更新
     * @param eventCode
     * @return
     */
    boolean save(EventCode eventCode);
    
    /**
     * 根据故障码状态查询所有的故障定义
     * @param isActive
     * @return
     */
    List<EventCode> findAll(boolean isActive);
    public Page<EventCode> findAll(Pageable  pageable);


    Page<EventCode> find(EventCodeQueryParam queryParam, Pageable pageable);
}

