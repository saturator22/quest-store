package com.codecool.Helper;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QSHelperTest {
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @DisplayName("Test redirecting")
    @Test
    void redirect() throws IOException {
        HttpExchange httpExchangeSpy = spy(HttpExchange.class);
        Headers requestHeadersMock = spy(Headers.class);
        Headers responseHeadersMock = spy(Headers.class);

        when(httpExchangeSpy.getRequestHeaders()).thenReturn(requestHeadersMock);
        when(httpExchangeSpy.getResponseHeaders()).thenReturn(responseHeadersMock);

        when(requestHeadersMock.get("HOST")).thenReturn(new ArrayList<>(Arrays.asList("1.1.1.1")));

        String whereTo = "/sample_endpoint";
        QSHelper.redirect(httpExchangeSpy, whereTo);

        String expected = "http://1.1.1.1/sample_endpoint";
        assertEquals(expected, responseHeadersMock.get("Location").get(0));
    }
}