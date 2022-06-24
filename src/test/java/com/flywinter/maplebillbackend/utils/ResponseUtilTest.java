package com.flywinter.maplebillbackend.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flywinter.maplebillbackend.entity.ResponseResult;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import static com.flywinter.maplebillbackend.constant.Constants.APPLICATION_JSON_CHARSET_UTF_8;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by IntelliJ IDEA
 * User:Xingkun Zhang
 * Date:2022/6/25 0:17
 * Description:
 */
class ResponseUtilTest {

    @Test
    void writeResponseResult() throws IOException {
        //given
        final var objectMapper = new ObjectMapper();
        final var successResult = ResponseResult.success();
        final var responseJson = objectMapper.writeValueAsString(successResult);

        final var mockResponse = mock(HttpServletResponse.class);
        final var mockPrinter = mock(PrintWriter.class);
        when(mockResponse.getWriter()).thenReturn(mockPrinter);
        //when
        ResponseUtil.writeResponseResult(mockResponse, successResult);
        //then
        verify(mockResponse).setContentType(APPLICATION_JSON_CHARSET_UTF_8);
        verify(mockResponse).getWriter();

        final var inOrder = inOrder(mockPrinter);
        inOrder.verify(mockPrinter,times(1)).write(responseJson);
        inOrder.verify(mockPrinter).flush();
        inOrder.verify(mockPrinter).close();


    }
}
