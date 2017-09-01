package com.iycharge.server.domain.service.impl;
import com.iycharge.server.domain.common.utils.ReflectField;
import com.iycharge.server.domain.entity.charger.Charger;
import com.iycharge.server.domain.entity.price.ParamSetting;
import com.iycharge.server.domain.entity.price.ParamSettingResult;
import com.iycharge.server.domain.repository.ParamSettingResultRepository;
import com.iycharge.server.domain.service.ParamSettingResultService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParamSettingResultServiceImpl implements ParamSettingResultService {
    @Autowired
    ParamSettingResultRepository paramSettingResultRepository;

    @Override
    public Page<ParamSettingResult> findAll(Pageable pageable) {
        return paramSettingResultRepository.findByDelStatus("normal",pageable);
    }
    @Override
    public List<ParamSettingResult> findByParamSetting(ParamSetting paramSetting) {
        return paramSettingResultRepository.findByDelStatusAndParamSetting("normal",paramSetting);
    }
    
    @Override
    @Transactional
    public ParamSettingResult save(ParamSettingResult paramSettingResult) {
        return paramSettingResultRepository.saveAndFlush(paramSettingResult);
    }
	@Override
	public ParamSettingResult findById(long id) {
		return paramSettingResultRepository.findOne(id);
	}
	
	  
    @Override
    public Page<ParamSettingResult> findAllSearch(final String []fields, final ParamSettingResult paramSettingResult,Pageable pageable) {
    	Specification<ParamSettingResult> spec = new Specification<ParamSettingResult>(
    			) {	
    		@Override
		public Predicate toPredicate(Root<ParamSettingResult> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    			Predicate ps = null;
    			Predicate plist[] =null;
    			Map<String,Object> map = new HashMap<String,Object>();
    			//封装获取不为空的值与属性
    			for(String fieldName:fields){
					Object o = ReflectField.getFieldValueByName(fieldName, paramSettingResult);
					if(o!=null&&!"".equals(o)){
						map.put(fieldName, o);
					}
    			}
    			//为属性附加sql条件
    			if(map!=null){
    				plist = new Predicate[map.size()];
    				int i=0;
    				for(String fieldName : map.keySet()) {
    					Path<String> nick = root.get(fieldName); 
    					Object str = map.get(fieldName);
    					if(fieldName.equals("charger")){
							Charger oper = (Charger)str;
							plist[i] = cb.equal(nick, oper.getId()); 
						}
    					i++;
        			}
    			}
    			if(plist.length>0){
    				ps = cb.and(plist);
    			}
				if(ps!=null){
					query.where(ps); //这里可以设置任意条查询条件
				}
				return null;
				}
		};
			return paramSettingResultRepository.findAll(spec,pageable);
    }
    @Override
    public ParamSettingResult findLatestResult(String chargerCode, String paramType) {
        Specification<ParamSettingResult> spec = new Specification<ParamSettingResult>() {            
            @Override
            public Predicate toPredicate(Root<ParamSettingResult> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                
                List<Expression<Boolean>> expressions = predicate.getExpressions();
                expressions.add(cb.equal(root.<Charger>get("charger").get("code"), chargerCode));
                expressions.add(cb.equal(root.<ParamSetting>get("paramSetting").get("paramType"), paramType));
                
                query.orderBy(cb.desc(root.get("createdAt")));
                return predicate;
            }
        };
        
        Page<ParamSettingResult> result = paramSettingResultRepository.findAll(spec, new PageRequest(0, 1));
        if(result.hasContent()) {
            return result.getContent().get(0);
        }
        return null;
    }
}
