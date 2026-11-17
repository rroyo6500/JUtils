package net.rroyo.jutils.utils.bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManager {

    private static String url = "jdbc:mysql://localhost:3306/";
    private static String database = "";

    private static String user = "";
    private static String password = "";

    private static Connection con;

    private ConnectionManager() {}

    public static void connect() throws SQLException {
        con = DriverManager.getConnection(url + database, user, password);
    }

    public static void close() throws SQLException {
        if (con != null && !con.isClosed()) {
            con.close();
            con = null;
        }
    }

    public static Connection getConnection() throws SQLException {
        if (con == null || con.isClosed()) {
            connect();
        }
        return con;
    }

    public static String getUrl() {
        return url;
    }

    public static String getDatabase() {
        return database;
    }

    public static String getUser() {
        return user;
    }

    public static String getPassword() {
        return password;
    }

    public static void setUrl(String url) {
        ConnectionManager.url = url;
    }

    public static void setDatabase(String database) {
        ConnectionManager.database = database;
    }

    public static void setUser(String user) {
        ConnectionManager.user = user;
    }

    public static void setPassword(String password) {
        ConnectionManager.password = password;
    }
}
