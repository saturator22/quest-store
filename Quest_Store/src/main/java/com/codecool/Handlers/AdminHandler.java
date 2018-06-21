package com.codecool.Handlers;

import com.codecool.DAO.AdminDAO;
import com.codecool.DAO.DAO;
import com.codecool.DAO.LoginDAO;
import com.codecool.DAO.MentorDAO;
import com.codecool.Helper.PasswordHash;
import com.codecool.Helper.QSHelper;
import com.codecool.Model.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String method = exchange.getRequestMethod();
        String response = "";
        Session session = Session.getInstance();
        URI uri = exchange.getRequestURI();
        System.out.println(uri.getPath());
//
//        for (Map.Entry <String, Integer> entry : session.getSessions().entrySet()) {
//            System.out.println(entry.getKey() + "/" + entry.getValue());
//        }

        HttpCookie cookie = session.setCookieInHandler(exchange);
        LoginDAO dao = new LoginDAO();
        Integer userId = session.getUserIdBySesssion(cookie);
        LoginData user = null;
        AdminDAO adminDAO = new AdminDAO();
        MentorDAO mentorDAO = new MentorDAO();

        user = dao.getLoginData(userId);

        if (user != null && method.equals("GET") && session.isValid(cookie.getValue())) { // user is an admin
            if (userIsAdmin(user)) {
                if (uri.getPath().equals("/admin")) {
                    response = renderView("static/templates/pages/admin.twig", null, "");
                    exchange.sendResponseHeaders(200, response.length());
                } else if (uri.getPath().equals("/admin/level")) {
                    response = renderView("static/templates/pages/adminAddLevel.twig", "levels", adminDAO.getLevels());
                    exchange.sendResponseHeaders(200, response.length());

                } else if (uri.getPath().equals("/admin/class")) {

                    response = renderView("static/templates/pages/adminAddClass.twig", "classes", mentorDAO.getClassRooms());
                    exchange.sendResponseHeaders(200, response.length());

                } else if (uri.getPath().equals("/admin/mentor")) {
                    response = renderView("static/templates/pages/adminAddMentor.twig", null, "");
                    exchange.sendResponseHeaders(200, response.length());
                }
            } else {
                QSHelper.redirect(exchange, "/dashboard");
            }
        }
        if (user == null && method.equals("GET") && !session.isValid(cookie.getValue())) {
            QSHelper.redirect(exchange, "/login");
        }

        if (method.equals("POST") && !session.isValid(cookie.getValue())) {
            exchange.sendResponseHeaders(401, -1);
        }

        if (method.equals("POST") && session.isValid(cookie.getValue())) {
            if (userIsAdmin(user)) {
                if (uri.getPath().equals("/admin/level")) {

                    Map <String, String> inputs = null;
                    try {
                        inputs = QSHelper.getPostStringData(exchange);
                    } catch (UnsupportedEncodingException e) {
                        System.err.println(e.getMessage());
                    }
                    Integer levelNumber = Integer.valueOf(inputs.get("level"));
                    Integer coolcoins = Integer.valueOf(inputs.get("coolcoins"));

                    adminDAO.addLevel(levelNumber, coolcoins);
                    QSHelper.redirect(exchange, "/admin/level");
                }
                if (uri.getPath().equals("admin/mentor")) {
                    Map <String, String> inputs = null;
                    try {
                        inputs = QSHelper.getPostStringData(exchange);
                    } catch (UnsupportedEncodingException e) {
                        System.err.println(e.getMessage());
                    }

//                    Integer levelNumber = Integer.valueOf(inputs.get("level"));
//                    Integer coolcoins = Integer.valueOf(inputs.get("coolcoins"));
                    Mentor mentor = new Mentor();

                    adminDAO.addMentor(mentor);
                    QSHelper.redirect(exchange, "/admin/mentor");
                }

                if (uri.getPath().equals("/admin/class")) {
                    Map <String, String> inputs = null;
                    try {
                        inputs = QSHelper.getPostStringData(exchange);
                    } catch (UnsupportedEncodingException e) {
                        System.err.println(e.getMessage());
                    }

                    String name = inputs.get("name");

                    adminDAO.addClass(new ClassRoom(name));

                    QSHelper.redirect(exchange, "/admin/class");
                }
            } else {
                exchange.sendResponseHeaders(401, -1);
            }
        }
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private boolean userIsAdmin(LoginData data) {
        return data.getRoleId() == 1;
    }

    private String renderView(String templatePath, String key, String value) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(templatePath);
        JtwigModel model = JtwigModel.newModel();
        model.with(key, value);
        return template.render(model);
    }

    private <E> String renderView(String templatePath, String key, List <E> value) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(templatePath);
        JtwigModel model = JtwigModel.newModel();
        model.with(key, value);
        return template.render(model);
    }
}
