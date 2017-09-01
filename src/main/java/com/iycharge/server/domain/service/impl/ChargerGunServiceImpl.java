package com.iycharge.server.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iycharge.server.domain.entity.charger.Charger;
import com.iycharge.server.domain.repository.ChargerGunRepository;
import com.iycharge.server.domain.service.ChargerGunService;

@Service
public class ChargerGunServiceImpl implements ChargerGunService {
	
	@Autowired
	private ChargerGunRepository chargerGunRepository;
	@Override
	public void deleteByCharger(Charger charger) {
		// TODO Auto-generated method stub
		chargerGunRepository.deleteByCharger(charger);
	}
   
}
