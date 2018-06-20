package com.codecool.Handlers;

import com.codecool.DAO.MentorDAO;
import com.codecool.DAO.StudentDAO;
import com.codecool.Model.ClassRoom;
import com.codecool.Model.Mentor;
import com.codecool.Model.Student;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MentorHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String response = "";
        String method = httpExchange.getRequestMethod();
        String requestURI = httpExchange.getRequestURI().toString();
        String decodedURI = URLDecoder.decode(requestURI, "UTF-8");

        MentorDAO mentorDAO = new MentorDAO();
        Mentor mentor = mentorDAO.getMentorById(21);
        List<ClassRoom> classRooms = mentorDAO.getMentorsClasses(mentor);

        final String DASHBOARD = "/dashboard";
        final String CLASSES = "/dashboard/classes";

        String lastDirectory = getLastFromURI(decodedURI, CLASSES + "/");
        String ADD = "/dashboard/classes/" + lastDirectory;

        // Send a form if it wasn't submitted yet.
        if(method.equals("GET")){

            if(decodedURI.equals(ADD)) {
                response = getAddLayout(lastDirectory);
            }
            else {
                switch (requestURI) {
                    case DASHBOARD:
                        response = getDashboardLayout();
                        break;
                    case CLASSES:
                        response = getClassesLayout(classRooms);
                        break;
                }
            }

        }

        // If the form was submitted, retrieve it's content.
//        if(method.equals("POST")){
//            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
//            BufferedReader br = new BufferedReader(isr);
//            String formData = br.readLine();
//
//            System.out.println(formData);
//            Map inputs = parseFormData(formData);
//
//            Post post = new Post(inputs.get("Message").toString(), inputs.get("Name").toString(),
//                    inputs.get("Email").toString(), null);
//
//            System.out.println(post.email);
//            System.out.println(post.name);
//            System.out.println(post.message);
//
//            postDao.insertPost(post);
//
//        }

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String getAddLayout(String lastDirectory) {
        String response;
        StudentDAO studentDAO = new StudentDAO();
        List<Student> studentList = studentDAO.getStudentsByClassName(lastDirectory);

        JtwigTemplate template = JtwigTemplate.classpathTemplate("static/templates/layouts/addlayout.twig");
        JtwigModel model = JtwigModel.newModel();

        model.with("title", "Add Student");
        model.with("addButtonName", "Add Student");
        model.with("list", studentList);
        model.with("functionName", "createForm()");
        model.with("jsPath", "/static/js/studentform.js");


        response = template.render(model);

        return response;
    }

    private String getClassesLayout(List<ClassRoom> classRooms) {
        String response;

        JtwigTemplate template = JtwigTemplate.classpathTemplate("static/templates/pages/studentManagement.twig");
        JtwigModel model = JtwigModel.newModel();

        model.with("classes", classRooms);

        response = template.render(model);

        return response;
    }

    private String getDashboardLayout() {
        String response;

        JtwigTemplate template = JtwigTemplate.classpathTemplate("static/templates/pages/mentor.twig");
        JtwigModel model = JtwigModel.newModel();
        response = template.render(model);

        return response;
    }

    private String getLastFromURI(String toParse, String splitter) {
        if(toParse.contains("/dashboard/classes/")) {
            try {
                String encodedURL = URLDecoder.decode(toParse, "UTF-8");
            } catch(UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            String[] pathArray = toParse.split(splitter);

            return pathArray[1];
        }
        return null;
    }

    /**
     * Form data is sent as a urlencoded string. Thus we have to parse this string to get data that we want.
     * See: https://en.wikipedia.org/wiki/POST_(HTTP)
     */
    private static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            // We have to decode the value because it's urlencoded. see: https://en.wikipedia.org/wiki/POST_(HTTP)#Use_for_submitting_web_forms
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }
}
