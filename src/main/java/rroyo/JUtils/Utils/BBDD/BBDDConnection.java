package rroyo.JUtils.Utils.BBDD;

import java.sql.*;

public class BBDDConnection {

    private final Connection conexion;

    public BBDDConnection(String url, String user, String pass) {
        try {
            conexion = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet executeQuery(String sql, Object... params) {
        try {
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            return pstmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void executeUpdate(String sql, Object... params) {
        try {
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            conexion.close();
        } catch (SQLException e) {}
    }

    public Connection getConexion() {
        return conexion;
    }

}
