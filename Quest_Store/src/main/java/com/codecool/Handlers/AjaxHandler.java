package com.codecool.Handlers;

import com.codecool.DAO.MentorDAO;
import com.codecool.Model.ClassRoom;
import com.codecool.Model.Mentor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class AjaxHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        String method = httpExchange.getRequestMethod();
//        String requestURI = httpExchange.getRequestURI().toString();

        MentorDAO mentorDAO = new MentorDAO();

        if(method.equals("GET")){
            List<ClassRoom> classRooms = mentorDAO.getClassRooms();

            Gson gsonBuilder = new GsonBuilder().create();
// Convert Java Array into JSON
            String jsonFromJavaArrayList = gsonBuilder.toJson(classRooms);
            response = jsonFromJavaArrayList;
        }

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
