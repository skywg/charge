package com.iycharge.server.domain.entity.review;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.station.Station;
import com.iycharge.server.domain.entity.BaseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "reviews")
public class Review extends BaseEntity {
    /*
     * 评论人或回复人
     * 目前只有系统能对评论进行回复，此时account_id赋值-1
     */
	@JsonView(Summary.class)
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
    private Account account;
    
    /*
     * 评论站点
     */
    @ManyToOne(cascade=CascadeType.REFRESH)
    private Station station;
    
    /*
     * 评论内容
     */
    @JsonView(Summary.class)
    @Column(length = 10000)
    private String content;
    
    /*
     * 评分
     */
    @Embedded
    @JsonView(Summary.class)
    private ReviewRating rating = new ReviewRating();
    
    /*
     * 
     */
    @ManyToOne(cascade=CascadeType.REFRESH)
    private Review parent;
    
    /*
     * 回复评论
     */
    @JsonView(Summary.class)
    @OneToMany(cascade=CascadeType.ALL, mappedBy="parent", fetch=FetchType.EAGER)
    private List<Review> replies = new ArrayList<>();
    
    /*
     * 状态：-1 : 未审核    0 ：审核未通过      1： 审核通过   
     */
    private byte status = -1;
    
    /*
     * 状态：-1 : 未审核    0 ：审核未通过      1： 审核通过   
     */
    @Transient 
    private String transientStatus;
    /**
     *  开始时间（用户查询时接收表单参数）
     */
    @JsonIgnore
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Transient 
    private Date startAt;
    /*
     * 1 表示评论 2 表示回复
     */
    @JsonIgnore
    @Column(name="differentiate")
    private String differentiate;
    

	public String getDifferentiate() {
		return differentiate;
	}

	public void setDifferentiate(String differentiate) {
		this.differentiate = differentiate;
	}

	public Date getStartAt() {
		return startAt;
	}

	public void setStartAt(Date startAt) {
		this.startAt = startAt;
	}

	public Review() {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ReviewRating getRating() {
        return rating;
    }

    public void setRating(ReviewRating rating) {
        this.rating = rating;
    }

    public Review getParent() {
        return parent;
    }

    public void setParent(Review parent) {
        this.parent = parent;
    }

    public List<Review> getReplies() {
        return replies;
    }

    public void setReplies(List<Review> replies) {
        this.replies = replies;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

	public String getTransientStatus() {
		return transientStatus;
	}

	public void setTransientStatus(String transientStatus) {
		this.transientStatus = transientStatus;
	}


	
	
}
