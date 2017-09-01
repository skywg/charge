package com.iycharge.server.domain.repository;

import com.iycharge.server.domain.entity.operator.Operator;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, Long> {
	
	public Page<Operator> findByDelStatus(String string, Pageable pageable);

	public List<Operator> findByDelStatus(String delStatus);

	public Page<Operator> findByDelStatusAndNameContaining(String string, String name, Pageable pageable);

	public List<Operator> findByNameAndDelStatus(String name, String string);

	public List<Operator> findByCode(String code);
}
