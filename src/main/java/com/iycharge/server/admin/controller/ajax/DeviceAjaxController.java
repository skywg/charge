package com.iycharge.server.admin.controller.ajax;

import com.iycharge.server.domain.elastic.document.Device;
import com.iycharge.server.domain.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DeviceAjaxController {
    @Autowired
    private DeviceService deviceService;

    @RequestMapping("/admin/ajax/devices/map")
    public List<Device> mapDevice(@PageableDefault(size = 20000) Pageable pageable) {

        return deviceService.findAll(pageable).getContent();

    }
}
