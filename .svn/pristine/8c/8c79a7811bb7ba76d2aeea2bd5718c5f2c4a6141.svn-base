package com.iycharge.server.domain.entity.utils.serializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 *
 * @author bwang
 */
public class JSonDateSerializer extends JsonSerializer<Date> {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    @Override
    public void serialize(Date date, JsonGenerator gen, SerializerProvider serializers)
            throws IOException, JsonProcessingException {
        if(date == null) {
            gen.writeNull();
        } else {
             gen.writeString(sdf.format(date));  
        }
    }

}
