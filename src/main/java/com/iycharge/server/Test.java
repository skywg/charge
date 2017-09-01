package com.iycharge.server;

import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.admin.cache.RCharger;
import com.iycharge.server.admin.cache.RConnector;
import com.iycharge.server.ccu.core.ClientChannelMap;
import com.iycharge.server.domain.entity.Image;
import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.charger.Charger;
import com.iycharge.server.domain.entity.charger.ChargerStatus;
import com.iycharge.server.domain.entity.station.Station;
import com.iycharge.server.domain.entity.station.StationStatus;
import com.iycharge.server.domain.entity.utils.RedisUtil;
import com.iycharge.server.domain.service.AccountService;
import com.iycharge.server.domain.service.ChargerService;
import com.iycharge.server.domain.service.StationService;

import io.netty.channel.socket.SocketChannel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class Test {
    @Autowired
    private ChargerService chargerService;
    @Autowired
    private StationService stationService;
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private RedisUtil redisUtil;
    
    @RequestMapping(value = "/test")
    public String test() {
        Account account = new Account();
        //account.setEmail(new EmailAddress("123456@163.com"));
        account.setPhone("12345678910");
        accountService.save(account);
        for (int i = 0; i < 100; i++) {
            Charger charger = new Charger();
            Station station = new Station();
            charger.setName("name" + i);
            Image image = new Image();
            image.setWidth(300);
            image.setHeight(300);
            image.setSrc("http://pic.sc.chinaz.com/files/pic/pic9/201508/apic14052.jpg");
            List<Image> images = new ArrayList<>();
            images.add(image);
            charger.setCode("code" + i);
            charger.setType("AC");
            charger.setDescription("description" + i);

            station.setProvince("北京");
            station.setCity("city" + i);
            station.setAddress("address" + i);
            station.setName("name" + i);
            station.setImages(images);
            Set<Charger> chargers = new HashSet<>();
            chargers.add(charger);
            station.setChargers(chargers);
            station.setLatitude(BigDecimal.valueOf(i));
            station.setLongitude(BigDecimal.valueOf(i));
            station.setStatus(StationStatus.IDLE);
            station.setPrice(BigDecimal.valueOf(i));

            //Price price = new Price();
/*            price.setType(PriceLevel.ALL);
            price.setPrice(BigDecimal.ONE);
            price.setPriceTimeStartHour(i);
            price.setPriceTimeStartMinute(i);
            price.setPriceTimeEndHour(i);
            price.setPriceTimeEndMinute(i);*/

            //priceService.save(price);
            chargerService.save(charger);
            stationService.save(station);
        }
        return "ok";
    }
    
    @RequestMapping("/admin/test2")
    public ResponseEntity<?> test2() {
        Map<String, String> map = new HashMap<>();
        map.put("errorCode", "422");
        map.put("errorDesc", "手机号已被注册");
        return new ResponseEntity<Map>(map, HttpStatus.OK);
    }
    
/*    public static void main(String[] args) {
        String str = "68190081485402010000110010F0ADBA0DBA0DBA0DADF0ADBA0D4416680C0183485402010000110010000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000004316";
        System.out.println(str.length() / 2);
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<str.length(); i=i+2){
            sb.append(str.charAt(i)).append(str.charAt(i+1)).append(" ");
        }
        System.out.println(sb.toString());
    }*/
    
    @RequestMapping("/css/add")
    public void addRCharger() {
        RCharger charger = new RCharger();
        charger.setCode("10002");
        charger.setName("1号桩");
        
        List<RConnector> connList = new ArrayList<>();
        
        RConnector conn = new RConnector();
        conn.setCode("1000102");
        conn.setName("A");
        conn.setStatus(ChargerStatus.CHARGING);
        conn.setElectricity(12.05);
        conn.setOutputCurrent(100);
        
        connList.add(conn);
        
        conn = new RConnector();
        conn.setCode("1000102");
        conn.setName("A");
        conn.setStatus(ChargerStatus.CHARGING);
        conn.setElectricity(13.05);
        conn.setOutputCurrent(120);
        
        connList.add(conn);
        
        charger.setConnectorList(connList);
        
        redisUtil.set(charger.getCode(), charger);
    }
    
    @RequestMapping("/css/get/{key}")
    public RCharger getRcharger(@PathVariable String key) {
        return (RCharger)redisUtil.get(RedisUtil.PREFIX_CHARGER + key);
    }
    
    @RequestMapping("/css/get/all")
    public Collection<RCharger> getAllRcharger(HttpServletRequest req, HttpServletResponse resp) {
        String keysStr = req.getParameter("key");
        Collection<String> keys = new HashSet<>();
        if(keysStr != null && keysStr.length() > 0) {
            keys.addAll(Arrays.asList(keysStr.split(",")));
        }
        Collection<RCharger> result = new ArrayList<>();
        Collection<Object> list = redisUtil.getByPattern(RedisUtil.PREFIX_CHARGER + "*");
        if(list != null && list.size() > 0) {
            for(Object item : list) {
                result.add((RCharger)item);
            }
        }
        return result;
    }
    
    @RequestMapping("/css/get/channel")
    public Map<String, SocketChannel> getClientConn() {
        return ClientChannelMap.getAll();
    }
    
    @RequestMapping("/css/get/charger")
    public Collection<Charger> getAllCharger() {
        return EntityUtil.getAllChargers();
    }
    
/*    @RequestMapping("/css/get/price/all")
    public Map<Long, PriceTemplate> getAllChargersPrice() {
        return priceTemplateService.findAllchargerPrice();
    }
    
    @RequestMapping("/css/get/price")
    public PriceTemplate getChargerPrice(@RequestParam("id")Long chargerId) {
        return priceTemplateService.findChargerPrice(chargerId);
    }*/
    
/*    public static void main(String[] args) {
        RCharger charger = new RCharger();
        charger.setCode("10002");
        charger.setName("1号桩");
        
        Collection<RConnector> connList = new HashSet<>();
        
        RConnector conn = new RConnector();
        conn.setCode("1000102");
        conn.setName("A");
        conn.setType("01");
        conn.setStatus(ChargerStatus.CHARGING);
        conn.setElectricity(12.05);
        conn.setOutputCurrent(100);
        
        connList.add(conn);
        
        conn = new RConnector();
        conn.setCode("1000102");
        conn.setName("A");
        conn.setType("01");
        conn.setStatus(ChargerStatus.CHARGING);
        conn.setElectricity(13.05);
        conn.setOutputCurrent(120);
        
        connList.add(conn);
        
        //charger.setConnectorList(connList);
        
        System.out.println(((RConnector)connList.toArray()[0]).getElectricity());
    }*/ 
}
