package com.example.targetapplication.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class CustomJsonSerializer extends JsonSerializer {
    public static final ObjectMapper objectMapper = createMapper();

    private static final ObjectMapper createMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);

        return mapper;
    }

    public CustomJsonSerializer() {
        super(objectMapper);
    }
}
