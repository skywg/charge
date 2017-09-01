package com.iycharge.server.domain.service.impl;

import com.iycharge.server.domain.entity.price.PeriodPrice;
import com.iycharge.server.domain.entity.price.PriceTemplate;
import com.iycharge.server.domain.repository.PeriodPriceRepository;
import com.iycharge.server.domain.service.PeriodPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeriodPriceServiceImpl implements PeriodPriceService {

    @Autowired
    private PeriodPriceRepository periodPriceRepository;

	@Override
	public PeriodPrice save(PeriodPrice price) {
		return periodPriceRepository.saveAndFlush(price);
	}
	
	@Override
	public List<PeriodPrice> findByTemplate(PriceTemplate template) {
		
		return periodPriceRepository.findByTemplate(template);
	}

	@Override
	public void delete(PeriodPrice price) {
		periodPriceRepository.delete(price);
		
	}
	

}
