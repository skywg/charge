package com.iycharge.server.domain.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iycharge.server.domain.entity.price.ParamSetting;

import java.util.List;

@Repository
public interface ParamSettingRepository extends JpaRepository<ParamSetting, Long> {
    public Page<ParamSetting> findByNameContaining(String name, Pageable pageable);

    public Page<ParamSetting> findAll(Specification<ParamSetting> spec, Pageable pageable);  //分页按条件查询
    
	public Page<ParamSetting> findByDelStatus(String delStatus,Pageable pageable);
	
	public List<ParamSetting> findByName(String name);

}
