package com.iycharge.server.domain.service.impl;

import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.api.util.IDCreator;
import com.iycharge.server.domain.common.utils.ReflectField;
import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.account.Card;
import com.iycharge.server.domain.entity.charger.Charger;
import com.iycharge.server.domain.entity.order.Order;
import com.iycharge.server.domain.entity.order.OrderItem;
import com.iycharge.server.domain.entity.order.OrderStatus;
import com.iycharge.server.domain.entity.order.PaidFrom;
import com.iycharge.server.domain.entity.utils.Utils;
import com.iycharge.server.domain.repository.CardRepository;
import com.iycharge.server.domain.repository.OrderRepository;
import com.iycharge.server.domain.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CardRepository cardRepository;
	
	@Override
	public Page<Order> findAll(Pageable pageable) {
		return orderRepository.findAll(pageable);
	}

	@Override
	public Page<Order> findById(long id,Pageable pageable) {
		return orderRepository.findById(id, pageable);
	}

	
	@Override
	public Order findById(long id) {
		return orderRepository.findById(id);
	}
	@Override
	public Page<Order> findByAccount(Account account, Pageable pageable) {
		return orderRepository.findByAccount(account, pageable);
	}

	@Override
	public Page<Order> findByCharger(Charger charger, Pageable pageable) {
		return orderRepository.findByCharger(charger, pageable);
	}
	@Override
	public List<Order> findByCharger(Charger charger) {
		return orderRepository.findByCharger(charger);
	}
	@Override
	public Order save(Order order) {
		return orderRepository.saveAndFlush(order);
	}

	@Override
	public Order payWithAlipay(Order order, Map<String, String> params) {

		order.setStatus(OrderStatus.PAID);
		/*
		 * order.setPaidFrom(PaidFrom.OTHER);
		 * order.setAlipayTradeNumber(params.get("trade_no"));
		 * order.setAlipayBuyerId(params.get("buyer_id"));
		 * order.setAlipayBuyerEmail(params.get("buyer_email"));
		 * 
		 * /* order.setAlipayTradeNumber(params.get("trade_no"));
		 * order.setAlipayBuyerId(params.get("buyer_id"));
		 * order.setAlipayBuyerEmail(params.get("buyer_email"));
		 * 
		 * try { order.setAlipayPaymentTime(simpleDateFormat.parse(params.get(
		 * "gmt_payment"))); } catch (Exception e) {
		 * order.setAlipayPaymentTime(new Date()); }
		 */
		return orderRepository.saveAndFlush(order);
	}

	@Override
	public Page<Order> findAllSearch(final String[] fields, final Order order, Pageable pageable) {
		Specification<Order> spec = new Specification<Order>() {

			@Override
			public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate plists[] = null;
				List<Predicate> plist = new ArrayList<Predicate>();
				 Map<String, Object> map = new HashMap<String, Object>();
					for (String fieldName : fields) {
						if("createdAt".equals(fieldName)){
							if(order.getStartAt()!=null&&order.getEndAt()!=null){
								
								List<Date> listDate = new ArrayList<Date>();
								  Calendar   calendar   =   new   GregorianCalendar(); 
							     calendar.setTime(order.getEndAt()); 
							     calendar.add(Calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动 
								listDate.add(order.getStartAt());
								listDate.add(calendar.getTime());
								map.put(fieldName, listDate);
							}
						}/*else if("stationName".equals(fieldName)){
							if(order.getCharger()!=null&&order.getCharger().getStation()!=null){
								map.put(fieldName, order.getCharger().getStation().getName());
							}
						}*/else{
							Object o = ReflectField.getFieldValueByName(fieldName, order);						
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
						if("createdAt".equals(fieldName)){
							nickdate = root.get(fieldName);
						}else if("checkboxstationname".equals(fieldName)){
							/*nick = root.get("charger").get("station").get("name");*/
							if(order.getCheckboxstationname()!=null){
								String stations[] = order.getCheckboxstationname().split(",");
								nick = root.get("charger").get("station").get("name");
								plist.add(nick.in(Arrays.asList(stations)));
								break;
							}
						}else if("cardNoOrPhoneNum".equals(fieldName)){
							nick = null;
						}else{
							nick = root.get(fieldName);
						}
						Object str = map.get(fieldName);
						if (fieldName.equals("status")) {
								OrderStatus os = (OrderStatus) str;
								plist.add(cb.equal(nick, os));
						} else if (fieldName.equals("account")) {
							if (StringUtils.hasText(order.getAccount().getRealName())) {
									nick = root.get(fieldName).get("realName");
									plist.add(cb.like(nick, "%"+order.getAccount().getRealName()+"%"));
							}
						} else if (fieldName.equals("charger")){
							if (StringUtils.hasText(order.getCharger().getName())) {
								nick = root.get(fieldName).get("name");
								plist.add(cb.like(nick,"%"+order.getCharger().getName()+"%"));
							}
						}/*else if(fieldName.equals("stationName")){
							if(StringUtils.hasText(order.getCharger().getStation().getName())){
								nick = root.get("charger").get("station").get("name");
								plist.add(cb.like(nick,"%"+ order.getCharger().getStation().getName() +"%"));
							}
						}*/else if(fieldName.equals("cardNoOrPhoneNum")){
							if(org.apache.commons.lang.StringUtils.isNotBlank(order.getCardNoOrPhoneNum())){
								String cnpn = order.getCardNoOrPhoneNum();
								Path<String> nick1 = root.join("account", JoinType.LEFT).get("phone");
								Path<String> nick2 = root.join("card",JoinType.LEFT).get("cardNo");
								plist.add(cb.or(cb.like(nick2, "%"+cnpn+"%"),cb.like(nick1, "%"+cnpn+"%")));
								//plist.add(cb.or(cb.like(nick1, "%"+cnpn+"%"),cb.like(nick2, "%"+cnpn+"%")));
							}
						}else if (fieldName.equals("orderId")){												
							 plist.add(cb.like(nick,"%"+ str+"%"));	
						}else if(fieldName.equals("createdAt")){
							List<Date> listdate = (List<Date>)str;
							plist.add(cb.between(nickdate, listdate.get(0), listdate.get(1)));
						}else{
							plist.add(cb.like(nick,"%"+str+"%"));
						}
					}
				
				}
				if (plist.size() > 0) {
					plists = new Predicate[plist.size()];
					//把集合转换成数组
					Predicate predicate = cb.and(plist.toArray(plists));
					if(predicate!=null){
						query.distinct(true);
						query.where(predicate);
					}
					
				}
				return null;
			}
		};
		return orderRepository.findAll(spec, pageable);
	}

    @Override
    public Order findByOrderId(String orderId) {      
        return orderRepository.findByOrderId(orderId);
    }


	@Override
	public Page<Order> searchTime(Long num, Long id,Pageable pageable) {
		
		return orderRepository.searchTime(num, id,pageable);
	}

	@Override
	public Page<Order> findAll(final String[] fields, final Order order, Pageable pageable) {
		Specification<Order> spec = new Specification<Order>() {

			@Override
			public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate plists[] = null;
				List<Predicate> plist = new ArrayList<Predicate>();
				 Map<String, Object> map = new HashMap<String, Object>();
					for (String fieldName : fields) {
						if("createdAt".equals(fieldName)){
							if(order.getStartAt()!=null&&order.getEndAt()!=null){
								
								List<Date> listDate = new ArrayList<Date>();
								  Calendar   calendar   =   new   GregorianCalendar(); 
							     calendar.setTime(order.getEndAt()); 
							     calendar.add(Calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动 
								listDate.add(order.getStartAt());
								listDate.add(calendar.getTime());
								map.put(fieldName, listDate);
							}
						}else{
							Object o = ReflectField.getFieldValueByName(fieldName, order);						
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
						if("createdAt".equals(fieldName)){
							nickdate = root.get(fieldName);
						}else{
							nick = root.get(fieldName);
						}
						Object str = map.get(fieldName);
						if (fieldName.equals("account")) {
							if (order.getAccount().getId()!=null) {
								
									nick = root.get(fieldName).get("id");
									plist.add(cb.equal(nick, order.getAccount().getId()));
								
							}
						} else if(fieldName.equals("createdAt")){
							List<Date> listdate = (List<Date>)str;
							plist.add(cb.between(nickdate, listdate.get(0), listdate.get(1)));
						}
					}
				
				}
				if (plist.size() > 0) {
					plists = new Predicate[plist.size()];
					//把集合转换成数组
					Predicate predicate = cb.and(plist.toArray(plists));
					if(predicate!=null){
						query.distinct(true);
						query.where(predicate);
					}
					
				}
				return null;
			}
		};
		return orderRepository.findAll(spec, pageable);
	}

	@Override
	public Page<Order> findAll(final Order order, Pageable pageable) {
		Specification<Order> spec = new Specification<Order>() {

			@Override
			public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate plists[] = null;
				List<Predicate> plist = new ArrayList<Predicate>();
				if(order.getStartAt()!=null&&order.getEndAt()!=null){
					 Calendar   calendar   =   new   GregorianCalendar(); 
				     calendar.setTime(order.getEndAt()); 
				     calendar.add(Calendar.DATE,1); 
					plist.add(cb.between(root.<Date>get("createdAt"), order.getStartAt(),calendar.getTime()));
				}
				if(order.getCharger()!=null){
					plist.add(cb.equal(root.<Charger>get("charger").<Long>get("id"),order.getCharger().getId()));
				}
				if (plist.size() > 0) {
					plists = new Predicate[plist.size()];
					//把集合转换成数组
					Predicate predicate = cb.and(plist.toArray(plists));
					if(predicate!=null){
						query.distinct(true);
						query.where(predicate);
					}
					
				}
				return null;
			}
		};
		return orderRepository.findAll(spec, pageable);
	}

    @Override
    @Transactional
    public void updateOrder(Order order) {
        // TODO Auto-generated method stub
        if(order.getCard() != null) {
            Card card = cardRepository.findByCardNo(order.getCard().getCardNo()); 
            //卡支付
            order.setOrderId(IDCreator.generator(IDCreator.BUS_CHARGING)); 
            order.setStatus(OrderStatus.PAID);
            order.setPaidFrom(PaidFrom.CARD.name());
            order.setCard(card);
            order.setCharger(EntityUtil.getCharger(order.getCharger().getCode()));    
            order.setAccount(null);
            order.setBalance(card.getMoney().subtract(order.getMoney()));
            orderRepository.save(order);    
            
            card.setMoney(card.getMoney().subtract(order.getMoney()));
            cardRepository.save(card);
        } else {
            //app支付
            Order dbOrder = orderRepository.findByOrderId(order.getOrderId());
            if(dbOrder.getStatus() == OrderStatus.PAID){
                return;
            }
            dbOrder.setIfName(order.getIfName());
            dbOrder.setIfType(order.getIfType());
            dbOrder.setStartSoc(order.getStartSoc());
            dbOrder.setEndSoc(order.getEndSoc());
            dbOrder.setStartAt(order.getStartAt());
            dbOrder.setEndAt(order.getEndAt());
            dbOrder.setChargeTime(order.getChargeTime());
            dbOrder.setDegree(order.getDegree());
            dbOrder.setMoney(order.getMoney());
            dbOrder.setStatus(OrderStatus.PAID);
            dbOrder.setPaidFrom(PaidFrom.WALLET.name());
            
            List<OrderItem> orderItems = order.getOrderItems();
            for(OrderItem orderItem : orderItems){
                orderItem.setOrder(dbOrder);
            }
            dbOrder.setOrderItems(orderItems);
            
            Account account = dbOrder.getAccount();
            account.setMoney(account.getMoney().subtract(order.getMoney()));
            dbOrder.setBalance(account.getMoney());
            orderRepository.save(dbOrder);
        }
    }

	@Override
	public Page<Order> searchOrderByCardNoAndDays(String cardNo, Integer days, Pageable pageable) {
		return orderRepository.searchOrderByCardNoAndDays(cardNo,days,pageable);
	}

	@Override
	public Page<Order> findByCard(Card card,Pageable pageable) {
		return orderRepository.findByCard(card,pageable);
	}

    @Override
    public List<Order> findByPeriod(Date startTime, Date endTime) {
        if(startTime == null || endTime == null) {
            return null;
        }
        return orderRepository.findByPeriod(startTime, endTime, OrderStatus.PAID);
    }

	@Override
	public List<Order> findByCondition(Map<String, String> field, Order order) {
		Specification<Order> spec = new Specification<Order>() {
			@Override
			public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				List<Expression<Boolean>> expressions = predicate.getExpressions();
				if(field.get("inputOrderId")!=null&&!field.get("inputOrderId").trim().equals("")){
					expressions.add(cb.like(root.<String>get("orderId"), "%"+field.get("inputOrderId")+"%"));
				}
				if(field.get("inputChargerName")!=null&&!field.get("inputChargerName").trim().equals("")){
					expressions.add(cb.like(root.<String>get("charger").get("name"), "%"+field.get("inputChargerName")+"%"));
				}
				if(field.get("inputCusName")!=null&&!field.get("inputCusName").trim().equals("")){
					expressions.add(cb.like(root.<String>get("account").get("realName"), "%"+field.get("inputCusName")+"%"));
				}
				if(field.get("status")!=null&&!field.get("status").trim().equals("")){
					expressions.add(cb.equal(root.<String>get("status"), order.getStatus()));
				}
				if(field.get("start")!=null&&field.get("end")!=null&&!field.get("start").trim().equals("")&&!field.get("end").trim().equals("")){
					List<Date> listDate = new ArrayList<Date>();
					Calendar   calendar   =   new   GregorianCalendar(); 
				    calendar.setTime(Utils.stringToDate(field.get("end"))); 
				    calendar.add(Calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动 
					listDate.add(Utils.stringToDate(field.get("start")));
					listDate.add(calendar.getTime());
					expressions.add(cb.between(root.<Date>get("createdAt"),listDate.get(0),listDate.get(1)));
				}
				if(field.get("checkboxinput")!=null&&!field.get("checkboxinput").trim().equals("")){
					String[] allStations = field.get("checkboxinput").split(",");
					expressions.add(root.get("charger").get("station").in(Arrays.asList(allStations)));
				}
				if(field.get("inputCardOrPhoneNumber")!=null&&!field.get("inputCardOrPhoneNumber").trim().equals("")){
						String cnpn = order.getCardNoOrPhoneNum();
						Path<String> nick1 = root.join("account", JoinType.LEFT).get("phone");
						Path<String> nick2 = root.join("card",JoinType.LEFT).get("cardNo");
						expressions.add(cb.or(cb.like(nick2, "%"+cnpn+"%"),cb.like(nick1, "%"+cnpn+"%")));
						//plist.add(cb.or(cb.like(nick1, "%"+cnpn+"%"),cb.like(nick2, "%"+cnpn+"%")));
				}
				if(org.apache.commons.lang.StringUtils.isNotBlank(field.get("checkboxstationname"))){
					String stations[] = field.get("checkboxstationname").split(",");
					Path<String> nick = root.get("charger").get("station").get("name");
					expressions.add(nick.in(Arrays.asList(stations)));
				}
				query.orderBy(cb.desc(root.get("createdAt")));
				query.distinct(true);
				return predicate;
			}
		};
		return orderRepository.findAll(spec);
	}
}
