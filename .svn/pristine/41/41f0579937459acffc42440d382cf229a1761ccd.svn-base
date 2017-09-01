package com.iycharge.server.domain.service.impl;

import java.util.ArrayList;
import java.util.Collections;
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

import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.domain.common.utils.ReflectField;
import com.iycharge.server.domain.elastic.repository.DeviceRepository;
import com.iycharge.server.domain.entity.account.Card;
import com.iycharge.server.domain.entity.charger.Charger;
import com.iycharge.server.domain.entity.charger.ChargerStatus;
import com.iycharge.server.domain.entity.operator.Operator;
import com.iycharge.server.domain.entity.station.Station;
import com.iycharge.server.domain.repository.ChargerRepository;
import com.iycharge.server.domain.service.ChargerService;

@Service
public class ChargerServiceImpl implements ChargerService {
    @Autowired
    ChargerRepository chargerRepository;
    @Autowired
    DeviceRepository deviceRepository;

    @Override
    public Page<Charger> findAll(Pageable pageable) {
        return chargerRepository.findByDelStatus("normal",pageable);
    }

    @Override
    @Transactional  
    public List<Charger> findAll() {
        List<Charger> chargers = chargerRepository.findByDelStatus("normal");
        for(Charger charger : chargers) {
System.out.println(charger.getStation());
        }
        return chargers;
    }

    @Override
    public Charger findById(long id) {
        return chargerRepository.findOne(id);
    }
    
    @Override
    public Charger findByCode(String code) {
    	List<Charger> list = chargerRepository.findByCodeAndDelStatus(code,"normal");
    	Charger charger = new Charger();
    	if(list!=null&&list.size()>0){
    		charger = list.get(0);
    	}
        return charger;
    }
    @Override
    public List<Charger> findByCodeList(String code) {
        return chargerRepository.findByCode(code);
    }
    @Override
    public List<Charger> findByQrcodeList(String qrcode) {
        return chargerRepository.findByQrcode(qrcode);
    }
    @Override
    public Page<Charger> findByNameContaining(String name, Pageable pageable) {
        return chargerRepository.findByNameContaining(name, pageable);
    }

    @Override
    public List<Charger> findByNameContaining(String name) {
        return chargerRepository.findByName(name);
    }
    @Override
    public Page<Charger> findByType(String type, Pageable pageable) {
        return chargerRepository.findByType(type, pageable);
    }

    @Override
    public Page<Charger> findByStatus(ChargerStatus status, Pageable pageable) {
        return chargerRepository.findByStatus(status, pageable);
    }

    @Override
    public List<Charger> findByStation(Station station) {
        return chargerRepository.findByDelStatusAndStation("normal",station);
    }

    @Override
    public Page<Charger> findByStation(Station station, Pageable pageable) {
        return chargerRepository.findByStation(station, pageable);
    }

    @Override
    public List<Charger> findByOperator(Operator operator) {
        return chargerRepository.findByDelStatusAndOperator("normal",operator);
    }

    @Override
    public Page<Charger> findByOperator(Operator operator, Pageable pageable) {
        return chargerRepository.findByOperator(operator, pageable);
    }

    @Override
    public Charger save(Charger charger) {
        charger = chargerRepository.saveAndFlush(charger);
        //chargerRepository.save(charger);
        return charger;
    }
    
    
    @Override
    public Charger delCharger(Charger charger) {
        charger = chargerRepository.saveAndFlush(charger);
        EntityUtil.removeCharger(charger);        
        return charger;
    }
    
    @Override
    public void del(Charger entity) {
        chargerRepository.delete(entity);
    	EntityUtil.removeCharger(entity);  
    }
    
