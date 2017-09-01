package com.iycharge.server.domain.repository;

import com.iycharge.server.domain.entity.account.Card;
import com.iycharge.server.domain.entity.account.CardStatus;
import com.iycharge.server.domain.entity.content.Content;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Created by godfrey on 16/10/8.
 * 继承JAP接口
 */
@Repository
public interface CardRepository extends JpaRepository<Card, Integer>  {



    Page<Card> findAll(Specification spec, Pageable pageable); // 分页按条件查询

    Page<Card> findByStatus(CardStatus status, Pageable pageable);

    Card findByCardNo(String cardNo);
    Card save(Card card);
    
    List<Card> findAll(Specification spec);
}
