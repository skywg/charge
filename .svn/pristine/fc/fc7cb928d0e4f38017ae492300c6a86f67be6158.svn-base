package com.iycharge.server.domain.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.iycharge.server.domain.entity.content.Content;
import com.iycharge.server.domain.entity.feedback.FeedBack;
import com.iycharge.server.domain.entity.feedback.FeedBackAuditLog;

public interface FeedBackService {
	public Page<FeedBack> findAll(Pageable pageable);
	public Page<FeedBack> findAllSearch(String  fields[],FeedBack feedBack,Pageable pageable);
	public FeedBack findById(Long id) ;
	public FeedBack save(FeedBack feedBack);
	public Page<FeedBack> findByAccountPhone(String phone,Pageable pageable);
	List<FeedBack> findByStatus();
}
