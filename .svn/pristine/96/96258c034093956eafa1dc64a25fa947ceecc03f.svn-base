package com.iycharge.server.domain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.account.AccountStation;
import com.iycharge.server.domain.entity.station.Station;
import com.iycharge.server.domain.repository.AccountStationRepository;
import com.iycharge.server.domain.service.AccountStationService;
@Service
public class AccountStationServiceImpl implements AccountStationService{
	
	@Autowired
	private AccountStationRepository accountStationRepository;
	@Override
	public List<AccountStation> findByAccountAndStation(Account account,Station station) {
		// TODO Auto-generated method stub
		return accountStationRepository.findByAccountAndStation(account,station);
	}

}
