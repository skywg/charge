package com.iycharge.server.report.schedule.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iycharge.server.report.entity.UserData;
import com.iycharge.server.report.schedule.repository.UserDataRepository;
import com.iycharge.server.report.schedule.service.UserDataService;

/**
 * 用户数统计业务接口实现类
 * @author bwang
 */
@Transactional(readOnly=true)
@Service
public class UserDataServiceImpl implements UserDataService {
    
    @Resource
    private UserDataRepository userDataRepository;
    
    @Transactional(readOnly=false)
    @Override
    public UserData save(UserData userData) {
        // TODO Auto-generated method stub
        return userDataRepository.save(userData);
    } 
    
    @Transactional(readOnly=false)
    public List<UserData> findByDayBetween(Date startdate,Date enddate){
		return userDataRepository.findByDayBetween(startdate, enddate);
	}
}
