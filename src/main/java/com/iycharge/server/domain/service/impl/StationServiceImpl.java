package com.iycharge.server.domain.service.impl;

import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.domain.common.utils.ReflectField;
import com.iycharge.server.domain.elastic.document.Device;
import com.iycharge.server.domain.elastic.repository.DeviceRepository;
import com.iycharge.server.domain.entity.account.Card;
import com.iycharge.server.domain.entity.operator.Operator;
import com.iycharge.server.domain.entity.station.Station;
import com.iycharge.server.domain.entity.station.StationStatus;
import com.iycharge.server.domain.repository.ChargerRepository;
import com.iycharge.server.domain.repository.StationRepository;
import com.iycharge.server.domain.service.StationService;

import org.elasticsearch.common.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Service
public class StationServiceImpl implements StationService {
    @Autowired
    StationRepository stationRepository;
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    ChargerRepository chargerRepository;
    @Override
    public Page<Station> findAll(Pageable pageable) {
    	Page<Station> page = stationRepository.findByDelStatus("normal",pageable);
        //        BeanUtils.copyProperties(page,sb);
    	List<Station> list =   page.getContent();
    	ChangeList(list);
    	return page;
    }
  
    public void ChangeList(List<Station> list){
    	if(list!=null){
    		for(Station st:list){
    			long id = st.getId();
    			List<Object[]> listNum =  chargerRepository.CountType(id);
    			for(Object[] co:listNum){
    				// DC("直流充电桩"), AC("交流充电桩");
    				String type = co[0].toString();
    				String num = co[1].toString();
    				if("AC".equals(type)){
    					st.setCountAC(num);
    				}
    				if("DC".equals(type)){
    					st.setCountDC(num);
    				}
    			}
    		}
    	}
    }
    @Override
    public List<Station> findListAll() {
        return stationRepository.findByDelStatus("normal");
    }

    @Override
    public Station findById(long id) {
        return stationRepository.findOne(id);
    }

    @Override
    public List<Station> findByOperator(Operator operator) {
        return stationRepository.findByDelStatusAndOperator("normal",operator);
    }
    
    @Override
    public List<Station> findByCode(String code) {
        return stationRepository.findByCode(code);
    }
    
    @Override
    public Page<Station> findByNameContaining(String name, Pageable pageable) {
        return stationRepository.findByNameContaining(name, pageable);
    }

    @Override
    public Page<Station> findByType(String type, Pageable pageable) {
        return stationRepository.findByStationType(type, pageable);
    }

    @Override
    public Page<Station> findByStatus(StationStatus status, Pageable pageable) {
        return stationRepository.findByStatus(status, pageable);
    }

    @Override
    public Page<Station> findByProvince(String province, Pageable pageable) {
        return stationRepository.findByProvince(province, pageable);
    }

    @Override
    public Page<Station> findByCity(String city, Pageable pageable) {
        return stationRepository.findByCity(city, pageable);
    }

    @Override
    public Page<Station> findByAddress(String address, Pageable pageable) {
        return stationRepository.findByAddress(address, pageable);
    }

    @Override
    public Page<Station> findByOperator(Operator operator, Pageable pageable) {
        return stationRepository.findByOperator(operator, pageable);
    }

    @Override
    public Page<Station> findAllByOrderByRatingTotalDesc(Pageable pageable) {
        return stationRepository.findAllByOrderByRatingTotalDesc(pageable);
    }

    @Override
    public Station save(Station station) {
        if (station.getSearchId() == null) {
            station.setSearchId(UUID.randomUUID().toString());
        }
        station = stationRepository.saveAndFlush(station);
        //在搜索引擎服务中建立索引
        deviceRepository.save(new Device(station));
        return station;
    }
    
    @Override
    public Station delStation(Station station) {
    	station = stationRepository.saveAndFlush(station);
    	deviceRepository.delete(station.getSearchId());
    	EntityUtil.removeStation(station);
        return station;
    }
    @Override
    public void del(Long id) {
    	stationRepository.delete(id);
    }
    
