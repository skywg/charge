package com.iycharge.server.report.schedule.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iycharge.server.domain.repository.BeanRepository;
import com.iycharge.server.report.entity.DataBean;
import com.iycharge.server.report.entity.RecordData;
import com.iycharge.server.report.schedule.repository.RecordDataRepository;
import com.iycharge.server.report.schedule.service.RecordDataService;

/**
 * 充值记录数统计业务接口实现类
 * @author zw
 */
@Transactional(readOnly=true)
@Service
public class RecordDataServiceImpl implements RecordDataService {
    
    @Resource
    private RecordDataRepository recordDataRepository;
    @Resource
    private BeanRepository beanRepository;
    @Transactional(readOnly=false)
    @Override
    public RecordData save(RecordData recordData) {
        return recordDataRepository.save(recordData);
    } 
    
    @Override 
   	public List<RecordData> findMoneyCount(String datetype,String start,String end){
   		String sql = "select ";
   		sql += "SUM(t.person_record) as personRecord,SUM(t.person_cost) as personCost,SUM(t.company_cost) as companyCost,SUM(t.company_record) as companyRecord,SUM(t.cost_total) as costTotal, SUM(t.record_total) as recordTotal, ";
   		String time = "";
   		if("year".equals(datetype)){
   			time = "DATE_FORMAT(t.day,'%Y') as time";
   		}else if("month".equals(datetype)){
   			time = "DATE_FORMAT(t.day,'%Y-%m') as time";
   		}else if("day".equals(datetype)){
   			time = "DATE_FORMAT(t.day,'%Y-%m-%d') as time";
   		}
   		sql+=time;
   		sql+=" from (select * from r_record_data where day between '"+start+"' and '"+end+"') t GROUP BY ";
 		if("year".equals(datetype)){
			sql+= " DATE_FORMAT(t.day,'%Y') order by DATE_FORMAT(t.day,'%Y')";
		}else if("month".equals(datetype)){
			sql+=  " DATE_FORMAT(t.day,'%Y-%m') order by DATE_FORMAT(t.day,'%Y-%m')" ;
		}else if("day".equals(datetype)){
			sql+=  " DATE_FORMAT(t.day,'%Y-%m-%d') order by DATE_FORMAT(t.day,'%Y-%m-%d')";
		}
   		return beanRepository.findMoneyCount(sql);
   	}
}
