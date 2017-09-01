package com.iycharge.server.api.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.domain.entity.BaseEntity;
import com.iycharge.server.domain.entity.content.Content;
import com.iycharge.server.domain.service.ContentService;



@RestController
@RequestMapping("/api/contents")
public class ContentRestController {
	 @Autowired
	 private ContentService contentService;
    /**
     * 查询首页内容
     * @param principal  
     * @return
     */
    @RequestMapping("/checklog")
    @JsonView(BaseEntity.Detail.class)
    public List<Content> getContents(@RequestParam("classification") String classification) {
     List<Content> contents =contentService.findByClassification(classification);
     List<Content> contents1 =new ArrayList<>();
     for (Content content : contents) {
		if (content.getStatus()==2) {
			contents1.add(content);
		
		}
	 }

        return contents1;
    }
    /**
     * 查询活动和问题内容
     * @param pageable      
     * @param principal     查询用户    
     * @return
     */
    @RequestMapping("/check")
    @JsonView(BaseEntity.Detail.class)
    public Map<String, Object> getCurrentAccountOrder(@PageableDefault(size = 30, direction=Sort.Direction.DESC, sort={"releasedAt"}) Pageable pageable, Principal principal,@RequestParam("classification") String classification) {
    	Page<Content> result =contentService.findByClassification(classification,Byte.parseByte("2"),pageable);
        Map<String, Object> map = new HashMap<>();
        map.put("currentPage", result.getNumber());
        map.put("totalPage"  , result.getTotalPages());
        map.put("itemCount"  , result.getContent().size());
        map.put("items"      , result.getContent());
        
        return map;
        }
}
