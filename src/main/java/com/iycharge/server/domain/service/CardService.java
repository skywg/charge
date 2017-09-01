package com.iycharge.server.domain.service;

import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.account.Card;
import com.iycharge.server.domain.entity.account.CardStatus;

import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


/**
 * Created by godfrey on 16/10/8.
 */
public interface CardService {

    // 根据会员昵称、持卡人、持卡人证件号码查询
    public Page<Card> search(Card card,Pageable pageable);

    public Page<Card> searchByStatus(Pageable pageable,CardStatus status);

    public Card findByCardNo(String cardNo);
    
    public Page<Card> findAll(Pageable pageable);

    public Card save(Card card);
    
    public List<Card> saveAll(List<Card> cards);
    public List<Card> findByCondition(Map<String,String> field , Card card);
}
