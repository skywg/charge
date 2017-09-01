package com.iycharge.server.ccu.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.iycharge.server.ccu.msg.entity.ChargerCommLog;
import com.iycharge.server.ccu.repository.ChargerCommLogRepository;
import com.iycharge.server.ccu.service.ChargerCommLogService;

/**
 *
 * @author bwang
 */
@Service
public class ChargerCommLogServiceImpl implements ChargerCommLogService {
    
    @Resource
    private ChargerCommLogRepository chargerCommLogRepository;
    
    @Override
    public ChargerCommLog save(ChargerCommLog log) {
       
        return chargerCommLogRepository.save(log);
    }

    @Override
    public Page<ChargerCommLog> search(String chargerCode, Date startTime, Date endTime, Pageable pageable) {   

        Page<ChargerCommLog> result = chargerCommLogRepository.findAll(new Specification<ChargerCommLog>(){
            @Override
            public Predicate toPredicate(Root<ChargerCommLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                
                List<Expression<Boolean>> expressions = predicate.getExpressions();
                
                if(!StringUtils.isEmpty(chargerCode)) {
                    expressions.add(cb.equal(root.<String>get("chargerCode"), chargerCode));
                }
                if(startTime != null && endTime != null) {
                	Calendar calendar = Calendar.getInstance();
					calendar.setTime(endTime);
					calendar.add(calendar.DATE, 1);
                    expressions.add(cb.between(root.<Date>get("logTime"), startTime, calendar.getTime()));
                }
                return predicate;
            }           
        }, pageable);
        return result;
    }
}
