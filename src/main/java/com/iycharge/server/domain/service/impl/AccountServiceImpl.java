package com.iycharge.server.domain.service.impl;

import com.iycharge.server.domain.common.utils.ReflectField;
import com.iycharge.server.domain.entity.Authorization;
import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.utils.EmailAddress;
import com.iycharge.server.domain.repository.AccountRepository;
import com.iycharge.server.domain.repository.AuthorizationRepository;
import com.iycharge.server.domain.service.AccountService;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AuthorizationRepository authorizationRepository;

    @Override
    public Page<Account> findAll(Pageable pageable) {
        return accountRepository.findByDelstatus("normal",pageable);
    }

    @Override
    public Page<Account> findAllSearch(final String []fields, final Account account,Pageable pageable) {
    	Specification<Account> spec = new Specification<Account>(
    			) {		
    		@Override
		public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    			Predicate ps = null;
    			Predicate plist[] =null;
    			Map<String,Object> map = new HashMap<String,Object>();
    			//封装获取不为空的值与属性
    			for(String fieldName:fields){
					Object o = ReflectField.getFieldValueByName(fieldName, account);
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
    					String str = (String)map.get(fieldName);
//    					if(fieldName.equals("realName")){
    						plist[i] = cb.like(nick, "%"+str+"%");
//						}else{
//							plist[i] = cb.equal(nick, str); 
//						}
    					i++;
        				//System.out.println("key= "+ key + " and value= " + map.get(key));
        			}
    			}
    			if(plist.length>0){
    				ps = cb.and(plist);
    			}
				if(ps!=null){
					query.where(ps); //这里可以设置任意条查询条件
				}
			    /** 
			         * 连接查询条件, 不定参数，可以连接0..N个查询条件 
			         */  
//			    query.where(cb.equal(nameType, type), cb.equal(nickName, name),cb.like(nickProvince, "%"+province+"%")); //这里可以设置任意条查询条件
				return null;
				}
		};
		return accountRepository.findAll(spec, pageable);
    	//    	if(StringUtils.isEmpty(realName)&&StringUtils.isEmpty(phone)){
