package com.iycharge.server.domain.repository;

import com.iycharge.server.domain.entity.account.Car;
import com.iycharge.server.domain.entity.account.CarAuditLog;
import com.iycharge.server.domain.entity.account.CarIdentifyStatus;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
	   
    @Override
	public Page<Car> findAll(Pageable pageable);
    
    Page findAll(Specification spec, Pageable pageable); // 分页按条件查询

    public List<Car> findByCarIdentifyStatus(CarIdentifyStatus carIdentifyStatus); 
    
    public Car findById(int id); 
    
}
