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
import com.iycharge.server.domain.entity.feedback.FeedBack;
import com.iycharge.server.domain.entity.feedback.FeedBackAuditLog;
import com.iycharge.server.domain.entity.feedback.FeedBackStatus;
import com.iycharge.server.domain.repository.FeedBackRepository;
import com.iycharge.server.domain.service.FeedBackService;
@Service
public class FeedBackServiceImpl implements FeedBackService{
    @Autowired
    private FeedBackRepository feedBackRepository;
	@Override
	public Page<FeedBack> findAll(Pageable pageable) {
		
		return feedBackRepository.findAll(pageable);
	}
	public Page<FeedBack> findAllSearch(final String[] fields, final FeedBack feedBack, Pageable pageable) {
		Specification<FeedBack> spec = new Specification<FeedBack>() {
			@Override
			public Predicate toPredicate(Root<FeedBack> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate ps = null;
				Predicate plist[] = null;
				List<Predicate> predicate = new ArrayList<Predicate>();
				Map<String, Object> map = new HashMap<String, Object>();
				for (String fieldName : fields) {
					if ("updatedAt".equals(fieldName)) {
						if (feedBack.getStartAt()!= null && feedBack.getUpdatedAt() != null) {
							List<Date> date = new ArrayList<Date>();
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(feedBack.getUpdatedAt());
							calendar.add(calendar.DATE, 1);
							date.add(feedBack.getStartAt());
							date.add(calendar.getTime());
							map.put(fieldName, date);
						}

				}else {
						Object o = ReflectField.getFieldValueByName(fieldName, feedBack);
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
						if ("updatedAt".equals(fieldName)) {
							nickDate = root.get("updatedAt");
						}else{
							nick=root.get(fieldName);
						}
						Object str = map.get(fieldName);
						if (fieldName.equals("accountPhone")) {
							if (StringUtils.isNotEmpty((String) (str))) {
								predicate.add(cb.like(nick, "%" + str + "%"));
							}
						}
						if (fieldName.equals("accountName")) {
							if (StringUtils.isNotEmpty((String) (str))) {
								predicate.add(cb.like(nick, "%" + str + "%"));
							}
						}
						if (fieldName.equals("channel")) {
							if (feedBack.getChannel()!= null) {
							    nick = root.get(fieldName);
								predicate.add(cb.equal(nick,feedBack.getChannel()));							
							}
						} 
						if (fieldName.equals("status")) {
							if (feedBack.getStatus()!= null) {
							    nick = root.get(fieldName);
								predicate.add(cb.equal(nick,feedBack.getStatus()));							
							}
						} 
						if (fieldName.equals("category")) {
							if (feedBack.getCategory()!= null) {
							    nick = root.get(fieldName);
								predicate.add(cb.equal(nick,feedBack.getCategory()));							
							}
						}
						if (fieldName.equals("updatedAt")) {
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
		return feedBackRepository.findAll(spec,pageable);
	}
	@Override
	public FeedBack findById(Long id) {
		return feedBackRepository.findById(id);
	}
	@Override
	public FeedBack save(FeedBack feedBack) {
		// TODO Auto-generated method stub
		return feedBackRepository.save(feedBack);
	}
	@Override
	public Page<FeedBack> findByAccountPhone(String phone, Pageable pageable) {
		
		return feedBackRepository.findByAccountPhone(phone,pageable);
	}
	@Override
	public List<FeedBack> findByStatus() {
		return feedBackRepository.findByStatus(FeedBackStatus.SUSPENDING);
	}

}
