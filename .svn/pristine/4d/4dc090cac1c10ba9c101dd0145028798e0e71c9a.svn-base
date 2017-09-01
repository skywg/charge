package com.iycharge.server.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iycharge.server.domain.entity.admin.ManagerLog;

/**
 * 系统用户操作日志DAO接口
 * @author bwang
 */
@Repository
public interface ManagerLogRepository extends  JpaRepository<ManagerLog, Long>{
    
    Page<ManagerLog> findAll(Specification<ManagerLog> spec, Pageable pageable);
}
