package vn.vifo.api.Ultils;

import org.slf4j.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonParserUtils {
    private static Logger log;

    public static <T> String stringify(T valueData) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING); 
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        try {
            return objectMapper.writeValueAsString(valueData);
        } catch (JsonProcessingException e) {
            log.error("ERROR: {}", e.getMessage());
            return "";
        }
    }
}
