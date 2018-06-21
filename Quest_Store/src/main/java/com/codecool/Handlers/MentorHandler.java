package com.codecool.Handlers;

import com.codecool.DAO.MentorDAO;
import com.codecool.DAO.StudentDAO;
import com.codecool.Helper.QSHelper;
import com.codecool.Model.ClassRoom;
import com.codecool.Model.Mentor;
import com.codecool.Model.Session;
import com.codecool.Model.Student;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.HttpCookie;
import java.net.URLDecoder;
import java.util.ArrayList;
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

        Session session = Session.getInstance();
        HttpCookie cookie = session.setCookieInHandler(httpExchange);
        Integer mentorId = session.getUserIdBySesssion(cookie);
        Mentor mentor;

        MentorDAO mentorDAO = new MentorDAO();

        final String DASHBOARD = "/mentor";
        final String CLASSES = "/mentor/classes";

        String lastDirectory = getLastFromURI(decodedURI, CLASSES + "/");
        String ADD = "/mentor/classes/" + lastDirectory;

        // Send a form if it wasn't submitted yet.

        try {
            mentor = mentorDAO.getMentorById(session.getUserIdBySesssion(cookie));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            mentor = null;
        }
        List<ClassRoom> classRooms = mentorDAO.getMentorsClasses(mentor);

        if(method.equals("GET") && session.isValid(cookie.getValue())){
            if(mentor != null) {
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
            } else {
                QSHelper.redirect(httpExchange, "/login");
            }
        }

        if (method.equals("GET") && !session.isValid(cookie.getValue())) {
            QSHelper.redirect(httpExchange, "/login");
        }
        // If the form was submitted, retrieve it's content.
        if(method.equals("POST") && session.isValid(cookie.getValue())){

            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();

            Map inputs = parseFormData(formData);

            StudentDAO studentDAO = new StudentDAO();

            Student student = new Student();

            student.setFirstName(inputs.get("Name").toString());
            student.setRoleId(3);
            student.setLevelId(1);
            student.setLastName(inputs.get("Last+Name").toString());
            student.setLogin(inputs.get("Login").toString());
            student.setEmail(inputs.get("Email").toString());
            student.setPassword(inputs.get("Password").toString());
            student.setClassId(getClassId(classRooms, inputs.get("Class").toString()));

            System.out.println(student);

            studentDAO.insertStudentData(student);

            response = getAddLayout(lastDirectory);
        }


        if (method.equals("POST") && !session.isValid(cookie.getValue())) {
            httpExchange.sendResponseHeaders(200, response.length());
            QSHelper.redirect(httpExchange, "/login");
        }
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public Integer getClassId(List<ClassRoom> classesList, String className){
        for(ClassRoom classRoom: classesList) {
            if(classRoom.getName().equals(className)) {
                return classRoom.getClassId();
            }
        }
        return null;
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
        if(toParse.contains("/mentor/classes/")) {
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
