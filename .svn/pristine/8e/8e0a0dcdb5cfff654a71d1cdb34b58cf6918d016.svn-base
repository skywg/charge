package com.iycharge.server.domain.service.impl;

import com.iycharge.server.domain.entity.operator.Operator;
import com.iycharge.server.domain.repository.OperatorRepository;
import com.iycharge.server.domain.service.OperatorService;

import java.util.List;

import org.elasticsearch.common.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OperatorServiceImpl implements OperatorService {
    @Autowired
    OperatorRepository operatorRepository;

    @Override
    public Page<Operator> findAll(Pageable pageable) {
        return operatorRepository.findByDelStatus("normal",pageable);
    }

    @Override
    public Operator findById(long id) {
        return operatorRepository.findOne(id);
    }
    @Override
    public List<Operator> findByName(String name) {
    	return operatorRepository.findByNameAndDelStatus(name,"normal");
    }
    
    @Override
    public List<Operator> findByCode(String code) {
        return operatorRepository.findByCode(code);
    }
    @Override
    public Operator save(Operator operator) {
        return operatorRepository.saveAndFlush(operator);
    }
    
    @Override
    public List<Operator> findListAll() {
        return operatorRepository.findByDelStatus("normal");
    }
    @Override
    public void del(Long id) {
    	operatorRepository.delete(id);
    }
    
    @Override
    public Page<Operator> findSearch(String name,Pageable pageable) {
    	if(StringUtils.isEmpty(name)){
    		return operatorRepository.findByDelStatus("normal",pageable);
    	}else{
    		return operatorRepository.findByDelStatusAndNameContaining("normal",name,pageable);
    	}
    		
    }
}
