package com.iycharge.server.domain.service;

import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.iycharge.server.domain.entity.account.Card;
import com.iycharge.server.domain.entity.charger.Charger;
import com.iycharge.server.domain.entity.charger.ChargerStatus;
import com.iycharge.server.domain.entity.operator.Operator;
import com.iycharge.server.domain.entity.station.Station;

public interface ChargerService {

    public Page<Charger> findAll(Pageable pageable);

    public List<Charger> findAll();

    public Charger findById(long id);

    public Charger findByCode(String code);

    public List<Charger> findByQrcodeList(String qrcode);
    
    public Page<Charger> findByNameContaining(String name, Pageable pageable);

    public Page<Charger> findByType(String type, Pageable pageable);

    public Page<Charger> findByStatus(ChargerStatus status, Pageable pageable);

    public List<Charger> findByStation(Station station);

    public Page<Charger> findByStation(Station station, Pageable pageable);

    public List<Charger> findByOperator(Operator operator);

    public Page<Charger> findByOperator(Operator operator, Pageable pageable);

    public Charger save(Charger charger);

	void del(Charger entity);

	Charger delCharger(Charger charger);

	Page<Charger> findAllSearch(String[] fields, Charger charger, Pageable pageable);
	
	List<Charger> findSearch(String[] fields, Charger charger);

	List<Object[]> CountType(long stationId);

	List<Charger> findByNameContaining(String name);

	List<Charger> findByCodeList(String code);
	
	public List<Charger> findByCondition(Map<String,String> field , Charger charger);
	    	
}
