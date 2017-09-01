package com.iycharge.server.domain.entity.account;

import java.util.Arrays;
import java.util.List;

/**
 * 车辆认证状态
 * @author sxiao
 */

public enum CarIdentifyStatus {
	PASSED("认证通过"),CHECKING("资料提交"),FAILED("认证失败");
	
	private String title;
    
    private CarIdentifyStatus(String title) {
        this.title = title;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public List<CarIdentifyStatus> asList() {
        return Arrays.asList(PASSED, CHECKING, FAILED);
    }
}
