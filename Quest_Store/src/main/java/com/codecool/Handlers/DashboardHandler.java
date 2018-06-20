package com.codecool.Handlers;

import com.codecool.DAO.LoginDAO;
import com.codecool.Model.LoginData;
import com.codecool.Model.Session;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;

public class DashboardHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String method = exchange.getRequestMethod();
        String response = "";
        Session session = Session.getInstance();
        HttpCookie cookie = session.setCookieInHandler(exchange);

        LoginDAO dao = new LoginDAO();
        Integer userId = session.getUserIdBySesssion(cookie);
        System.out.println(userId);
        LoginData user = null;
        user = dao.getLoginData(userId);

        if (method.equals("GET") && session.isValid(cookie.getValue())) {
            String whereTo = "";
            if (user == null) {
                whereTo = "/login";
            }
            switch (user.getRoleId()) {
                case 1:
                    whereTo = "/admin";
                    break;
                case 2:
                    whereTo = "/mentor";
                    break;
                case 3:
                    whereTo = "/store";
                    break;
            }
            String hostPort = exchange.getRequestHeaders().get("HOST").get(0);
            exchange.getResponseHeaders().set("Location", "http://" + hostPort + whereTo);
            exchange.sendResponseHeaders(301, -1);
        }
//        if (method.equals("GET") && !session.isValid(cookie.getValue())) {
//            String hostPort = exchange.getRequestHeaders().get("HOST").get(0);
//            String whereTo = "/login";
//            exchange.getResponseHeaders().set("Location", "http://" + hostPort + whereTo);
//            exchange.sendResponseHeaders(301, -1);
//        }


        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
