package net.rroyo.jutils.utils.bbdd;

import net.rroyo.jutils.utils.bbdd.data.value.Value;
import net.rroyo.jutils.utils.logging.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class SQL implements AutoCloseable{

    private final String sql;
    private final List<Value> values;
    private Statement s;

    private final Class<? extends Statement> statementClassType;

    public SQL(String sql, List<Value> values, Class<? extends Statement> statementClassType) {
        this.sql = sql;
        this.values = values;
        this.statementClassType = statementClassType;
    }

    public Statement getStatement() throws SQLException {
        if (s == null) {
            if (statementClassType == PreparedStatement.class) {
                s = ConnectionManager.getConnection().prepareStatement(sql,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY
                );
            } else if (statementClassType == CallableStatement.class) {
                try {
                    s = ConnectionManager.getConnection().prepareCall(sql,
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    );
                } catch (SQLException e) {
                    Log.error(e.getMessage());
                    throw e;
                }
            }
        }
        if (values != null && !values.isEmpty())
            for (int i = 0; i < values.size(); i++) {
                if (statementClassType == PreparedStatement.class) {
                    ((PreparedStatement) s).setObject(i + 1, values.get(i).getValue());
                } else if (statementClassType == CallableStatement.class) {
                    switch (values.get(i).getValueType()) {
                        case IN -> ((CallableStatement) s).setObject(i + 1, values.get(i).getValue());
                        case OUT -> ((CallableStatement) s).registerOutParameter(i + 1,
                                values.get(i).getType());
                        case INOUT -> {
                            ((CallableStatement) s).setObject(i + 1, values.get(i).getValue());
                            ((CallableStatement) s).registerOutParameter(i + 1,
                                    values.get(i).getType());
                        }
                    }
                }
            }
        return s;
    }

    public boolean execute() throws SQLException {
        try {
            CallableStatement cs = (CallableStatement) getStatement();
            return cs.execute();
        } catch (ClassCastException e) {
            PreparedStatement ps = (PreparedStatement) getStatement();
            return ps.execute();
        } catch (SQLException e) {
            Log.error(e.getMessage());
            return false;
        }
    }
    public ResultSet executeQuery() throws SQLException {
        PreparedStatement ps = (PreparedStatement) getStatement();
        return ps.executeQuery();
    }
    public int executeUpdate() throws SQLException {
        PreparedStatement ps = (PreparedStatement) getStatement();
        return ps.executeUpdate();
    }

    public Object getOutValue(int paramIndex) {
        if (statementClassType == CallableStatement.class) {
            try {
                return ((CallableStatement) s).getObject(paramIndex);
            } catch (SQLException e) {
                Log.error(e.getMessage());
                return null;
            }
        } else {
            Log.warn("Requires a CallableStatement to get OUT values");
            return null;
        }
    }

    public List<Integer> getOutParameterIndexes() {
        if (statementClassType == CallableStatement.class) {
            List<Integer> indexes = new ArrayList<>();
            for (int i = 0; i < values.size(); i++) {
                switch (values.get(i).getValueType()) {
                    case OUT, INOUT -> indexes.add(i+1);
                }
            }
            return indexes;
        } else {
            Log.warn("Requires a CallableStatement to get OUT indexes");
            return List.of();
        }
    }

    @Override
    public String toString() {
        return sql;
    }

    @Override
    public void close() throws SQLException {
        if (s != null) {
            s.close();
        }
    }
}
