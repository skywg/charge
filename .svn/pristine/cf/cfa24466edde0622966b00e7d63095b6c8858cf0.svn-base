package com.iycharge.server.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iycharge.server.domain.entity.content.Content;
import com.iycharge.server.domain.entity.feedback.FeedBack;
import com.iycharge.server.domain.entity.feedback.FeedBackAuditLog;
import com.iycharge.server.domain.entity.feedback.FeedBackStatus;
@ResponseBody
public interface FeedBackRepository extends JpaRepository<FeedBack, Long>{
	public Page<FeedBack> findAll(Pageable pageable);
	public Page<FeedBack> findAll(Specification spc,Pageable pageable);
	public FeedBack findById(Long id);
	public FeedBack save(FeedBack feedBack);
	public Page<FeedBack> findByAccountPhone(String phone,Pageable papageable);
	public List<FeedBack> findByStatus(FeedBackStatus status);
}
