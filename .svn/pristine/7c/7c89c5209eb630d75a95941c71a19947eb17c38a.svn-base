package com.iycharge.server.domain.service;

import com.iycharge.server.domain.entity.operator.Operator;
import com.iycharge.server.domain.entity.station.Station;
import com.iycharge.server.domain.entity.station.StationStatus;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StationService {

    public Page<Station> findAll(Pageable pageable);

    public Station findById(long id);

    public List<Station> findByCode(String code);

    public Page<Station> findByNameContaining(String name, Pageable pageable);
    public List<Station> findByNameContaining(String name);

    public Page<Station> findByType(String type, Pageable pageable);

    public Page<Station> findByStatus(StationStatus status, Pageable pageable);

    public Page<Station> findByProvince(String province, Pageable pageable);

    public Page<Station> findByCity(String city, Pageable pageable);

    public Page<Station> findByAddress(String address, Pageable pageable);

    public Page<Station> findByOperator(Operator operator, Pageable pageable);

    public Page<Station> findAllByOrderByRatingTotalDesc(Pageable pageable);

    public Station save(Station station);

	List<Station> findListAll();
	
	void del(Long id);

	Page<Station> findAllSearch(String[] fields, Station station, Pageable pageable);

	Station delStation(Station station);

	List<Station> findByOperator(Operator operator);
	
	List<Station> findByCondition(Map<String,String> map,Station station);

	List<Station> findSearch(String[] fields, Station station);
}
