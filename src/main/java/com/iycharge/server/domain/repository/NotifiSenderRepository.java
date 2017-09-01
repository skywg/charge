package com.iycharge.server.domain.repository;

import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.notification.Notification;
import com.iycharge.server.domain.entity.notification.Notification_Sender;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface NotifiSenderRepository extends JpaRepository<Notification_Sender, Long> {
    @Override
	public List<Notification_Sender> findAll();
     
    public Notification_Sender findById(Long id);
    
    Page findAll(Specification spec, Pageable pageable); // 分页按条件查询
    
    @Query("select n from Notification_Sender n where n.notification_id=:notification_id and n.account_id=:account_id")
    public Notification_Sender findByNotificationAndAccount(@Param("notification_id") Long notification_id
    		,@Param("account_id") Long account_id);
    
    public List<Notification_Sender> findByAccount(Account account);
    
    public Page<Notification_Sender> findByAccount(Account account,Pageable papageable);
    
    public List<Notification_Sender> findByNotification(Notification notification);
}