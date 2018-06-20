package com.codecool.Handlers;

import com.codecool.DAO.AdminDAO;
import com.codecool.DAO.DAO;
import com.codecool.DAO.LoginDAO;
import com.codecool.Model.LoginData;
import com.codecool.Model.Session;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.util.Map;

public class AdminHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String method = exchange.getRequestMethod();
        String response = "";
        Session session = Session.getInstance();

        for (Map.Entry <String, Integer> entry : session.getSessions().entrySet()) {
            System.out.println(entry.getKey() + "/" + entry.getValue());
        }

        HttpCookie cookie = session.setCookieInHandler(exchange);
        LoginDAO dao = new LoginDAO();
        Integer userId = session.getUserIdBySesssion(cookie);
        LoginData user = null;
        user = dao.getLoginData(userId);
        System.out.println(user);
        if (user != null && method.equals("GET") && session.isValid(cookie.getValue())) { // user is an admin
            if(userIsAdmin(user)) {
                response = renderView("static/templates/pages/admin.twig", "cookie", cookie.getValue().toLowerCase());
                exchange.sendResponseHeaders(200, response.length());
            } else {
                String hostPort = exchange.getRequestHeaders().get("HOST").get(0);
                String whereTo = "/dashboard";
                exchange.getResponseHeaders().set("Location", "http://" + hostPort + whereTo);
                exchange.sendResponseHeaders(301, -1);
            }
        }
        if (user == null && method.equals("GET") && !session.isValid(cookie.getValue())) {
            String hostPort = exchange.getRequestHeaders().get("HOST").get(0);
            String whereTo = "/login";
            exchange.getResponseHeaders().set("Location", "http://" + hostPort + whereTo);
            exchange.sendResponseHeaders(301, -1);
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
}
