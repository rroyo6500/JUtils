package net.rroyo.jutils.utils.bbdd.generator;

import net.rroyo.jutils.utils.bbdd.Syntax;
import net.rroyo.jutils.utils.bbdd.data.value.Value;
import net.rroyo.jutils.utils.bbdd.data.field.Field;
import net.rroyo.jutils.utils.bbdd.data.table.Table;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class InsertInto extends SQLSyntax {

    private final Table table;
    private int fieldNum;

    protected InsertInto(SQLBuilder sqlBuilder, Table table, Field... field) {
        super(sqlBuilder);

        this.table = table;

        sqlBuilder.append(table.toString());
        if (field.length > 0) {
            Field[] f = new Field[table.getColNum()];
            System.arraycopy(field, 0, f, 0, table.getColNum());

            fieldNum = f.length;
            sqlBuilder.append("(");
            String fieldsString = Arrays.stream(f)
                    .map(Field::toString)
                    .collect(Collectors.joining(", "));
            sqlBuilder.append(fieldsString);
            sqlBuilder.append(")");
        }

    }

    @Override
    protected String syntax() {
        return Syntax.INSERT_INTO;
    }

    public Values values(Object... values) throws SQLException {
        Value[] v = Value.toArray(values);
        return new Values(sqlBuilder, fieldNum, table, v);
    }

    public Values values(Value... values) throws SQLException {
        return new Values(sqlBuilder, fieldNum, table, values);
    }

}
