package com.iycharge.server.domain.entity.utils.serializer;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.iycharge.server.domain.entity.charger.Charger;

/**
 *
 * @author bwang
 */
public class JsonChargerSerializer extends JsonSerializer<Charger> {

    /* (non-Javadoc)
     * @see com.fasterxml.jackson.databind.JsonSerializer#serialize(java.lang.Object, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider)
     */
    @Override
    public void serialize(Charger charger, JsonGenerator gen, SerializerProvider serializers)
            throws IOException, JsonProcessingException {
        if(charger == null) {
            gen.writeNull();
        } else {
            Map<String, String> map = new TreeMap<>();
            if(charger.getStation() != null){
                map.put("stationNo", charger.getStation().getCode());
                map.put("stationName", charger.getStation().getName());
            }
            map.put("chargeNo"  , charger.getCode());
            map.put("chargeName", charger.getName());
            gen.writeObject(map);
        }
    }

}
