package com.iycharge.server.domain.service.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.iycharge.server.domain.entity.price.ParamTemplate;
import com.iycharge.server.domain.entity.price.ParamTemplateQueryParam;
import com.iycharge.server.domain.repository.ParamTemplateRepository;
import com.iycharge.server.domain.service.ParamTemplateService;
@Service
public class ParamTemplateServiceImpl implements ParamTemplateService{
		@Autowired
		private ParamTemplateRepository paramTemplateRepository;

		@Override
		public Page<ParamTemplate> findAll(Pageable pageable) {
			
			return paramTemplateRepository.findAll(pageable);
		}
		@Override
		public ParamTemplate findById(long id) {
			
			return paramTemplateRepository.findOne(id);
		}
		 @Transactional(readOnly=true)
		 @Override
		 public Page<ParamTemplate> find(final ParamTemplateQueryParam queryParam, Pageable pageable) {
		        Page<ParamTemplate> result = paramTemplateRepository.findAll(new Specification<ParamTemplate>() {          
		            @Override
		            public Predicate toPredicate(Root<ParamTemplate> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		                Predicate predicate = cb.conjunction();
		                
		                List<Expression<Boolean>> expressions = predicate.getExpressions();
		                if(StringUtils.hasText(queryParam.getType())) {
		                	Path<String> nick = root.<String>get("type");
		                    expressions.add(cb.equal(nick, queryParam.getType())); 
		                }
		                if(StringUtils.hasText(queryParam.getStatus())) {
		                	Path<String> nick = root.<String>get("status");
		                    expressions.add(cb.equal(nick, queryParam.getStatus())); 
		                }if (StringUtils.hasText(queryParam.getDelStatus())) {
		                	Path<String> nick = root.<String>get("delStatus");
		                	expressions.add(cb.equal(nick, queryParam.getDelStatus()));
						}
		                
		                
		               
		                return predicate;
		            }

		        }, pageable); 
		        
		        return result;
		    }
		@Override
		public void del(ParamTemplate paramTemplate) {
			paramTemplateRepository.delete(paramTemplate);
			
		}
		@Override
	    public ParamTemplate save(ParamTemplate template) {
	        return paramTemplateRepository.saveAndFlush(template);
	    }
		@Override
		public List<ParamTemplate> findByType(String type) {
			
			return paramTemplateRepository.findByType(type);
		}
		@Override
		public ParamTemplate findChargerPrice(Long chargerId) {
			 List<Object[]> datas = paramTemplateRepository.findChargerPrice(chargerId, new Date(), "PRICE");
		        if(datas != null && datas.size() > 0) {
		            return (ParamTemplate)datas.get(0)[1];
		        }
		        return null;
		}
		@Override
		public ParamTemplate findChargerParam(Long chargerId) {
			List<Object[]> datas = paramTemplateRepository.findChargerPrice(chargerId, new Date(), "PARAM");
	        if(datas != null && datas.size() > 0) {
	            return (ParamTemplate)datas.get(0)[1];
	        }
	        return null;
		}
		@Override
		public Page<ParamTemplate> findByDelStatus(String delStatus, Pageable pageable) {
			
			return paramTemplateRepository.findByDelStatus(delStatus, pageable);
		}
}
