package com.iycharge.server.domain.repository;

import com.iycharge.server.domain.entity.charger.Charger;
import com.iycharge.server.domain.entity.charger.ChargerStatus;
import com.iycharge.server.domain.entity.operator.Operator;
import com.iycharge.server.domain.entity.station.Station;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChargerRepository extends JpaRepository<Charger, Long> {
    public List<Charger> findByCodeAndDelStatus(String code,String delStatus);
    
    public List<Charger> findByDelStatus(String delStatus);

    public Page<Charger> findByNameContaining(String name, Pageable pageable);

    public Page<Charger> findByType(String type, Pageable pageable);

    public Page<Charger> findByStatus(ChargerStatus status, Pageable pageable);

    public Page<Charger> findByStation(Station station, Pageable pageable);
    
    public Page<Charger> findByOperator(Operator operator, Pageable pageable);

    public Page<Charger> findAll(Specification<Charger> spec, Pageable pageable);  //分页按条件查询
    
	public Page<Charger> findByDelStatus(String delStatus,Pageable pageable);
	
	@Query("select p.type,count(p.type) as num "
			+ "from Charger p where p.station.id =:stationId and p.delStatus='normal' group by p.type")
	public List<Object[]> CountType(@Param("stationId")Long stationId);
	
	public List<Charger> findByName(String name);

	public List<Charger> findByDelStatusAndStation(String string, Station station);

	public List<Charger> findByDelStatusAndOperator(String string, Operator operator);

	public List<Charger> findByQrcode(String qrcode);
	
	public List<Charger> findByCode(String code);
	 
	public List<Charger> findAll(Specification<Charger> spec);
	@Query("select p.status,count(p.status) as num "
			+ "from Charger p where p.delStatus='normal' group by p.status")
	public List<Object[]> CountStatus();
	
	@Query("select count(1) from Charger p where p.delStatus='normal'")
	public String countCharger();
	
	//select t.latitude,t.longitude,s.num,s.station_id,s.STATUS from stations t ,(select count(STATUS) as num,station_id,STATUS FROM chargers  where del_status='normal' GROUP BY station_id,STATUS) s where t.id=s.station_id and t.del_status='normal'
	@Query("select p.station.id,p.status,count(p.status) as num "
			+ "from Charger p where p.delStatus='normal' group by p.station.id,p.status")
	public List<Object[]> countAllCharger();

}
