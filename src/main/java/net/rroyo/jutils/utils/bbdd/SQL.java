package net.rroyo.jutils.utils.bbdd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public final class SQL implements AutoCloseable{

    private final String sql;
    private final List<Object> values;
    private PreparedStatement ps;

    public SQL(String sql, List<Object> values) {
        this.sql = sql;
        this.values = values;
    }

    public PreparedStatement getPreparedStatement() throws SQLException {
        if (ps == null) {
            ps = ConnectionManager.getConnection().prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
        }
        if (values != null && !values.isEmpty())
            for (int i = 0; i < values.size(); i++)
                ps.setObject(i + 1, values.get(i));
        return ps;
    }

    public boolean execute() throws SQLException {
        PreparedStatement ps = getPreparedStatement();
        return ps.execute();
    }
    public ResultSet executeQuery() throws SQLException {
        PreparedStatement ps = getPreparedStatement();
        return ps.executeQuery();
    }
    public int executeUpdate() throws SQLException {
        PreparedStatement ps = getPreparedStatement();
        return ps.executeUpdate();
    }

    @Override
    public String toString() {
        return sql;
    }

    @Override
    public void close() throws SQLException {
        if (ps != null) {
            ps.close();
        }
    }
}
