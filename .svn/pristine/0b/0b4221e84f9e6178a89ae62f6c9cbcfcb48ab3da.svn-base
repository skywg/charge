package com.iycharge.server.admin.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.iycharge.server.domain.entity.charger.ChargerStatus;

/**
 * redis中电桩的数据结构
 * @author bwang
 */
public class RCharger implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2023554943761840263L;
    
    /**
     * 电桩id
     */
    private Long id;
    
    /**
     * 电桩编号
     */
    private String code;
    
    /**
     * 电桩名称
     */
    private String name;
    
    /**
     * 电桩状态
     */
    private ChargerStatus chargerStatus;
    
    /**
     * 离线时间
     */
    private Date offlineDate;
    
    private List<RConnector> connectorList;
    
    public RCharger() {
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChargerStatus getChargerStatus() {
        return chargerStatus;
    }

    public void setChargerStatus(ChargerStatus chargerStatus) {
        this.chargerStatus = chargerStatus;
    }

    public List<RConnector> getConnectorList() {
        return connectorList;
    }

    public void setConnectorList(List<RConnector> connectorList) {
        this.connectorList = connectorList;
    }
    
    public Date getOfflineDate() {
        return offlineDate;
    }

    public void setOfflineDate(Date offlineDate) {
        this.offlineDate = offlineDate;
    }

    public RConnector getRConnectorByName(String connName) {
        if(connectorList != null && connectorList.size() > 0) {
            for(RConnector connector : connectorList) {
                if(connector.getName().equals(connName)) {
                    return connector;
                }
            }
        }
        return null;
    }
    
    public RConnector getRConnectorByCode(String code) {
        if(connectorList != null && connectorList.size() > 0) {
            for(RConnector connector : connectorList) {
                if(connector.getCode().equals(code)) {
                    return connector;
                }
            }
        }
        return null;
    }
    
    public void addRConnector(RConnector connector) {
        if(connectorList == null) {
            connectorList = new ArrayList<>();
        }
        if(connectorList.isEmpty()) {
            connectorList.add(connector);
        } else {
            for(RConnector conn : connectorList) {
                if(conn.equals(connector)) {
                    
                }
            }
        }
    }
}