package com.codecool.Model;

import com.sun.net.httpserver.HttpExchange;

import java.net.HttpCookie;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Session {
    private Map <String, Integer> sessions;

    private static final Session instance = new Session();

    private Session() {
        this.sessions = new HashMap<>();
    }

    public static Session getInstance(){
        return instance;
    }

    public void add(String sessionId, int userId) {
        this.sessions.put(sessionId, userId);
    }

    public void remove(String sessionId) {
        this.sessions.remove(sessionId);
    }

    public boolean isValid(String sessionId) {
        if(sessionId == null) return false;
        return this.sessions.containsKey(sessionId);
    }

    public Integer getUserIdBySesssion(HttpExchange exchange) {
        return this.sessions.get(exchange.getRequestHeaders().getFirst("Cookie"));
    }

    public HttpCookie setCookieInHandler(HttpExchange exchange) {
        UUID uuid = UUID.randomUUID();
        HttpCookie cookie = null;
        String cookieStr = exchange.getRequestHeaders().getFirst("Cookie");

        if (cookieStr != null) {  // Cookie already exists
            cookie = HttpCookie.parse(cookieStr).get(0);
        } else { // Create a new cookie
            cookie = new HttpCookie("sessionId", String.valueOf(uuid));
            exchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
        }
        return cookie;
    }
}
