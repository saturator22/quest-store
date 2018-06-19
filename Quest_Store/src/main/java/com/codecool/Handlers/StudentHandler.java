package com.codecool.Handlers;

import com.codecool.DAO.ArtifactDAO;
import com.codecool.DAO.StudentDAO;
import com.codecool.Model.Artifact;
import com.codecool.Model.ShopObject;
import com.codecool.Model.Student;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class StudentHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        System.out.println("Not implemented yet");
    }

    private List<ShopObject> getAvailableArtifacts() {
        ArtifactDAO aDAO = new ArtifactDAO();
        List<ShopObject> availableArtifacts = new ArrayList<ShopObject>(aDAO.getAvailableArtifacts());
        return availableArtifacts;
    }

    private List<Artifact> getOwnedArtifacts(Integer studentId) {
        ArtifactDAO aDAO = new ArtifactDAO();
        List<Artifact> studentInventory = new ArrayList<>(aDAO.getStudentArtifacts(studentId));
        return studentInventory;
    }

    private void sendPersonalizedPage(HttpExchange httpExchange, String sessionId) throws IOException {
        StudentDAO sDAO = new StudentDAO();
        Integer userId = 1;
        Student activeStudent = sDAO.getStudentById(userId);
//        Integer userId = sessionsUsers.get(sessionId);
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/questStore.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("user-name", activeStudent.getFirstName() + " " + activeStudent.getLastName());
        model.with("user-ballance", activeStudent.getBalance());
        model.with("user-level", activeStudent.getLevelId());
        model.with("owned-artifacts", getOwnedArtifacts(userId));
        model.with("available-artifacts", getAvailableArtifacts());
        String response = template.render(model);
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

}
