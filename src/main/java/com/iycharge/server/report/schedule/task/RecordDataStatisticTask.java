package com.iycharge.server.report.schedule.task;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.iycharge.server.domain.entity.order.Order;
import com.iycharge.server.domain.entity.order.RechargeRecord;
import com.iycharge.server.domain.service.OrderService;
import com.iycharge.server.domain.service.RechargeRecordService;
import com.iycharge.server.report.entity.RecordData;
import com.iycharge.server.report.schedule.service.RecordDataService;

/**
 * 充值统计任务
 * @author zw
 */
@Component
public class RecordDataStatisticTask extends AbstractTask {
    
    @Resource
    private RechargeRecordService rechargeRecordService;
    
    @Resource
    private OrderService orderService;
    
    @Resource
    private RecordDataService recordDataService;
    public RecordDataStatisticTask() {    
        
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
        //当天
        List<Order> orderList = orderService.findByPeriod(getStartTime(), getEndTime());
        RecordData recordData = new RecordData();
        //企业消费金额
    	double companycost = 0d;
    	//个人消费金额
    	double personcost = 0d;
        if(orderList != null && !orderList.isEmpty()) {
        	// CARD("卡"), WALLET("钱包"), ALIPAY("支付宝"), WEBCHART("微信"),CASH("现金支付"),OFFLINE("线下转账");
        	for(Order order : orderList) {
        		BigDecimal bi = order.getMoney();
            	//企业
            	if("CASH".equals(order.getPaidFrom())||"OFFLINE".equals(order.getPaidFrom())){
            		companycost+=bi.doubleValue();
            	}else{
            		personcost+=bi.doubleValue();
            	}
            }
        	recordData.setCompanyCost(new BigDecimal(String.valueOf(companycost)));
        	recordData.setPersonCost(new BigDecimal(String.valueOf(personcost)));
        	
        }
        recordData.setCostTotal(new BigDecimal(String.valueOf(companycost+personcost)));
        List<RechargeRecord> list = rechargeRecordService.findByPeriod(getStartTime(), getEndTime());
        //企业充值金额
    	double companyrecord =  0d;
    	//个人充值金额
    	double personrecord = 0d;
        if(list != null && !list.isEmpty()) {
        	for(RechargeRecord rechargeRecord : list) {
        		BigDecimal bi  = rechargeRecord.getMoney();
        		//企业
            	if("CASH".equals(rechargeRecord.getPaidFrom())||"OFFLINE".equals(rechargeRecord.getPaidFrom())){
            		companyrecord+=bi.doubleValue();
            	}else{
            		personrecord+=bi.doubleValue();
            	}
        	}
        	recordData.setCompanyRecord(new BigDecimal(String.valueOf(companyrecord)));
        	recordData.setPersonRecord(new BigDecimal(String.valueOf(personrecord)));
        }
        recordData.setRecordTotal(new BigDecimal(String.valueOf(companyrecord+personrecord)));
        recordData.setDay(getStartTime());
        recordDataService.save(recordData);
    }
}
