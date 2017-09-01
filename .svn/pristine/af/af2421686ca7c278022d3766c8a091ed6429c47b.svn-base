package com.iycharge.server.domain.entity.utils.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;

public class ImageJsonSerializer extends JsonSerializer<String> {
    
    @Value("${charger.image.server.domain}")
    private String imageServer;
    
    @Override
    public void serialize(String src, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException, JsonProcessingException {
        if (src == null || src.isEmpty()) {
            jsonGenerator.writeNull();
        } else {
            jsonGenerator.writeString(imageServer + src);
        }
    }
}
