package com.iycharge.server.domain.service;

import com.iycharge.server.domain.entity.operator.Operator;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OperatorService {

    public Page<Operator> findAll(Pageable pageable);

    public Operator findById(long id);

    public Operator save(Operator operator);

	public void del(Long operatorId);

	Page<Operator> findSearch(String name, Pageable pageable);

	List<Operator> findListAll();

	List<Operator> findByCode(String code);

	List<Operator> findByName(String name);
}
