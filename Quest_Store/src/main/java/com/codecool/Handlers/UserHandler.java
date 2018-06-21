package com.codecool.Handlers;

import com.codecool.DAO.LoginDAO;
import com.codecool.DAO.StudentDAO;
import com.codecool.Helper.PasswordHash;
import com.codecool.Helper.QSHelper;
import com.codecool.Model.Session;
import com.codecool.Model.User;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.HttpCookie;
import java.util.Map;

public class UserHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String response = "";
        Session session = Session.getInstance();
        HttpCookie cookie = session.setCookieInHandler(exchange);

        StudentDAO dao = new StudentDAO();
        Integer userId = session.getUserIdBySesssion(cookie);
        User user;

        try {
            user = dao.getStudentById(userId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            user = null;
        }

        if (method.equals("GET") && session.isValid(cookie.getValue())) {
            if (user != null) {
                response = renderView("static/templates/pages/userEdit.twig", "user", user);
                exchange.sendResponseHeaders(200, response.length());
            } else if (user == null) {
                QSHelper.redirect(exchange, "/login");
            }
        }

        if (method.equals("GET") && !session.isValid(cookie.getValue())) {
            QSHelper.redirect(exchange, "/login");
        }

        if (method.equals("POST") && session.isValid(cookie.getValue())) {
            Map <String, String> inputs = null;
            try {
                inputs = QSHelper.getPostStringData(exchange);
            } catch (UnsupportedEncodingException e) {
                System.err.println(e.getMessage());
            }

            String newEmail = inputs.get("email");
            String newFirstName = inputs.get("firstName");
            String newLastName = inputs.get("lastName");
            String oldPassword = PasswordHash.encrypt(inputs.get("oldPassword"));

            // TODO: User should be able to update account details only when she/he enters correct password
//            if(user.getPassword().equals(oldPassword)) {
            user.setFirstName(newFirstName);
            user.setLastName(newLastName);
            user.setEmail(newEmail);
            user.setPassword(oldPassword);
            dao.updatePersonalData(user);
//            }
            QSHelper.redirect(exchange, "/user");
        }
        if (method.equals("POST") && !session.isValid(cookie.getValue())) {
            exchange.sendResponseHeaders(401, -1);
        }
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String renderView(String templatePath, String key, User value) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(templatePath);
        JtwigModel model = JtwigModel.newModel();
        model.with(key, value);
        return template.render(model);
    }
}
