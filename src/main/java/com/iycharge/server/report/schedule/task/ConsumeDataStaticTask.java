package com.iycharge.server.report.schedule.task;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.account.AccountRechargeLog;
import com.iycharge.server.domain.entity.order.Order;
import com.iycharge.server.domain.service.AccountRechargeLogService;
import com.iycharge.server.domain.service.AccountService;
import com.iycharge.server.domain.service.OrderService;
import com.iycharge.server.report.entity.UserBillData;
import com.iycharge.server.report.schedule.service.UserBillDataService;

@Component
public class ConsumeDataStaticTask extends AbstractTask {

	@Resource
	private OrderService orderService;
	@Resource
	private AccountService accountService;
	@Resource
	private UserBillDataService userBillDataService;
	@Resource 
	private AccountRechargeLogService accountRechargeLogService;
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
		List<Order> orders = orderService.findByPeriod(getStartTime(), getEndTime());
		List<Account> companyAccounts = accountService.findByAccountType("COMPANY");
		Map<Long,List<Order>> dayMapData = getOrdersByAccount(orders);
		for(Account account : companyAccounts){
			List<UserBillData> lastest = userBillDataService.findByAccountOrderByIdDesc(account);
			List<AccountRechargeLog> arls = accountRechargeLogService.findByCondition(getStartTime(), getEndTime(), account, true);
			UserBillData ubd = null;
			BigDecimal totalElectricity = null;
			BigDecimal totalMoney = null;
			BigDecimal totalRecharge = null;
			//判断以前是否有数记录数据
			if(lastest.size()<1){
				ubd= new UserBillData();
				totalElectricity = new BigDecimal(0);
				totalMoney = new BigDecimal(0);
				totalRecharge = new BigDecimal(0);
			}else{
				ubd = lastest.get(0);
				totalElectricity = ubd.getTotalElectricity()!=null?ubd.getTotalElectricity():(new BigDecimal(0));
				totalMoney = ubd.getTotalMoney()!=null?ubd.getTotalMoney():(new BigDecimal(0));
				totalRecharge = ubd.getTotalRecharge()!=null?ubd.getTotalRecharge():(new BigDecimal(0));
			}
			ubd.setAccount(account);
			ubd.setMonth(getFirst());
			//增加当天的数据
			List<Order> os = dayMapData.get(account.getId());
			if(os!=null){
				for(Order order : os){
					if(order!=null){
						totalElectricity=totalElectricity.add(order.getDegree());
						totalMoney=totalMoney.add(order.getMoney());
					}
				}
			}
			if(arls.size()>0){
				for(AccountRechargeLog arl:arls){
					totalRecharge=totalRecharge.add(arl.getMoney());
				}
			}
			ubd.setTotalElectricity(totalElectricity);
			ubd.setTotalMoney(totalMoney);
			ubd.setTotalRecharge(totalRecharge);
			if(lastest.size()<1){
				ubd.setStartBalance(account.getMoney());
			}else{
				//如果会员不是第一次添加，排除第一天外，期初金额不变
				ubd.setStartBalance(lastest.get(0).getStartBalance());
			}
			ubd.setEndBalance(account.getMoney());
			//月初处理
			if(isFirsr(getStartTime())){
				//月初统计本月的期初金额 和 上月的期末金额
				if(lastest.size()<1){
					//如果是第一次添加会员，期初金额设置为0,此字段默认为0;
				}else{
					//期末金额设置为期初金额+上月总充值金额-上月总消费金额
					UserBillData lastMonth = lastest.get(0);
					BigDecimal eb = account.getMoney();
					lastMonth.setEndBalance(eb);
					userBillDataService.save(lastMonth);
					//不是第一次添加，在月初的时候，期初金额设置为上月的期末金额。
					ubd.setStartBalance(eb);
					ubd.setTotalElectricity(new BigDecimal(0));
					ubd.setTotalMoney(new BigDecimal(0));
					ubd.setTotalRecharge(new BigDecimal(0));
					ubd.setEndBalance(eb);
				}
			}
			userBillDataService.save(ubd);
		}
	}
	private boolean isFirsr(Date date){
		Calendar calendar = Calendar.getInstance(); 
        calendar.setTime(date); 
        calendar.set(Calendar.DATE, (calendar.get(Calendar.DATE))); 
        if (calendar.get(Calendar.DAY_OF_MONTH) == 1) { 
            return true; 
        } 
        return false; 
		     
	}
	//判断是否为每月的第一天
	private Date getFirst(){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		return c.getTime();
	}
	
	public static boolean isEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DATE) == calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	/**
	 *
	 *每天订单   根据不同的企业会员将Order 赋值到map中
	 *key - accountId
	 *value - List<Order> 
	 */
	private Map<Long,List<Order>> getOrdersByAccount(List<Order> orders){
		Map<Long,List<Order>> map = new HashMap<>();
		if(orders ==null || orders.size() < 1){
			return map;
		}
		for(Order order : orders){
			if(order.getCard()!=null&&"COMPANY".equals(order.getCard().getAccount().getAccountType())){
				Account account = order.getCard().getAccount();
				if(map.get(account.getId())!=null){
					map.get(account.getId()).add(order);
				}else{
					List<Order> tmp = new ArrayList<>();
					tmp.add(order);
					map.put(account.getId(), tmp);	
				}
			}
		}
		return map;
	}
	
}
