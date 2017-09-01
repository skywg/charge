package com.iycharge.server.domain.service;

import com.iycharge.server.domain.entity.Authorization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuthorizationService {

    public Page<Authorization> findAll(Pageable pageable);

    public Authorization findById(long id);

    public void save(Authorization authorization);
}
