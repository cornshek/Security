package com.pd.util;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonStrUtil {

    public static String toPrettyFormat(String jsonStr) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Object obj = objectMapper.readValue(jsonStr, Object.class);

        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    }
}
