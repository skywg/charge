package com.iycharge.server.domain.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.iycharge.server.domain.entity.admin.AppType;
import com.iycharge.server.domain.entity.admin.AppVersion;
import com.iycharge.server.domain.entity.admin.AppVersionQueryParam;

/**
 *  
 * @author sxiao
 */
public interface AppVersionService {
	
	public Page<AppVersion> findAll(Pageable  pageable);

	public Page<AppVersion> findAllSearch(AppVersionQueryParam queryParam,AppVersion appVersion,Pageable pageable);

    AppVersion findById(Long id);
	
    public AppVersion save(AppVersion appVersion);
    
    public List<AppVersion> findByAppType(AppType appType);
}
