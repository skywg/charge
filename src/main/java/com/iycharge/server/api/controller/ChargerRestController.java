package com.iycharge.server.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.admin.cache.RCharger;
import com.iycharge.server.admin.cache.RConnector;
import com.iycharge.server.domain.entity.BaseEntity;
import com.iycharge.server.domain.entity.charger.Charger;
import com.iycharge.server.domain.entity.charger.ChargerGun;
import com.iycharge.server.domain.entity.utils.RedisUtil;
import com.iycharge.server.domain.service.ChargerService;
import com.iycharge.server.domain.service.ParamTemplateService;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取电桩数据API
 *
 * @author bwang
 */
@RestController
@RequestMapping("/api/chargers/")
public class ChargerRestController {
    
    @Autowired
    ParamTemplateService paramTemplateService;
    
    @Resource
    private RedisUtil redisUtil;
    
    @Autowired
    ChargerService chargerService;
    
    @RequestMapping("{chargerId}")
    @JsonView(BaseEntity.Detail.class)
    public Charger getById(@PathVariable("chargerId") Long ChargerId) {
        Charger charger = EntityUtil.getCharger(ChargerId);
        checkCharger(charger, ChargerId);
        return charger;
    }
    @RequestMapping(value = "/code")
    @JsonView(BaseEntity.Detail.class)
    public Charger getByCode(@RequestParam("code") String code) {
    	Charger  charger  =chargerService.findByCode(code);
    	Long ChargerId =charger.getId();
        checkCharger(charger, ChargerId);
        return charger;
    }
    @RequestMapping(value = "/qrcode")
    @JsonView(BaseEntity.Detail.class)
    public Charger getByQrcode(@RequestParam("qrcode") String qrcode) {
    	List<Charger>  charger1 = chargerService.findByQrcodeList(qrcode);
    	if (charger1.size()==0) {
			return null;
		}
    	Long ChargerId =  charger1.get(0).getId();
        Charger charger = EntityUtil.getCharger(ChargerId);
        checkCharger(charger, ChargerId);
        return charger;
    }
    public void checkCharger(Charger charger,Long ChargerId){
    	if(charger == null) {
            charger = EntityUtil.getCharger(String.valueOf(ChargerId));
        }
        if(charger != null) {
            if(charger.getStation() != null) {
                charger.setStation(EntityUtil.getStation(charger.getStation().getId()));
            }            
            charger.setPriceTemplate(paramTemplateService.findChargerPrice(ChargerId));
            RCharger rcharger  = (RCharger)redisUtil.get(RedisUtil.PREFIX_CHARGER + charger.getCode());
            if(rcharger != null) {
            	//=======获取枪状态========//
                List<RConnector> rConnectors = rcharger.getConnectorList();
                List<ChargerGun> cgs = charger.getGuns();
                if(null!=rConnectors&&rConnectors.size()>0){
                	for(int i = 0 ; i < rConnectors.size() ; i++){
                		for(int j = 0 ; j < cgs.size() ; j++){
                			if(cgs.get(j)==null||!cgs.get(j).getGunName().equals(rConnectors.get(i).getName())){
                				continue;
                			}
                			cgs.get(j).setStatus(rConnectors.get(i).getStatus());
                		}
                	}
                	charger.setGuns(cgs);
                }
                //=======获取枪状态========//
                charger.setStatus(rcharger.getChargerStatus());
            }
        }
    }
}
