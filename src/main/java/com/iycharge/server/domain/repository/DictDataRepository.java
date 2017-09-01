package com.iycharge.server.domain.repository;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iycharge.server.domain.entity.dict.DictCategory;
import com.iycharge.server.domain.entity.dict.DictData;

/**
 *
 * @author bwang
 */
@Repository
public interface DictDataRepository extends JpaRepository<DictData, Long> {
 
	public Page<DictData> findAll(Pageable pageable);

	public List<DictData> findAll();
	
	public List<DictData> findByDictCategory(DictCategory dict);
	
	public Page<DictData> findByDictCategory(Pageable pageable,DictCategory dict);
	
	@Modifying
	@Query("delete from DictData d where d.id=:id")
	void deleteDictDataById(@Param("id") Long id);
	
	public DictData findByDictKey(String dictKey);
}
 