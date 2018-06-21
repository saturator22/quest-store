package com.codecool.Handlers;

import com.codecool.DAO.LoginDAO;
import com.codecool.Helper.PasswordHash;
import com.codecool.Helper.QSHelper;
import com.codecool.Model.LoginData;
import com.codecool.Model.Session;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.HttpCookie;
import java.util.Map;

public class AuthHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();
            String response = "";
            Session session = Session.getInstance();
            HttpCookie cookie = session.setCookieInHandler(exchange);

            if (method.equals("GET") && !session.isValid(cookie.getValue())) {
                response = renderView("static/templates/pages/login.twig", "cookie", cookie.getValue().toLowerCase());
                exchange.sendResponseHeaders(200, response.length());

            } else if (method.equals("GET") && session.isValid(cookie.getValue())) {
                QSHelper.redirect(exchange, "/dashboard");
            }

            if (method.equals("POST") && !session.isValid(cookie.getValue())) {
                Map<String, String> inputs = null;
                try {
                    inputs = QSHelper.getPostStringData(exchange);
                } catch (UnsupportedEncodingException e) {
                    System.err.println(e.getMessage());
                }

                //USER VALIDATION
                String enteredPassword = PasswordHash.encrypt(inputs.get("password"));
                String enteredEmail = inputs.get("email");
                LoginDAO loginDAO = new LoginDAO();
                LoginData user = null;
                user = loginDAO.getLoginData(enteredEmail);
                System.out.println(user);

                if (user != null && user.getPassword().equals(enteredPassword)) {

                    session.add(cookie.getValue(), user.getUserId()); //Add new server-side session
                    QSHelper.redirect(exchange, "/dashboard");
                }
                QSHelper.redirect(exchange, "/login");
            }

            if (method.equals("POST") && session.isValid(cookie.getValue())) {
                session.remove(cookie.getValue());
                QSHelper.redirect(exchange, "/login");
            }
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        private String renderView(String templatePath, String key, String value) {
            JtwigTemplate template = JtwigTemplate.classpathTemplate(templatePath);
            JtwigModel model = JtwigModel.newModel();
            model.with(key, value);
            return template.render(model);
        }
}
