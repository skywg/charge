package com.iycharge.server.domain.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.elasticsearch.common.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iycharge.server.domain.common.utils.ReflectField;
import com.iycharge.server.domain.entity.admin.LogQueryParam;
import com.iycharge.server.domain.entity.admin.ManagerLog;
import com.iycharge.server.domain.entity.charger.Charger;
import com.iycharge.server.domain.entity.event.Event;
import com.iycharge.server.domain.entity.event.EventQueryParam;
import com.iycharge.server.domain.entity.event.EventStatus;
import com.iycharge.server.domain.entity.order.Order;
import com.iycharge.server.domain.entity.utils.Utils;
import com.iycharge.server.domain.repository.EventRepository;
import com.iycharge.server.domain.service.EventService;

/**
 *
 * @author bwang
 */
@Transactional(readOnly=true)
@Service
public class EventServiceImpl implements EventService {
	
	@Autowired
	private EventRepository eventRepository;

	@Override
	public Page<Event> findAll(Pageable pageable) {

		return eventRepository.findAll(pageable);
	}
	@Override
	public Event findById(Long id) {
		
		return eventRepository.findOne(id);
	}
	
	@Transactional(readOnly=false)
	@Override
	public Event save(Event event) {
		
		return eventRepository.save(event);
	}


    @Override
    public List<Object[]> statisticByEventType(Date startTime, Date endTime) {
        // TODO Auto-generated method stub
        return eventRepository.statisticByEventType(startTime, endTime);
    }

    @Override
    public List<Event> findByEventStatus() {
        return eventRepository.findByEventStatus(EventStatus.SUSPENDING);
    }
    
    @Override
    public List<Object[]> statisticByEventLevel(Date startTime, Date endTime) {
        // TODO Auto-generated method stub
        return eventRepository.statisticByEventLevel(startTime, endTime);
    }
    @Transactional(readOnly=true)
    @Override
    public Page<Event> find(final EventQueryParam queryParam, Pageable pageable) {
        @SuppressWarnings("unchecked")
		Page<Event> result = eventRepository.findAll(new Specification<Event>() {          
            @Override
            public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                
                List<Expression<Boolean>> expressions = predicate.getExpressions();
                
                if(queryParam.getStation()!=null&&!queryParam.getStation().equals("")) {
                   
                	expressions.add(cb.like(root.<String>get("station"), "%" + queryParam.getStation() + "%"));
                }
                if (queryParam.getEventCode()!=null) {
					if (!queryParam.getEventCode().getEventLevel().equals("")) {
						 Path<String> nick = root.<String>get("eventCode").get("eventLevel");
						 expressions.add(cb.equal(nick, queryParam.getEventCode().getEventLevel()));
					}
					if (!queryParam.getEventCode().getEventType().equals("")) {
						 Path<String> nick = root.<String>get("eventCode").get("eventType");
						 expressions.add(cb.equal(nick, queryParam.getEventCode().getEventType()));
					}
				}
                if(queryParam.getCharger() != null) {
                	Path<String> nick = root.get("charger").get("name");
                    expressions.add(cb.like(nick, "%"+queryParam.getCharger().getName()+"%"));
                }
                if(queryParam.getStartTime() != null && queryParam.getEndTime() != null) {
                	Calendar calendar = Calendar.getInstance();
					calendar.setTime(queryParam.getEndTime());
					calendar.add(calendar.DATE, 1);
                    expressions.add(cb.between(root.<Date>get("createdAt"), queryParam.getStartTime(), calendar.getTime()));
                }
                if(queryParam.getEventStatus()!=null){
                	if (!queryParam.getEventStatus().equals("")) {
						 Path<String> nick = root.<String>get("eventStatus");
						 expressions.add(cb.equal(nick, queryParam.getEventStatus()));
					}
                }
                
                return predicate;
            }
        }, pageable); 
        
        return result;
    }
	@Override
	public List<Event> findByCondition(Map<String, String> field, Event event) {
		Specification<Event> spec = new Specification<Event>() {
			@Override
			public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				List<Expression<Boolean>> expressions = predicate.getExpressions();
				
			// 	   conditionMap.put("inputCusName", inputCusName);
			// 	   conditionMap.put("inputChargerName", inputChargerName);
			// 	   conditionMap.put("eventLevel", eventLevel);
			 //	   conditionMap.put("eventType", eventType);
			 //	   conditionMap.put("start", start);
			 //	   conditionMap.put("end", end);
				if(field.get("inputCusName")!=null&&!field.get("inputCusName").toString().trim().equals("")){
					expressions.add(cb.like(root.<String>get("station"), "%" + field.get("inputCusName") + "%"));
				}
				if(field.get("inputChargerName")!=null&&!field.get("inputCusName").toString().trim().equals("")){
					expressions.add(cb.like(root.<String>get("charger").get("name"), "%" + field.get("inputChargerName") + "%"));
				}
				if(field.get("eventLevel")!=null&&!field.get("eventLevel").toString().trim().equals("")){
					expressions.add(cb.equal(root.<String>get("eventCode").get("eventLevel"),field.get("eventLevel")));
				}
				if(field.get("eventType")!=null&&!field.get("eventType").toString().trim().equals("")){
					expressions.add(cb.equal(root.<String>get("eventCode").get("eventType"),field.get("inputCusName")));
				}
				if(field.get("start")!=null&&field.get("end")!=null
						&&!field.get("start").toString().trim().equals("")
						&&!field.get("end").toString().trim().equals("")){
					expressions.add(cb.between(root.<Date>get("createdAt"), Utils.stringToDate(field.get("start")),Utils.stringToDate(field.get("end"))));
				}
				query.orderBy(cb.desc(root.get("createdAt")));
				return predicate;
			}
		};
		return eventRepository.findAll(spec);
	}


}
