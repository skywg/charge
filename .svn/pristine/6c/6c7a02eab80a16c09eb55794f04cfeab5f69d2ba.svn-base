package com.iycharge.server.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iycharge.server.domain.entity.dict.DictCategory;

/**
 *
 * @author bwang
 */
@Repository
public interface DictCategoryRepository extends JpaRepository<DictCategory, Long> {
 
   public Page<DictCategory> findAll(Pageable pageable);
   public Page<DictCategory> findAll(Specification<DictCategory> spec,Pageable pageable);
   public List<DictCategory> findAll();
   
   @Query("select d1 from DictCategory d1 , DictCategory d2 where d1.id=d2.parent.id")
   public List<DictCategory> findByIds(long id);
   
   public DictCategory findByName(String name);
   
   @Modifying
   @Query("delete from DictCategory d where d.id=:id")
   void deleteById(@Param("id") Long id);
   
}
 