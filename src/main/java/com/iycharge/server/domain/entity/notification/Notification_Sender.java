package com.iycharge.server.domain.entity.notification;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.domain.entity.BaseEntity.Summary;
import com.iycharge.server.domain.entity.account.Account;
@Entity
@Table(name = "notification_sender")
public class Notification_Sender {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Summary.class)
    private Long id;
	
    @JsonView(Summary.class)
    @Column(columnDefinition="boolean default 1", name="status")
    private boolean status = true;
    
    @ManyToOne
	@JoinColumn(name="account_id")
	private Account account;
    
	@ManyToOne
	@JoinColumn(name="notification_id")
	private Notification notification;
	
    @JsonView(Summary.class)
    @Column(columnDefinition="boolean default 0", name="ifread")
	private boolean ifRead = false;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Notification getNotification() {
		return notification;
	}
	public void setNotification(Notification notification) {
		this.notification = notification;
	}
	public boolean isIfRead() {
		return ifRead;
	}
	public void setIfRead(boolean ifRead) {
		this.ifRead = ifRead;
	}
	
	
	
}
