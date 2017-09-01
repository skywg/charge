package com.iycharge.server.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.iycharge.server.domain.entity.admin.LogQueryParam;
import com.iycharge.server.domain.entity.admin.ManagerLog;


/**
 * 系统用户操作日志业务接口
 * @author bwang
 */
public interface ManagerLogService {
    
    /**
     * 默认分页查询
     * @param pageable      分页相关参数
     * @return
     */
    Page<ManagerLog> findAll(Pageable pageable);
    
    /**
     * 给定查询条件作分页查询
     * @param queryParam    查询参数
     * @param pageable      分页相关参数     
     * @return
     */
    Page<ManagerLog> find(LogQueryParam queryParam, Pageable pageable);
    
    /**
     * 保存用户操作日志
     * @param managerLog    系统用户日志
     * @return
     */
    boolean save(ManagerLog managerLog);
}
