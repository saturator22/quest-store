package com.codecool;

import com.codecool.Handlers.*;
import com.sun.net.httpserver.HttpServer;
import com.codecool.Handlers.StaticHandler;

import java.net.InetSocketAddress;

public class App {
    public static void main(String[] args) throws Exception {
        // create a server on port 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // set routes
        server.createContext("/", new DashboardHandler());
        server.createContext("/dashboard", new DashboardHandler());
        server.createContext("/store", new StoreHandler());
        server.createContext("/login", new AuthHandler());
        server.createContext("/admin", new AdminHandler());
        server.createContext("/mentor", new MentorHandler());
        server.createContext("/static", new StaticHandler());
        server.createContext("/questStore", new StudentHandler());
        server.setExecutor(null); // creates a default executor
        // start listening
        server.start();

    }
}

