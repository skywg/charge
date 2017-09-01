package com.iycharge.server.domain.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.iycharge.server.domain.entity.event.EventCode;
import com.iycharge.server.domain.entity.event.EventCodeQueryParam;
import com.iycharge.server.domain.repository.EventCodeRepository;
import com.iycharge.server.domain.service.EventCodeService;


/**
 * 
 * @author bwang
 */
@Service
public class EventCodeServiceImpl implements EventCodeService {
    @Resource
    private EventCodeRepository eventCodeRepository;
    
    @Override
    public EventCode findByEventCode(int eventCode) {
        return eventCodeRepository.findOne(eventCode);
    }

    @Override
    public boolean save(EventCode eventCode) {
        eventCodeRepository.save(eventCode);        
        return true;
    }

    @Override
    public List<EventCode> findAll(boolean isActive) {      
        return eventCodeRepository.findByIsActive(isActive);
    }

	@Override
	public Page<EventCode> findAll(Pageable pageable) {
		return eventCodeRepository.findAll(pageable);
	}

	 @Transactional(readOnly=true)
	 @Override
	 public Page<EventCode> find(final EventCodeQueryParam queryParam, Pageable pageable) {
	        Page<EventCode> result = eventCodeRepository.findAll(new Specification<EventCode>() {          
	            @Override
	            public Predicate toPredicate(Root<EventCode> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                Predicate predicate = cb.conjunction();
	                
	                List<Expression<Boolean>> expressions = predicate.getExpressions();
	                if(StringUtils.hasText(queryParam.getEventType())) {
	                	Path<String> nick = root.<String>get("eventType");
	                    expressions.add(cb.equal(nick, queryParam.getEventType())); 
	                }
	                if(StringUtils.hasText(queryParam.getEventLevel())) {
	                	Path<String> nick = root.<String>get("eventLevel");
	                    expressions.add(cb.equal(nick, queryParam.getEventLevel())); 
	                }
	                
	                
	               
	                return predicate;
	            }

	        }, pageable); 
	        
	        return result;
	    }

	
	

}
