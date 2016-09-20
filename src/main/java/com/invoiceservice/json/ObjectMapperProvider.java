package com.invoiceservice.json;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

/**
 * Jackson ObjectMapper provider 
 * 
 */

@Provider
@Component
@Produces(MediaType.APPLICATION_JSON)
public class ObjectMapperProvider extends JacksonJaxbJsonProvider {
    static ObjectMapper jsonMapper;

    static {
        jsonMapper = new ObjectMapper();
        jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        jsonMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        jsonMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        jsonMapper.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, false);
        // jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        jsonMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    public ObjectMapperProvider() {
        super();
        setMapper(jsonMapper);
    }
    
    public static ObjectMapper getDefaultObjectMapper() {
        return jsonMapper;
    }
}