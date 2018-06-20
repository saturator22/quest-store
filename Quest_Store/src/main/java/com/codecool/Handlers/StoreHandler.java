package com.codecool.Handlers;

import com.codecool.Model.Session;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;

public class StoreHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String response = "";
        Session session = Session.getInstance();
        HttpCookie cookie = session.setCookieInHandler(exchange);

        if(method.equals("GET")) {
            response += "STORE VIEW";
            exchange.sendResponseHeaders(200, response.length());
        }
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
