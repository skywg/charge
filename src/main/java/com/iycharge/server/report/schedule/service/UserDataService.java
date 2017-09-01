package com.iycharge.server.report.schedule.service;

import java.util.Date;
import java.util.List;

import com.iycharge.server.report.entity.UserData;

/**
 * 用户数统计业务接口类
 * @author bwang
 */
public interface UserDataService {
    
    /**
     * 保存用户数汇总统计数据
     * @param userData
     * @return
     */
    UserData save(UserData userData);
    
    public List<UserData> findByDayBetween(Date startdate,Date enddate);
}
