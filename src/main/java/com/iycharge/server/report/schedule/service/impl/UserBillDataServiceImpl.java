package com.iycharge.server.report.schedule.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.event.EventCode;

import com.iycharge.server.report.entity.UserBillData;
import com.iycharge.server.report.schedule.repository.UserBillDataRepository;
import com.iycharge.server.report.schedule.service.UserBillDataService;

/**
 *
 * @author bwang
 */
@Service
@Transactional(readOnly=true)
public class UserBillDataServiceImpl implements UserBillDataService {
    
    @Resource
    private UserBillDataRepository userBillDataRepository;
    
    @PersistenceContext
    private EntityManager em;
    
     @Transactional(readOnly=true)
	 @Override
	 public Page<UserBillData> find(final Map<String, String> queryParam, Pageable pageable) {
	        @SuppressWarnings("unchecked")
			Page<UserBillData> result = userBillDataRepository.findAll(new Specification<EventCode>() {          
	            @Override
	            public Predicate toPredicate(Root<EventCode> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                Predicate predicate = cb.conjunction();
	                List<Expression<Boolean>> expressions = predicate.getExpressions();
	                if(queryParam.get("start") != null &&  !queryParam.get("start").trim().equals("") && queryParam.get("end") != null&&!queryParam.get("end").trim().equals("")) {
	                	String start1  = queryParam.get("start");
	                	String end1  = queryParam.get("end");
	                	String datetype =queryParam.get("datetype");
	                	Date start=null;
	                	Date end=null;
	            		if ("year".equals(datetype)) {
	            			SimpleDateFormat sf  =new SimpleDateFormat("yyyy");
							try {
								start = sf.parse(start1);
								end =sf.parse(end1);
							} catch (ParseException e) {
								e.printStackTrace();
							}
	            		}else if ("month".equals(datetype)) {
	            			SimpleDateFormat sf  =new SimpleDateFormat("yyyy-MM");
		                	
							try {
								start = sf.parse(start1);
								end =sf.parse(end1);
							} catch (ParseException e) {
								e.printStackTrace();
							}
	            			
	            		}else if ("day".equals(datetype)) {
	            			SimpleDateFormat sf  =new SimpleDateFormat("yyyy-MM-dd");
		                	
							try {
								start = sf.parse(start1);
								end =sf.parse(end1);
							} catch (ParseException e) {
								e.printStackTrace();
							}
	            		}
	                	
	                	
	                    expressions.add(cb.between(root.<Date>get("month"), start,end));
	                }
	               
	                if (queryParam.get("accounts")!=null && !queryParam.get("accounts").trim().equals("")) {
	                	 String[] accounts =queryParam.get("accounts").split(",");
	                	 Path<Object> nick = root.<Object>get("account").get("realName");
	                	 if(accounts.length > 1) {
	                	     expressions.add(nick.in(accounts));
	                	 } else {
	                	     expressions.add(cb.equal(nick,accounts[0])); 
	                	 }
	                	 
					}
	                return predicate;
	            }

	        }, pageable); 
	        
	        return result;
	    }

    
    @Transactional(readOnly=false)
    @Override
    public UserBillData save(UserBillData userBillData) {
        // TODO Auto-generated method stub
        String sql = 
                "INSERT INTO r_user_bill_data (account_id, total_electricity, total_money, start_balance, total_recharge, end_balance, month, created_at, updated_at) " +
                "values (?,?,?,?,?,?,?,now(),now()) ON DUPLICATE KEY UPDATE " +
                "total_electricity=values(total_electricity), total_money=values(total_money), total_recharge=values(total_recharge), end_balance=values(end_balance), updated_at=values(updated_at)";
        Query dataQuery = em.createNativeQuery(sql);
        if(userBillData != null) {
            int index = 1;
            dataQuery.setParameter(index++, userBillData.getAccount().getId());
            dataQuery.setParameter(index++, userBillData.getTotalElectricity().doubleValue());
            dataQuery.setParameter(index++, userBillData.getTotalMoney().doubleValue());
            dataQuery.setParameter(index++, userBillData.getStartBalance().doubleValue());
            dataQuery.setParameter(index++, userBillData.getTotalRecharge().doubleValue());
            dataQuery.setParameter(index++, userBillData.getEndBalance().doubleValue());
            dataQuery.setParameter(index++, userBillData.getMonth(), TemporalType.DATE);
            
            dataQuery.executeUpdate();
        }
        
        return null;

    }
    public List<UserBillData> findByCondition(final Map<String,String> queryParam , UserBillData userBillData){
    	Specification<UserBillData> spec = new Specification<UserBillData>() {
			@Override
			public Predicate toPredicate(Root<UserBillData> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				List<Expression<Boolean>> expressions = predicate.getExpressions();
				if(queryParam.get("start") != null &&  !queryParam.get("start").trim().equals("") && queryParam.get("end") != null&&!queryParam.get("end").trim().equals("")) {
                	String start1  = queryParam.get("start");
                	String end1  = queryParam.get("end");
                	String datetype =queryParam.get("datetype");
                	Date start=null;
                	Date end=null;
            		if ("year".equals(datetype)) {
            			SimpleDateFormat sf  =new SimpleDateFormat("yyyy");
						try {
							start = sf.parse(start1);
							end =sf.parse(end1);
						} catch (ParseException e) {
							e.printStackTrace();
						}
            		}else if ("month".equals(datetype)) {
            			SimpleDateFormat sf  =new SimpleDateFormat("yyyy-MM");
	                	
						try {
							start = sf.parse(start1);
							end =sf.parse(end1);
						} catch (ParseException e) {
							e.printStackTrace();
						}
            			
            		}else if ("day".equals(datetype)) {
            			SimpleDateFormat sf  =new SimpleDateFormat("yyyy-MM-dd");
	                	
						try {
							start = sf.parse(start1);
							end =sf.parse(end1);
						} catch (ParseException e) {
							e.printStackTrace();
						}
            		}
                	
                	
                    expressions.add(cb.between(root.<Date>get("month"), start,end));
                }
               
                if (queryParam.get("accounts")!=null && !queryParam.get("accounts").trim().equals("")) {
                	 String[] accounts =queryParam.get("accounts").split(",");
                	 Path<String> nick = root.<String>get("account").get("realName");
                	 if(accounts.length > 1) {
                	     expressions.add(nick.in(accounts));
                	 } else {
                	     expressions.add(cb.equal(nick,accounts[0])); 
                	 }
				}
				query.orderBy(cb.desc(root.<String>get("id")));
				return predicate;
			}
		};
		return userBillDataRepository.findAll(spec);
    }
	@Override
	public Page<UserBillData> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return userBillDataRepository.findAll(pageable);
	}


	@Override
	public UserBillData findById(Long id) {
		// TODO Auto-generated method stub
		return userBillDataRepository.findById(id);
	}


	


  


	@Override
	public List<UserBillData> findByAccountOrderByIdDesc(Account account) {
		// TODO Auto-generated method stub
		return userBillDataRepository.findByAccountOrderByIdDesc(account);
	}


	@Override
	public UserBillData findByAccountAndMonth(Account account, Date month) {
		// TODO Auto-generated method stub
		return userBillDataRepository.findByAccountAndMonth(account, month);
	}    

}
