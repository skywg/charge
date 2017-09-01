package com.iycharge.server.domain.repository;

import com.iycharge.server.domain.entity.price.PeriodPrice;
import com.iycharge.server.domain.entity.price.PriceTemplate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeriodPriceRepository extends JpaRepository<PeriodPrice, Long> {
  
    //@Query("select * from periodPrice where template in(select t from priceTemplate t where t.id=?)")
    public List<PeriodPrice> findByTemplate(PriceTemplate template);
    
    
}
