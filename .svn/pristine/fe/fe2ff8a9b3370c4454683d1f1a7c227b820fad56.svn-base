package com.iycharge.server.report.schedule.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iycharge.server.domain.repository.BeanRepository;
import com.iycharge.server.report.entity.AcDcDataBean;
import com.iycharge.server.report.entity.ChargingData;
import com.iycharge.server.report.entity.DataBean;
import com.iycharge.server.report.schedule.repository.ChargingDataRepository;
import com.iycharge.server.report.schedule.service.ChargingDataService;

/**
 * 充电消费数据操作业务接口实现类
 * @author bwang
 */
@Transactional(readOnly=true)
@Service
public class ChargingDataServiceImpl implements ChargingDataService {
    @Resource
    private ChargingDataRepository chargingDataRepository;
    
    @Resource
    private BeanRepository beanRepository;
    
    @Transactional(readOnly=false)
    @Override
    public ChargingData save(ChargingData chargingData) {
        // TODO Auto-generated method stub
        if(chargingData == null) {
            return null;
        }
        return chargingDataRepository.save(chargingData);
    }
    
    @Transactional(readOnly=false)
    @Override
    public List<ChargingData> saveAll(List<ChargingData> list) {
        // TODO Auto-generated method stub
        if(list == null || list.isEmpty()) {
            return null;
        }
        List<ChargingData> result = new ArrayList<>();
        for(int i=0; i<list.size(); i=i+100) {
            int to = list.size() < 100 ? list.size() : (i + 100);
            result.addAll(chargingDataRepository.save(list.subList(i, to)));
            i = to;
        }
        return result;
    }
    @Override
    public List<ChargingData> findByDayBetween(Date startdate,Date enddate){
		return chargingDataRepository.findByDayBetween(startdate, enddate);
	}

	@Override
	public List<ChargingData> findAllSearch(String[] fields, ChargingData chargingData, Date start, Date end) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<AcDcDataBean> findAcDc(String type,String counttype,String datetype,String start,String end){
		String sql = "select ";
		if("money".equals(counttype)){
			//钱
			sql += "SUM(t.money_ac) as acvalue ,SUM(t.money_dc) as dcvalue,SUM(t.money_app) as appvalue,SUM(t.money_card) as cardvalue ";
		}else if("elc".equals(counttype)){
			//电量
			sql += "SUM(t.electric_ac) as acvalue ,SUM(t.electric_dc) as dcvalue,SUM(t.electric_app) as appvalue,SUM(t.electric_card) as cardvalue ";
		}else if("num".equals(counttype)){
			//次数
			sql += "SUM(t.times_ac) as acvalue ,SUM(t.times_dc) as dcvalue,SUM(t.times_app) as appvalue,SUM(t.times_card) as cardvalue ";
		}
		sql+=" from (select * from r_charging_data where day between '"+start+"' and '"+end+"') t";
		return beanRepository.findAcDc(sql);
	}
	
	@Override
	public List<String> findTypeSearch(String ids,String type,String table ,String datetype,String start,String end){
		String sql = "select ";
		if("all".equals(type)){
			sql+="'' as typevalue ";
	 	}else{
	 		sql+= "t."+type+" as typevalue "; 
	 	}
		sql+=" from (select * from "+table+" where station_id in("+ids+") and day between '"+start+"' and '"+end+"') t GROUP BY ";
		if("all".equals(type)){
	 	}else{
	 		sql+="t."+type;
	 	}
		return beanRepository.getType(sql);
	}
	
	@Override
	public List<String> findType(String type,String table ,String datetype,String start,String end){
		String sql = "select ";
		if("all".equals(type)){
			sql+="'' as typevalue ";
	 	}else{
	 		sql+= "t."+type+" as typevalue "; 
	 	}
		sql+=" from (select * from "+table+" where day between '"+start+"' and '"+end+"') t GROUP BY ";
		if("all".equals(type)){
	 	}else{
	 		sql+="t."+type;
	 	}
		return beanRepository.getType(sql);
	}
	
