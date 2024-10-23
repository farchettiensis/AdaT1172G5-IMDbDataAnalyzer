package edu.ada.t1172.groupfive.imdbdataanalyzer.database;

import org.h2.tools.Server;

import java.sql.SQLException;

public class H2ServerConfig {

    public static void start(String webPort) {
        try {
            Server.createWebServer("-web", "-webAllowOthers", "-webPort", webPort).start();
            System.out.printf("H2 started at http://localhost:%s%n", webPort);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
