package com.iycharge.server.domain.entity.account;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.iycharge.server.domain.entity.station.Station;
/**
 * 增加会员和充电站关系映射
 * 一个会员可以对应多个充电站，一个充电战可以有多个会员
 *
 */
@Entity
@Table(name="account_station")
public class AccountStation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    @ManyToOne
	@JoinColumn(name="account_id")
	private Account account;
    
	@ManyToOne
	@JoinColumn(name="station_id")
	private Station station;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Station getStation() {
		return station;
	}
	public void setStation(Station station) {
		this.station = station;
	}
	
	
}
