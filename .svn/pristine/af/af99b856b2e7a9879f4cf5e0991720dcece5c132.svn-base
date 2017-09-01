package com.iycharge.server.domain.service.impl;

import com.iycharge.server.domain.entity.Authorization;
import com.iycharge.server.domain.repository.AuthorizationRepository;
import com.iycharge.server.domain.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    @Autowired
    AuthorizationRepository authorizationRepository;

    @Override
    public Page<Authorization> findAll(Pageable pageable) {
        return authorizationRepository.findAll(pageable);
    }

    @Override
    public Authorization findById(long id) {
        return authorizationRepository.findOne(id);
    }

    @Override
    public void save(Authorization authorization) {
        authorizationRepository.save(authorization);
    }

}
