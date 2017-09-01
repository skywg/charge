package com.iycharge.server.domain.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.account.Car;
import com.iycharge.server.domain.entity.account.CarAuditLog;


public interface CarService {
	
	public Page<Car> findAll(Pageable  pageable);

	public Page<Car> findAllSearch(String  fields[],Car car,Pageable pageable);

	Car findById(Long id);
	
    public Car save(Car car);
    
    public Account addAndUpdate(final Account account , Car car);
    
    public Car updateCarAndCarAuditLog(Car car , final List<CarAuditLog> carAuditLogList);

	List<Car> findByCarIdentifyStatus();
}
