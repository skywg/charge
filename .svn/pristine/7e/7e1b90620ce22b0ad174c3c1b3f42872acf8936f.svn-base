package com.iycharge.server.domain.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iycharge.server.domain.entity.price.ParamTemplate;

@ResponseBody
public interface ParamTemplateRepository extends JpaRepository<ParamTemplate, Long>{
	public Page<ParamTemplate> findAll(Pageable pageable);
	public Page<ParamTemplate> findByDelStatus(String delStatus,Pageable pageable);
	Page<ParamTemplate> findAll(Specification<ParamTemplate> spec, Pageable pageable);
	List<ParamTemplate> findByType(String type);
	/**
     * 查询单个电桩的最新电价
     * @param chargerId
     * @return
     */
    @Query(value=
            "select max(ps.effectiveTime), ps.paramTemplate from ParamSetting ps, ParamSettingResult psr " +
            "where ps.id=psr.paramSetting.id " +
            "and ps.paramTemplate.status='VALID' and psr.charger.id=:chargerId and ps.effectiveTime < :date and ps.paramType=:type group by psr.charger")
    List<Object[]> findChargerPrice(@Param("chargerId")Long chargerId, @Param("date")Date date, @Param("type")String type);
    /**
     * 查询单个电桩的最新参数模板
     * @param chargerId
     * @return
     */
    @Query(value=
            "select max(ps.effectiveTime), ps.paramTemplate from ParamSetting ps, ParamSettingResult psr " +
            "where ps.id=psr.paramSetting.id " +
            "and ps.paramTemplate.status='VALID' and psr.charger.id=:chargerId and ps.effectiveTime < :date and ps.paramType=:type group by psr.charger")
    List<Object[]> findChargerParam(@Param("chargerId")Long chargerId, @Param("date")Date date, @Param("type")String type);
	
}
