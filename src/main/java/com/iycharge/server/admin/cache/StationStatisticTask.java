package com.iycharge.server.admin.cache;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.iycharge.server.domain.entity.charger.ChargerStatus;
import com.iycharge.server.domain.entity.station.Station;
import com.iycharge.server.domain.entity.station.StationStatus;
import com.iycharge.server.domain.entity.utils.RedisUtil;

/**
 * 定时计算站点状态
 * @author bwang
 */
@Component
public class StationStatisticTask {
    
    @Resource
    private RedisUtil redisUtil;
    
    
    /**
     * 每隔5秒计算一次充电站的状态,电桩数,空闲电桩数
     */
    @Scheduled(cron="0/5 * *  * * ? ")
    public void calcStationStatus() {
        Collection<Station> stationList = EntityUtil.getAllStations();
        if(stationList != null && stationList.size() > 0) {
            Map<Long, RStationData> map = new HashMap<>();
            for(Station station : stationList) {
                RStationData data = new RStationData();
                data.setStationId(station.getId());
                data.setStatus(StationStatus.OFFLINE);                
                map.put(station.getId(), data);
            }
            
            //查询所有的电桩的状态数据
            Collection<Object> rchargerList = redisUtil.getByPattern(RedisUtil.PREFIX_CHARGER + "*");
            if(rchargerList != null && rchargerList.size() > 0) {
                Map<Long, Set<ChargerStatus>> statusMap = new HashMap<>();
                for(Object obj : rchargerList) {
                    RCharger rcharger = (RCharger)obj;
                    
                    if(rcharger.getId() == null) {
                        continue;
                    }
                    
                    Station station = EntityUtil.getStationByChargerId(rcharger.getId());
                    if(station == null) {
                        continue;
                    }
                    
                    RStationData rstation = map.get(station.getId());
                    if(rstation == null) {
                        continue;
                    }
                    
                    Set<ChargerStatus> statusSet = statusMap.get(station.getId());
                    if(statusSet == null) {
                        statusSet = new HashSet<>();
                        statusMap.put(station.getId(), statusSet);
                    }
                    
                    statusSet.add(rcharger.getChargerStatus());
                    
                    rstation.setTotalNum(rstation.getTotalNum() + 1);
                    
                    if(rcharger.getChargerStatus() == ChargerStatus.IDLE) {
                        rstation.setIdleNum(rstation.getIdleNum() + 1);
                    } else if (rcharger.getChargerStatus() == ChargerStatus.CHARGING) {
                        rstation.setChargingNum(rstation.getChargingNum() + 1);
                    } else if (rcharger.getChargerStatus() == ChargerStatus.REPAIR) {
                        rstation.setFaultNum(rstation.getFaultNum() + 1);
                    } else {
                        rstation.setOfflineNum(rstation.getOfflineNum() + 1);
                    }
                }
                
                //计算桩的状态
                //规则： 
                //     故障 : 至少有一个桩有故障
                //     充电 ：至少有一个桩在充电
                //     空闲 ：至少有一个桩是空闲
                //     离线 ：所有桩都离线
                // 状态计算优先级： 故障 > 充电  > 空闲  > 离线
                for(Long stationId : statusMap.keySet()) {
                    if(map.containsKey(stationId)) {
                        Set<ChargerStatus> statusSet = statusMap.get(stationId);                       
                        if (statusSet.contains(ChargerStatus.REPAIR)) {
                            map.get(stationId).setStatus(StationStatus.FAULT);
                        } else if (statusSet.contains(ChargerStatus.CHARGING)) {
                            map.get(stationId).setStatus(StationStatus.CHARGING);
                        }  else if (statusSet.contains(ChargerStatus.IDLE)) {
                            map.get(stationId).setStatus(StationStatus.IDLE);
                        } else {
                            map.get(stationId).setStatus(StationStatus.OFFLINE);
                        }
                    }
                }
            }
            
            for(RStationData rstation : map.values()) {
                redisUtil.set(RedisUtil.PREFIX_STATION + rstation.getStationId(), rstation);
            }
        }
    }
    
    public static class RStationData {
        
        /**
         * 站点id
         */
        private Long stationId;
        
        /**
         * 站点状态
         */
        private StationStatus status;
        
        /**
         * 电桩数
         */
        private int totalNum;
        
        /**
         * 空闲电桩数
         */
        private int idleNum;
        
        /**
         * 故障电桩数
         */
        private int faultNum;
        
        /**
         * 正在充电的电桩数
         */
        private int chargingNum;
        
        /**
         * 离线电桩数
         */
        private int offlineNum;
        
        public RStationData() {
            
        }

        public Long getStationId() {
            return stationId;
        }

        public void setStationId(Long stationId) {
            this.stationId = stationId;
        }

        public StationStatus getStatus() {
            return status;
        }

        public void setStatus(StationStatus status) {
            this.status = status;
        }

        public int getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
        }

        public int getIdleNum() {
            return idleNum;
        }

        public void setIdleNum(int idleNum) {
            this.idleNum = idleNum;
        }

        public int getFaultNum() {
            return faultNum;
        }

        public void setFaultNum(int faultNum) {
            this.faultNum = faultNum;
        }

        public int getChargingNum() {
            return chargingNum;
        }

        public void setChargingNum(int chargingNum) {
            this.chargingNum = chargingNum;
        }

        public int getOfflineNum() {
            return offlineNum;
        }

        public void setOfflineNum(int offlineNum) {
            this.offlineNum = offlineNum;
        }
    }
}
