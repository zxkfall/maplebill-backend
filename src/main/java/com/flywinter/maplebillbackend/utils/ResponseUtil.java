package com.flywinter.maplebillbackend.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flywinter.maplebillbackend.entity.ResponseResult;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.flywinter.maplebillbackend.constant.Constants.APPLICATION_JSON_CHARSET_UTF_8;

/**
 * Created by IntelliJ IDEA
 * User:Xingkun Zhang
 * Date:2022/6/24 23:27
 * Description:
 */
public class ResponseUtil {

    private ResponseUtil() {
    }

    public static void writeResponseResult(HttpServletResponse response, ResponseResult<Object> responseResult) throws IOException {
        final var objectMapper = new ObjectMapper();
        final var result = objectMapper.writeValueAsString(responseResult);
        response.setContentType(APPLICATION_JSON_CHARSET_UTF_8);
        try (PrintWriter writer = response.getWriter()
        ) {
            writer.write(result);
            writer.flush();
        }
    }
}
