package com.iycharge.server.domain.service;

import com.iycharge.server.domain.elastic.document.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface DeviceService {
    public Page<Device> searchByDistance(BigDecimal longitude, BigDecimal latitude, String search, Boolean idleonly, Pageable pageable);

    public Device save(Device device);

    public Page<Device> findAll(Pageable pageable);
    
    /**
     * 删除索引
     * @param device
     * @return
     */
    void delete(Device device);
}
