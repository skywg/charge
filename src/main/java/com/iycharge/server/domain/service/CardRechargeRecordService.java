package com.iycharge.server.domain.service;

import com.iycharge.server.domain.entity.account.Card;
import com.iycharge.server.domain.entity.account.CardRechargeRecord;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by godfrey on 16/10/12.
 */
public interface CardRechargeRecordService {

    Page<CardRechargeRecord> findByCardNoWeekRecord(String cardNo, Pageable pageable);

    Page<CardRechargeRecord> findByCardNoMonthRecord(String cardNo,Pageable pageable);

    Page<CardRechargeRecord> findByCardNoThreeMonthRecord(String cardNo,Pageable pageable);

    Page<CardRechargeRecord> findByCardNoYearRecord(String cardNo,Pageable pageable);

    Page<CardRechargeRecord> findAll(Pageable pageable);
    Page<CardRechargeRecord> findByCard(Card card,Pageable pageable);

    CardRechargeRecord save(CardRechargeRecord cardRechargeRecord);
    List<CardRechargeRecord> saveAll(List<CardRechargeRecord> cardRechargeRecords);
}