    @Override
    public Page<Charger> findAllSearch(final String []fields, final Charger charger,Pageable pageable) {
    	Specification<Charger> spec = new Specification<Charger>(
    			) {	
    		@Override
		public Predicate toPredicate(Root<Charger> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    			Predicate ps = null;
    			Predicate plist[] =null;
    			Map<String,Object> map = new HashMap<String,Object>();
    			//封装获取不为空的值与属性
    			for(String fieldName:fields){
					Object o = ReflectField.getFieldValueByName(fieldName, charger);
					if(o!=null&&!"".equals(o)){
						if("station".equals(fieldName)){
							Station s = (Station)o;
							if(s!=null&&s.getId()!=null){
								map.put(fieldName, s);
							}
						}else{
							map.put(fieldName, o);
						}
					}
				}
    			//为属性附加sql条件
    			if(map!=null){
    				plist = new Predicate[map.size()];
    				int i=0;
    				for(String fieldName : map.keySet()) {
    					Path<String> nick = root.get(fieldName); 
    					Object str = map.get(fieldName);
    					if(fieldName.equals("type")){
							if(StringUtils.isNotBlank(str.toString())){
								plist[i] = cb.equal(nick, str); 
							}
						}else if(fieldName.equals("chargeIf")){
							if(StringUtils.isNotBlank(str.toString())){
								plist[i] = cb.equal(nick, str);
							}
						}else if(fieldName.equals("operator")){
							Operator oper = (Operator)str;
							plist[i] = cb.equal(nick, oper.getId()); 
						}else if(fieldName.equals("station")){
							Station sta = (Station)str;
							plist[i] = cb.equal(nick, sta.getId()); 
						}else if(fieldName.equals("delStatus")){
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
			return chargerRepository.findAll(spec,pageable);
    }
    
    
    @Override
    public List<Charger> findSearch(final String []fields, final Charger charger) {
    	Specification<Charger> spec = new Specification<Charger>(
    			) {	
    		@Override
		public Predicate toPredicate(Root<Charger> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    			Predicate ps = null;
    			Predicate result = null;
    			List<Predicate> plist =new ArrayList<Predicate>();
    			List<Predicate> pliststation =new ArrayList<Predicate>();
    			Map<String,Object> map = new HashMap<String,Object>();
    			//封装获取不为空的值与属性
    			for(String fieldName:fields){
					Object o = ReflectField.getFieldValueByName(fieldName, charger);
					if(o!=null&&!"".equals(o)){
						map.put(fieldName, o);
					}
				}
    			//为属性附加sql条件
    			if(map!=null){
    				int i=0;
    				for(String fieldName : map.keySet()) {
    					if(fieldName.equals("delStatus")){
    						Path<String> nick = root.get(fieldName); 
        					Object str = map.get(fieldName);
        					plist.add(cb.equal(nick, str)); 
						}else if(fieldName.equals("stationcheckboxs")){
							Path<String> nicks = root.get("station");
							List<Station> ls = charger.getLstation();
							if(ls!=null&&ls.size()>0){
								for(Station st:ls){
									pliststation.add(cb.equal(nicks, st.getId()));
								}
							}
						}else{
							Path<String> nick = root.get(fieldName); 
	    					Object str = map.get(fieldName);
	    					plist.add(cb.like(nick, "%"+str+"%"));
						}
        			}
    			}
    			if(plist.size()>0){
    				Predicate all = null;
    				Predicate arr[]=new Predicate[plist.size()];
    				if(pliststation.size()>0){
    					Predicate arrstation[]=new Predicate[pliststation.size()];
    					all = cb.or(pliststation.toArray(arrstation));
    				}
    				ps = cb.and(plist.toArray(arr));
    				if(all!=null){
    					result = cb.and(ps,all);
    				}else{
    					result =ps;
    				}
    			}
				if(result!=null){
					query.where(result); //这里可以设置任意条查询条件
				}
				return null;
				}
		};
			return chargerRepository.findAll(spec);
    }

	@Override
	public List<Object[]> CountType(long stationId) {
		return chargerRepository.CountType(stationId);
	}

	@Override
	public List<Charger> findByCondition(final Map<String, String> field, Charger charger) {
		Specification<Charger> spec = new Specification<Charger>() {
			@Override
			public Predicate toPredicate(Root<Charger> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				List<Expression<Boolean>> expressions = predicate.getExpressions();
				if(field.get("name")!=null&&!field.get("name").trim().equals("")){
					expressions.add(cb.like(root.<String>get("name"), "%"+field.get("name")+"%"));
				}
				if(field.get("ctype")!=null&&!field.get("ctype").trim().equals("")){
					expressions.add(cb.equal(root.<String>get("type"),field.get("ctype")));
				}
				if(field.get("cif")!=null&&!field.get("cif").trim().equals("")){
					expressions.add(cb.equal(root.<String>get("chargeIf"), field.get("cif")));
				}
				if(field.get("stationId")!=null&&!field.get("stationId").trim().equals("")){
					expressions.add(cb.equal(root.<String>get("station").get("id"), field.get("stationId")));
				}
				expressions.add(cb.equal(root.<String>get("delStatus"), "normal"));
				query.orderBy(cb.desc(root.get("id")));
				query.distinct(true);
				return predicate;
			}
		};
		return chargerRepository.findAll(spec);
	}

	

}
