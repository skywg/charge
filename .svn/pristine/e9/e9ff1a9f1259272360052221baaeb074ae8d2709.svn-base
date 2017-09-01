package com.iycharge.server.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iycharge.server.domain.entity.account.Preference;
import com.iycharge.server.domain.repository.PreferenceRepository;
import com.iycharge.server.domain.service.PreferenceService;
@Service
public class PreferenceServiceImpl implements PreferenceService{
	@Autowired
	private PreferenceRepository preferenceRepository;
	@Override
	public Preference save(Preference preference) {
		
		return preferenceRepository.save(preference);
	}

}