	@Override
	public List<DataBean> findBean(String type,String counttype,String datetype,String start,String end){
		//String sql = "select province as province,SUM(moneyac)+SUM(moneydc) as money,DATE_FORMAT(day,'%Y-%m') as time FROM r_charging_data GROUP BY province,DATE_FORMAT(day,'%Y-%m')";
		String sql = "select ";
		if("all".equals(type)){
			sql+="'' as typevalue, ";
	 	}else{
	 		sql+= "t."+type+" as typevalue, "; 
	 	}
		if("money".equals(counttype)){
			//钱
			sql += "SUM(t.money_ac)+SUM(t.money_dc) as countnums ,";
			sql += "SUM(t.money_ac) as acvalue ,SUM(t.money_dc) as dcvalue,SUM(t.money_app) as appvalue,SUM(t.money_card) as cardvalue, ";
		}else if("elc".equals(counttype)){
			//电量
			sql += "SUM(t.electric_ac)+SUM(t.electric_dc) as countnums ,";
			sql += "SUM(t.electric_ac) as acvalue ,SUM(t.electric_dc) as dcvalue,SUM(t.electric_app) as appvalue,SUM(t.electric_card) as cardvalue, ";
		}else if("num".equals(counttype)){
			//次数
			sql += "SUM(t.times_ac)+SUM(t.times_dc) as countnums ,";
			sql += "SUM(t.times_ac) as acvalue ,SUM(t.times_dc) as dcvalue,SUM(t.times_app) as appvalue,SUM(t.times_card) as cardvalue, ";
		}
		String time = "";
		if("year".equals(datetype)){
			time = "DATE_FORMAT(t.day,'%Y') as time";
		}else if("month".equals(datetype)){
			time = "DATE_FORMAT(t.day,'%Y-%m') as time";
		}else if("day".equals(datetype)){
			time = "DATE_FORMAT(t.day,'%Y-%m-%d') as time";
		}
		sql+=time;
		sql+=" from (select * from r_charging_data where day between '"+start+"' and '"+end+"') t GROUP BY ";
		if("all".equals(type)){
			if("year".equals(datetype)){
				sql+= "DATE_FORMAT(t.day,'%Y')";
			}else if("month".equals(datetype)){
				sql+=  "DATE_FORMAT(t.day,'%Y-%m')";
			}else if("day".equals(datetype)){
				sql+=  "DATE_FORMAT(t.day,'%Y-%m-%d')";
			}
	 	}else{
	 		sql+="t."+type;
	 		if("year".equals(datetype)){
				sql+= ",DATE_FORMAT(t.day,'%Y')";
			}else if("month".equals(datetype)){
				sql+=  ",DATE_FORMAT(t.day,'%Y-%m')";
			}else if("day".equals(datetype)){
				sql+=  ",DATE_FORMAT(t.day,'%Y-%m-%d')";
			}
	 	}
		
		return beanRepository.getAll(sql);
	}
	
	
	@Override
	public List<DataBean> findBeanSearch(String ids,String type,String counttype,String datetype,String start,String end){
		//String sql = "select province as province,SUM(moneyac)+SUM(moneydc) as money,DATE_FORMAT(day,'%Y-%m') as time FROM r_charging_data GROUP BY province,DATE_FORMAT(day,'%Y-%m')";
		String sql = "select ";
		if("all".equals(type)){
			sql+="'' as typevalue, ";
	 	}else{
	 		sql+= "t."+type+" as typevalue, "; 
	 	}
		if("money".equals(counttype)){
			//钱
			sql += "SUM(t.money_ac)+SUM(t.money_dc) as countnums ,";
			sql += "SUM(t.money_ac) as acvalue ,SUM(t.money_dc) as dcvalue,SUM(t.money_app) as appvalue,SUM(t.money_card) as cardvalue, ";
		}else if("elc".equals(counttype)){
			//电量
			sql += "SUM(t.electric_ac)+SUM(t.electric_dc) as countnums ,";
			sql += "SUM(t.electric_ac) as acvalue ,SUM(t.electric_dc) as dcvalue,SUM(t.electric_app) as appvalue,SUM(t.electric_card) as cardvalue, ";
		}else if("num".equals(counttype)){
			//次数
			sql += "SUM(t.times_ac)+SUM(t.times_dc) as countnums ,";
			sql += "SUM(t.times_ac) as acvalue ,SUM(t.times_dc) as dcvalue,SUM(t.times_app) as appvalue,SUM(t.times_card) as cardvalue, ";
		}
		String time = "";
		if("year".equals(datetype)){
			time = "DATE_FORMAT(t.day,'%Y') as time";
		}else if("month".equals(datetype)){
			time = "DATE_FORMAT(t.day,'%Y-%m') as time";
		}else if("day".equals(datetype)){
			time = "DATE_FORMAT(t.day,'%Y-%m-%d') as time";
		}
		sql+=time;
		sql+=" from (select * from r_charging_data where station_id in("+ids+") and day between '"+start+"' and '"+end+"') t GROUP BY ";
		if("all".equals(type)){
			if("year".equals(datetype)){
				sql+= "DATE_FORMAT(t.day,'%Y')";
			}else if("month".equals(datetype)){
				sql+=  "DATE_FORMAT(t.day,'%Y-%m')";
			}else if("day".equals(datetype)){
				sql+=  "DATE_FORMAT(t.day,'%Y-%m-%d')";
			}
	 	}else{
	 		sql+="t."+type;
	 		if("year".equals(datetype)){
				sql+= ",DATE_FORMAT(t.day,'%Y')";
			}else if("month".equals(datetype)){
				sql+=  ",DATE_FORMAT(t.day,'%Y-%m')";
			}else if("day".equals(datetype)){
				sql+=  ",DATE_FORMAT(t.day,'%Y-%m-%d')";
			}
	 	}
		
		return beanRepository.getAll(sql);
	}
	
}
