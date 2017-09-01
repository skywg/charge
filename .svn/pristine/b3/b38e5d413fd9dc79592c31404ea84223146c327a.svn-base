package com.iycharge.server.domain.entity.account.favorite;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.domain.entity.BaseEntity;
import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.station.Station;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 我的收藏
 *
 * @author bwang
 */
@Entity
@Table(name = "favorites")
public class Favorite extends BaseEntity {
    
    /*
     * 用户收藏站点
     */
    @JsonView(Summary.class)
    @ManyToOne(cascade={CascadeType.MERGE, CascadeType.REFRESH},  optional = false)
    @JoinColumn(name="station_id")
    private Station station;
   
    /*
     * 是否取消关注
     */
    @JsonView(Summary.class)
    private Boolean isCancel = false;
    
    /*
     * 收藏用户
     */
    @JsonIgnore
    @ManyToOne(cascade={CascadeType.MERGE, CascadeType.REFRESH},  optional = false)
    private Account account;
    
    
    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Boolean getIsCancel() {
        return isCancel;
    }

    public void setIsCancel(Boolean isCancel) {
        this.isCancel = isCancel;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
