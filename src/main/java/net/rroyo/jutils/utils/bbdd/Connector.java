package net.rroyo.jutils.utils.bbdd;

import java.sql.Connection;

public class Connector {

    private static String url = "jdbc:mysql://localhost/";
    private static String database = "";

    private static String user = "";
    private static String password = "";

    public static final Connection con;

    protected Connector() {
        con = new Connection
    }
}
