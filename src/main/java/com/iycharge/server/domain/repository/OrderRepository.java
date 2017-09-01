package com.iycharge.server.domain.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.account.Card;
import com.iycharge.server.domain.entity.charger.Charger;
import com.iycharge.server.domain.entity.order.Order;
import com.iycharge.server.domain.entity.order.OrderStatus;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	public Page<Order> findByAccount(Account account, Pageable pageable);
   
	
	public Page<Order> findByCharger(Charger charger, Pageable pageable);
	
	public List<Order> findByCharger(Charger charger);

	public Page<Order> findById(Long id, Pageable pageable);
	
	public Order findById(long id);
	
	public Page<Order> findByCard(Card card,Pageable pageable);
	
	public List<Order> findAll(Specification<Order> spec);
	
	Page<Order> findAll(Specification<Order> spec, Pageable pageable);
	@Query("select o from Order o where to_days(now())-to_days(o.createdAt)<:num and o.charger.id=:id")
	Page<Order> searchTime(@Param("num")Long num,@Param("id")Long id,Pageable pageable);
	
	/**
	 * 根据订单号查找订单
	 * @param orderId          订单号
	 */
	Order findByOrderId(String orderId);
	
	
	/**
	 * 用户总数
	 */
	@Query("select count(1) as num from Order ")
	public String countCharger();
	
	/**
	 * 用户本月总数
	 */
	@Query("select count(1) from Order s where (DATE_FORMAT(NOW(),'%y/%m')) = DATE_FORMAT(createdAt,'%y/%m') ")
	public String countChargerMonth();
	
	/**
	 * 用户每天总数
	 */
	@Query("select count(1) from Order where datediff(DATE(createdAt),DATE(now()))=0")
	public String countChargerDay();
	
	
	/**
	 * 电量总数
	 */
	@Query("select sum(degree) as num from Order ")
	public String countChargerDegree();
	
	/**
	 * 电量本月总数
	 */
	@Query("select sum(degree) from Order s where (DATE_FORMAT(NOW(),'%y/%m')) = DATE_FORMAT(createdAt,'%y/%m') ")
	public String countChargerDegreeMonth();
	
	/**
	 * 电量每天总数
	 */
	@Query("select sum(degree) from Order where datediff(DATE(createdAt),DATE(now()))=0")
	public String countChargerDegreeDay();
	
	
	/**
	 * 每个充电桩电量每天总数,充电次数
	 */
	@Query("select count(1) as num,sum(t.degree) from Order t where t.charger.id=:chargerId and datediff(DATE(t.createdAt),DATE(now()))=0")
	public String chargerDegreeDay(@Param("chargerId")Long chargerId);
	
	/**
	 * 电量钱总数
	 */
	@Query("select sum(money) as num from Order")
	public String countChargerMoney();
	
	/**
	 * 电量钱本月总数
	 */
	@Query("select sum(money) from Order s where (DATE_FORMAT(NOW(),'%y/%m')) = DATE_FORMAT(createdAt,'%y/%m') ")
	public String countChargerMoneyMonth();
	
	/**
	 * 电量钱每天总数
	 */
	@Query("select sum(money) from Order where datediff(DATE(createdAt),DATE(now()))=0")
	public String countChargerMoneyDay();

	/**
	 * 根据卡号以及相隔天数查询订单记录
	 * @param cardNo
	 * @param days
	 * @param pageable
     * @return
     */
	@Query("select o from Order o where to_days(now())-to_days(o.createdAt)<=:days and o.card.cardNo=:cardNo")
	Page<Order> searchOrderByCardNoAndDays(@Param("cardNo")String cardNo,@Param("days")long days,Pageable pageable);
	
	/**
	 * 查询某时段内的有效订单数
	 * @param startTime        查询起始时间
	 * @param endTime          查询结束时间
	 * @return
	 */
	@Query("select order from Order order where :startTime <= startAt and startAt < :endTime and status=:status")
	List<Order> findByPeriod(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("status") OrderStatus status);

}
