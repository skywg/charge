package com.iycharge.server.domain.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.elasticsearch.common.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.iycharge.server.domain.common.utils.ReflectField;
import com.iycharge.server.domain.entity.content.Content;
import com.iycharge.server.domain.repository.ContentRepository;
import com.iycharge.server.domain.service.ContentService;
@Service
public class ContentServiceImpl implements ContentService{
	@Autowired
	private ContentRepository contentRepository;
	@Override
	public Page<Content> findAll(Pageable pageable) {
		 return contentRepository.findAll(pageable);
	}
	@Override
	public Content findById(Long id) {
		
		return contentRepository.findById(id);
	}
	@Override
	public Content save(Content content) {
		
		return contentRepository.save(content);
	}
	@Override
	public Page<Content> findAllSearch(final String[] fields, final Content content, Pageable pageable) {
		Specification<Content> spec = new Specification<Content>() {
			@Override
			public Predicate toPredicate(Root<Content> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate ps = null;
				Predicate plist[] = null;
				List<Predicate> predicate = new ArrayList<Predicate>();
				Map<String, Object> map = new HashMap<String, Object>();
				for (String fieldName : fields) {
					if ("releasedAt".equals(fieldName)) {
						if ("releasedAt".equals(fieldName)) {
							if (content.getStartAt()!= null && content.getReleasedAt() != null) {
								List<Date> date = new ArrayList<Date>();
								date.add(content.getStartAt());
								date.add(content.getReleasedAt());
								map.put("releasedAt", date);
							}
						}
					}else {
						Object o = ReflectField.getFieldValueByName(fieldName, content);
						if (o != null && !"".equals(o)) {
							map.put(fieldName, o);
						}
					}
				}
				// 为属性附加sql条件
				if (map != null) {
					for (String fieldName : map.keySet()) {
						Path<String> nick = null; 
						Path<Date> nickDate = null;
						Path<Byte> nick1 = null; 
						if ("releasedAt".equals(fieldName)) {
							nickDate = root.get("releasedAt");
						}else{
							if("transientStatus".equals(fieldName)){
								nick1 = root.get("status");
							}else{
								nick=root.get(fieldName);
							}
						}
						Object str = map.get(fieldName);
						if (fieldName.equals("title")) {
							if (StringUtils.isNotEmpty((String) (str))) {
								predicate.add(cb.like(nick, "%" + str + "%"));
							}
						}
						if (fieldName.equals("classification")) {
							if (content. getClassification()!= null) {
							    nick = root.get(fieldName);
								predicate.add(cb.equal(nick,content.getClassification()));							
							}
						} 
						if (fieldName.equals("transientStatus")) {
							if (StringUtils.isNotEmpty((String) (str))) {
								byte value = -1;
								if(str.toString().trim().equals("-1")){
									value = -1;
								}else if(str.toString().trim().equals("0")){
									value = 0;
								}else if(str.toString().trim().equals("1")){
									value = 1;
								}else if(str.toString().trim().equals("2")){
									value = 2;
								}else if(str.toString().trim().equals("3")){
									value = 3;
								}
								predicate.add(cb.equal(nick1,value));							
							}
						} 
						if (fieldName.equals("releasedAt")) {
							List<Date> date = (List<Date>) str;
							predicate.add(cb.between(nickDate, date.get(0), date.get(1)));
						}
					}
				}
				if (predicate.size() > 0) {
					plist = new Predicate[predicate.size()];
					// 把集合转换成数组
					ps = cb.and(predicate.toArray(plist));
					if (ps != null) {
						query.where(ps);
					}
				}
				return null;
			}
		};
		return contentRepository.findAll(spec,pageable);
	}
	@Override
	public List<Content> findByClassification(String classification) {
		
		return contentRepository.findByClassification(classification);
	}
	@Override
	public Page<Content> findByClassification(String classification,Byte status, Pageable pageable) {
		
		return contentRepository.searchContentByClassificationAndStatus(classification, status, pageable);
	}
	@Override
	public List<Content> findByStatus(Byte status) {
		
		return contentRepository.findByStatus(status);
	}
	

	

}
