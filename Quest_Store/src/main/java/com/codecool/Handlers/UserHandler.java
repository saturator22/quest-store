package com.codecool.Handlers;

import com.codecool.DAO.LoginDAO;
import com.codecool.DAO.StudentDAO;
import com.codecool.Helper.PasswordHash;
import com.codecool.Model.LoginData;
import com.codecool.Model.Session;
import com.codecool.Model.Student;
import com.codecool.Model.User;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.HttpCookie;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.HashMap;
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
                String hostPort = exchange.getRequestHeaders().get("HOST").get(0);
                String whereTo = "/login";
                exchange.getResponseHeaders().set("Location", "http://" + hostPort + whereTo);
                exchange.sendResponseHeaders(301, -1);
            }
        }

        if (method.equals("GET") && !session.isValid(cookie.getValue())) {
            String hostPort = exchange.getRequestHeaders().get("HOST").get(0);
            String whereTo = "/login";
            exchange.getResponseHeaders().set("Location", "http://" + hostPort + whereTo);
            exchange.sendResponseHeaders(301, -1);
        }

        if (method.equals("POST") && session.isValid(cookie.getValue())) {
            Map<String, String> inputs = null;
            try {
                inputs = getPostStringData(exchange);
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
            String hostPort = exchange.getRequestHeaders().get("HOST").get(0);
            String whereTo = "/user";
            exchange.getResponseHeaders().set("Location", "http://" + hostPort + whereTo);
            exchange.sendResponseHeaders(301, -1);
        }

        if (method.equals("PUT") && !session.isValid(cookie.getValue())) {
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

    private Map<String, String> getPostStringData(HttpExchange exchange) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(exchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(inputStreamReader);
        String formData = br.readLine();
        return parseFormData(formData);
    }

    private static Map <String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if(!keyValue[1].isEmpty()){
                String value = URLDecoder.decode(keyValue[1], "UTF-8");
                map.put(keyValue[0], value);
            }
        }
        return map;
    }
}
