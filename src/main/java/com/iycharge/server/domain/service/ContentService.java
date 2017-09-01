package com.iycharge.server.domain.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.iycharge.server.domain.entity.content.Content;


public interface ContentService {
	public Page<Content> findAll(Pageable pageable);
	public Page<Content> findAllSearch(String  fields[],Content content,Pageable pageable);
	public Content findById(Long id) ;
	public List<Content> findByClassification(String classification) ;
	public Page<Content> findByClassification(String classification,Byte status,Pageable pageable) ;
	public Content save(Content content) ;
	public List<Content> findByStatus(Byte status);
}
