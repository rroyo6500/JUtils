package net.rroyo.jutils.utils.bbdd;

import net.rroyo.jutils.enums.bbdd.SQLInstruction;
import net.rroyo.jutils.utils.logging.Log;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SQL implements AutoCloseable{

    private final String sql;
    private final SQLInstruction instruction;
    private final List<Object> values;
    private PreparedStatement ps;

    public SQL(String sql, SQLInstruction instruction, List<Object> values) {
        this.sql = sql;
        this.instruction = instruction;
        this.values = values;
        Log.info("Generated SQL: " + sql);
    }

    public PreparedStatement getPreparedStatement() throws SQLException {
        if (ps == null) {
            ps = ConnectionManager.getConnection().prepareStatement(sql);
        }
        if (values != null && !values.isEmpty())
            for (int i = 0; i < values.size(); i++)
                ps.setObject(i + 1, values.get(i));
        return ps;
    }

    public Object execute() throws SQLException {
        PreparedStatement ps = getPreparedStatement();
        return switch (instruction) {
            case SELECT -> ps.executeQuery();
            case INSERT_INTO, CREATE_TABLE, DROP_TABLE, DELETE, UPDATE
                    -> ps.executeUpdate();
        };
    }

    @Override
    public String toString() {
        return sql;
    }

    @Override
    public void close() throws Exception {
        if (ps != null) {
            ps.close();
        }
    }
}
