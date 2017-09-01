package com.iycharge.server.ccu.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iycharge.server.ccu.msg.entity.ChargerRealtimeData;

/**
 * 充电桩实时数据DAO接口
 * @author bwang
 */
@Repository
public interface ChargerRealtimeDataRepository extends JpaRepository<ChargerRealtimeData, Long> {
    
	public List<ChargerRealtimeData> findByChargerNoOrderByIdDesc(String chargerNo);
	
	/**
	 * 查询电桩在某个时间后的充电实时数据
	 * @param chargerNo
	 * @param startAt
	 * @return
	 */
	@Query("select t from ChargerRealtimeData t where t.chargerNo=:chargerNo and t.createdAt >= :startAt order by t.createdAt desc")
	public List<ChargerRealtimeData> findByChargerNo(@Param("chargerNo")String chargerNo, @Param("startAt")Date startAt);
}
