package com.iycharge.server.domain.repository;

import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.notification.Notification;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

  //  public Page<Notification> findByRecipient(Account recipient, Pageable pageable);
    
    @Override
	public List<Notification> findAll();
     
    public Notification findById(Long id);
    
    Page findAll(Specification spec, Pageable pageable); // 分页按条件查询
   

}