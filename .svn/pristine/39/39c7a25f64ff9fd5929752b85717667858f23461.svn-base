package com.iycharge.server.domain.entity.utils.serializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.iycharge.server.domain.entity.station.Station;

/**
 * 自定义Station序列化器
 * @author bwang
 */
public class JsonStationSerializer extends JsonSerializer<Station> {

    @Override
    public void serialize(Station station, JsonGenerator gen, SerializerProvider serializers)
            throws IOException, JsonProcessingException {
        
        if(station == null) {
            gen.writeNull();
        } else{
            Map<String, Object> map = new HashMap<>();
            map.put("id", station.getId());
            map.put("code", station.getCode());
            map.put("name", station.getName());
            map.put("location", station.getLocation());
            map.put("longitude", station.getLongitude());
            map.put("latitude", station.getLatitude());
            map.put("status", station.getStatus().name());
//            map.put("chargerType", station.getChargerType().getTitle());
//            map.put("stationType", station.getStationType().getTitle());
//            map.put("paymentMethod", station.getPaymentMethod().getTitle());
//            map.put("area", station.getArea().getTitle());
            map.put("price",station.getPrice());
            map.put("operatingStatus", station.getOperatingStatus().getTitle());
            map.put("fee", station.getFee());
            map.put("openTime", station.getOpenTime());
            
            gen.writeObject(map);
        }
        
    }

}
