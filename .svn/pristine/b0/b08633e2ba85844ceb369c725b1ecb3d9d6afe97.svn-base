package com.iycharge.server.domain.service;

import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.account.Card;
import com.iycharge.server.domain.entity.charger.Charger;
import com.iycharge.server.domain.entity.order.Order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderService {

	public Page<Order> findAll(Pageable pageable);

	public Page<Order> findById(long id,Pageable pageable);

	public Page<Order> findByAccount(Account account, Pageable pageable);
	
	Page<Order> findByCard(Card card,Pageable pageable);
	public Page<Order> findByCharger(Charger charger, Pageable pageable);

	public Order save(Order account);

	public Order payWithAlipay(Order order, Map<String, String> params);
	
	Page<Order> findAllSearch(String[] fields, Order order, Pageable pageable);
	
	Page<Order> findAll(String[] fields, Order order, Pageable pageable);
	
	Page<Order> findAll(Order order, Pageable pageable);
	
	public Order findById(long id);
	
	Page<Order> searchTime(Long num,Long id,Pageable pageable);
	
	
	/**
	 * 根据订单号查询订单
	 * @param orderId      订单流水号
	 * @return
	 */
	Order findByOrderId(String orderId);

	List<Order> findByCharger(Charger charger);
	public List<Order> findByCondition(Map<String,String> field , Order order);
	/**
     * 更新订单信息
     * @param order    充电完成后电桩上传充电记录
     */
    void updateOrder(Order order);

	Page<Order> searchOrderByCardNoAndDays(String cardNo,Integer days,Pageable pageable);
	
	/**
     * 查询某时段内的有效订单数
     * @param startTime        查询起始时间
     * @param endTime          查询结束时间
     * @return
     */
    List<Order> findByPeriod(Date startTime, Date endTime);

}
