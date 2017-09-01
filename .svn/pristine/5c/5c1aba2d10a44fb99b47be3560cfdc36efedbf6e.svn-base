package com.iycharge.server.admin.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.domain.entity.charger.Charger;
import com.iycharge.server.domain.entity.dict.DictCategory;
import com.iycharge.server.domain.entity.dict.DictData;
import com.iycharge.server.domain.entity.event.EventCode;
import com.iycharge.server.domain.entity.operator.Operator;
import com.iycharge.server.domain.entity.station.Station;
import com.iycharge.server.domain.service.ChargerService;
import com.iycharge.server.domain.service.DictCategoryService;
import com.iycharge.server.domain.service.DictDataService;
import com.iycharge.server.domain.service.EventCodeService;
import com.iycharge.server.domain.service.OperatorService;
import com.iycharge.server.domain.service.StationService;

/**
 * web启动时，加载设备信息
 * @author bwang
 */
@Component
public class LoadEntityRunner {
   
    private Logger logger = LoggerFactory.getLogger(LoadEntityRunner.class);
    
    @Autowired
    private OperatorService operatorService;
    
    @Autowired
    private StationService stationService;
    
    @Autowired
    private ChargerService chargerService;
    
    @Autowired
    private DictDataService dictDataService;
    
    @Autowired
    private DictCategoryService dictCategoryService;
    
    @Autowired 
    private EventCodeService eventCodeService;
      
    public void run() throws Exception {
        try {
            List<Operator> operators = operatorService.findListAll();           
            List<Station>  stations  = stationService.findListAll();
            List<Charger>  chargers  = chargerService.findAll();
            List<DictData> dictDatas = dictDataService.findAll();
            List<DictCategory> dictCategorys = dictCategoryService.findAll();
            List<EventCode> eventCodes = eventCodeService.findAll(true);
            
            EntityUtil.addDictDatas(dictDatas);
            EntityUtil.addDictCategorys(dictCategorys);
            EntityUtil.addOperators(operators);
            EntityUtil.addStations(stations);
            EntityUtil.addChargers(chargers);
            EntityUtil.addEventCodes(eventCodes);
            
        } catch (Exception e) {
            logger.error("load entity object error!", e);
            throw e;
        }
    }  
}
