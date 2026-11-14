package net.rroyo.jutils.utils.bbdd.generator;

import net.rroyo.jutils.utils.bbdd.Syntax;
import net.rroyo.jutils.utils.bbdd.data.table.Table;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Values extends SQLSyntax {

    private final Table table;
    private int cols;

    protected Values(SQLBuilder sqlBuilder, int fieldNum, Table table, Object... values) throws SQLException {
        super(sqlBuilder);

        this.cols = fieldNum;
        this.table = table;

        sqlBuilder.append(syntax());
        init(values);
    }

    private void init(Object... values) throws SQLException {
        if (cols != 0) {
            sqlBuilder.append("(");
            for (int i = 1; i <= cols; i++) {
                sqlBuilder.append("?");
                if (i != cols) sqlBuilder.append(",");
            }
            sqlBuilder.append(")");
        } else {
            cols = SQLGenerator.sql()
                    .select()
                    .from(table)
                    .build().executeQuery()
                    .getMetaData().getColumnCount();

            sqlBuilder.append("(");
            for (int i = 1; i <= cols; i++) {
                sqlBuilder.append("?");
                if (i != cols) sqlBuilder.append(",");
            }
            sqlBuilder.append(")");
        }

        List<Object> v = new ArrayList<>();
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
        sqlBuilder.append(",");
        init(values);
        return this;
    }

}
