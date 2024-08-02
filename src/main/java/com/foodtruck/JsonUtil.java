package com.foodtruck;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


import java.io.IOException;


public class JsonUtil {

    private JsonUtil() {
    }

    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Include.ALWAYS
     * Include.NON_EMPTY
     * Include.NON_DEFAULT
     *
     * @param include
     * @return
     */
    private static ObjectMapper getMapper(JsonInclude.Include include) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            if (include != null) {
                objectMapper.setSerializationInclusion(include);
            } else {
                objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
            }
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            objectMapper.findAndRegisterModules();
        }
        return objectMapper;

    }

    private static ObjectMapper getMapper() {
        return getMapper(null);
    }

    public static String toJson(Object object) {
        if (object == null) {
            return "";
        }
        try {
            return getMapper().writeValueAsString(object);
        } catch (IOException e) {
            return "";
        }
    }


}
