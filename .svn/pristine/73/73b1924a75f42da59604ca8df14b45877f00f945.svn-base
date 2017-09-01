package com.iycharge.server.domain.service.impl;

import com.iycharge.server.domain.entity.account.Card;
import com.iycharge.server.domain.entity.account.CardRechargeRecord;
import com.iycharge.server.domain.repository.CardRechargeRecordRepository;
import com.iycharge.server.domain.service.CardRechargeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 * Created by godfrey on 16/10/12.
 * 卡充值记录服务实现类
 */
@Service
public class CardRechargeRecordServiceImpl implements CardRechargeRecordService {

    @Autowired
    private CardRechargeRecordRepository cardRechargeRecordRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<CardRechargeRecord> findByCardNoWeekRecord(final String cardNo, Pageable pageable) {
        return cardRechargeRecordRepository.searchRecordByTradeTime(7,cardNo,pageable);
    }

    @Override
    public Page<CardRechargeRecord> findByCardNoMonthRecord(String cardNo, Pageable pageable) {
        return cardRechargeRecordRepository.searchRecordByTradeTime(30,cardNo,pageable);
    }

    @Override
    public Page<CardRechargeRecord> findByCardNoThreeMonthRecord(String cardNo, Pageable pageable) {
        return cardRechargeRecordRepository.searchRecordByTradeTime(90,cardNo,pageable);



    }

    @Override
    public Page<CardRechargeRecord> findByCardNoYearRecord(String cardNo, Pageable pageable) {
        return cardRechargeRecordRepository.searchRecordByTradeTime(new Integer(365),cardNo,pageable);


    }


    @Override
    public Page<CardRechargeRecord> findAll(Pageable pageable) {
        return cardRechargeRecordRepository.findAll(pageable);
    }

	@Override
	public Page<CardRechargeRecord> findByCard(Card card, Pageable pageable) {
		return cardRechargeRecordRepository.findByCard(card, pageable);
	}

	@Override
	public CardRechargeRecord save(CardRechargeRecord cardRechargeRecord) {
		// TODO Auto-generated method stub
		return cardRechargeRecordRepository.save(cardRechargeRecord);
	}

	@Override
	public List<CardRechargeRecord> saveAll(List<CardRechargeRecord> cardRechargeRecords) {
		// TODO Auto-generated method stub
		return cardRechargeRecordRepository.save(cardRechargeRecords);
	}
}
