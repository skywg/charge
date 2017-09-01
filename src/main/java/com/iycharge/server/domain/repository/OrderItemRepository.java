package com.iycharge.server.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iycharge.server.domain.entity.order.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{
	
	@Query("select t.createdAt,t.degree,t.money from OrderItem t where datediff(DATE(t.createdAt),DATE(now()))=0")
	public List<Object[]> findAllOrderItem();
	
/*	@Query("select count(1) as num,sum(t.degree) as degree from OrderItem t where t.id in(:id) and datediff(DATE(t.createdAt),DATE(now()))=0")
	public Object[] findAllOrderItemTem(@Param("id")List<Long> id);*/
	
	
}
