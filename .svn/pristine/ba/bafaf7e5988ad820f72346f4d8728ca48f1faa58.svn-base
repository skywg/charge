package com.iycharge.server.domain.service.impl;

import com.iycharge.server.domain.common.utils.ReflectField;
import com.iycharge.server.domain.entity.review.Review;
import com.iycharge.server.domain.repository.ReviewRepository;
import com.iycharge.server.domain.service.ReviewService;

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

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    ReviewRepository reviewRepository;

    @Override
    public Page<Review> findAll(Pageable pageable) {
        return reviewRepository.findByAccountNotNull(pageable);
    }

    @Override
    public Review findById(long id) {
        return reviewRepository.findOne(id);
    }
    @Override
    public List<Review> findByStatus() {
        return reviewRepository.findByStatus((byte)-1);
    }
    
    @Override
    public Review save(Review review) {
        return reviewRepository.saveAndFlush(review);
    }

    @Override
	public Page<Review> findAllSearch(final String[] fields, final Review review, Pageable pageable) {
		Specification<Review> spec = new Specification<Review>() {
			@Override
			public Predicate toPredicate(Root<Review> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate ps = null;
				Predicate plist[] = null;
				List<Predicate> predicate = new ArrayList<Predicate>();
				Map<String, Object> map = new HashMap<String, Object>();
				for (String fieldName : fields) {
					if ("updatedAt".equals(fieldName)) {
							if (review.getStartAt()!= null && review.getUpdatedAt() != null) {
								List<Date> date = new ArrayList<Date>();
								Calendar calendar = Calendar.getInstance();
								calendar.setTime(review.getUpdatedAt());
								calendar.add(calendar.DATE, 1);
								date.add(review.getStartAt());
								date.add(calendar.getTime());
								map.put(fieldName, date);
							}

					} else if("realName".equals(fieldName)){
						Object o = ReflectField.getFieldValueByName(fieldName, review.getAccount());
						if (o!= null && !"".equals(o)) {
							map.put(fieldName, o.toString().trim());
						}
					}else if("name".equals(fieldName)){
						Object o1 = ReflectField.getFieldValueByName(fieldName, review.getStation());
						if (o1 != null && !"".equals(o1)) {
							map.put(fieldName,o1.toString().trim());
						}
					}else if("differentiate".equals(fieldName)){
						Object o1 = ReflectField.getFieldValueByName(fieldName, review);
						if (o1 != null && !"".equals(o1)) {
							map.put(fieldName,o1.toString().trim());
						}
					}
					else if("transientStatus".equals(fieldName)){
						Object o1 = ReflectField.getFieldValueByName(fieldName, review);
						if (o1 != null && !"".equals(o1)) {
							byte value = -1;
							if(o1.toString().trim().equals("-1")){
								value = -1;
							}else if(o1.toString().trim().equals("0")){
								value = 0;
							}else if(o1.toString().trim().equals("1")){
								value = 1;
							}
							map.put("status",value);
							map.put("transientStatus",o1.toString().trim());
						}
					}
				}
				// 为属性附加sql条件
				if (map != null) {
					for (String fieldName : map.keySet()) {
						Path<String> nick = null;
						Path<Date> nickDate = null;
						Path<String> nick1 = null;
						Path<String> nick2 = null;
						Path<Byte> nick3 = null;
						if(!"transientStatus".equals(fieldName)){
							if ("updatedAt".equals(fieldName)) {
								nickDate = root.get("updatedAt");
							}else if("realName".equals(fieldName)){
								nick = root.get("account").get(fieldName);
								
							}else if("name".equals(fieldName)){
								nick1 = root.get("station").get(fieldName);
							}else if("status".equals(fieldName)){
								nick3 = root.get("status");
							}else{
								nick2 =root.get(fieldName);
							}
							Object str = map.get(fieldName);
							if (fieldName.equals("name")) {
								if (StringUtils.isNotEmpty((String) (str))) {
									predicate.add(cb.like(nick1, "%" + str + "%"));
								}
							} else if (fieldName.equals("realName")) {
								if (StringUtils.isNotEmpty((String) (str))) {
									predicate.add(cb.like(nick, "%" + str + "%"));
								}
							} else if (fieldName.equals("differentiate")) {
								
								if (StringUtils.isNotEmpty((String) (str))) {
									predicate.add(cb.like(nick2, "%" + str + "%"));
								}
							}if (fieldName.equals("updatedAt")) {
								List<Date> date = (List<Date>) str;
								predicate.add(cb.between(nickDate, date.get(0), date.get(1)));
							}
							if (fieldName.equals("status")) {
								Object va = map.get("transientStatus");
								if (StringUtils.isNotEmpty((String) (va))) {
									predicate.add(cb.equal(nick3,str));
								}
							}
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
		return reviewRepository.findAll(spec, pageable);
	}
}
