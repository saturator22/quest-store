package com.codecool.Handlers;

import com.codecool.DAO.LoginDAO;
import com.codecool.Helper.PasswordHash;
import com.codecool.Model.LoginData;
import com.codecool.Model.Session;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.HttpCookie;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class AuthHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();
            String response = "";
            Session session = Session.getInstance();
            HttpCookie cookie = session.setCookieInHandler(exchange);

            //TEST/DEBUG ONLY
//            System.out.println("==== SESSIONS ====");
//            for (Map.Entry<String, Integer> entry : session.getSessions().entrySet())
//            {
//                System.out.println(entry.getKey() + "/" + entry.getValue());
//            }
//            System.out.println("==== SESSIONS ====");

            if (method.equals("GET") && !session.isValid(cookie.getValue())) {
                response = renderView("static/templates/pages/login.twig", "cookie", cookie.getValue().toLowerCase());
                exchange.sendResponseHeaders(200, response.length());

            } else if (method.equals("GET") && session.isValid(cookie.getValue())) {

                String hostPort = exchange.getRequestHeaders().get("HOST").get(0);
                String whereTo = "/dashboard";
                exchange.getResponseHeaders().set("Location", "http://" + hostPort + whereTo);
                exchange.sendResponseHeaders(301, -1);
            }

            if (method.equals("POST") && !session.isValid(cookie.getValue())) {
                Map<String, String> inputs = null;
                try {
                    inputs = getPostStringData(exchange);
                } catch (UnsupportedEncodingException e) {
                    System.err.println(e.getMessage());
                }

                //USER VALIDATION
                String enteredPassword = PasswordHash.encrypt(inputs.get("password"));
                String enteredEmail = inputs.get("email");
                LoginDAO loginDAO = new LoginDAO();
                LoginData user = null;
                user = loginDAO.getLoginData(enteredEmail);

                if (user != null && user.getPassword().equals(enteredPassword)) {

                    session.add(cookie.getValue(), user.getUserId()); //Add new server-side session
                    String hostPort = exchange.getRequestHeaders().get("HOST").get(0);
                    String whereTo = "/dashboard";
                    exchange.getResponseHeaders().set("Location", "http://" + hostPort + whereTo);
                    exchange.sendResponseHeaders(301, -1);
                }
                String hostPort = exchange.getRequestHeaders().get("HOST").get(0);
                String whereTo = "/login";
                exchange.getResponseHeaders().set("Location", "http://" + hostPort + whereTo);
                exchange.sendResponseHeaders(301, -1);
            }

            if (method.equals("POST") && session.isValid(cookie.getValue())) {
                session.remove(cookie.getValue());
                String hostPort = exchange.getRequestHeaders().get("HOST").get(0);
                String whereTo = "/login";
                exchange.getResponseHeaders().set("Location", "http://" + hostPort + whereTo);
                exchange.sendResponseHeaders(301, -1);
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

        private Map <String, String> getPostStringData(HttpExchange exchange) throws IOException {
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
                String value = URLDecoder.decode(keyValue[1], "UTF-8");
                map.put(keyValue[0], value);
            }
            return map;
        }

}
