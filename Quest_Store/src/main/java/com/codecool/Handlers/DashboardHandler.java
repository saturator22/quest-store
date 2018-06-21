package com.codecool.Handlers;

import com.codecool.DAO.LoginDAO;
import com.codecool.Helper.QSHelper;
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
        LoginData user;

        try {
            user = dao.getLoginData(userId);
        } catch (Exception e) {
            user = null;
            System.out.println(e.getMessage());
        }

        if (user == null && method.equals("GET") && !session.isValid(cookie.getValue())) {
            QSHelper.redirect(exchange, "/login");
        }

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
            QSHelper.redirect(exchange, whereTo);
        }
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
