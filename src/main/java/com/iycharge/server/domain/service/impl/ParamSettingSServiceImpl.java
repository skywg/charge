package com.iycharge.server.domain.service.impl;

import com.iycharge.server.domain.common.utils.ReflectField;
import com.iycharge.server.domain.entity.price.ParamSetting;
import com.iycharge.server.domain.repository.ParamSettingRepository;
import com.iycharge.server.domain.service.ParamSettingSService;
import org.elasticsearch.common.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Service
public class ParamSettingSServiceImpl implements ParamSettingSService {
    @Autowired
    ParamSettingRepository paramSettingRepository;

    @Override
    public Page<ParamSetting> findAll(Pageable pageable) {
        return paramSettingRepository.findByDelStatus("normal",pageable);
    }

    @Override
    public List<ParamSetting> findAll() {
        return paramSettingRepository.findAll();
    }

    @Override
    public ParamSetting findById(long id) {
        return paramSettingRepository.findOne(id);
    }
    
    @Override
    public ParamSetting save(ParamSetting paramSetting) {
        paramSetting = paramSettingRepository.saveAndFlush(paramSetting);
        return paramSetting;
    }
    
    
    @Override
    public ParamSetting delParamSetting(ParamSetting paramSetting) {
        paramSetting = paramSettingRepository.saveAndFlush(paramSetting);
        return paramSetting;
    }
    
    @Override
    public void del(ParamSetting entity) {
    	paramSettingRepository.delete(entity);
    }
    
    @Override
    public Page<ParamSetting> findAllSearch(final String []fields, final ParamSetting paramSetting,Pageable pageable) {
    	Specification<ParamSetting> spec = new Specification<ParamSetting>(
    			) {	
    		@Override
		public Predicate toPredicate(Root<ParamSetting> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    			Predicate ps = null;
    			Predicate plist[] =null;
    			Map<String,Object> map = new HashMap<String,Object>();
    			//封装获取不为空的值与属性
    			for(String fieldName:fields){
					Object o = ReflectField.getFieldValueByName(fieldName, paramSetting);
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
    					if(fieldName.equals("paramType")){
							if(StringUtils.isNotBlank(str.toString())){
								plist[i] = cb.equal(nick, str);
							}
						}else if(fieldName.equals("sendFlag")){
							plist[i] = cb.equal(nick, str); 
						}else{
							plist[i] = cb.like(nick, "%"+str+"%");
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
			return paramSettingRepository.findAll(spec,pageable);
    }

}
