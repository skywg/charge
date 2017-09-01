package com.iycharge.server.api.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.api.util.IDCreator;
import com.iycharge.server.domain.entity.BaseEntity;
import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.feedback.FeedBack;
import com.iycharge.server.domain.entity.feedback.FeedBackStatus;
import com.iycharge.server.domain.entity.order.Order;
import com.iycharge.server.domain.service.AccountService;
import com.iycharge.server.domain.service.FeedBackService;

@RestController
@RequestMapping("/api/feedBacks")
public class FeedBackRestController {
		@Autowired
		private FeedBackService feedBackService;
		@Autowired
		private AccountService accountService;
		
		@Transactional
	    @RequestMapping(value = "/add", method = RequestMethod.POST)
	    @JsonView(BaseEntity.Private.class)
		public String add(Principal principal,
						@RequestParam("content")String content,
						@RequestParam("channel")String channel,
						@RequestParam("category")String category){
			Account account = accountService.findById(Long.parseLong(principal.getName()));
			FeedBack feedBack =new FeedBack();
			String seqNo = IDCreator.generator(IDCreator.BUS_FEED_BACK);
			feedBack.setSeqNo(seqNo);
			feedBack.setContent(content);
			feedBack.setAccountName(account.getRealName());
			feedBack.setAccountPhone(account.getPhone());
			feedBack.setValid(true);
			feedBack.setChannel(channel);
			feedBack.setCategory(category);
			feedBack.setStatus(FeedBackStatus.SUSPENDING);
			feedBackService.save(feedBack);
			return "success";
		}
		/**
	     * 查询客服中心问题
	     * @param pageable      
	     * @param principal     查询用户    
	     * @return
	     */
	    @RequestMapping("/check")
	    @JsonView(BaseEntity.Detail.class)
	    public Map<String, Object> getFeedBack(@PageableDefault(size = 30, direction=Sort.Direction.DESC, sort={"updatedAt"}) Pageable pageable, Principal principal) {
	        Page<FeedBack> result = feedBackService.findByAccountPhone(accountService.findById(Long.valueOf(principal.getName())).getPhone(), pageable);
	        Map<String, Object> map = new HashMap<>();
	        map.put("currentPage", result.getNumber());
	        map.put("totalPage"  , result.getTotalPages());
	        map.put("itemCount"  , result.getContent().size());
	        map.put("items"      , result.getContent()); 
	        return map;
	    }
}
