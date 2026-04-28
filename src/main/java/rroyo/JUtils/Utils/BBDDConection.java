package rroyo.JUtils.Utils;

import java.sql.*;

public class BBDDConection {

    private final Connection conexion;

    public BBDDConection(String url, String user, String pass) {
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
