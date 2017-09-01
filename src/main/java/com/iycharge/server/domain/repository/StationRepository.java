package com.iycharge.server.domain.repository;

import com.iycharge.server.domain.entity.operator.Operator;
import com.iycharge.server.domain.entity.station.Station;
import com.iycharge.server.domain.entity.station.StationStatus;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {

    public Page<Station> findByNameContaining(String name, Pageable pageable);

    public Page<Station> findByStationType(String type, Pageable pageable);

    public Page<Station> findByStatus(StationStatus status, Pageable pageable);

    public Page<Station> findByProvince(String province, Pageable pageable);

    public Page<Station> findByCity(String city, Pageable pageable);

    public Page<Station> findByAddress(String address, Pageable pageable);

    public Page<Station> findByOperator(Operator operator, Pageable pageable);

    public Page<Station> findAllByOrderByRatingTotalDesc(Pageable pageable);

	public Page<Station> findByStationTypeAndNameAndProvince(String type, String name, String province,
			Pageable pageable);
	
	public Page<Station> findAll(Specification<Station> spec, Pageable pageable);  //分页按条件查询

	public Page<Station> findByDelStatus(String string, Pageable pageable);

	public List<Station> findByDelStatus(String string);

	public List<Station> findByDelStatusAndOperator(String string, Operator operator);

	public List<Station> findByName(String name);

	public List<Station> findByCode(String code);
	
	public List<Station> findAll(Specification<Station> spec);
	
	@Query("select count(1) from Station p where p.delStatus='normal'")
	public String countStation();
}
