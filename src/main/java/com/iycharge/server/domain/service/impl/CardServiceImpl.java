package com.iycharge.server.domain.service.impl;

import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.account.Card;
import com.iycharge.server.domain.entity.account.CardStatus;
import com.iycharge.server.domain.repository.CardRepository;
import com.iycharge.server.domain.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by godfrey on 16/10/8.
 * 实现关联查询
 */
@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardRepository;

    // TODO: 16/10/12  分页查询未测试
    @Override
    public Page<Card> search(final Card card,Pageable pageable) {

        Specification specification = new Specification<Card>(){
            @Override
            public Predicate toPredicate(Root<Card> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();

                Predicate accountNameLike = null;

                if(null!=card && null!=card.getAccount()&& null!=card.getAccount().getNickname()&&!"".equals(card.getAccount().getNickname())){
                    accountNameLike=cb.like(root.<Account>get("account").<String>get("nickname"),"%"+card.getAccount().getNickname()+"%") ;
                }
                Predicate cardIdLike = null;
                if(null!=card && null!=card.getCertificateId() && !"".equals(card.getCertificateId())){
                    cardIdLike=cb.like(root.<String>get("certificateId"),"%"+card.getCertificateId()+"%");

                }

                Predicate cardOwnerLike = null;
                if(null!=card && null!=card.getOwner() &&!"".equals(card.getOwner())){
                    cardOwnerLike=cb.like(root.<String>get("owner"),"%"+card.getOwner()+"%");
                }
                if(null!=card && null!=card.getCardNo() &&!"".equals(card.getCardNo())){
                    cardOwnerLike=cb.like(root.<String>get("cardNo"),"%"+card.getCardNo()+"%");
                }
                if(null!=accountNameLike){
                    predicateList.add(accountNameLike);
                }
                if(null!=cardIdLike){
                    predicateList.add(cardIdLike);
                }

                if(null!=cardOwnerLike){
                    predicateList.add(cardOwnerLike);
                }

                Predicate[] p = new Predicate[predicateList.size()];
                query.where(cb.and(predicateList.toArray(p)));

                return null;
            }
        };

        return cardRepository.findAll(specification,pageable);
    }

    @Override
    public Page<Card> searchByStatus(Pageable pageable,CardStatus status) {
        return cardRepository.findByStatus(status,pageable);
    }

    @Override
    public Card findByCardNo(String cardNo) {
        return cardRepository.findByCardNo(cardNo);
    }





	@Override
	public Card save(Card card) {
		// TODO Auto-generated method stub
		return cardRepository.saveAndFlush(card);
	}

	@Override
	public List<Card> saveAll(List<Card> cards) {
		return cardRepository.save(cards);
	}

	@Override
	public Page<Card> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return cardRepository.findAll(pageable);
	}

	 public List<Card> findByCondition(Map<String,String> field , Card card){
	    	Specification<Card> spec = new Specification<Card>() {
				@Override
				public Predicate toPredicate(Root<Card> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					Predicate predicate = cb.conjunction();
					List<Expression<Boolean>> expressions = predicate.getExpressions();
					if(field.get("nickname")!=null&&!field.get("nickname").trim().equals("")){
						expressions.add(cb.like(root.<String>get("account").get("nickname"), "%"+field.get("nickname")+"%"));
					}
					if(field.get("owner")!=null&&!field.get("owner").trim().equals("")){
						expressions.add(cb.like(root.<String>get("owner"), "%"+field.get("owner")+"%"));
					}
					if(field.get("certificateId")!=null&&!field.get("certificateId").trim().equals("")){
						expressions.add(cb.like(root.<String>get("certificateId"), "%"+field.get("certificateId")+"%"));
					}
					query.orderBy(cb.desc(root.get("sendDate")));
					return predicate;
				}
			};
			return cardRepository.findAll(spec);
	    }

}
