package com.iycharge.server.ccu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iycharge.server.ccu.msg.entity.ChargerCommLog;

/**
 *
 * @author bwang
 */
@Repository
public interface ChargerCommLogRepository extends JpaRepository<ChargerCommLog, Long> {
    
    Page<ChargerCommLog> findAll(Specification<ChargerCommLog> spec, Pageable pageable);
}
