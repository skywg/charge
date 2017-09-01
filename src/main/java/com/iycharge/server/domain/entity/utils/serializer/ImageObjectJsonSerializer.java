package com.iycharge.server.domain.entity.utils.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.iycharge.server.domain.entity.Image;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;

public class ImageObjectJsonSerializer extends JsonSerializer<Image> {
    
    @Value("${charger.image.server.domain}")
    private String imageServer;
    
    @Override
    public void serialize(Image image, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException, JsonProcessingException {
        
        if (image == null || image.getSrc().isEmpty()) {
            jsonGenerator.writeNull();
        } else {
            jsonGenerator.writeString(imageServer + image.getSrc());
        }
    }
}
