package com.codecool.Handlers;

import com.codecool.DAO.ArtifactDAO;
import com.codecool.DAO.StudentDAO;
import com.codecool.Helper.QSHelper;
import com.codecool.Model.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;

public class StudentHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        Session session = Session.getInstance();
        HttpCookie cookie = session.setCookieInHandler(httpExchange);


        StudentDAO dao = new StudentDAO();
        Integer userId = session.getUserIdBySesssion(cookie);
        Student activeAccount;

        try {
            activeAccount = dao.getStudentById(userId);

        } catch (Exception e) {

            System.out.println(e.getMessage());
            activeAccount = null;
        }

        final String GET_METHOD = "GET";
        final String POST_METHOD = "POST";

        String method = httpExchange.getRequestMethod();

        if (method.equals("GET") && session.isValid(cookie.getValue())) {
            if (activeAccount != null) {
                sendPersonalizedPage(httpExchange, activeAccount);
            } else if (activeAccount == null) {
                QSHelper.redirect(httpExchange, "/login");
            }
        }
        
        if (method.equals(POST_METHOD)) {
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String requestData = br.readLine();
            String[] request = requestData.split("=");

            if (request[0].equals("buy")) {
                Integer productId = Integer.valueOf(request[1]);
                buyArtifact(productId, activeAccount);
                sendPersonalizedPage(httpExchange, activeAccount);
            }

            if (request[0].equals("use")) {
                Integer productId = Integer.valueOf(request[1]);
                useArtifact(productId);
                sendPersonalizedPage(httpExchange, activeAccount);
            }
        }
    }

    private boolean useArtifact(Integer uniqueId) {
        ArtifactDAO artifactDAO = new ArtifactDAO();
        Boolean result = artifactDAO.useArtifact(uniqueId);
        return result;
    }

    private boolean buyArtifact(Integer productId, Student activeAccount) {
        Boolean isSuccesfull = null;
        ArtifactDAO artifactDAO = new ArtifactDAO();
        StudentDAO studentDAO = new StudentDAO();
        Artifact chosenProduct = artifactDAO.getArtifactById(productId);
        Integer price = chosenProduct.getPrice();
        Integer balance = activeAccount.getBalance();

        if (balance >= price) {
            artifactDAO.addArtifactToStudent(chosenProduct, activeAccount.getUserId());
            balance -= price;
            activeAccount.setBalance(balance);
            studentDAO.updateBalance(activeAccount);

            isSuccesfull = true;
            System.out.println("Shoping succesfull, item has been added!");

        } else {
            System.out.println("Not enough £££. Complete more quests.");
            isSuccesfull = false;
        }

        return isSuccesfull;
    }

    private Student getActiveAccount(Integer userId) {
        StudentDAO sdao = new StudentDAO();
        return sdao.getStudentById(userId);
    }

    private List<ShopObject> getAvailableArtifacts() {
        ArtifactDAO aDAO = new ArtifactDAO();
        List<ShopObject> availableArtifacts = new ArrayList<>(aDAO.getAvailableArtifacts());
        return availableArtifacts;
    }

    private List<Artifact> getOwnedArtifacts(Integer studentId) {
        ArtifactDAO aDAO = new ArtifactDAO();
        List<Artifact> studentInventory = aDAO.getStudentArtifacts(studentId);
        return studentInventory;
    }

    private void sendPersonalizedPage(HttpExchange httpExchange, Student activeStudent) throws IOException {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("static/templates/pages/questStore.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("username", activeStudent.getFirstName() + " " + activeStudent.getLastName());
        model.with("userbalance", activeStudent.getBalance());
        model.with("userlevel", activeStudent.getLevelId());
        model.with("ownedcards", getOwnedArtifacts(activeStudent.getUserId()));
        model.with("availablecards", getAvailableArtifacts());
        String response = template.render(model);
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

}
