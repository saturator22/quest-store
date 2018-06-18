package com.codecool.Handlers;

import com.codecool.DAO.ArtifactDAO;
import com.codecool.DAO.StudentDAO;
import com.codecool.Model.Artifact;
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

    private List<Artifact> getAvailableArtifacts() {
        ArtifactDAO aDAO = new ArtifactDAO();
        List<Artifact> availableArtifacts = new ArrayList<Artifact>(aDAO.getAvailableArtifacts());
        return availableArtifacts;
    }


}
