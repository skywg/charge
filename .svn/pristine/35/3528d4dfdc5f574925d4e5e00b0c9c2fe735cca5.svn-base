package com.iycharge.server.domain.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iycharge.server.domain.entity.event.Event;
import com.iycharge.server.domain.entity.event.EventStatus;

/**
 *
 * @author bwang
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    
    @Override
	public Page<Event> findAll(Pageable pageable);
    
    Page findAll(Specification spec, Pageable pageable); // 分页按条件查询
    
    public Event findByChargerId(int id);
    
    Event save(Event event);
    
    List<Event> findByEventStatus(EventStatus eventStatus);
    
    List<Event> findAll(Specification<Event> spec);
    /**
     * 按告警类型，统计时段内电桩的告警数
     * @param startTime     统计起始时间
     * @param endTime       统计结束时间
     * @return
     */
    @Query("select e.charger.id, ec.eventType, count(e.id) from Event e, EventCode ec where e.eventCode.code = ec.code and :startTime <= e.eventTime and e.eventTime < :endTime and ec.isActive=true group by e.charger, ec.eventType")
    List<Object[]> statisticByEventType(@Param("startTime")Date startTime, @Param("endTime")Date endTime);
    
    /**
     * 按告警级别，统计时段内电桩的告警数
     * @param startTime     统计起始时间
     * @param endTime       统计结束时间
     * @return
     */
    @Query("select e.charger.id, ec.eventLevel, count(e.id) from Event e, EventCode ec where e.eventCode.code = ec.code and :startTime <= e.eventTime and e.eventTime < :endTime and ec.isActive=true group by e.charger, ec.eventLevel")
    List<Object[]> statisticByEventLevel(@Param("startTime")Date startTime, @Param("endTime")Date endTime);
}
