package com.iycharge.server.domain.service.impl;

import com.iycharge.server.domain.common.utils.ReflectField;
import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.order.Order;
import com.iycharge.server.domain.entity.order.OrderStatus;
import com.iycharge.server.domain.entity.order.RechargeRecord;
import com.iycharge.server.domain.repository.RechargeRecordRepository;
import com.iycharge.server.domain.service.RechargeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Service
public class RechargeRecordServiceImpl implements RechargeRecordService {

    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private RechargeRecordRepository rechargeRecordRepository;


    @Override
    public Page<RechargeRecord> findAll(Pageable pageable) {
        return rechargeRecordRepository.findAll(pageable);
    }

    @Override
    public RechargeRecord findById(long id) {
        return rechargeRecordRepository.findOne(id);
    }
    
    @Override
    public RechargeRecord findByTradeNo(String tradeNo) {
        // TODO Auto-generated method stub
        return rechargeRecordRepository.findByTradeNo(tradeNo);
    }

    @Override
    public Page<RechargeRecord> findByAccount(Account account, Pageable pageable) {
        return rechargeRecordRepository.findByAccount(account, pageable);
    }

    @Override
    public Page<RechargeRecord> findByAccountAndStatus(Account account, OrderStatus status, Pageable pageable) {
        return rechargeRecordRepository.findByAccountAndStatus(account, status, pageable);
    }

    @Override
    public RechargeRecord save(RechargeRecord rechargeRecord) {
        return rechargeRecordRepository.saveAndFlush(rechargeRecord);
    }

    @Override
    public RechargeRecord payWithAlipay(RechargeRecord rechargeRecord, Map<String, String> params) {

        rechargeRecord.setStatus(OrderStatus.PAID);
        rechargeRecord.setPaidFrom("ALIPAY");
        rechargeRecord.setAlipayTradeNumber(params.get("trade_no"));
        rechargeRecord.setAlipayBuyerId(params.get("buyer_id"));
        rechargeRecord.setAlipayBuyerEmail(params.get("buyer_email"));

        try {
            rechargeRecord.setAlipayPaymentTime(simpleDateFormat.parse(params.get("gmt_payment")));
            rechargeRecord.setUpdatedAt(rechargeRecord.getAlipayPaymentTime());
        }
        catch (Exception e) {
            rechargeRecord.setAlipayPaymentTime(new Date());
        }
      
        return rechargeRecordRepository.saveAndFlush(rechargeRecord);
    }


	@Override
	public Page<RechargeRecord> searchTime(Long num, Long id,Pageable pageable) {
		return rechargeRecordRepository.searchTime(num, id,pageable);
	}

	@Override
	public Page<RechargeRecord> findAll(final String[] fields ,final RechargeRecord rechargeRecord, Pageable pageable) {
		Specification<RechargeRecord> spec = new Specification<RechargeRecord>(){

			@Override
			public Predicate toPredicate(Root<RechargeRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate plists[] = null;
				List<Predicate> plist = new ArrayList<Predicate>();
				 Map<String, Object> map = new HashMap<String, Object>();
					for (String fieldName : fields) {
						if("updatedAt".equals(fieldName)){
							if(rechargeRecord.getStartAt()!=null&&rechargeRecord.getEndAt()!=null){
								
								List<Date> listDate = new ArrayList<Date>();
								  Calendar   calendar   =   new   GregorianCalendar(); 
							     calendar.setTime(rechargeRecord.getEndAt()); 
							     calendar.add(Calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动 
								listDate.add(rechargeRecord.getStartAt());
								listDate.add(calendar.getTime());
								map.put(fieldName, listDate);
							}
						}else{
							Object o = ReflectField.getFieldValueByName(fieldName, rechargeRecord);						
							if (o != null && !"".equals(o)){
									map.put(fieldName, o);
								}	
						}						
				}

				// 为属性附加sql条件
				if (map != null) {
					for (String fieldName : map.keySet()) {
						Path<String> nick = null;
						Path<Date> nickdate = null;
						if("updatedAt".equals(fieldName)){
							nickdate = root.get(fieldName);
						}else{
							nick = root.get(fieldName);
						}
						Object str = map.get(fieldName);
					
						if(fieldName.equals("updatedAt")){
							List<Date> listdate = (List<Date>)str;
							plist.add(cb.between(nickdate, listdate.get(0), listdate.get(1)));
						}else if (fieldName.equals("account")) {
							if (rechargeRecord.getAccount().getId()!=null) {
								nick = root.get(fieldName).get("id");
								plist.add(cb.equal(nick, rechargeRecord.getAccount().getId()));
						}
					} 
					}
				
				}
				if (plist.size() > 0) {
					plists = new Predicate[plist.size()];
					//把集合转换成数组
					Predicate predicate = cb.and(plist.toArray(plists));
					if(predicate!=null){
						query.where(predicate);
					}
					
				}
				return null;
			}
		};
		return rechargeRecordRepository.findAll(spec, pageable);
	}

	@Override
	public List<RechargeRecord> findByPeriod(Date startTime, Date endTime) {
		 if(startTime == null || endTime == null) {
	            return null;
	        }
	    return rechargeRecordRepository.findByPeriod(startTime, endTime, OrderStatus.PAID);
	}
}
