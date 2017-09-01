package com.iycharge.server.api.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.domain.entity.BaseEntity;
import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.review.Review;
import com.iycharge.server.domain.entity.review.ReviewRating;
import com.iycharge.server.domain.entity.station.Station;
import com.iycharge.server.domain.service.AccountService;
import com.iycharge.server.domain.service.ReviewService;
import com.iycharge.server.domain.service.StationService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewRestController {
	@Autowired
	private AccountService accountService;
	@Autowired
	private StationService stationService;
	@Autowired
	private ReviewService reviewService;
    
    @Transactional
    @RequestMapping("/add")
    @JsonView(BaseEntity.Summary.class)
    public String addReview(@RequestParam("accountId") Long accountId,
    		@RequestParam("stationId") Long stationId,
    		@RequestParam("content") String content,
    		@RequestParam("total")  int total,
    		Principal principal) {
    	Account account =accountService.findById(accountId);
    	Station station	=stationService.findById(stationId);
    	Review review =new Review();
    	review.setAccount(account);
    	review.setStation(station);
    	review.setContent(content);
    	review.setDifferentiate("1");
    	ReviewRating rating =new ReviewRating();
    	rating.setTotal(BigDecimal.valueOf(total));
    	review.setRating(rating);
    	review.setCreatedAt(new Date());
    	review.setStatus(Byte.valueOf("-1"));
    	reviewService.save(review);
    	return "success";
    }
}
