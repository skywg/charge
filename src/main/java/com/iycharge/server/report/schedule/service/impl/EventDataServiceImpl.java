package com.iycharge.server.report.schedule.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iycharge.server.domain.repository.BeanRepository;
import com.iycharge.server.report.entity.DataBean;
import com.iycharge.server.report.entity.EventData;
import com.iycharge.server.report.schedule.repository.EventDataRepository;
import com.iycharge.server.report.schedule.service.EventDataService;

/**
 * 告警统计数据操作业务接口实现类
 * @author bwang
 */
@Transactional(readOnly=true)
@Service
public class EventDataServiceImpl implements EventDataService {
    
    @Resource
    private EventDataRepository eventDataRepository;
    @Resource
    private BeanRepository beanRepository;
    @Transactional(readOnly=false)
    @Override
    public boolean save(EventData eventData) {
        if(eventData != null) {
            eventDataRepository.save(eventData);
        }
        return true;
    }
    
    @Transactional(readOnly=false)
    @Override
    public boolean saveAll(List<EventData> eventDataList) {
        if(eventDataList != null && !eventDataList.isEmpty()) {
            int to = 0;
            for(int i=0; i<eventDataList.size(); i=i+100) {
                to = eventDataList.size() < 100 ? eventDataList.size() : (i + 100);
                eventDataRepository.save(eventDataList.subList(i, to));
                i = to;
            }
        }
        return true;
    }
    

	
	@Override
	public List<DataBean> findEventType(String countname,String counttype,String table,String start,String end){
		String sql = "select ";
		sql += "t."+counttype+" as typevalue,SUM(t."+countname+") as eventnums ";
		sql+=" from (select * from "+table+" where day between '"+start+"' and '"+end+"') t GROUP BY ";
		sql += "t."+counttype;
		return beanRepository.findEventType(sql);
	}
	
    
    @Override
	public List<DataBean> findEventNum(String type,String datetype,String start,String end){
		String sql = "select ";
		if("all".equals(type)){
			sql+="'' as typevalue, ";
	 	}else{
	 		sql+= "t."+type+" as typevalue, "; 
	 	}
		sql += "SUM(t.event_level_num) as countnums ,";
		String time = "";
		if("year".equals(datetype)){
			time = "DATE_FORMAT(t.day,'%Y') as time";
		}else if("month".equals(datetype)){
			time = "DATE_FORMAT(t.day,'%Y-%m') as time";
		}else if("day".equals(datetype)){
			time = "DATE_FORMAT(t.day,'%Y-%m-%d') as time";
		}
		sql+=time;
		sql+=" from (select * from r_event_data where day between '"+start+"' and '"+end+"') t GROUP BY ";
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
		return beanRepository.findEventNum(sql);
	}
    
    
    @Override 
   	public List<DataBean> findType(String countname,String counttype,String datetype,String table,String start,String end){
   		String sql = "select ";
   		sql += "SUM(t."+countname+") as countnums, t."+counttype+" as typevalue, ";
   		String time = "";
   		if("year".equals(datetype)){
   			time = "DATE_FORMAT(t.day,'%Y') as time";
   		}else if("month".equals(datetype)){
   			time = "DATE_FORMAT(t.day,'%Y-%m') as time";
   		}else if("day".equals(datetype)){
   			time = "DATE_FORMAT(t.day,'%Y-%m-%d') as time";
   		}
   		sql+=time;
   		sql+=" from (select * from "+table+" where day between '"+start+"' and '"+end+"') t GROUP BY ";
 		if("year".equals(datetype)){
			sql+= " DATE_FORMAT(t.day,'%Y'),t."+counttype+" order by DATE_FORMAT(t.day,'%Y')";
		}else if("month".equals(datetype)){
			sql+=  " DATE_FORMAT(t.day,'%Y-%m'),t."+counttype+" order by DATE_FORMAT(t.day,'%Y-%m')" ;
		}else if("day".equals(datetype)){
			sql+=  " DATE_FORMAT(t.day,'%Y-%m-%d'),t."+counttype+" order by DATE_FORMAT(t.day,'%Y-%m-%d')";
		}
   		return beanRepository.findEventNum(sql);
   	}
    
}
