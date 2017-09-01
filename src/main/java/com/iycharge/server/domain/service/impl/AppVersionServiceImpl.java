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
import com.iycharge.server.domain.common.utils.ReflectField;
import com.iycharge.server.domain.entity.admin.AppType;
import com.iycharge.server.domain.entity.admin.AppVersion;
import com.iycharge.server.domain.entity.admin.AppVersionQueryParam;
import com.iycharge.server.domain.repository.AppVersionRepository;
import com.iycharge.server.domain.service.AppVersionService;

/**
 *
 * @author bwang
 */
@Service
public class AppVersionServiceImpl implements AppVersionService {

	@Autowired
	AppVersionRepository appVersionRepository;

	@Override
	public Page<AppVersion> findAll(Pageable pageable) {

		return appVersionRepository.findAll(pageable);
	}

	/**
	 * 多条件查询
	 */
	@Override
	public Page<AppVersion> findAllSearch(AppVersionQueryParam queryParam, final AppVersion appVersion, Pageable pageable) {
		@SuppressWarnings("unchecked")
		Page<AppVersion> search = appVersionRepository.findAll(new Specification<AppVersion>() {
			@Override
			public Predicate toPredicate(Root<AppVersion> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				List<Expression<Boolean>> expressions = predicate.getExpressions();
				if(queryParam.getStartTime()!=null){
					if(queryParam.getEndTime()==null){
						queryParam.setEndTime(new Date());
					}
					expressions.add(cb.between(root.<Date>get("updatedAt"), queryParam.getStartTime(), queryParam.getEndTime()));
				}
				if(queryParam.getTitle()!=null&&!queryParam.getTitle().trim().equals("")){
					expressions.add(cb.like(root.<String>get("title"),"%"+queryParam.getTitle()+"%"));
				}
				if(queryParam.getVersion()!=null&&!queryParam.getVersion().trim().equals("")){
					expressions.add(cb.like(root.<String>get("version"),"%"+ queryParam.getVersion() +"%"));
				}
				return predicate;
			}
		},pageable);
		return search;
	}

	@Override
	public AppVersion findById(Long id) {

		return appVersionRepository.findOne(id);
	}

	@Override
	public AppVersion save(AppVersion appVersion) {
		
		return appVersionRepository.saveAndFlush(appVersion);
	}

	@Override
	public List<AppVersion> findByAppType(AppType appType) {
		return appVersionRepository.findByAppType(appType);
	}
}
