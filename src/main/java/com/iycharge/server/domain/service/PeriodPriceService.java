package com.iycharge.server.domain.service;

import com.iycharge.server.domain.entity.price.PeriodPrice;
import com.iycharge.server.domain.entity.price.PriceTemplate;

import java.util.List;

public interface PeriodPriceService {
   
    public PeriodPrice save(PeriodPrice price);

    public void delete(PeriodPrice price);
    
    public List<PeriodPrice> findByTemplate(PriceTemplate template);
    
}