    @Override
    public Page<Station> findAllSearch(final String []fields, final Station station,Pageable pageable) {
    	Specification<Station> spec = new Specification<Station>(
    			) {		
    		@Override
		public Predicate toPredicate(Root<Station> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    			Predicate ps = null;
    			Predicate plist[] =null;
    			Map<String,Object> map = new HashMap<String,Object>();
    			//封装获取不为空的值与属性
    			for(String fieldName:fields){
					Object o = ReflectField.getFieldValueByName(fieldName, station);
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
//    					if(fieldName.equals("province")){
//    						plist[i] = cb.like(nick, "%"+str+"%");
//						}else 
						if(fieldName.equals("stationType")){
							if(StringUtils.isNotBlank(str.toString())){
								plist[i] = cb.equal(nick, str); 
							}
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
//    	Specification<Station> spec = new Specification<Station>(
//    			) {
//					@Override
//					public Predicate toPredicate(Root<Station> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//							Predicate p = null;
//							for(String fieldName:fields){
//								Object o = ReflectField.getFieldValueByName(fieldName, station);
//								if(o!=null&&!"".equals(o)){
//									Path<String> nick = root.get(fieldName); 
//									if(fieldName.equals("province")){
//										p = cb.like(nick, "%"+o+"%");
//									}else if(fieldName.equals("type")){
//										StationType t = (StationType)o;
//										if(StringUtils.isNotBlank(t.getTitle())){
//											p = cb.and(cb.equal(nick, o)); 
//										}
//									}else{
//										p = cb.like(nick, "%"+o+"%");
//										//p = cb.and(cb.equal(nick, o)); 
//									}
//								}
//							}
//							if(p!=null){
//								query.where(p); //这里可以设置任意条查询条件
//							}
////							Path<String> nameType = root.get("type");  
////						    Path<String> nickProvince = root.get("province");  
////						    Path<String> nickName = root.get("name");  
////						    Predicate p = null;
////						    p = cb.and(cb.equal(nameType, type)); 
////						    p = cb.and(cb.equal(nickName, name)); 
////						    p = cb.like(nickProvince, "%"+province+"%");
//						    /** 
//						         * 连接查询条件, 不定参数，可以连接0..N个查询条件 
//						         */  
////						    query.where(cb.equal(nameType, type), cb.equal(nickName, name),cb.like(nickProvince, "%"+province+"%")); //这里可以设置任意条查询条件
//							return null;
//					}
//		};
		Page<Station> page = stationRepository.findAll(spec, pageable);
		List<Station> list =   page.getContent();
    	ChangeList(list);
    	return page;
//    	if(type==null&&StringUtils.isEmpty(name)&&StringUtils.isEmpty(province)){
//    		return stationRepository.findAll(pageable);
//    	}else{
//    		return stationRepository.findByTypeAndNameAndProvince(type,name,province,pageable);
//    	}
    	//stationRepository.findAll(, pageable);  
    }
    
    
    @Override
    public List<Station> findSearch(final String []fields, final Station station) {
    	Specification<Station> spec = new Specification<Station>(
    			) {		
    		@Override
		public Predicate toPredicate(Root<Station> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    			Predicate ps = null;
    			List<Predicate> plist =new ArrayList<Predicate>();
    			Map<String,Object> map = new HashMap<String,Object>();
    			//封装获取不为空的值与属性
    			for(String fieldName:fields){
					Object o = ReflectField.getFieldValueByName(fieldName, station);
					if(o!=null&&!"".equals(o)){
						map.put(fieldName, o);
					}
				}
    			//为属性附加sql条件
    			if(map!=null){
    				for(String fieldName : map.keySet()) {
    					Path<String> nick = root.get(fieldName); 
    					Object str = map.get(fieldName);
    					plist.add(cb.like(nick, "%"+str+"%"));
        			}
    			}
    			if(plist.size()>0){
    				Predicate codep = null;
    				Predicate namep = null;
    				Predicate all = null;
    				if(StringUtils.isNotEmpty(station.getCodeAndName())){
    					Path<String> nickcode = root.get("code"); 
    					codep = cb.like(nickcode, "%"+station.getCodeAndName()+"%");
    					Path<String> nickname = root.get("name"); 
    					namep = cb.like(nickname, "%"+station.getCodeAndName()+"%");
    					all = cb.or(codep,namep);
        			}
    			    Predicate[] pre = new Predicate[plist.size()];
    				ps = cb.and(plist.toArray(pre));
    				if(all!=null){
    					ps = cb.and(ps,all);
    				}
    			}
				if(ps!=null){
					query.where(ps); //这里可以设置任意条查询条件
				}
				return null;
				}
		};
		List<Station> page = stationRepository.findAll(spec);
    	return page;
    }

	@Override
	public List<Station> findByNameContaining(String name) {
		
		return stationRepository.findByName(name);
	}

	@Override
	public List<Station> findByCondition(Map<String, String> field, Station station) {
		Specification<Station> spec = new Specification<Station>() {
			@Override
			public Predicate toPredicate(Root<Station> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				List<Expression<Boolean>> expressions = predicate.getExpressions();
				if(field.get("name")!=null&&!field.get("name").trim().equals("")){
					expressions.add(cb.like(root.<String>get("name"), "%"+field.get("nickname")+"%"));
				}
				if(field.get("stype")!=null&&!field.get("stype").trim().equals("")){
					expressions.add(cb.equal(root.<String>get("stationType"), field.get("stype")));
				}
				if(field.get("provice")!=null&&!field.get("provice").trim().equals("")){
					expressions.add(cb.equal(root.<String>get("province"), field.get("provice")));
				}
				expressions.add(cb.equal(root.<String>get("delStatus"), "normal"));
				query.orderBy(cb.desc(root.get("id")));
				return predicate;
			}
		};
		List<Station> stations = stationRepository.findAll(spec);
		ChangeList(stations);
		return stations;
	}

}
