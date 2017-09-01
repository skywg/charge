package com.iycharge.server.domain.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.iycharge.server.domain.entity.price.ParamSetting;

public interface ParamSettingSService {

    public Page<ParamSetting> findAll(Pageable pageable);

    public List<ParamSetting> findAll();

    public ParamSetting findById(long id);

    public ParamSetting save(ParamSetting ParamSetting);

	void del(ParamSetting entity);

	ParamSetting delParamSetting(ParamSetting ParamSetting);

	Page<ParamSetting> findAllSearch(String[] fields, ParamSetting ParamSetting, Pageable pageable);


}