//    		return accountRepository.findAll(pageable);
//    	}else{
//    		return accountRepository.findByRealNameAndPhone(realName,phone,pageable);
//    	}
    		
    }
    
    @Override
    public Account findById(long id) {
        return accountRepository.findOne(id);
    }

    @Override
    public Account findByEmail(EmailAddress email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public Account findByPhone(String phone) {
        return accountRepository.findByPhone(phone);
    }

    @Override
    public Account findByNickname(String nickname) {
        return accountRepository.findByNickname(nickname);
    }

    @Override
    public Page<Account> findByNicknameContaining(String nickname, Pageable pageable) {
        return accountRepository.findByNicknameContaining(nickname, pageable);
    }

    @Override
    public Page<Account> findByNicknameContainingOrPhoneContaining(String nickname, String phone, Pageable pageable) {
        return accountRepository.findByNicknameContainingOrPhoneContaining(nickname, phone, pageable);
    }

    @Override
    public Page<Account> findByBlockedTrue(Pageable pageable) {
        return accountRepository.findByBlockedTrue(pageable);
    }

    @Override
    public Authorization findAuthorization(Authorization.Provider provider, String uid) {
        return authorizationRepository.findByProviderAndUid(provider, uid);
    }

    @Override
    public Authorization addAuthorization(final Account account, Authorization authorization) {
        authorization.setAccount(account);
        return authorizationRepository.saveAndFlush(authorization);

    }

    @Override
    public Authorization updateAuthorization(final Account account, Authorization authorization) {
        authorization.setAccount(account);
        return authorizationRepository.saveAndFlush(authorization);
    }

    @Override
    @Transactional
    public Account save(Account account) {
        return accountRepository.saveAndFlush(account);
    }
    
    @Override
    public void del(Long id) {
       accountRepository.delete(id);
    }


	@Override
	public List<Account> findAll() {
		// TODO Auto-generated method stub
		return accountRepository.findAll();
	}

	@Override
	public List<Account> findByAccountType(String accountType) {
		// TODO Auto-generated method stub
		return accountRepository.findByAccountType(accountType);
	}

	

	@Override
	public List<Account> findByConditionMap(final Map<String, String> map, final Account account) {
		Specification<Account> spec = new Specification<Account>() {

			@Override
			public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    			Predicate ps = null;
    			Predicate plist[] =null;
    			if(map!=null){
    				int p = 0 ;
    				for(String key : map.keySet()){
    					if(map.get(key)!=null){
    						p++;
    					}
    				}
    				plist = new Predicate[p];
    				int i=0;
    				for(String key : map.keySet()){
    					Path<String> nick = null; 
    					Path<Date> nickDate=null;
    					if(map.get(key)!=null){
    						if(key.equals("age")){
        						nickDate= root.get("birth");
        						String ageBetween[] = map.get("age").split("-");
        						plist[i] = cb.between(nickDate, getYear(Integer.parseInt(ageBetween[1])), getYear(Integer.parseInt(ageBetween[0])));
        					}else if(key.equals("gender")){
        						nick = root.get(key);
        						plist[i] = cb.equal(nick, account.getGender());
        					}else{
        						nick = root.get(key);
        						plist[i] = cb.equal(nick, map.get(key));
        					}
        					i++;
    					}
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
		return accountRepository.findAll(spec);
	}

    
    @Override
    public int[] statisticByAccountType(Date startTime, Date endTime) {
        int[] result = {0, 0};
        List<Object[]> items = accountRepository.statisticByAccountType(startTime, endTime);
        if(items != null && items.size() >0) {
            for(Object[] item : items) {
            	if(item[0]!=null){
            		String accountType = item[0].toString();
                	if(accountType.equals("COMPANY")) {
                        result[0] = ((Long)item[1]).intValue();
                    } else {
                        result[1] = ((Long)item[1]).intValue();
                    }
                }
            }
        }
        return result;
    }

    @Override
    public int[] statisticByAccountType(Date time) {
        int[] result = {0, 0};
        List<Object[]> items = accountRepository.statisticByAccountType(time);
        if(items != null && items.size() >0) {
            for(Object[] item : items) {
            	if(item[0]!=null){
	                String accountType = item[0].toString();
	                if(accountType.equals("COMPANY")) {
	                    result[0] = ((Long)item[1]).intValue();
	                } else {
	                    result[1] = ((Long)item[1]).intValue();
	                }
            	}
            }
        }
        return result;
    }
    public List<Account> findByCondition(Map<String,String> field , Account account){
    	Specification<Account> spec = new Specification<Account>() {
			@Override
			public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				List<Expression<Boolean>> expressions = predicate.getExpressions();
				if(field.get("realName")!=null&&!field.get("realName").trim().equals("")){
					expressions.add(cb.like(root.<String>get("realName"), "%"+field.get("realName")+"%"));
				}
				if(field.get("phone")!=null&&!field.get("phone").trim().equals("")){
					expressions.add(cb.like(root.<String>get("phone"), "%"+field.get("phone")+"%"));
				}
				if(field.get("inputAccountType")!=null&&!field.get("inputAccountType").trim().equals("")){
					expressions.add(cb.equal(root.<String>get("accountType"), field.get("inputAccountType")));
				}
				query.orderBy(cb.desc(root.<String>get("id")));
				return predicate;
			}
		};
		return accountRepository.findAll(spec);
    }
    
    private Date getYear(int age){
    	Date todayDate=new Date();    
		long beforeTime=(todayDate.getTime()/(1000*age))-60*60*24*365;    
		todayDate.setTime(beforeTime*(1000*age)); 
		return todayDate;
    }
    
    @Transactional(readOnly=false)
	@Override
	public void delete(Authorization authorization) {
		authorizationRepository.deleteAuthorizationById(authorization.getId());	
	}

	@Override
	public Account findByEmail(String email) {
		// TODO Auto-generated method stub
		return accountRepository.findByEmail(email);
	}
}
