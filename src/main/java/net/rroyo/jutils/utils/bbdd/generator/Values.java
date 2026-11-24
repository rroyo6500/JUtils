package net.rroyo.jutils.utils.bbdd.generator;

import net.rroyo.jutils.utils.bbdd.Syntax;
import net.rroyo.jutils.utils.bbdd.data.value.Value;
import net.rroyo.jutils.utils.bbdd.data.table.Table;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Values extends SQLSyntax {

    private final Table table;
    private int cols;

    protected Values(SQLBuilder sqlBuilder, int colNum, Table table, Value... values) throws SQLException {
        super(sqlBuilder);

        this.cols = colNum;
        this.table = table;

        init(values);
    }

    private void init(Value... values) throws SQLException {
        if (cols != 0) {
            sqlBuilder.append("(");
            for (int i = 1; i <= cols; i++) {
                sqlBuilder.append("?");
                if (i != cols) sqlBuilder.append(",");
            }
            sqlBuilder.append(")");
        } else {
            cols = table.getColNum();

            sqlBuilder.append("(");
            for (int i = 1; i <= cols; i++) {
                sqlBuilder.append("?");
                if (i != cols) sqlBuilder.append(",");
            }
            sqlBuilder.append(")");
        }

        List<Value> v = new ArrayList<>();
        for (int i = 0; i < cols && i < values.length; i++) {
            v.add(values[i]);
        }
        sqlBuilder.addValues(v);
    }

    @Override
    protected String syntax() {
        return Syntax.VALUES;
    }

    public Values values(Object... values) throws SQLException {
        Value[] v = Value.toArray(values);
        return values(v);
    }

    public Values values(Value... values) throws SQLException {
        sqlBuilder.append(",");
        init(values);
        return this;
    }

}
