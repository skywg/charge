package com.iycharge.server.domain.service;

import java.util.List;

import com.iycharge.server.domain.entity.account.CardRechargeLog;

public interface CardRechargeLogService {
	public CardRechargeLog save(CardRechargeLog cardRechargeLog);
	public List<CardRechargeLog> saveAll(List<CardRechargeLog> cardRechargeLogs);
}
