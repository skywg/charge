package com.iycharge.server.domain.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.iycharge.server.domain.entity.admin.LogQueryParam;
import com.iycharge.server.domain.entity.admin.ManagerLog;
import com.iycharge.server.domain.repository.ManagerLogRepository;
import com.iycharge.server.domain.service.ManagerLogService;

/**
 *
 * @author bwang
 */
@Service
public class ManagerLogServiceImpl implements ManagerLogService {
    
    @Resource
    private ManagerLogRepository managerLogRepository;
    
    @Override
    public Page<ManagerLog> findAll(Pageable pageable) {
        return managerLogRepository.findAll(pageable);
    }
    
    @Transactional(readOnly=true)
    @Override
    public Page<ManagerLog> find(final LogQueryParam queryParam, Pageable pageable) {
        Page<ManagerLog> result = managerLogRepository.findAll(new Specification<ManagerLog>() {          
            @Override
            public Predicate toPredicate(Root<ManagerLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                
                List<Expression<Boolean>> expressions = predicate.getExpressions();
                if(StringUtils.hasText(queryParam.getLoginName())) {
                    //用户登录
                    expressions.add(cb.like(root.<String>get("loginName"), "%" + queryParam.getLoginName() + "%")); 
                }
                if(queryParam.getLogModule() != null&&StringUtils.hasText(queryParam.getLogModule())) {
                    //日志 所属模块
                    expressions.add(cb.equal(root.<String>get("logModule"), queryParam.getLogModule()));
                }
                if(queryParam.getLogType() != null&&StringUtils.hasText(queryParam.getLogType())) {
                    //日志操作类型
                    expressions.add(cb.equal(root.<String>get("logType"), queryParam.getLogType()));
                }
                if(queryParam.getStartTime() != null && queryParam.getEndTime() != null) {
                    expressions.add(cb.between(root.<Date>get("logTime"), queryParam.getStartTime(), queryParam.getEndTime()));
                }
                return predicate;
            }
        }, pageable); 
        
        return result;
    }
    
    @Transactional
    @Override
    public boolean save(ManagerLog managerLog) {
        managerLogRepository.saveAndFlush(managerLog);
        return true;
    }

}
