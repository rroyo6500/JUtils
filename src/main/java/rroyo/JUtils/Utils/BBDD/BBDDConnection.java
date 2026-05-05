package rroyo.JUtils.Utils.BBDD;

import rroyo.JUtils.Utils.Validator;

import java.sql.*;

/**
 * Utility class to manage database connection and SQL statement execution
 * in a simplified way using JDBC.
 * <p>
 * Provides methods for executing read queries (SELECT) and write operations
 * (INSERT, UPDATE, DELETE) through prepared statements.
 * </p>
 *
 * @author rroyo
 */
public final class BBDDConnection implements AutoCloseable {

    private final Connection conexion;

    /**
     * Constructor initializing the database connection.
     *
     * @param url  JDBC connection URL (e.g., jdbc:mysql://localhost:3306/db).
     * @param user Username for authentication.
     * @param pass Password for authentication.
     * @throws SQLException If an error occurs establishing the connection.
     */
    public BBDDConnection(String url, String user, String pass) throws SQLException {
        Validator.notBlank(url, "URL cannot be empty");
        Validator.notBlank(user, "Database user cannot be empty");
        conexion = DriverManager.getConnection(url, user, pass);
    }

    /**
     * Executes a read SQL query (SELECT) using variable parameters.
     *
     * @param sql    SQL statement with placeholders (?).
     * @param params Objects to be assigned to statement placeholders.
     * @return A {@link ResultSet} with query results.
     * @throws SQLException If an error occurs during SQL execution.
     */
    public ResultSet executeQuery(String sql, Object... params) throws SQLException {
        Validator.notBlank(sql, "SQL cannot be blank");
        PreparedStatement pstmt = conexion.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            pstmt.setObject(i + 1, params[i]);
        }
        return pstmt.executeQuery();
    }

    /**
     * Executes an update SQL statement (INSERT, UPDATE, or DELETE).
     * <p>
     * This method automatically handles closing the {@link PreparedStatement} after execution.
     * </p>
     *
     * @param sql    SQL statement with placeholders (?).
     * @param params Objects to be assigned to statement placeholders.
     * @throws SQLException If an error occurs during SQL execution.
     */
    public void executeUpdate(String sql, Object... params) throws SQLException {
        Validator.notBlank(sql, "SQL cannot be blank");
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            pstmt.executeUpdate();
        }
    }

    /**
     * Closes the current database connection.
     *
     * @throws SQLException If an error occurs closing the connection.
     */
    @Override
    public void close() throws SQLException {
        if (conexion != null && !conexion.isClosed()) {
            conexion.close();
        }
    }

    /**
     * Gets the underlying JDBC connection object.
     *
     * @return The {@link Connection} object managed by this class.
     */
    public Connection getConexion() {
        return conexion;
    }
}