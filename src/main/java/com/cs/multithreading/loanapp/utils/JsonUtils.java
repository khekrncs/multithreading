/**
 * @author KK
 * @version 1.0
 */

package com.cs.multithreading.loanapp.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class JsonUtils {

    // Thread-safe singleton ObjectMapper
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Convert object to JSON string
     * @param object Object to convert
     * @return JSON string representation
     * @throws RuntimeException if conversion fails
     */
    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert object to JSON: " + e.getMessage(), e);
        }
    }

    /**
     * Convert JSON string to object of specified type
     * @param json JSON string
     * @param clazz Target class type
     * @param <T> Generic type
     * @return Object of type T
     * @throws RuntimeException if conversion fails
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert JSON to object: " + e.getMessage(), e);
        }
    }

    /**
     * Pretty print JSON string
     * @param object Object to convert
     * @return Formatted JSON string
     */
    public static String toPrettyJson(Object object) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert object to pretty JSON: " + e.getMessage(), e);
        }
    }
}
