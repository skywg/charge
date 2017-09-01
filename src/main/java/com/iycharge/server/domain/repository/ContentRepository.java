package com.iycharge.server.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.iycharge.server.domain.entity.content.Content;

public interface ContentRepository extends JpaRepository<Content, Long>{
	public Page<Content> findAll(Pageable pageable);
	public Page<Content> findAll(Specification spec, Pageable pageable);
	public Content findById(Long id);
	public List<Content> findByClassification(String classification) ;
	@Query("select o from Content o where o.status=:status and o.classification=:classification")
	public Page<Content> searchContentByClassificationAndStatus(@Param("classification") String classification,@Param("status") Byte status,Pageable pageable) ;
	@Override
	public Content save(Content content);
	public List<Content> findByStatus(Byte status);
}
