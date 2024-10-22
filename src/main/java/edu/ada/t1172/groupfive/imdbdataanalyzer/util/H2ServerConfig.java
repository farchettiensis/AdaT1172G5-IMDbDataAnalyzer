package edu.ada.t1172.groupfive.imdbdataanalyzer.util;

import org.h2.tools.Server;

import java.sql.SQLException;

public class H2ServerConfig {

    public static void start() {
        try {
            Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8080").start();
            System.out.println("H2 started at http://localhost:8080");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
