package net.rroyo.jutils.utils.bbdd.generator;

import net.rroyo.jutils.utils.bbdd.data.Field;

import java.sql.SQLException;

public class OrderBy extends SQLSyntax{

    protected OrderBy(SQLBuilder sqlBuilder, Field... field) throws SQLException {
        super(sqlBuilder);

        StringBuilder f = new StringBuilder();
        if (field.length == 0) throw new SQLException("ORDER BY requires a field");
        else if (field.length == 1) f.append(field[0]);
        else for (int i = 0; i < field.length; i++) {
                f.append(field[i]);
                if (i != field.length-1) f.append(", ");
        }
        sqlBuilder.append(syntax(), f.toString());
    }

    @Override
    protected String syntax() {
        return "ORDER BY";
    }
}
