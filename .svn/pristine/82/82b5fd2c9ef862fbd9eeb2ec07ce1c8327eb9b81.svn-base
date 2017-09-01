package com.iycharge.server.domain.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iycharge.server.domain.entity.admin.ManagerLog;
import com.iycharge.server.domain.entity.event.EventCode;

/**
 *
 * @author bwang
 */
@Repository
public interface EventCodeRepository extends JpaRepository<EventCode, Integer> {
    

	public Page<EventCode> findAll(Pageable pageable);
	
	public EventCode findByCode(int code);
    /**
     * 
     * @param isActive    故障码是否可用
     * @return
     */
    List<EventCode>  findByIsActive(boolean isActive);
    EventCode save(EventCode eventCode);
    Page<EventCode> findAll(Specification<EventCode> spec, Pageable pageable);

}
