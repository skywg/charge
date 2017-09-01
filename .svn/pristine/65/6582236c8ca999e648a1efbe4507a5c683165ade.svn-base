package com.iycharge.server.api.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.admin.cache.StationStatisticTask.RStationData;
import com.iycharge.server.domain.elastic.document.Device;
import com.iycharge.server.domain.entity.station.Station;
import com.iycharge.server.domain.entity.utils.RedisUtil;
import com.iycharge.server.domain.service.DeviceService;

@RestController
@RequestMapping("/api/devices")
public class DeviceRestController {
    @Autowired
    private DeviceService deviceService;
    
    @Autowired
    private RedisUtil redisUtil;
    
    @RequestMapping("/search")
    public List<Device> search(@PageableDefault(size = 30) Pageable pageable,
                               @RequestParam(value = "longitude", defaultValue = "5") BigDecimal longitude,
                               @RequestParam(value = "latitude", defaultValue = "5") BigDecimal latitude,
                               @RequestParam(value = "search", defaultValue = "") String search,
                               @RequestParam(value = "idleonly", defaultValue = "false") Boolean idelonly
    ) {
        List<Device> deviceList = deviceService.searchByDistance(longitude, latitude, search, idelonly, pageable).getContent();        
        if(deviceList == null || deviceList.isEmpty()) {
            return deviceList;
        }
        
        Map<Long, Device> deviceMap = new HashMap<>();
        for(Device device :deviceList) {
            Station station = EntityUtil.getStation(device.getStationId());
            //过滤掉已经删除的站点
            if(station != null && station.getDelStatus().equals("normal") 
                               && station.getSearchId().equals(device.getId())) {
                deviceMap.put(device.getStationId(), device);
            }
        }
        
        //获取站点状态、电桩数量、空闲电桩数量
        Collection<Object> rstationList = redisUtil.getByPattern(RedisUtil.PREFIX_STATION + "*");
        if(rstationList != null && rstationList.size() > 0) {
            for(Object obj : rstationList) {
                RStationData rstation = (RStationData)obj;
                if(deviceMap.containsKey(rstation.getStationId())) {
                    deviceMap.get(rstation.getStationId()).setStatus(rstation.getStatus());
                    deviceMap.get(rstation.getStationId()).setTotalCount(rstation.getTotalNum());
                    deviceMap.get(rstation.getStationId()).setIdleCount(rstation.getIdleNum());
                }
            }
        }
        
        deviceList = new ArrayList<>(deviceMap.values());
        Collections.sort(deviceList, new Comparator<Device>(){
            public int compare(Device o1, Device o2) {
                return o1.getDistance() > o2.getDistance() ? 1 : -1;
            }         
        });
        
        if(idelonly) {
            for(Iterator<Device> iter = deviceList.iterator(); iter.hasNext(); ) {
                Device device = iter.next();
                if(device.getIdleCount() == 0) {
                    iter.remove();
                }
            }
        }
        return deviceList;
    }
}
