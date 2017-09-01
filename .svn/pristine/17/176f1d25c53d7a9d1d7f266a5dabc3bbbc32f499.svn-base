package com.iycharge.server.domain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iycharge.server.domain.entity.account.CardRechargeLog;
import com.iycharge.server.domain.repository.CardRechargeLogRepository;
import com.iycharge.server.domain.service.CardRechargeLogService;
@Service
public class CardRechargeLogServiceImpl implements CardRechargeLogService {
	@Autowired
	private CardRechargeLogRepository cardRechargeLogRepository;
	@Override
	public CardRechargeLog save(CardRechargeLog cardRechargeLog) {
		return cardRechargeLogRepository.saveAndFlush(cardRechargeLog);
	}
	@Override
	public List<CardRechargeLog> saveAll(List<CardRechargeLog> cardRechargeLogs) {
		// TODO Auto-generated method stub
		return cardRechargeLogRepository.save(cardRechargeLogs);
	}

}
