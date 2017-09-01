package com.iycharge.server.report.schedule.task;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.iycharge.server.domain.service.AccountService;
import com.iycharge.server.report.entity.UserData;
import com.iycharge.server.report.schedule.service.UserDataService;

/**
 * 用户数统计任务
 * @author bwang
 */
@Component
public class UserDataStatisticTask extends AbstractTask {
    
    @Resource
    private AccountService accountService;
    
    @Resource
    private UserDataService userDataService;
    
    public UserDataStatisticTask() {

    }
    
    @Override
    public void run() {
        try {
            while(check()) {
                doWork();
                
                this.getTask().setFlag(true);
                updateTask();
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getTask().setFlag(false);
            updateTask();
        }
    }
       
    @Override
    public void doWork() {
        UserData userData = new UserData();
        
        int[] data = accountService.statisticByAccountType(getStartTime(), getEndTime());
        userData.setNewCNum(data[0]);
        userData.setNewPNum(data[1]);
        
        data  = accountService.statisticByAccountType(getEndTime());
        userData.setTotalCNum(data[0]);
        userData.setTotalPNum(data[1]);
        
        userData.setDay(getStartTime());
        
        userDataService.save(userData);
    }
}
