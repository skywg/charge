package com.iycharge.server.domain.service;


import com.iycharge.server.domain.entity.review.Review;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {

    public Page<Review> findAll(Pageable pageable);

    public Review findById(long id);

    public Review save(Review review);
    
    public Page<Review> findAllSearch(String  fields[],Review review,Pageable pageable);

	List<Review> findByStatus();

}
