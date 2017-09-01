package com.iycharge.server.domain.repository;


import com.iycharge.server.domain.entity.review.Review;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
	
	Page<Review> findAll(Specification<Review> spec,Pageable pageable);
	List<Review> findByStatus(Byte status);
	
	Page<Review> findByAccountNotNull(Pageable pageable);
	//Page<Review> findByAccountNotNullAndUpdateAtBetween(Pageable pageable);
}
