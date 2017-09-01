package com.iycharge.server.domain.repository;

import com.iycharge.server.domain.entity.account.Card;

import com.iycharge.server.domain.entity.account.CardRechargeRecord;
import com.iycharge.server.domain.entity.order.Order;
import com.iycharge.server.domain.entity.order.Order;
import com.iycharge.server.domain.entity.account.CardRechargeRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by godfrey on 16/10/12.
 * 卡充值记录资源库接口
 */
@Repository
public interface CardRechargeRecordRepository extends JpaRepository<CardRechargeRecord, Integer> {

    Page<CardRechargeRecord> findAll(Specification spec, Pageable pageable); // 分页按条件查询

    Page<CardRechargeRecord> findByTradeNo(String tradeNo, Pageable pageable);
    Page<CardRechargeRecord> findByCard(Card card, Pageable pageable);


    @Query("select o from CardRechargeRecord o where 1=1 and to_days(now())-to_days(o.tradeTime)<=:days and o.card.cardNo=:cardNo")
    Page<CardRechargeRecord> searchRecordByTradeTime(@Param("days") long days,@Param("cardNo")String cardNo,Pageable pageable);

}
